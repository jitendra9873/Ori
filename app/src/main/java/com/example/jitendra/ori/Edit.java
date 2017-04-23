package com.example.jitendra.ori;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.jitendra.ori.R.id.fname;
import static com.example.jitendra.ori.R.id.spinner;


/**
 * Created by jitendra on 21/4/17.
 */


public class Edit extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sharedpreferences;
    int year1, month1, day1;
    private EditText mfname;
    private EditText mlname;
    private EditText memail;
    private EditText mmno;
    private TextView save1;
    private NoDefaultSpinner mspinner;
    private String mGender;
    private SharedPreferences prefsPrivate;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                    year1 = arg1;
                    month1 = arg2 + 1;
                    day1 = arg3;
                }
            };

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        prefsPrivate = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

        dateView = (TextView) findViewById(R.id.button1);
        mfname = (EditText) findViewById(fname);
        mlname = (EditText) findViewById(R.id.lname);
        memail = (EditText) findViewById(R.id.email);
        mmno = (EditText) findViewById(R.id.mno);
        mspinner = (NoDefaultSpinner) findViewById(spinner);
        mfname.setText(prefsPrivate.getString("fname", ""));
        mlname.setText(prefsPrivate.getString("lname", ""));
        memail.setText(prefsPrivate.getString("email", ""));
        mmno.setText(prefsPrivate.getString("no", ""));
        String x = prefsPrivate.getString("gender_n", "-");
        if (x != "-") {
            mspinner.setSelection(getIndex(mspinner, x));
        }
        if (prefsPrivate.getInt("year1", 0) == 0) {
            dateView.setText("(Optional)");
/*            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month + 1, day);*/
        } else {
            showDate(prefsPrivate.getInt("year1", 0), prefsPrivate.getInt("month1", 0), prefsPrivate.getInt("day1", 0));
        }


        save1 = (TextView) findViewById(R.id.save);
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mfname = (EditText) findViewById(fname);
                if ((mfname.getText().toString().matches(""))) {
                    Toast.makeText(getApplicationContext(), "Enter first name", Toast.LENGTH_SHORT).show();
                } else {
                    mlname = (EditText) findViewById(R.id.lname);
                    if ((mlname.getText().toString().matches(""))) {
                        Toast.makeText(getApplicationContext(), "Enter last name", Toast.LENGTH_SHORT).show();
                    } else {
                        memail = (EditText) findViewById(R.id.email);
                        if (!isValidEmail(memail.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Email not valid", Toast.LENGTH_SHORT).show();
                        } else {
                            mmno = (EditText) findViewById(R.id.mno);
                            if (!isValidMobile(mmno.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Number not valid", Toast.LENGTH_SHORT).show();

                            } else {
                                mspinner = (NoDefaultSpinner) findViewById(spinner);
                                String text = "test";
                                try {
                                    text = mspinner.getSelectedItem().toString();
                                } catch (NullPointerException e) {
                                }


                                prefsPrivate = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefsPrivate.edit();
                                editor.putString("fname", mfname.getText().toString());
                                editor.putString("lname", mlname.getText().toString());
                                editor.putString("email", memail.getText().toString());
                                editor.putString("no", mmno.getText().toString());
                                if (text != "test")
                                    editor.putString("gender_n", text);
                                editor.putInt("day1", day1);
                                editor.putInt("month1", month1);
                                editor.putInt("year1", year1);

                                editor.commit();
                                Intent intent = new Intent(Edit.this, MainActivity.class);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(), "Value of day1 "+day1, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    private boolean isValidMobile(String phone) {
        if (phone.length() != 10)
            return false;
        return true;
    }

    //private method of your class
    private int getIndex(NoDefaultSpinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        if (day / 10 == 0 && month / 10 == 0)
            dateView.setText("0" + day + "-" + "0" + month + "-" + year);
        else if (day / 10 == 0)
            dateView.setText("0" + day + "-" + month + "-" + year);
        else if (month / 10 == 0)
            dateView.setText(day + "-" + "0" + month + "-" + year);
        else
            dateView.setText(day + "-" + month + "-" + year);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Edit.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        finish();
        return true;
    }
}
