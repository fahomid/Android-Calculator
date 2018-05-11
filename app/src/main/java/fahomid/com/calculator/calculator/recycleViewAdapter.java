package fahomid.com.calculator.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.itemViewHolder> {

    private RecyclerView recyclerView;
    private JSONArray historyList;

    public class itemViewHolder extends RecyclerView.ViewHolder {
        public TextView historyString, historyResult, itemPosition;
        public itemViewHolder(View view) {
            super(view);
            historyString = (TextView) view.findViewById(R.id.historyString);
            historyResult = (TextView) view.findViewById(R.id.historyResult);
            itemPosition = (TextView) view.findViewById(R.id.itemPosition);
        }
    }

    public recycleViewAdapter(JSONArray item) {
        this.historyList = item;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
        return new itemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(recycleViewAdapter.itemViewHolder holder, int position) {
        try {
            holder.historyString.setText(historyList.getJSONArray(position).getString(0));
            holder.historyResult.setText(takeCareOfExponential(historyList.getJSONArray(position).getString(1)));
            holder.itemPosition.setText(String.valueOf(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return historyList.length();
    }

    private String takeCareOfExponential(String s) {
        s = s.replace(",", "");
        if((!s.contains(".") && s.length() > 16) || (s.contains(".") && s.length() > 17)) {
            if(s.contains(".")) {
                String[] str = s.split("\\.");
                if(str[1].length() > 3 && str[1].charAt(0) == '0' && str[1].charAt(1) == '0'&& str[1].charAt(2) == '0') {
                    return new DecimalFormat("#.###############E+00").format(getDoubleFromString(s));
                } else {
                    return new DecimalFormat("#,###.###############").format(getDoubleFromString(s));
                }
            } else {
                return new DecimalFormat("#.###############E+00").format(getDoubleFromString(s));
            }
        }
        return new DecimalFormat("#,###.###############").format(getDoubleFromString(s));
    }

    //simply returns string to double
    private double getDoubleFromString(String s) {
        return Double.parseDouble(s);
    }
}
