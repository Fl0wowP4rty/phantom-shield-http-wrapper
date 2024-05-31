package tech.skidonion.api.wrapper.util;

import tech.skidonion.api.wrapper.exception.BadRequestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    public static long convertToMillis(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(timeString);
            return date.getTime();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String convertToString(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millis);
        return sdf.format(date);
    }
}
