package com.zenika.dorm.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.mongo.DormDaoMongo;
import com.zenika.dorm.core.dao.mongo.MongoDB;
import com.zenika.dorm.core.dao.mongo.MongoDBInstance;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.impl.DormServiceImpl;
import com.zenika.dorm.core.ws.resource.DormResource;
import com.zenika.dorm.maven.rs.resource.MavenResource;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.service.impl.MavenServiceImpl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(new JerseyServletModule() {

            @Override
            protected void configureServlets() {

                // jax rs resources
                bind(DormResource.class);
                bind(MavenResource.class);

                // dorm core
                bind(DormDao.class).to(DormDaoMongo.class);
                bind(MongoDB.class).to(MongoDBInstance.class);
                bind(DormService.class).to(DormServiceImpl.class);

                // dorm maven
                bind(MavenService.class).to(MavenServiceImpl.class);

                // Route all requests through GuiceContainer
                serve("/*").with(GuiceContainer.class);
            }
        });
    }
}
