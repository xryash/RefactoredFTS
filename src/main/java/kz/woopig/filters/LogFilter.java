package kz.woopig.filters;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Priority(2)
@Provider
public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        System.out.println("-- req headers --");
        log(reqContext.getUriInfo(), reqContext.getHeaders());

    }

    @Override
    public void filter(ContainerRequestContext reqContext,
                       ContainerResponseContext resContext) throws IOException {
        System.out.println("-- res headers --");
        log(reqContext.getUriInfo(), resContext.getHeaders());

    }

    private void log(UriInfo uriInfo, MultivaluedMap<String, ?> headers) {
        System.out.println("Path: " + uriInfo.getPath());
        //for (Header h : )
        //headers.entrySet().forEach(h -> System.out.println(h.getKey() + ": " + h.getValue()));
        for ( Map.Entry<String, ? extends List<?>> h : headers.entrySet())
            System.out.println(h.getKey() + ": " + h.getValue());
    }
}
