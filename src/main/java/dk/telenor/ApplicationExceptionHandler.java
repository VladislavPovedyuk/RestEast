package dk.telenor;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Vladyslav Povediuk.
 */

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException>{

    @Override
    public Response toResponse(ApplicationException e) {
        if (e.getClass().getSimpleName().equals("ApplicationException")) {
            return Response.status(404).entity(e.getMessage()).build();
        } else if (e.getClass().getSimpleName().equals("SecurityException")) {
            return Response.status(403).entity(e.getMessage()).build();
        } else {
            return Response.status(501).build();
        }

    }
}
