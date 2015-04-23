package edu.wpi.wgo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CreateEvent extends ActionBarActivity {

    EditText startTimeField;
    EditText endTimeField;
    EditText startDateField;
    EditText endDateField;
    Calendar startTime;
    Calendar endTime;
    ImageView mImageView;
    Bitmap imageBitmap;
    Button capture;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    SimpleDateFormat time_format = new SimpleDateFormat("hh:mm a", Locale.US);
    SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startTime = new GregorianCalendar();
        endTime = new GregorianCalendar();
        endTime.set(Calendar.HOUR, endTime.get(Calendar.HOUR) + 1);

        startTimeField = (EditText)this.findViewById(R.id.startTimeField);
        endTimeField = (EditText)this.findViewById(R.id.endTimeField);
        startDateField = (EditText)this.findViewById(R.id.startDateField);
        endDateField = (EditText)this.findViewById(R.id.endDateField);
        startTimeField.setKeyListener(null);
        endTimeField.setKeyListener(null);
        startDateField.setKeyListener(null);
        endDateField.setKeyListener(null);
        startTimeField.setText(time_format.format(startTime.getTime()));
        endTimeField.setText(time_format.format(endTime.getTime()));
        startDateField.setText(date_format.format(startTime.getTime()));
        endDateField.setText(date_format.format(endTime.getTime()));

        mImageView = (ImageView) this.findViewById(R.id.eventPhoto);

        if(savedInstanceState != null){
            imageBitmap = savedInstanceState.getParcelable("eventPhoto");
            if(imageBitmap != null){
                mImageView.setImageBitmap(imageBitmap);
            }
        }

        capture = (Button) findViewById(R.id.photoButton);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }


    /* Camera functionality
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }


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

    @Override
    public void onSaveInstanceState(Bundle outstate){
        outstate.putParcelable("eventPhoto", imageBitmap);
        super.onSaveInstanceState(outstate);
    }

    public void changeStartTime(View view){
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startTime.set(Calendar.MINUTE, minute);
                startTimeField.setText(time_format.format(startTime.getTime()));
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
                endTimeField.setText(time_format.format(endTime.getTime()));
            }
        }, endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), false);
        tp.show();
    }

    public void changeStartDate(View view){
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startTime.set(Calendar.YEAR, year);
                startTime.set(Calendar.MONTH, monthOfYear);
                startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDateField.setText(date_format.format(startTime.getTime()));
            }
        }, startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    public void changeEndDate(View view){
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endTime.set(Calendar.YEAR, year);
                endTime.set(Calendar.MONTH, monthOfYear);
                endTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDateField.setText(date_format.format(endTime.getTime()));
            }
        }, endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), endTime.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    public void makeEvent(View view){
        Event event = new Event(imageBitmap,
                ((EditText)findViewById(R.id.eventField)).getText().toString(),
                ((EditText)findViewById(R.id.locationField)).getText().toString(),
                startTime.getTime(),
                endTime.getTime(),
                ((EditText)findViewById(R.id.descriptionField)).getText().toString());
    }
}
