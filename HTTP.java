package org.usfirst.frc.team555.robot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public class HTTP {

    public String r="DEFAULT";
    
    private HttpServer server;
    private boolean on=false;
    
    public HTTP(int port, String name) throws Exception
    {
    	start(port,name);
    }
    
    public void start(int port,String name) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/"+name, new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        on=true;
    }
    
    public void stop()
    {
    	if(server!=null && on)
    	{
    		server.stop(0);
    		on=false;
    	}
    }
    
     class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = r;
            Headers h=t.getResponseHeaders();
            h.add("Access-Control-Allow-Origin","*");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public  void setResponse(String response)
    {
        r=response;
    }
    
}