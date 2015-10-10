package jgiger.a2.vs.inf.ethz.ch.vs_jgiger_webservices;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import jgiger.a2.vs.inf.ethz.ch.vs_jgiger_webservices.http.SimpleHttpClient;

/**
 * Created by Andreas on 10.10.2015.
 */
public class SimpleHttpClientImpl implements SimpleHttpClient {

    private final String LOG_TAG = "SimpleHttpClientImpl";

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
    }

}
