import play.GlobalSettings;

import com.fever.liveppt.config.ConfigModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {

    private static final Injector INJECTOR = createInjector(); 

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
      return INJECTOR.getInstance(controllerClass);
    }

    private static Injector createInjector() {
      return Guice.createInjector(new ConfigModule());
    }

}