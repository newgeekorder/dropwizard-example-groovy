package com.example.helloworld;

import com.example.helloworld.auth.ExampleAuthenticator;
import com.example.helloworld.cli.RenderCommand;
import com.example.helloworld.cli.SetupDatabaseCommand;
import com.example.helloworld.core.Template;
import com.example.helloworld.core.User;
import com.example.helloworld.db.PeopleDAO;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.*;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.views.ViewBundle;

import javax.ws.rs.Path;

public class HelloWorldService extends Service<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    private HelloWorldService() {
        super("hello-world");
        addCommand(new RenderCommand());
        addCommand(new SetupDatabaseCommand());
        addBundle(new AssetsBundle());
        addBundle(new ViewBundle());
    }

    @Override
    protected void initialize(HelloWorldConfiguration configuration,
                              Environment environment) throws ClassNotFoundException {
        environment.addProvider(new BasicAuthProvider<User>(new ExampleAuthenticator(),
                                                            "SUPER SECRET STUFF"));

        final Template template = configuration.buildTemplate();

        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(configuration.getDatabaseConfiguration(), "h2");
        final PeopleDAO peopleDAO = db.onDemand(PeopleDAO.class);

        environment.addHealthCheck(new TemplateHealthCheck(template));
        environment.addResource(new HelloWorldResource(template));
        environment.addResource(new GroovyHelloWorldResource(template));
        environment.addResource(new ProtectedResource());
        environment.addResource(new ViewResource());

        environment.addResource(new PeopleResource(peopleDAO));
        environment.addResource(new PersonResource(peopleDAO));
    }
}
