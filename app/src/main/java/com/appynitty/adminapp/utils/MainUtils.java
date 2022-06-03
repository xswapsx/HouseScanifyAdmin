package com.appynitty.adminapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainUtils {

    //Staging Server
//    public static final String BASE_URL = "http://183.177.126.33:6560";
    public static final String BASE_URL = "http://202.65.157.254:6560";  //Temp url

    //Live Server
//    public static final String BASE_URL = "https://ictsbm.com:30443";


    public static final String CONTENT_TYPE = "application/json";
    public static final String EMP_TYPE_ADMIN = "A";

    public static final String EMP_TYPE = "EmpType";
    public static final String USER_ID = "userId";
    public static final String APP_ID = "appId";
    public static final String IS_LOGIN = "IsLoggedIn";

    private static final String EMP_SERVER_DATE_FORMATE = "dd-MM-yyyy";

    private static final String TITLE_DATE_FORMATE = "dd MMM yyyy";

    private static final String SEMI_MONTH_FORMATE = "MMM";
    private static final String DATE_VALUE_FORMATE = "dd";
    public static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    public static final String SERVER_DATE_FORMATE_LOCAL = "yyyy-MM-dd";

    private static final String SERVER_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_TIME_24HR_FORMATE = "HH:mm";

    public static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMATE_LOCAL = "yyyy-MM-dd HH:mm:ss.SSS";


    public interface PREFS {
        String  APP_ID = "appId";
    }


    public static String serverDateFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static String getServerTime() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }
    public static String getLocalDate() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeWithMilliesSecond() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeLocal() {

        SimpleDateFormat format = new SimpleDateFormat(MainUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getTitleDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDate(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractMonth(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDate(String date, String fromFormat, String toFormat) {

        SimpleDateFormat providedFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);

        try {
            return requiredFormat.format(providedFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEmpTimeLineFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_TIME_24HR_FORMATE, Locale.ENGLISH);
        SimpleDateFormat timelineFormat = new SimpleDateFormat(SERVER_TIME_FORMATE, Locale.ENGLISH);

        try {
            return timelineFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTitleDateFormatEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}
