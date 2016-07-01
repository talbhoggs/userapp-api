package com.ibm.ph.amperca.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext res, ContainerResponseContext resp) throws IOException {
       
       System.out.println("Running CorsResponseFilter");
       resp.getHeaders().add("Access-Control-Allow-Origin", "*");
       resp.getHeaders().add("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With");
       resp.getHeaders().add("Access-Control-Allow-Credentials", "true");
       resp.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
