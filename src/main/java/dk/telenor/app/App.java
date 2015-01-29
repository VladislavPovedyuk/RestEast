package dk.telenor.app;


import dk.telenor.rest.JSONService;
import dk.telenor.rest.SecurityInterceptor;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladyslav Povediuk.
 */
public class App extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public App() {
        singletons.add(new JSONService());
        singletons.add(new SecurityInterceptor());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }


}
