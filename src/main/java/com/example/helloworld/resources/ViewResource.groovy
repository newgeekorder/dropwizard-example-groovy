package com.example.helloworld.resources

import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.core.Context
import javax.servlet.http.HttpServletRequest
import com.example.helloworld.views.ViewModel

@Path("/views")
@Produces(MediaType.TEXT_HTML)
public class ViewResource {
    @GET
    @Path("/example")
    public ViewModel home(@Context HttpServletRequest request) {
        return new ViewModel("example.vm", [:])
    }
}
