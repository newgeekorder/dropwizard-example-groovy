package com.onesecondshy.services.myexample.resources

import com.onesecondshy.services.myexample.core.Person

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

/**
 * @author Adam Jordens (adam@jordens.org)
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class ApiResource {
    @Path("/persons")
    @GET
    public List<Person> list(@Context HttpServletRequest request) {
        return [new Person(id: 1L, name: "Johnny Jetson", email: "johnny@jetson.org", active: true)]
    }
}
