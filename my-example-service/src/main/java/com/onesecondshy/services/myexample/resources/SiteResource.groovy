package com.onesecondshy.services.myexample.resources

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import javax.ws.rs.GET

import javax.ws.rs.core.Context
import javax.servlet.http.HttpServletRequest

import com.onesecondshy.services.myexample.views.ViewModel

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class SiteResource {
    @GET
    public ViewModel home(@Context HttpServletRequest request) {
        return ViewModel.createHomeView([name: "Johnny Jetson"], request)
    }
}
