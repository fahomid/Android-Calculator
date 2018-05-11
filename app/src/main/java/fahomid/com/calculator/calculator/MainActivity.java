package fahomid.com.calculator.calculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.sqrt;

public class MainActivity extends Activity implements View.OnClickListener {
    //final values
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String SIX = "6";
    public static final String SEVEN = "7";
    public static final String EIGHT = "8";
    public static final String NINE = "9";
    public static final String ZERO = "0";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "×";
    public static final String DIVIDE = "÷";
    public static final String EQUAL = "=";
    public static final String ROOT = "√";
    public static final String SQUARE = "sqr";
    public static final String NEGATIVE = "negate";

    //declaring memory buttons
    private Button m_mc;
    private Button m_mr;
    private Button m_plus;
    private Button m_minus;
    private Button m_save;

    //declaring 0 to 9 and point
    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private Button btn_five;
    private Button btn_six;
    private Button btn_seven;
    private Button btn_eight;
    private Button btn_nine;
    private Button btn_zero;
    private Button btn_point;

    //declaring Other buttons
    private Button btn_plus_minus;
    private Button btn_plus;
    private Button btn_minus;
    private Button btn_division;
    private Button btn_multiplication;
    private Button btn_equal;
    private Button btn_clear_all;
    private Button btn_clear_entry;
    private Button btn_clear_last;
    private Button btn_percentage;
    private Button btn_root;
    private Button btn_square;
    private Button btn_fraction;


    //flags and others
    private boolean doubleBackToExitPressedOnce;
    private CommonMethods commonMethods;
    JSONArray jsArray;
    private String temp_calculation_string= "";
    private boolean calculationStringExist = false;

    //display views
    private TextView calculationStringView;
    private TextView currentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing common methods
        commonMethods = new CommonMethods(getApplicationContext());

        //initiating views
        calculationStringView = findViewById(R.id.resultString);
        currentResult = findViewById(R.id.currentResult);

        //initiating memory buttons
        m_mc = findViewById(R.id.m_mc);
        m_mr = findViewById(R.id.m_mr);
        m_plus = findViewById(R.id.m_plus);
        m_minus = findViewById(R.id.m_minus);
        m_save = findViewById(R.id.m_save);

        m_mc.setOnClickListener(this);
        m_mr.setOnClickListener(this);
        m_plus.setOnClickListener(this);
        m_minus.setOnClickListener(this);
        m_save.setOnClickListener(this);

