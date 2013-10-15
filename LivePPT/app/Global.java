import com.fever.liveppt.config.CloudSlidesInjectionConfigModule;
import play.GlobalSettings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;

public class Global extends GlobalSettings {

	private static final Injector INJECTOR = createInjector();

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        //开启Gzip压缩
        return new Class[]{GzipFilter.class};
    }

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        //为controller提供Guice依赖注入
		return INJECTOR.getInstance(controllerClass);
	}

	private static Injector createInjector() {
		return Guice.createInjector(new CloudSlidesInjectionConfigModule());
	}

}