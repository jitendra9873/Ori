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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class AddContract {

    public static final String CONTENT_AUTHORITY = "com.example.jitendra.ori";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ADD = "add";

    private AddContract() {
    }

    public static final class AddEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ADD);


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ADD;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ADD;

        public final static String TABLE_NAME = "address";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ADD1 = "add1";
        public final static String COLUMN_ADD2 = "add2";
        public final static String COLUMN_ADD3 = "add3";
        public final static String COLUMN_CITY = "city";
        public final static String COLUMN_NICK = "nick";
        public final static String COLUMN_PIN = "pin";
        public static final int HOME = 0;
        public static final int WORK = 1;
        public static final int OTHER = 2;

        public static boolean isValidnickname(int x) {
            if (x == HOME || x == WORK || x == OTHER) {
                return true;
            }
            return false;
        }
    }

}

