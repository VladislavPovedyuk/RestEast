package dk.telenor.security;

import dk.telenor.storage.Company;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * @author Vladyslav Povediuk.
 */

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {

    @Context
    HttpServletRequest servletRequest;

    @Override
    public ServerResponse preProcess(HttpRequest httpRequest,
                                     ResourceMethod resourceMethod) throws Failure, WebApplicationException {

        String methodName = resourceMethod.getMethod().getName();
        String token = "";
        ServerResponse response = null;
        try {
            if (methodName.equals("getEmployeeById") || methodName.equals("getAllEmployees") || methodName.equals("registerToken")) {
                response = null;
            } else {
                MultivaluedMap<String, String> map = httpRequest.getUri().getQueryParameters();
                token = map.get("token").get(0);
                Map<String, Long> tokens = Company.getTokens();
                if (tokens.get(token) != null) {
                    response = null;
                } else {
                    response = new ServerResponse("Access denied token invalid", 403, new Headers<Object>());
                }
            }
        } catch (Exception ex) {
            response = new ServerResponse("Exception occured", 404, new Headers<Object>());
        }

        return response;
    }
}
