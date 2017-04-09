package turaev.exchangerates.loader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import turaev.exchangerates.R;

/**
 * Created by Lenovo on 09.04.2017.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Pair<String, Double>> data;

    public RateAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Pair<String, Double>> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RateViewHolder.newInstance(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(RateViewHolder holder, int position) {
        holder.currency.setText(data.get(position).first);
        holder.rate.setText(String.format("%.2f", data.get(position).second));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RateViewHolder extends RecyclerView.ViewHolder {
        final TextView currency;
        final TextView rate;

        private RateViewHolder(View itemView) {
            super(itemView);
            currency = (TextView) itemView.findViewById(R.id.currency_name);
            rate = (TextView) itemView.findViewById(R.id.currency_rate);
        }

        static RateViewHolder newInstance(LayoutInflater inflater, ViewGroup parent) {
            final View view = inflater.inflate(R.layout.rate_holder, parent, false);
            return new RateViewHolder(view);

        }

    }
}
