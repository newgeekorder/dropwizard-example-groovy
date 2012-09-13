package com.example.helloworld.views

import com.yammer.dropwizard.views.ViewRenderer
import com.yammer.dropwizard.views.View
import javax.ws.rs.WebApplicationException
import com.sun.jersey.api.container.ContainerException
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext

public class VelocityViewRenderer implements ViewRenderer {
    private final VelocityEngine engine = new VelocityEngine()

    public VelocityViewRenderer() {
        def templatePath = VelocityViewRenderer.getResource("/").getFile()

        Properties properties = new Properties()
        properties.put("file.resource.loader.path", templatePath)
        engine.init(properties)
    }

    @Override
    public boolean isRenderable(View view) {
        return view.getTemplateName().endsWith(".vm")
    }

    @Override
    public void render(View view,
                       Locale locale,
                       OutputStream output) throws IOException, WebApplicationException {
        try {
            def writer = new OutputStreamWriter(output)

            VelocityContext context = new VelocityContext(view.properties)
            Template template = engine.getTemplate(view.getTemplateName())
            template.merge(context, writer)

            writer.flush()
            writer.close()
        } catch (Exception e) {
            throw new ContainerException(e)
        }
    }

}
