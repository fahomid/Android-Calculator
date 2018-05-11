package fahomid.com.calculator.calculator;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class CommonMethods {
    private JSONObject dataStates;
    private JSONArray jsArray;
    private String temp_calculation_string;
    private JSONArray historyList;
    private Context applicationContext;
    protected static boolean runningFirstTime = true;

    CommonMethods(Context context) {
        applicationContext = context;
        dataStates = new JSONObject();
        //get saved history list
        if((temp_calculation_string = this.getHistory("history", "historyList", applicationContext)) != null) {
            try {
                historyList = new JSONArray(temp_calculation_string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            historyList = new JSONArray();
        }

        //get saved flags
        if((temp_calculation_string = this.getHistory("history", "dataStates", applicationContext)) != null) {
            try {
                dataStates = new JSONObject(temp_calculation_string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            setDefaultFlags();
        }
        if(runningFirstTime) {
            setDefaultFlags();
            runningFirstTime = false;
        }
    }

    protected void setDefaultFlags() {
        setAvailableCalculationStrings(new JSONArray());
        setCurrentOperation("");
        setHasCurrentValue(false);
        setDividedByZero(false);
        setDoComplexMath(false);
        setEqualBtnPressed(false);
        setResetMath(false);
        setCurrentValue(0);
        setResultValue(0);
        setLastValue(0);
        setIsFromIntent(false);
        setCurrentStringResult("0");
        setCurrentCalculationStrings("");
        saveHistory("history", "dataStates", getDataStates().toString(), applicationContext);
    }

    protected JSONArray getDataStates(String key) {
        try {
            return dataStates.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    protected String getDataStates() {
        return dataStates.toString();
    }

    protected JSONArray getHistoryList() {
        return historyList;
    }

    protected void setHistoryList(JSONArray arr) {
        historyList.put(arr);
    }

    protected String getMemoryValue() {
        if((temp_calculation_string = this.getHistory("history", "memory", applicationContext)) != null) {
            return temp_calculation_string;
        }
        return null;
    }

    protected void setMemoryAdd(double value) {
        if((temp_calculation_string = this.getHistory("history", "memory", applicationContext)) != null) {
            temp_calculation_string = getStringFromDouble(getDoubleFromString(temp_calculation_string) + value);
            saveHistory("history", "memory", temp_calculation_string, applicationContext);
        } else {
            saveHistory("history", "memory", getStringFromDouble(value), applicationContext);
        }
    }

    protected void setMemoryMinus(double value) {
        if((temp_calculation_string = this.getHistory("history", "memory", applicationContext)) != null) {
            temp_calculation_string = getStringFromDouble(getDoubleFromString(temp_calculation_string) - value);
            saveHistory("history", "memory", temp_calculation_string, applicationContext);
        } else saveHistory("history", "memory", getStringFromDouble(value), applicationContext);
    }


    protected void setMemorySave(double value) {
        saveHistory("history", "memory", getStringFromDouble(value), applicationContext);
    }

    protected void clearSavedMemory() {
        clearMemory("history", "memory", applicationContext);
    }

    //returns string from double value
    protected String getStringFromDouble(double data) {
        return new DecimalFormat("#.################").format(data);
    }

    //simply returns string to double
    protected double getDoubleFromString(String s) {
        return Double.parseDouble(s);
    }

    //getters and setters
    //these two will set or get status of isFromIntent
    protected boolean getIsFromIntent() {
        try {
            return dataStates.getBoolean("isFromIntent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setIsFromIntent(boolean statement) {
        try {
            dataStates.put("isFromIntent", statement);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or get current calculation strings
    protected String getCurrentCalculationStrings() {
        try {
            return dataStates.getString("currentCalculationStrings");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    protected void setCurrentCalculationStrings(String string) {
        try {
            dataStates.put("currentCalculationStrings", string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or get current calculation result strings
    protected String getCurrentStringResult() {
        try {
            return dataStates.getString("currentStringResult");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void setCurrentStringResult(String resultValue) {
        try {
            dataStates.put("currentStringResult", resultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these four will set or return the calculation string
    protected JSONArray getAvailableCalculationStrings() {
        try {
            return dataStates.getJSONArray("calculationStrings");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.print("States", dataStates.toString());
        return new JSONArray();
    }
    protected void setAvailableCalculationStrings(JSONArray arr) {
        try {
            dataStates.put("calculationStrings", arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void setAvailableCalculationStrings(String value) {
        setAvailableCalculationStrings(value, false);
    }
    protected void setAvailableCalculationStrings(String value, boolean updateLast) {
        try {
            jsArray = getAvailableCalculationStrings();
            if(updateLast && getNumberOfAvailableStrings() > 0) jsArray.put(jsArray.length() - 1, value);
            else jsArray.put(value);
            dataStates.put("calculationStrings", jsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected String getLastAvailableCalculationString() {
        try {
            jsArray = getAvailableCalculationStrings();
            if(jsArray.length() > 0) return jsArray.getString(jsArray.length() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    protected void removeLastAvailableCalculationString() {
        JSONArray temp = new JSONArray();
        try {
            jsArray = getAvailableCalculationStrings();
            for(int i = 0; i < jsArray.length() - 1; i++) {
                temp.put(jsArray.getString(i));
            }
            dataStates.put("calculationStrings", temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected int getNumberOfAvailableStrings() {
        jsArray = getAvailableCalculationStrings();
        return jsArray.length();
    }
    protected void removeAllAvailableStrings() {
        try {
            dataStates.put("calculationStrings", new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void print(String s, String t) {
        System.out.println(s +": "+ t);
    }

    //these two will set or return the current value flag
    protected boolean getHasCurrentValue() {
        try {
            return dataStates.getBoolean("hasCurrentValue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setHasCurrentValue(boolean value) {
        try {
            dataStates.put("hasCurrentValue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the current operation flag
    protected String getCurrentOperation() {
        try {
            System.out.println(dataStates.toString());
            return dataStates.getString("currentOperation");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    protected void setCurrentOperation(String value) {
        try {
            dataStates.put("currentOperation", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the divided by zero flag
    protected boolean getDividedByZero() {
        try {
            return dataStates.getBoolean("dividedByZero");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setDividedByZero(boolean value) {
        try {
            dataStates.put("dividedByZero", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the do complex math flag
    protected boolean getDoComplexMath() {
        try {
            return dataStates.getBoolean("doComplexMath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setDoComplexMath(boolean value) {
        try {
            dataStates.put("doComplexMath", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the equal button press flag
    protected boolean getEqualBtnPressed() {
        try {
            return dataStates.getBoolean("equalBtnPressed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setEqualBtnPressed(boolean value) {
        try {
            dataStates.put("equalBtnPressed", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the reset math flag
    protected boolean getResetMath() {
        try {
            return dataStates.getBoolean("resetMath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void setResetMath(boolean value) {
        try {
            dataStates.put("resetMath", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the current value
    protected double getCurrentValue() {
        try {
            return dataStates.getDouble("currentValue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    protected void setCurrentValue(double value) {
        try {
            dataStates.put("currentValue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the result value
    protected double getResultValue() {
        try {
            return dataStates.getDouble("resultValue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    protected void setResultValue(double value) {
        try {
            dataStates.put("resultValue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //these two will set or return the last value
    protected double getLastValue() {
        try {
            return dataStates.getDouble("lastValue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    protected void setLastValue(double value) {
        try {
            dataStates.put("lastValue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*//save data into shared preferences
    protected void saveHistory(String dataType, String key, String value, Context application){
        SharedPreferences pref = application.getSharedPreferences(dataType, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //get history if exist, if not return null
    protected String getHistory(String dataType, String key, Context application) {
        SharedPreferences pref = application.getSharedPreferences(dataType, MODE_PRIVATE);
        return pref.getString(key, null);
    }


    //clear history from memory
    protected void clearMemory(String dataType, String key, Context application) {
        SharedPreferences pref = application.getSharedPreferences(dataType, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }*/

    //save data into shared preferences
    protected void saveHistory(String dataType, String key, String value, Context application){
        File file = new File(application.getFilesDir(), "history");
        String filename = "historyList";
        JSONObject data;
        try {
            data = new JSONObject(getHistoryFromFile(application).toString());
            data.put(key, value);
            String fileContents = data.toString();
            FileOutputStream outputStream;
            try {
                outputStream = application.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //get history from file
    protected JSONObject getHistoryFromFile(Context application) {
        FileInputStream fis = null;
        try {
            fis = application.openFileInput("historyList");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    //get history if exist, if not return null
    protected String getHistory(String dataType, String key, Context application) {
        JSONObject data = getHistoryFromFile(application);
        for(int i = 0; i < data.length(); i++) {
            if (data.has(key)) {
                try {
                    return  data.getString(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    //clear history from memory
    protected void clearMemory(String dataType, String key, Context application) {
        JSONObject data = getHistoryFromFile(application);
        data.remove(key);
    }

    //clear history all
    protected void clearMemoryAll(Context application) {
        File file = new File(application.getFilesDir(), "history");
        String filename = "historyList";
        String fileContents = "";
        FileOutputStream outputStream;
        try {
            outputStream = application.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
