package fahomid.com.calculator.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class history extends Activity {
    private RecyclerView recyclerView;
    private recycleViewAdapter recycleviewAdapter;
    private LinearLayoutManager mLayoutManager;

    //display views
    private TextView calculationStringView;
    private TextView currentResult;

    //flags & others
    private boolean historyCleared = false;
    private CommonMethods commonMethods;
    private String temp_calculation_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        //initializing common methods
        commonMethods = new CommonMethods(getApplicationContext());

        //initiate
        currentResult = findViewById(R.id.currentResult);
        calculationStringView = findViewById(R.id.resultString);
        currentResult= findViewById(R.id.currentResult);

        //set view data
        calculationStringView.setText(commonMethods.getCurrentCalculationStrings().toString());
        currentResult.setText(commonMethods.getCurrentStringResult());
        currentResult.setTextSize(currentResult.getText().length() > 12 ? 25 : 40);

        if(commonMethods.getHistoryList().length() > 0) {
            recyclerView = findViewById(R.id.historyRecycleView);
            recycleviewAdapter = new recycleViewAdapter(commonMethods.getHistoryList());
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recycleviewAdapter);
            recycleviewAdapter.notifyDataSetChanged();
            findViewById(R.id.noHistory).setVisibility(View.GONE);
            findViewById(R.id.historyRecycleView).setVisibility(View.VISIBLE);
            findViewById(R.id.historyDelete).setVisibility(View.VISIBLE);
        }
    }


    public void backToMain(View view) {
        backToHomepage();
    }

    public void getHistoryDetails(View view) {
        commonMethods.removeAllAvailableStrings();
        System.out.println(getCurrentStringValue());
        commonMethods.setCurrentOperation("");
        TextView tempView = view.findViewById(R.id.historyResult);
        commonMethods.setResultValue(getDoubleFromString(tempView.getText().toString().replace(",", "")));
        commonMethods.setCurrentValue(0);
        commonMethods.setDoComplexMath(true);
        commonMethods.setIsFromIntent(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selectedResult", tempView.getText().toString());
        tempView = view.findViewById(R.id.historyString);
        intent.putExtra("selectedString", tempView.getText().toString());
        intent.putExtra("calculationStringExist", true);
        commonMethods.setAvailableCalculationStrings(tempView.getText().toString().replace("=", ""));
        commonMethods.setAvailableCalculationStrings(calculationStringView.getText().toString());
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
        startActivity(intent);
        finish();
    }

    public void clearHistory(View view) {
        findViewById(R.id.historyRecycleView).setVisibility(View.GONE);
        findViewById(R.id.historyDelete).setVisibility(View.GONE);
        findViewById(R.id.noHistory).setVisibility(View.VISIBLE);
        //commonMethods.clearMemory("history", "historyList", getApplicationContext());
        commonMethods.clearMemoryAll(getApplicationContext());
        historyCleared = true;
    }

    //this method returns the list of calculation instruction as string
    private String getCalculationString(JSONArray array) {
        String listString = "";
        for(int i = 0; i < array.length(); i++) {
            try {
                listString += array.getString(i);
            } catch (JSONException e) {
                listString += "";
            }
        }
        return listString;
    }

    //simply returns string to double
    private double getDoubleFromString(String s) {
        return Double.parseDouble(s);
    }

    //methods for setting the current result
    private void setCurrentResult(String preparedResult) {
        currentResult.setText(preparedResult);
        currentResult.setTextSize(preparedResult.length() > 12 ? 25 : 40);
    }

    private void setCurrentResult(double preparedResult) {
        currentResult.setText(new DecimalFormat("#,###.################").format(preparedResult));
        if(getCurrentStringValue().length() > 16) currentResult.setText(new DecimalFormat("#.###############E+00").format(preparedResult));
        currentResult.setText(currentResult.getText().toString().toLowerCase());
        currentResult.setTextSize(getCurrentStringValue(false).length() > 12 ? 25 : 40);
    }

    //overload with default parameter
    private String getCurrentStringValue() {
        return getCurrentStringValue(true);
    }

    //simply returns the current result/value
    private String getCurrentStringValue(boolean flag) {
        return (flag ? currentResult.getText().toString().replace(",", "") : currentResult.getText().toString());
    }

    private void backToHomepage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backToHomepage();
    }
}
