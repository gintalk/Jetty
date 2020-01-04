/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jetty;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 *
 * @author duytu
 */
public class SimpleServer {
    public static Server createServer(int port) throws FileNotFoundException{
        Path keystorePath = Paths.get("src/main/resources/keystore.jks")
                .toAbsolutePath();
        if(keystorePath == null){
            throw new FileNotFoundException();
        }
        
        Server server = new Server();
        
        HttpConfiguration config = new HttpConfiguration();
        config.addCustomizer(new SecureRequestCustomizer());
        
        SslContextFactory sslCF = new SslContextFactory.Server();
        sslCF.setKeyStorePath(keystorePath.toString());
        sslCF.setKeyStorePassword("password");
        sslCF.setKeyManagerPassword("password");
        
        ServerConnector connector = new ServerConnector(server,
            new SslConnectionFactory(sslCF, "http/1.1"),
            new HttpConnectionFactory(config));
        connector.setHost("localhost");
        connector.setPort(port);
        connector.setIdleTimeout(50000);
        
        server.addConnector(connector);
        
        ServletContextHandler servletCH = new ServletContextHandler();
        servletCH.setContextPath("/");
        
        servletCH.setAttribute("my.array", new int[]{1, 2, 3});
        servletCH.addServlet(ShowArrayServlet.class, "/showArray");
        servletCH.addServlet(ShowArrayReverseServlet.class, "/showArrayReverse");
                
        server.setHandler(servletCH);
        
        return server;
    }
    
    public static void main(String[] args) throws Exception{
        Server server = createServer(8090);
        
        server.start();
        server.join();
    }
}
