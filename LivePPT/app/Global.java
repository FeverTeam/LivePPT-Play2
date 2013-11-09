import com.fever.liveppt.config.CloudSlidesInjectionConfigModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.PageQueryController;
import controllers.PathController;
import controllers.WAMPSampleController;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;
import play.libs.F;
import play.mvc.Http;
import play.mvc.SimpleResult;
import ws.wamplay.controllers.WAMPlayServer;

import static play.mvc.Results.movedPermanently;

public class Global extends GlobalSettings {

    private static final Injector INJECTOR = createInjector();

    private static Injector createInjector() {
        return Guice.createInjector(new CloudSlidesInjectionConfigModule());
    }

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        //开启Gzip压缩
        return new Class[]{GzipFilter.class};
    }

    @Override
    public F.Promise<SimpleResult> onHandlerNotFound(Http.RequestHeader request) {
        //将not found的请求重定向301到首页
        return F.Promise.pure(movedPermanently("/"));
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        //为controller提供Guice依赖注入
        return INJECTOR.getInstance(controllerClass);
    }

    @Override
    public void onStart(Application app) {
        //加入WAMP静态controller
        WAMPlayServer.addController(new WAMPSampleController());
        WAMPlayServer.addController(new PageQueryController());  //页码查询
        WAMPlayServer.addController(new PathController());  //笔迹处理
    }

}