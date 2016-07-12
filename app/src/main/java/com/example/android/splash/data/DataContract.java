package com.example.android.splash.data;

import android.provider.BaseColumns;

/**
 * Created by ayush on 23/6/16.
 */
public class DataContract {
    public static final class Report implements BaseColumns

    {
        public static final String TABLE_NAME = "report_pending";
        public static final String TABLE_NAME2 = "report_prev";
        public static final String DATA = "data";
        public static final String IMAGE="image";
        public static final String LATITUDE="latitude";
        public static final String LONGITUDE="longitude";
        public static final String IMEI="imei";
    }


}

