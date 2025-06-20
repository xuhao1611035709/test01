package com.jwvdp.books.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {
        public static String parseDateToStr(String format,Date date) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }
}
