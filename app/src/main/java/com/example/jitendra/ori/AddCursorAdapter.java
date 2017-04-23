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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jitendra.ori.data.AddContract.AddEntry;

public class AddCursorAdapter extends CursorAdapter {

    public AddCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView mflat = (TextView) view.findViewById(R.id.flat);
        TextView mstreet = (TextView) view.findViewById(R.id.street);
        TextView mcity_pin = (TextView) view.findViewById(R.id.city_pin);
        TextView mlandmark = (TextView) view.findViewById(R.id.landmark);

        int flatColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD1);
        int streetColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD2);
        int landmarkColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_ADD3);
        int cityColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_CITY);
        int pinColumnIndex = cursor.getColumnIndex(AddEntry.COLUMN_PIN);

        String flat = cursor.getString(flatColumnIndex);
        String street = cursor.getString(streetColumnIndex);
        String landmark = cursor.getString(landmarkColumnIndex);
        String city = cursor.getString(cityColumnIndex);
        String pin = cursor.getString(pinColumnIndex);

        // Update the TextViews
        mflat.setText(flat);
        mstreet.setText(street);
        if (landmark.matches("")) {
            mlandmark.setVisibility(View.INVISIBLE);
        } else {
            mlandmark.setText("Landmark :" + landmark);
        }
        mcity_pin.setText(city + "-" + pin);
    }
}
