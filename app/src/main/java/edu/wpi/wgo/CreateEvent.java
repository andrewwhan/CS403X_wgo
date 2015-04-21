package edu.wpi.wgo;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CreateEvent extends ActionBarActivity {

    EditText startTimeField;
    EditText endTimeField;
    Calendar startTime;
    Calendar endTime;

    SimpleDateFormat date_format = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startTime = new GregorianCalendar();
        endTime = new GregorianCalendar();
        endTime.set(Calendar.HOUR, endTime.get(Calendar.HOUR) + 1);

        startTimeField = (EditText)this.findViewById(R.id.editText3);
        endTimeField = (EditText)this.findViewById(R.id.editText4);
        startTimeField.setKeyListener(null);
        endTimeField.setKeyListener(null);
        startTimeField.setText(date_format.format(startTime.getTime()));
        endTimeField.setText(date_format.format(endTime.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeStartTime(View view){
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startTime.set(Calendar.MINUTE, minute);
                startTimeField.setText(date_format.format(startTime.getTime()));
            }
        }, startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE), false);
        tp.show();
    }

    public void changeEndTime(View view){
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endTime.set(Calendar.MINUTE, minute);
                endTimeField.setText(date_format.format(endTime.getTime()));
            }
        }, endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), false);
        tp.show();
    }
}