        //initiating 0 to 9 and point
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_four = findViewById(R.id.btn_four);
        btn_five = findViewById(R.id.btn_five);
        btn_six = findViewById(R.id.btn_six);
        btn_seven = findViewById(R.id.btn_seven);
        btn_eight = findViewById(R.id.btn_eight);
        btn_nine = findViewById(R.id.btn_nine);
        btn_zero = findViewById(R.id.btn_zero);
        btn_point = findViewById(R.id.btn_point);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);
        btn_zero.setOnClickListener(this);
        btn_point.setOnClickListener(this);

        //initiating other buttons
        btn_plus_minus = findViewById(R.id.btn_plus_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_division = findViewById(R.id.btn_division);
        btn_multiplication = findViewById(R.id.btn_multiplication);
        btn_equal = findViewById(R.id.btn_equal);
        btn_clear_all = findViewById(R.id.btn_clear_all);
        btn_clear_entry = findViewById(R.id.btn_clear_entry);
        btn_clear_last = findViewById(R.id.btn_clear_last);
        btn_percentage = findViewById(R.id.btn_percentage);
        btn_root = findViewById(R.id.btn_root);
        btn_square = findViewById(R.id.btn_square);
        btn_fraction = findViewById(R.id.btn_fraction);


        //setting other button'c click event
        btn_clear_all.setOnClickListener(this);
        btn_clear_entry.setOnClickListener(this);
        btn_clear_last.setOnClickListener(this);
        btn_plus_minus.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiplication.setOnClickListener(this);
        btn_division.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_percentage.setOnClickListener(this);
        btn_root.setOnClickListener(this);
        btn_square.setOnClickListener(this);
        btn_fraction.setOnClickListener(this);


        //initiate
        currentResult = findViewById(R.id.currentResult);
        calculationStringView = findViewById(R.id.resultString);

        calculationStringView.setText(commonMethods.getCurrentCalculationStrings().toString());
        setCurrentResult(commonMethods.getCurrentStringResult());

        //check if any saved memory exist
        if(commonMethods.getMemoryValue() == null) disableMemoryButtons();

        //check if anything came history intent
        if(getIntent().hasExtra("selectedResult")) setCurrentResult(getIntent().getStringExtra("selectedResult"));
        if(getIntent().hasExtra("selectedString")) setCalculationString(getIntent().getStringExtra("selectedString").replace("=", ""));
        if(getIntent().hasExtra("calculationStringExist")) calculationStringExist = getIntent().getBooleanExtra("calculationStringExist", false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                valueOperation(ONE);
                break;

            case R.id.btn_two:
                valueOperation(TWO);
                break;

            case R.id.btn_three:
                valueOperation(THREE);
                break;

            case R.id.btn_four:
                valueOperation(FOUR);
                break;

            case R.id.btn_five:
                valueOperation(FIVE);
                break;

            case R.id.btn_six:
                valueOperation(SIX);
                break;

            case R.id.btn_seven:
                valueOperation(SEVEN);
                break;

            case R.id.btn_eight:
                valueOperation(EIGHT);
                break;

            case R.id.btn_nine:
                valueOperation(NINE);
                break;

            case R.id.btn_zero:
                valueOperation(ZERO);
                break;
            case R.id.btn_point:
                pointOperation();
                break;
            case R.id.btn_plus_minus:
                plusMinusInterChange();
                break;

            case R.id.btn_plus:
                doMath(PLUS);
                break;

            case R.id.btn_minus:
                doMath(MINUS);
                break;

            case R.id.btn_multiplication:
                doMath(MULTIPLY);
                break;

            case R.id.btn_division:
                doMath(DIVIDE);
                break;

            case R.id.btn_equal:
                equalBtnPressed();
                break;

            case R.id.btn_clear_all:
                clearAll();
                break;

            case R.id.btn_clear_entry:
                clearEntry();
                break;

            case R.id.btn_percentage:
                getPercentage();
                break;

            case R.id.btn_root:
                getRoot();
                break;

            case R.id.btn_square:
                getSquare();
                break;

            case R.id.btn_fraction:
                getFraction();
                break;

            case R.id.btn_clear_last:
                clearLast();
                break;

            case R.id.m_mc:
                memoryClear();
                break;

            case R.id.m_mr:
                memoryRecall();
                break;

            case R.id.m_plus:
                memoryPlus();
                break;

            case R.id.m_minus:
                memoryMinus();
                break;

            case R.id.m_save:
                memorySave();
                break;
        }
    }

    private void memoryClear() {
        commonMethods.clearSavedMemory();
        disableMemoryButtons();
    }

    private void memorySave() {
        commonMethods.setMemorySave(getDoubleFromString(getCurrentStringValue()));
        enableMemoryButtons();
    }

    private void memoryMinus() {
        commonMethods.setMemoryMinus(getDoubleFromString(getCurrentStringValue()));
        enableMemoryButtons();
    }

    private void memoryPlus() {
        commonMethods.setMemoryAdd(getDoubleFromString(getCurrentStringValue()));
        enableMemoryButtons();
    }

    private void memoryRecall() {
        setCurrentResult(getDoubleFromString(commonMethods.getMemoryValue()));
        commonMethods.setHasCurrentValue(false);
    }

    private void disableMemoryButtons() {
        m_mc.setTextColor(Color.GRAY);
        m_mc.setClickable(false);
        m_mr.setTextColor(Color.GRAY);
        m_mr.setClickable(false);
    }

    private void enableMemoryButtons() {
        m_mc.setTextColor(Color.BLACK);
        m_mc.setClickable(true);
        m_mr.setTextColor(Color.BLACK);
        m_mr.setClickable(true);
    }

    //method to clear last entered digit
    private void clearLast() {
        if(commonMethods.getDividedByZero()) clearAll();
        if(commonMethods.getHasCurrentValue() && !commonMethods.getEqualBtnPressed()) {
            temp_calculation_string = getCurrentStringValue();
            if (temp_calculation_string.length() == 1) currentResult.setText("0");
            else
                setCurrentResult(getDoubleFromString(temp_calculation_string.substring(0, temp_calculation_string.length() - 1)));
        }
    }

    //method to clear current entry
    private void clearEntry() {
        if(commonMethods.getDividedByZero()) clearAll();
        setCurrentResult("0");
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //clear all or reset the calculator excluding the saved memory
    private void clearAll() {
        commonMethods.setDefaultFlags();
        setCurrentResult("0");
        setCalculationString("");
        enableMathButtons();
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //will handle fraction button
    private void getFraction() {
        /*commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
        if(commonMethods.getDoComplexMath()) {
            commonMethods.setAvailableCalculationStrings("1/" + "(" + commonMethods.getLastAvailableCalculationString(), true);
        } else {
            commonMethods.setAvailableCalculationStrings("1/" + "(" + getCurrentStringValue() + ")");
        }
        setCalculationString(getCalculationString());
        setCurrentResult(1/commonMethods.getCurrentValue());
        commonMethods.setHasCurrentValue(true);
        commonMethods.setDoComplexMath(true);
        commonMethods.setLastValue(1 / commonMethods.getCurrentValue());
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());*/


        commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
        if(commonMethods.getIsFromIntent()) {
            temp_calculation_string = getCalculationString().replace("=", "");
            commonMethods.removeAllAvailableStrings();
            commonMethods.setAvailableCalculationStrings("1/(" + temp_calculation_string + ")");
        } else if(commonMethods.getDoComplexMath() && commonMethods.getNumberOfAvailableStrings() > 0) {
            commonMethods.setAvailableCalculationStrings("1/(" + commonMethods.getLastAvailableCalculationString() + ")", true);
        } else {
            commonMethods.setAvailableCalculationStrings("1/(" + getCurrentStringValue() + ")");
        }
        setCalculationString(getCalculationString());
        setCurrentResult(1/commonMethods.getCurrentValue());
        commonMethods.setHasCurrentValue(true);
        commonMethods.setDoComplexMath(true);
        commonMethods.setResetMath(true);
        commonMethods.setLastValue(commonMethods.getCurrentValue() * commonMethods.getCurrentValue());
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());

    }


    //will handle square button
    private void getSquare() {
        commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
        if(commonMethods.getIsFromIntent()) {
            temp_calculation_string = getCalculationString().replace("=", "");
            commonMethods.removeAllAvailableStrings();
            commonMethods.setAvailableCalculationStrings(SQUARE + "(" + temp_calculation_string + ")");
        } else if(commonMethods.getDoComplexMath() && commonMethods.getNumberOfAvailableStrings() > 0) {
            commonMethods.setAvailableCalculationStrings(SQUARE + "(" + commonMethods.getLastAvailableCalculationString() + ")", true);
        } else {
            commonMethods.setAvailableCalculationStrings(SQUARE + "(" + getCurrentStringValue() + ")");
        }
        setCalculationString(getCalculationString());
        setCurrentResult(commonMethods.getCurrentValue() * commonMethods.getCurrentValue());
        commonMethods.setHasCurrentValue(true);
        commonMethods.setDoComplexMath(true);
        commonMethods.setResetMath(true);
        commonMethods.setLastValue(commonMethods.getCurrentValue() * commonMethods.getCurrentValue());
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //will handle root button
    private void getRoot() {
        commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
        if(commonMethods.getIsFromIntent()) {
            temp_calculation_string = getCalculationString().replace("=", "");
            commonMethods.removeAllAvailableStrings();
            commonMethods.setAvailableCalculationStrings(ROOT + "(" + temp_calculation_string + ")");
        } else if(commonMethods.getDoComplexMath()) {
            commonMethods.setAvailableCalculationStrings(ROOT + "(" + commonMethods.getLastAvailableCalculationString() + ")", true);
        } else {
            commonMethods.setAvailableCalculationStrings(ROOT + "(" + getCurrentStringValue() + ")");
        }
        setCalculationString(getCalculationString());
        setCurrentResult(sqrt(commonMethods.getCurrentValue()));
        commonMethods.setHasCurrentValue(true);
        commonMethods.setDoComplexMath(true);
        commonMethods.setLastValue(sqrt(commonMethods.getCurrentValue()));
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //will handle percentage button
    private void getPercentage() {
        commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
        setCurrentResult(commonMethods.getResultValue() * (commonMethods.getCurrentValue()/100));
        if(commonMethods.getDoComplexMath()) {
            commonMethods.removeAllAvailableStrings();
            setCalculationString(getStringFromDouble(commonMethods.getResultValue() * (commonMethods.getCurrentValue()/100)));
        } else {
            commonMethods.setAvailableCalculationStrings(getStringFromDouble(commonMethods.getResultValue() * (commonMethods.getCurrentValue()/100)));
            setCalculationString(getCalculationString());
        }
        commonMethods.setHasCurrentValue(true);
        commonMethods.setDoComplexMath(true);
        commonMethods.setLastValue(commonMethods.getResultValue() * (commonMethods.getCurrentValue()/100));
        commonMethods.saveHistory("history", "dataStares", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //changes positive value to negative
    private void plusMinusInterChange() {
        if(commonMethods.getEqualBtnPressed()) commonMethods.removeAllAvailableStrings();
        temp_calculation_string = getCurrentStringValue();
        double d = Double.parseDouble(temp_calculation_string);
        d = d * (-1);
        setCurrentResult(d);
        if(commonMethods.getResetMath()) {
            if(commonMethods.getDoComplexMath()) {
                commonMethods.setAvailableCalculationStrings(NEGATIVE + "(" + temp_calculation_string + ")", true);
            } else {
                commonMethods.setAvailableCalculationStrings(NEGATIVE + "(" + temp_calculation_string + ")");
                commonMethods.setDoComplexMath(true);
            }
            commonMethods.setResultValue(0);
            setCalculationString(NEGATIVE+ "(" + temp_calculation_string + ")");
        }
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //this will handle plus, minus, multiplication and division
    private void doMath(String operation) {
        if(commonMethods.getCurrentOperation().equals(DIVIDE) && getCurrentStringValue().equals("0")) {
            dividedByZero();
            return;
        }

        if(!commonMethods.getHasCurrentValue() && commonMethods.getNumberOfAvailableStrings() == 0) {
            System.out.println("In Condition 1");
            commonMethods.setAvailableCalculationStrings("0");
            commonMethods.setAvailableCalculationStrings(operation);
            commonMethods.setResultValue(0);
            currentResult.setText("0");
            setCalculationString(getCalculationString());
            commonMethods.setHasCurrentValue(false);
            commonMethods.setCurrentOperation(operation);
        } else if(!commonMethods.getHasCurrentValue() && commonMethods.getNumberOfAvailableStrings() > 0) {
            System.out.println("In Condition 2");
            commonMethods.removeLastAvailableCalculationString();
            commonMethods.setAvailableCalculationStrings(operation);
            calculationStringView.setText(getCalculationString());
            commonMethods.setHasCurrentValue(false);
            commonMethods.setCurrentOperation(operation);
        } else if(commonMethods.getHasCurrentValue() && commonMethods.getNumberOfAvailableStrings() > 0) {
            System.out.println("In Condition 3");
            commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
            commonMethods.setLastValue(commonMethods.getCurrentValue());
            commonMethods.setResultValue(doMathMain(commonMethods.getCurrentOperation(), commonMethods.getResultValue(), commonMethods.getCurrentValue()));
            setCurrentResult(commonMethods.getResultValue());

            //if complex root/square/fraction etc come will handle them seperately
            if(commonMethods.getDoComplexMath()) commonMethods.setDoComplexMath(false);
            else commonMethods.setAvailableCalculationStrings(getStringFromDouble(commonMethods.getCurrentValue()));
            commonMethods.setAvailableCalculationStrings(operation);
            setCalculationString(getCalculationString());
            commonMethods.setHasCurrentValue(false);
            commonMethods.setCurrentOperation(operation);
        } else if(commonMethods.getHasCurrentValue() && commonMethods.getNumberOfAvailableStrings() == 0) {
            System.out.println("In Condition 4");
            commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
            commonMethods.setLastValue(commonMethods.getCurrentValue());
            commonMethods.setResultValue(commonMethods.getCurrentValue());

            //if complex root/square/fraction etc come will handle them separately
            if(commonMethods.getDoComplexMath()) commonMethods.setDoComplexMath(false);
            else commonMethods.setAvailableCalculationStrings(getStringFromDouble(commonMethods.getCurrentValue()));
            commonMethods.setAvailableCalculationStrings(operation);
            setCurrentResult(commonMethods.getResultValue());
            setCalculationString(getCalculationString());
            commonMethods.setCurrentOperation(operation);
            commonMethods.setHasCurrentValue(false);
        }

        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
        commonMethods.setResetMath(false);
    }


    //returns string from double value
    private String getStringFromDouble(double data) {
        return new DecimalFormat("#.################").format(data);
    }


    //simply returns string to double
    private double getDoubleFromString(String s) {
        return Double.parseDouble(s);
    }


    //this will handle what happens if divided by zero
    private void dividedByZero() {
        setCurrentResult(getString(R.string.dividebyzero));
        disableMathButtons();
        commonMethods.setDividedByZero(true);
    }


    //this will handle the equal button press event
    private void equalBtnPressed() {
        if(commonMethods.getEqualBtnPressed()) {
            commonMethods.removeAllAvailableStrings();
            if(!commonMethods.getCurrentOperation().isEmpty()) commonMethods.setAvailableCalculationStrings(getCurrentStringValue());
            commonMethods.setAvailableCalculationStrings(commonMethods.getCurrentOperation());
            System.out.println("SSSSSSSSSSSSS:" +commonMethods.getAvailableCalculationStrings().toString());
        }
        if(commonMethods.getDividedByZero()) clearAll();
        if(commonMethods.getCurrentOperation().equals(DIVIDE) && (commonMethods.getLastValue() == 0 || getDoubleFromString(getCurrentStringValue()) == 0)) {
            dividedByZero();
            return;
        }
        if(commonMethods.getHasCurrentValue()) commonMethods.setResultValue(doMathMain(commonMethods.getCurrentOperation(), commonMethods.getResultValue(), commonMethods.getLastValue()));
        else {
            commonMethods.setCurrentValue(getDoubleFromString(getCurrentStringValue()));
            commonMethods.setLastValue(commonMethods.getCurrentValue());
            commonMethods.setResultValue(doMathMain(commonMethods.getCurrentOperation(), commonMethods.getResultValue(), commonMethods.getCurrentValue()));
        }
        setCurrentResult(commonMethods.getResultValue());
        setCalculationString("");
        if(!commonMethods.getDoComplexMath()) commonMethods.setAvailableCalculationStrings(getStringFromDouble(commonMethods.getLastValue()));
        commonMethods.setAvailableCalculationStrings(EQUAL);
        temp_calculation_string = getCalculationString();
        commonMethods.removeAllAvailableStrings();
        commonMethods.setAvailableCalculationStrings(temp_calculation_string);
        commonMethods.setAvailableCalculationStrings(getStringFromDouble(commonMethods.getResultValue()));
        jsArray = commonMethods.getAvailableCalculationStrings();
        commonMethods.setHistoryList(jsArray);
        commonMethods.removeAllAvailableStrings();
        commonMethods.setHasCurrentValue(true);
        commonMethods.setResetMath(true);
        commonMethods.setEqualBtnPressed(true);
        commonMethods.setDoComplexMath(false);

        if(getCurrentStringValue().equals("0")) commonMethods.setCurrentOperation("");

        //update history
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
        commonMethods.saveHistory("history", "historyList", commonMethods.getHistoryList().toString(), getApplicationContext());
    }

    private void setCalculationString(String calculationString) {
        calculationString = calculationString.replace("=", "");
        calculationStringView.setText(calculationString);
        commonMethods.setCurrentCalculationStrings(calculationString);
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //overload with default parameter
    private String getCurrentStringValue() {
        return getCurrentStringValue(true);
    }

    //simply returns the current result/value
    private String getCurrentStringValue(boolean flag) {
        return (flag ? currentResult.getText().toString().replace(",", "") : currentResult.getText().toString());
    }

    //this will calculate and return the result of two values
    private double doMathMain(String operation, double value1, double value2) {
        double result = 0;
        switch (operation) {
            case PLUS:
                result = value1 + value2;
                break;

            case MINUS:
                result = value1 - value2;
                break;

            case MULTIPLY:
                result = value1 * value2;
                break;

            case DIVIDE:
                if(value2 == 0) {
                    commonMethods.setDividedByZero(true);
                } else {
                    result = value1 / value2;
                }
                break;

            default:
                result = Double.parseDouble(getCurrentStringValue());
                break;
        }
        return result;
    }


    //this will handle all the input values from 0 to 9
    private void valueOperation(String val) {
        if(commonMethods.getResetMath()) {
            commonMethods.removeLastAvailableCalculationString();
            setCalculationString(getCalculationString());
            commonMethods.setHasCurrentValue(false);
            commonMethods.setDoComplexMath(false);
            commonMethods.setResetMath(false);
        }
        if(commonMethods.getDividedByZero()) clearAll();
        if(commonMethods.getEqualBtnPressed()) setCurrentResult("0");
        if(!commonMethods.getHasCurrentValue()) temp_calculation_string = val;
        else temp_calculation_string = getCurrentStringValue(false) + val; //getting with all commas
        temp_calculation_string = formatInputValue(temp_calculation_string);
        String formattedValue = temp_calculation_string.replace(",", "");
        formattedValue = formattedValue.replace(".", "");
        if(formattedValue.length() > 16) return;
        setCurrentResult(temp_calculation_string);
        commonMethods.setHasCurrentValue(true);
        commonMethods.setLastValue(getDoubleFromString(getCurrentStringValue()));
        commonMethods.setResetMath(false);
        commonMethods.setEqualBtnPressed(false);
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
    }


    //methods for setting the current result
    private void setCurrentResult(String preparedResult) {
        currentResult.setText(preparedResult);
        currentResult.setTextSize(preparedResult.length() > 12 ? 25 : 40);
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
    }

    private void setCurrentResult(double preparedResult) {
        if(Double.isNaN(preparedResult)) {
            currentResult.setText("Invalid input");
            commonMethods.setCurrentStringResult("Invalid input");
            disableMathButtons();
            commonMethods.setDividedByZero(true);
            return;
        }
        currentResult.setText(new DecimalFormat("#,###.################").format(preparedResult));
        temp_calculation_string = takeCareOfExponential(getStringFromDouble(preparedResult));
        currentResult.setText(temp_calculation_string);
        currentResult.setText(currentResult.getText().toString().toLowerCase());
        currentResult.setTextSize(getCurrentStringValue(false).length() > 12 ? 25 : 40);
        commonMethods.setCurrentStringResult(temp_calculation_string);
        commonMethods.setCurrentCalculationStrings(getCalculationString());
        commonMethods.saveHistory("history", "dataStates", commonMethods.getDataStates().toString(), getApplicationContext());
    }

    private String takeCareOfExponential(String s) {
        double tempNumber = getDoubleFromString(s);
        if(s.length() > 18) return new DecimalFormat("#.###############E+00").format(getDoubleFromString(s));

        if(tempNumber == 0) return "0";
        if(tempNumber < 0) tempNumber = tempNumber * (-1);
        if(tempNumber > 0.001) {
            return new DecimalFormat("#,###.###############").format(getDoubleFromString(s));
        }
        return new DecimalFormat("#.###############E+00").format(getDoubleFromString(s));
    }

    //returns length of double value
    private int getDoubleLength(double preparedResult) {
        temp_calculation_string = String.valueOf(preparedResult);
        temp_calculation_string = temp_calculation_string.replaceAll("\\s+","");
        return temp_calculation_string.length();
    }


    //this method disables all the math operation buttons
    private void disableMathButtons() {
        btn_plus_minus.setClickable(false);
        btn_plus_minus.setTextColor(Color.GRAY);

        btn_plus.setClickable(false);
        btn_plus.setTextColor(Color.GRAY);

        btn_minus.setClickable(false);
        btn_minus.setTextColor(Color.GRAY);

        btn_division.setClickable(false);
        btn_division.setTextColor(Color.GRAY);

        btn_multiplication.setClickable(false);
        btn_multiplication.setTextColor(Color.GRAY);

        btn_point.setClickable(false);
        btn_point.setTextColor(Color.GRAY);

        btn_fraction.setClickable(false);
        btn_fraction.setTextColor(Color.GRAY);

        btn_square.setClickable(false);
        btn_square.setTextColor(Color.GRAY);

        btn_root.setClickable(false);
        btn_root.setTextColor(Color.GRAY);

        btn_percentage.setClickable(false);
        btn_percentage.setTextColor(Color.GRAY);
    }

    //this method enables all the math operation buttons
    private void enableMathButtons() {
        btn_plus_minus.setClickable(true);
        btn_plus_minus.setTextColor(Color.BLACK);

        btn_plus.setClickable(true);
        btn_plus.setTextColor(Color.BLACK);

        btn_minus.setClickable(true);
        btn_minus.setTextColor(Color.BLACK);

        btn_division.setClickable(true);
        btn_division.setTextColor(Color.BLACK);

        btn_multiplication.setClickable(true);
        btn_multiplication.setTextColor(Color.BLACK);

        btn_point.setClickable(true);
        btn_point.setTextColor(Color.BLACK);

        btn_fraction.setClickable(true);
        btn_fraction.setTextColor(Color.BLACK);

        btn_square.setClickable(true);
        btn_square.setTextColor(Color.BLACK);

        btn_root.setClickable(true);
        btn_root.setTextColor(Color.BLACK);

        btn_percentage.setClickable(true);
        btn_percentage.setTextColor(Color.BLACK);
    }


    //method to handle all point related operation
    private void pointOperation() {
        if(commonMethods.getHasCurrentValue()) {
            temp_calculation_string = getCurrentStringValue();
            if(commonMethods.getResetMath()) {
                setCurrentResult("0.");
            } else if (!temp_calculation_string.contains(".")) {
                temp_calculation_string += ".";
                setCurrentResult(temp_calculation_string);
            }
        } else {
            setCurrentResult("0.");
        }
        commonMethods.setHasCurrentValue(true);
    }


    //method to format string value
    private String formatInputValue(String s) {
        s = s.replace(",", "");
        if(s.contains(".")) {
            String[] str = s.split("\\.");
            str[0] = new DecimalFormat("#,###").format(Double.parseDouble(str[0]));
            return str[0] +"."+ str[1];
        } else return new DecimalFormat("#,###").format(Double.parseDouble(s));
    }

    //this method returns the list of calculation instruction as string
    private String getCalculationString() {
        String listString = "";
        try {
            jsArray = commonMethods.getDataStates("calculationStrings");
            for(int i = 0; i < jsArray.length(); i++) listString += jsArray.getString(i);
            return listString;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    //load history and pass all flag state
    public void loadHistory(View view) {
        Intent intent = new Intent(this, history.class);
        startActivity(intent);
        finish();
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            commonMethods.setDefaultFlags();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //temporary to test values
    private void printDummy(String s) {
        Log.d("Printed in: ", s);
        Log.d("current value:", String.valueOf(commonMethods.getCurrentValue()));
        Log.d("result value:", String.valueOf(commonMethods.getResultValue()));
        Log.d("last value:", String.valueOf(commonMethods.getLastValue()));
        Log.d("string value:", getCalculationString());
    }
}
