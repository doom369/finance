package com.ddumanskiy.web;

import com.ddumanskiy.web.handlers.AverageHandler;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by ddumanskiy
 * Date : 12/19/2014.
 */
public class WebServer {

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8181).build();
        ResourceConfig config = new ResourceConfig().packages(AverageHandler.class.getPackage().getName());
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
    }

}
