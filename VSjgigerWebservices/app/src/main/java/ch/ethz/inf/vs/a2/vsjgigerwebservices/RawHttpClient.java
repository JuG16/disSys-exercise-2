package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequest;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClient;

/**
 * Created by Andreas on 13.10.2015.
 */
public class RawHttpClient implements SimpleHttpClient {

    public String execute(Object request) {

        System.out.println("debug: Executing a http request raw");


        if (!(request instanceof HttpRawRequestImpl)) return null;

        HttpRawRequest requ = (HttpRawRequestImpl) request;
        PrintWriter printer;
        String response = "";
        String host = requ.getHost();

        System.out.println("debug: Host: " + host);

        int port = requ.getPort();
        if (host == null) return null;

        System.out.println("debug: Port: " + String.valueOf(port));

        try {
            Socket socket = new Socket(host, port);

            System.out.println("debug: Connected with a socket.");

            String sendString = requ.generateRequest();

            System.out.println("debug: Sending: " + sendString);

            printer = new PrintWriter( socket.getOutputStream(), true);

            printer.print(sendString);
            printer.flush();

            InputStream inStream = socket.getInputStream( );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inStream));
            String line;
            while ((line = reader.readLine()) != null) {
                response = response + line;
            }


            System.out.println("debug: Got message: " + response);



        }
        catch(Exception e) {

            System.err.println("Something went wrong while raw connecting to host : " + host + " with port " + port);
            System.err.println(e.toString());

            return null;

        }


        return response;
    }





    /*private final String LOG_TAG = "SimpleHttpClientImpl";

    @Override
    public String execute(Object request) {
        Socket socket = new Socket();

        PrintWriter printWriter = null;
        try { // auto generated try catch
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (printWriter != null){
            System.out.println("request: "+ request);

            // send request
            printWriter.print(request);
            printWriter.flush(); // should do according to assignment sheet


            // receive response
            BufferedReader bufferedReader= null;
            try {
                bufferedReader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = "";
            String line = null;
            try { // auto generated try catch
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (line != null){
                response = response + line;
                try { // auto generated try catch
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Response: "+response);
            try { // yet another try catch.. hurray
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        } else {
            Log.d(LOG_TAG, "print writer was a null, and it's a bada"); // todo: change back from Mario English to normal
        }
        return null;
    }*/

}
