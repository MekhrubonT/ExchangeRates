package turaev.exchangerates.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by Lenovo on 09.04.2017.
 */

public class Utils {
    private static final ThreadLocal<byte[]> bufferThreadLocal = new ThreadLocal<>();

    private static byte[] getIOBuffer() {
        byte[] buffer = bufferThreadLocal.get();
        if (buffer == null) {
            buffer = new byte[8192];
            bufferThreadLocal.set(buffer);
        }
        return buffer;
    }

    public static String readToString(InputStream in, Charset charset) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buffer = getIOBuffer();
        int readSize;

        while ((readSize = in.read(buffer)) >= 0) {
            baos.write(buffer, 0, readSize);
        }
        return new String(baos.toByteArray(), charset);
    }

    static public void tryToCloseConnection(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static public boolean checkConnection(Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            final NetworkInfo ni = manager.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }
}
