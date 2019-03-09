package kz.woopig.application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;


/**
 * Hello world!
 *
 */
class App
{


    private static ResourceConfig getConfig() {
        return new ApplicationConfig();
    }


    public static void main( String[] args ) throws IOException {

        URI BASE_URI = URI.create("http://" + Inet4Address.getLocalHost().getHostAddress() +  ":8080/app/");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, getConfig());

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        server.start();
        System.out.println( "Hello World!" );

    }
}
