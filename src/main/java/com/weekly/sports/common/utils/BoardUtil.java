package com.weekly.sports.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BoardUtil {

    public static String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }
}
