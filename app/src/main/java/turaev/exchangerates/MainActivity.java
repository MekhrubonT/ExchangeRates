package turaev.exchangerates;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import turaev.exchangerates.loader.LoadResult;
import turaev.exchangerates.loader.RateAdapter;
import turaev.exchangerates.loader.RatesDownload;
import turaev.exchangerates.loader.ResultType;
import turaev.exchangerates.utils.RecylcerDividersDecorator;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LoadResult> {

    public static final String CURRENCY = "CURRENCY";
    private ProgressBar progressView = null;
    private TextView errorView = null;
    private RecyclerView recyclerView = null;
    private RateAdapter adapter = null;
    private EditText inputCurrency = null;
    private Button refreshButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public Loader<LoadResult> onCreateLoader(int id, Bundle args) {
        System.out.println("creating\n");
        return new RatesDownload(this, args.getString(CURRENCY));
    }

    @Override
    public void onLoadFinished(Loader<LoadResult> loader, LoadResult data) {
        System.out.println(data.resultType);
        if (data.resultType == ResultType.OK) {
            displayResult(data.data);
        } else {
            displayError(data.resultType);
        }
        getSupportLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoaderReset(Loader<LoadResult> loader) {
        displayResult(Collections.EMPTY_LIST);
    }


    private void setVisibilities(int pv, int rv, int ev) {
        progressView.setVisibility(pv);
        recyclerView.setVisibility(rv);
        errorView.setVisibility(ev);
    }

    private void displayResult(List<Pair<String, Double>> data) {
        if (data.isEmpty()) {
            setVisibilities(View.GONE, View.GONE, View.VISIBLE);
            errorView.setText(R.string.EMPTYCURRENCYLIST);
        } else {
            if (adapter == null) {
                adapter = new RateAdapter(this);
                recyclerView.setAdapter(adapter);
            }
            adapter.setData(data);
            setVisibilities(View.GONE, View.VISIBLE, View.GONE);
        }
    }

    private void displayError(ResultType resultType) {
        setVisibilities(View.GONE, View.GONE, View.VISIBLE);
        errorView.setText(resultType.toString());
    }

    private void init() {
        inputCurrency = (EditText) findViewById(R.id.input_text);
        refreshButton = (Button) findViewById(R.id.refresh);
        progressView = (ProgressBar) findViewById(R.id.progress);
        errorView = (TextView) findViewById(R.id.error_text);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == refreshButton) {
                    refresh();
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new RecylcerDividersDecorator(ContextCompat.getColor(this, R.color.gray_a)));

    }

    private void refresh() {
        setVisibilities(View.VISIBLE, View.GONE, View.GONE);
        Bundle data = new Bundle();
        data.putString(CURRENCY, inputCurrency.getText().toString());
        getSupportLoaderManager().initLoader(0, data, this);
    }
}
