package com.example.helloworld.resources

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.yammer.dropwizard.logging.Log
import com.example.helloworld.core.Template
import java.util.concurrent.atomic.AtomicLong
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import com.yammer.dropwizard.jersey.caching.CacheControl
import java.util.concurrent.TimeUnit
import com.example.helloworld.core.Saying
import javax.ws.rs.QueryParam
import com.google.common.base.Optional
import javax.ws.rs.POST
import javax.validation.Valid

@Path("/groovy-hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class GroovyHelloWorldResource {
    private static final Log LOG = Log.forClass(GroovyHelloWorldResource.class)

    private final Template template
    private final AtomicLong counter

    public GroovyHelloWorldResource(Template template) {
        this.template = template
        this.counter = new AtomicLong()
    }

    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {    
        return new Saying(counter.incrementAndGet(), template.render(name))
    }

    @POST
    public void receiveHello(@Valid Saying saying) {
        LOG.info("Received a saying: {}", saying)
    }
}
