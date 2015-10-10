package jgiger.a2.vs.inf.ethz.ch.vs_jgiger_webservices;

import jgiger.a2.vs.inf.ethz.ch.vs_jgiger_webservices.http.HttpRawRequest;

/**
 * Created by Andreas on 10.10.2015.
 */
public class HttpRawRequestImpl implements HttpRawRequest {

    // in a browser use
    // http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature
    // in order to get the temperature info, so i assume we should take the first part as the host, and 8081 as the port

    private int port;
    String host, path;

    @Override
    public String generateRequest() {
        String httpRequestHeader = "GET "+getPath()+" HTTP/1.1\r\n";
        httpRequestHeader = httpRequestHeader + "Host: " + getHost()+":" + getPort()+"/sunspots/Spot1/sensors/temperature"+"\r\n";
        httpRequestHeader = httpRequestHeader + "Connection: close\r\n\r\n"; // connection is closed after completion
        return httpRequestHeader;
    }

    private String getPath() {
        if(path != null)
            return path;
        return"/";
    }


    public HttpRawRequestImpl(String host,int port,String path){
        this.host = host;
        this.path = path;
        this.port = port;
    }

    @Override
    public String getHost() {
        if(host != null)
            return host;
        return "http://vslab.inf.ethz.ch";
    }

    @Override
    public int getPort() {
        if(port != 0)
            return port;
        return 8081;
    }
}
