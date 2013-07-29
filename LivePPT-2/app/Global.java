import play.GlobalSettings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.liveppt.configs.InjectionConfigModule;

/**
 * Google Guice 配置文件
 * Author 黎伟杰
 */

public class Global extends GlobalSettings {

  private static final Injector INJECTOR = createInjector(); 

  @Override
  public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
    return INJECTOR.getInstance(controllerClass);
  }

  private static Injector createInjector() {
    return Guice.createInjector(new InjectionConfigModule());
  }

}