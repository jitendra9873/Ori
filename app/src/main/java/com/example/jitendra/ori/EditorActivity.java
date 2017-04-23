/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jitendra.ori;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendra.ori.data.AddContract.AddEntry;

import static com.example.jitendra.ori.R.id.add1;
import static com.example.jitendra.ori.R.id.add2;
import static com.example.jitendra.ori.R.id.add3;
import static com.example.jitendra.ori.R.id.city;

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private EditText address1;
    private EditText address2;
    private EditText address3;
    private EditText mpincode;
    private EditText mcity;
    private TextView mbutton;
/*    private ToggleButton b1;
    private ToggleButton b2;
    private ToggleButton b3;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        address1 = (EditText) findViewById(add1);
        address2 = (EditText) findViewById(add2);
        address3 = (EditText) findViewById(add3);
        mpincode = (EditText) findViewById(R.id.pincode);
        mcity = (EditText) findViewById(city);
        mbutton = (TextView) findViewById(R.id.button4);
/*        b1=(ToggleButton)findViewById(R.id.button1);
        b2=(ToggleButton)findViewById(R.id.button2);
        b3=(ToggleButton)findViewById(R.id.button3);

        b1.setOnCheckedChangeListener(changeChecker);
        b2.setOnCheckedChangeListener(changeChecker);
        b3.setOnCheckedChangeListener(changeChecker);*/
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read from input fields
                // Use trim to eliminate leading or trailing white space
                String add1 = address1.getText().toString().trim();
                if ((add1.matches(""))) {
                    Toast.makeText(getApplicationContext(), "Please specify a house  number", Toast.LENGTH_SHORT).show();
                } else {
                    String add2 = address2.getText().toString().trim();
                    if ((add2.matches(""))) {
                        Toast.makeText(getApplicationContext(), "Please specify a Street name", Toast.LENGTH_SHORT).show();
                    } else {
                        String add3 = address3.getText().toString().trim();

                        String pin = mpincode.getText().toString().trim();
                        if (!isValidPin(pin)) {
                            Toast.makeText(getApplicationContext(), "Enter a valid 6-digit pincode", Toast.LENGTH_SHORT).show();
                        } else {
                            String city = mcity.getText().toString().trim();
                            if ((city.matches(""))) {
                                Toast.makeText(getApplicationContext(), "Please specify your city", Toast.LENGTH_SHORT).show();
                            } else {
                                int pin1 = Integer.parseInt(pin);

                                ContentValues values = new ContentValues();
                                values.put(AddEntry.COLUMN_ADD1, add1);
                                values.put(AddEntry.COLUMN_ADD2, add2);
                                values.put(AddEntry.COLUMN_ADD3, add3);
                                values.put(AddEntry.COLUMN_CITY, city);
                                values.put(AddEntry.COLUMN_PIN, pin1);

                                Uri newUri = getContentResolver().insert(AddEntry.CONTENT_URI, values);

                                if (newUri == null) {
                                    Toast.makeText(getApplicationContext(), "Failed",
                                            Toast.LENGTH_SHORT).show();
                                } /*else {
                    Toast.makeText(getApplicationContext(), "Success",
                            Toast.LENGTH_SHORT).show();
                }*/
                                onBackPressed();
                            }
                        }
                    }
                }
            }
        });
    }

/*    OnCheckedChangeListener changeChecker = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (buttonView == b1) {
                    b2.setChecked(false);
                    b3.setChecked(false);
                }
                if (buttonView == b2) {
                    b1.setChecked(false);
                    b2.setChecked(false);
                }
                if (buttonView == b3) {
                    b2.setChecked(false);
                    b1.setChecked(false);
                }
            }
        }
    };*/

    private boolean isValidPin(String pin) {
        if (pin.length() != 6)
            return false;
        return true;
    }


    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
/*        Intent intent = new Intent(EditorActivity.this, CatalogActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AddEntry._ID,
                AddEntry.COLUMN_ADD1,
                AddEntry.COLUMN_ADD2,
                AddEntry.COLUMN_ADD3,
                AddEntry.COLUMN_CITY,
                AddEntry.COLUMN_PIN};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                AddEntry.CONTENT_URI,
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int add1ColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD1);
            int add2ColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD2);
            int add3ColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD3);
            int cityColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_CITY);
            int pinColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_PIN);


            // Update the views on the screen with the values from the database
            address1.setText(cursor.getString(add1ColumnIndex));
            address2.setText(cursor.getString(add2ColumnIndex));
            address3.setText(cursor.getString(add3ColumnIndex));
            mcity.setText(cursor.getString(cityColumnIndex));
            mpincode.setText(Integer.toString(cursor.getInt(pinColumnIndex)));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        address1.setText("");
        address2.setText("");
        address3.setText("");
        mpincode.setText("");
        mcity.setText("");
    }


}

