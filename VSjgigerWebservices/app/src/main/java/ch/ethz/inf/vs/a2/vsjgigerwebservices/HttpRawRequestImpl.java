package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequest;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;

/**
 * Created by Andreas on 10.10.2015.
 */
public class HttpRawRequestImpl implements HttpRawRequest {

    // in a browser use
    // http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature
    // in order to get the temperature info, so i assume we should take the first part as the host, and 8081 as the port

    private int port;
    private String host, path;

    @Override
    public String generateRequest() {
        String httpRequestHeader = "GET "+getPath()+" HTTP/1.1\r\n";
        httpRequestHeader = httpRequestHeader + "Host: " + getHost()+":" + getPort()+"\r\n";
        httpRequestHeader = httpRequestHeader + "Connection: close\r\n\r\n"; // connection is closed after completion
        return httpRequestHeader;
    }

    private String getPath() {
        if(path != null || path.length() == 0)
            return path;
        return RemoteServerConfiguration.PATH;
    }


    public HttpRawRequestImpl(String host,int port,String path){
        this.host = host;
        this.path = path;
        this.port = port;
    }

    @Override
    public String getHost() {
        if(host != null || host.length() != 0)
            return host;
        return RemoteServerConfiguration.HOST;
    }

    @Override
    public int getPort() {
        if(port != 0)
            return port;
        return RemoteServerConfiguration.REST_PORT;
    }
}