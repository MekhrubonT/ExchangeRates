package turaev.exchangerates.loader;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lenovo on 09.04.2017.
 */

public class CreateURLConnection {
    private final static Uri BASEURL =Uri.parse("http://api.fixer.io/latest");

    static public HttpURLConnection create(String base) throws IOException {
        Uri uri = BASEURL.buildUpon().appendQueryParameter("base", base).build();
        Log.d("Trying to connect", String.valueOf(uri));
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }
}
