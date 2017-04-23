package com.example.jitendra.ori;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.jitendra.ori.Edit.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    private TextView name12;
    private TextView mobile1;
    private TextView gender12;
    private TextView email1;
    private TextView date1;
    private TextView add;

    private SharedPreferences prefsPrivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefsPrivate = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        name12 = (TextView) findViewById(R.id.name1);
        mobile1 = (TextView) findViewById(R.id.mobile_no);
        email1 = (TextView) findViewById(R.id.email_add);
        gender12 = (TextView) findViewById(R.id.gender1);
        date1 = (TextView) findViewById(R.id.dateofbirth);
        name12.setText(prefsPrivate.getString("fname", "") + " " + prefsPrivate.getString("lname", ""));
        mobile1.setText(prefsPrivate.getString("no", "-"));
        email1.setText(prefsPrivate.getString("email", "-"));
        try {
            gender12.setText(prefsPrivate.getString("gender_n", "-"));
        } catch (NullPointerException e) {
            System.out.print("nullpointer exceptionr");
        }
        if (prefsPrivate.getInt("day1", 0) == 0) {
            date1.setText("-");
        } else
            showDate(prefsPrivate.getInt("year1", 0), prefsPrivate.getInt("month1", 0), prefsPrivate.getInt("day1", 0));
        add = (TextView) findViewById(R.id.address);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatalogActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    private void showDate(int year, int month, int day) {
        if (day / 10 == 0 && month / 10 == 0)
            date1.setText("0" + day + "-" + "0" + month + "-" + year);
        else if (day / 10 == 0)
            date1.setText("0" + day + "-" + month + "-" + year);
        else if (month / 10 == 0)
            date1.setText(day + "-" + "0" + month + "-" + year);
        else
            date1.setText(day + "-" + month + "-" + year);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.edit_menu:
                Intent intent = new Intent(MainActivity.this, Edit.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }*/
}
