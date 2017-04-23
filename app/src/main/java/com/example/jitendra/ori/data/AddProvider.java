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

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.jitendra.ori.data.AddContract.AddEntry;

public class AddProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = AddProvider.class.getSimpleName();
    private static final int ADD = 100;
    private static final int ADD_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(AddContract.CONTENT_AUTHORITY, AddContract.PATH_ADD, ADD);

        sUriMatcher.addURI(AddContract.CONTENT_AUTHORITY, AddContract.PATH_ADD + "/#", ADD_ID);
    }

    /** Database helper object */
    private AddDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new AddDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ADD:
                cursor = database.query(AddEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ADD_ID:
                selection = AddEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(AddEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADD:
                return insertAddress(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertAddress(Uri uri, ContentValues values) {
        String name;
        // Check that the name is not null
        name = values.getAsString(AddEntry.COLUMN_ADD1);
        if (name == null) {
            throw new IllegalArgumentException("A uHouse No. is required");
        }

        name = values.getAsString(AddEntry.COLUMN_ADD2);
        if (name == null) {
            throw new IllegalArgumentException("Street is required");
        }
        name = values.getAsString(AddEntry.COLUMN_ADD3);
        if (name == null) {
            throw new IllegalArgumentException("Street is required");
        }
        name = values.getAsString(AddEntry.COLUMN_CITY);
        if (name == null) {
            throw new IllegalArgumentException("City is required");
        }
        Integer weight = values.getAsInteger(AddEntry.COLUMN_PIN);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pincode requires valid Pincode");
        }
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(AddEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADD:
                return updateAddress(uri, contentValues, selection, selectionArgs);
            case ADD_ID:
                selection = AddEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateAddress(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateAddress(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(AddEntry.COLUMN_ADD1)) {
            String name = values.getAsString(AddEntry.COLUMN_ADD1);
            if (name == null) {
                throw new IllegalArgumentException("no add");
            }
        }
        if (values.containsKey(AddEntry.COLUMN_ADD2)) {
            String name = values.getAsString(AddEntry.COLUMN_ADD2);
            if (name == null) {
                throw new IllegalArgumentException("no add");
            }
        }
        if (values.containsKey(AddEntry.COLUMN_ADD3)) {
            String name = values.getAsString(AddEntry.COLUMN_ADD3       );
            if (name == null) {
                throw new IllegalArgumentException("no add");
            }
        }
        if (values.containsKey(AddEntry.COLUMN_CITY)) {
            String name = values.getAsString(AddEntry.COLUMN_CITY);
            if (name == null) {
                throw new IllegalArgumentException("no city");
            }
        }
        if (values.containsKey(AddEntry.COLUMN_PIN)) {
            Integer weight = values.getAsInteger(AddEntry.COLUMN_PIN);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("no pin");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(AddEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADD:
                // Delete all rows that match the selection and selection args
                return database.delete(AddEntry.TABLE_NAME, selection, selectionArgs);
            case ADD_ID:
                // Delete a single row given by the ID in the URI
                selection = AddEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(AddEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADD:
                return AddEntry.CONTENT_LIST_TYPE;
            case ADD_ID:
                return AddEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
