package turaev.exchangerates.loader;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.List;

/**
 * Created by Lenovo on 09.04.2017.
 */

public class LoadResult {

    public final List<Pair<String, Double>> data;

    public final ResultType resultType;

    LoadResult(List<Pair<String, Double>> data, ResultType resultType) {
        this.data = data;
        this.resultType = resultType;
    }
}
