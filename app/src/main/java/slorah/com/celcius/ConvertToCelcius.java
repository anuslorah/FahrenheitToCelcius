package slorah.com.celcius;

import android.os.Bundle;
import java.text.DecimalFormat;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConvertToCelcius  extends Activity implements TextView.OnEditorActionListener{
    //variables for the widgets
    private EditText fahrenheitvalue;
    private TextView celciusvalue;

    //define SharedPreferences object
    private SharedPreferences savedValues;

    private String fahrenheitString = "";
    private float celciusdegrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_celcius);

        //get references to the widgets
        fahrenheitvalue = (EditText) findViewById(R.id.fahrenheitDegree);
        celciusvalue = (TextView) findViewById(R.id.celciusDegree);

        //set the listeners
        fahrenheitvalue.setOnEditorActionListener(this);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {

        //save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitvalue.getText().toString());
        editor.putFloat("celsiusdegrees", celciusdegrees);

        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume(){

        super.onResume();

        //get the instance variables
        fahrenheitvalue.setText(savedValues.getString("fahrenheitString", "0"));
        celciusdegrees = savedValues.getFloat("celciusdegrees", celciusdegrees);

        celciusvalue.setText(fahrenheitString);

        //convert and display temperature
        convertTemperature();
    }

    public void convertTemperature() {

        //get fahrehheit value
        fahrenheitString = fahrenheitvalue.getText().toString();
        float fahrenheit = Float.parseFloat(fahrenheitString);

        //calculate celcius value
        celciusdegrees =  ((fahrenheit-32) * 5f/9f);

        //display data on the layout
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        celciusvalue.setText(df.format(celciusdegrees));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId ==EditorInfo.IME_ACTION_UNSPECIFIED) {
            convertTemperature();
        }
        // hide soft keyboard
        return false;
    }
}
