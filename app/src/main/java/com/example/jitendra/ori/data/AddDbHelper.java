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
package com.example.jitendra.ori.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jitendra.ori.data.AddContract.AddEntry;

public class AddDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = AddDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "shelter.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public AddDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ADD_TABLE = "CREATE TABLE " + AddEntry.TABLE_NAME + " ("
                + AddEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AddEntry.COLUMN_ADD1 + " TEXT NOT NULL, "
                + AddEntry.COLUMN_ADD2 + " TEXT NOT NULL, "
                + AddEntry.COLUMN_ADD3 + " TEXT NOT NULL, "
                + AddEntry.COLUMN_CITY + " TEXT, "
                + AddEntry.COLUMN_PIN + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ADD_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}