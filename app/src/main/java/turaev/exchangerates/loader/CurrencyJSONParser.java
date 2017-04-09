package turaev.exchangerates.loader;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import turaev.exchangerates.utils.Utils;

/**
 * Created by Lenovo on 09.04.2017.
 */

class CurrencyJSONParser {

    List<Pair<String, Double>> parse(InputStream in) throws IOException, JSONException {
        final JSONObject json = new JSONObject(Utils.readToString(in, StandardCharsets.UTF_8));
        return parseRates(json);
    }

    private List<Pair<String, Double>> parseRates(JSONObject json) throws JSONException {
        System.out.println(json);
        JSONObject data = json.getJSONObject("rates");
        Iterator<String> iterator = data.keys();
        ArrayList<Pair<String, Double>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            String currencyCode = iterator.next();
            result.add(new Pair<>(currencyCode, data.getDouble(currencyCode)));
        }
        return result;
    }

}
