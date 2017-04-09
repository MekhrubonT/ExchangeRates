package turaev.exchangerates.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

import turaev.exchangerates.utils.Utils;

public class RatesDownload extends AsyncTaskLoader<LoadResult> {
    private final String base;
    private ResultType downloadResult;
    private List<Pair<String, Double>> currencyRates = null;

    public RatesDownload(Context context, String base) {
        super(context);
        this.base = base.isEmpty() ? "USD" : base;
        downloadResult = ResultType.ERROR;
    }


    @Override
    public LoadResult loadInBackground() {
        HttpURLConnection connection = null;
        InputStream in = null;
        System.out.println("RatesDownload.loadInBackground");
        try {
            connection = CreateURLConnection.create(base);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = connection.getInputStream();
                currencyRates = new CurrencyJSONParser().parse(in);
                downloadResult = ResultType.OK;
                System.out.println("Finished ok");
            } else {
                throw new BadResponseException("Cannot connect to " + connection.getURL() + ": " + connection.getResponseCode());
            }
        } catch (JSONException e) {
            downloadResult = ResultType.ERROR;
            Log.d("Ex " + "JSON", "Json object can't be parsed", e);
        } catch (MalformedURLException e) {
            downloadResult = ResultType.ERROR;
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            downloadResult = Utils.checkConnection(getContext()) ? ResultType.ERROR : ResultType.NO_INTERNET;
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            Utils.tryToCloseConnection(in);
            if (connection != null) {
                connection.disconnect();
            }
        }

        return new LoadResult(currencyRates, downloadResult);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
