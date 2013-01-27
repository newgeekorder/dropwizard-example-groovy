package com.onesecondshy.services.myexample

import com.onesecondshy.services.myexample.resources.*

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.views.ViewBundle
import org.eclipse.jetty.server.session.SessionHandler

import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.jdbi.DBIFactory
import org.skife.jdbi.v2.DBI
import com.yammer.dropwizard.assets.AssetsBundle

public class ApplicationService extends Service<ApplicationConfiguration> {
    private static final String ASSET_ENDPOINT = "/assets/"

    public static void main(String[] args) throws Exception {
        new ApplicationService().run(args)
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.setName("my-example-service")
        bootstrap.addBundle(new AssetsBundle("/assets", ASSET_ENDPOINT))
        bootstrap.addBundle(new MigrationsBundle<ApplicationConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(ApplicationConfiguration configuration) {
                return configuration.database
            }
        });
        bootstrap.addBundle(new ViewBundle())
    }

    @Override
    public void run(ApplicationConfiguration configuration,
                       Environment environment) throws ClassNotFoundException {
        SessionHandler sessionHandler = new SessionHandler()
        sessionHandler.getSessionManager().setMaxInactiveInterval(60 * 15)

        DBI db = new DBIFactory().build(environment, configuration.database, "myexampleDB")
        environment.addResource(new SiteResource())
        environment.addResource(new ApiResource())
        environment.setSessionHandler(sessionHandler)
    }
}
