package ch.ethz.inf.vs.a2.vsjgigerwebservices.http;


import ch.ethz.inf.vs.a2.vsjgigerwebservices.LibHttpClient;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.RawHttpClient;

/**
 * Created by leynas on 25.09.15.
 */
public abstract class SimpleHttpClientFactory {
    public static SimpleHttpClient getInstance(Type type) {
        switch (type) {
            case RAW:
                return new RawHttpClient();
            case LIB:
                return new LibHttpClient();
            default:
                return null;
        }
    }

    public enum Type {
        RAW, LIB;
    }
}
