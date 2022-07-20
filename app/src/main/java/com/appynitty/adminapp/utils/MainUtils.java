package com.appynitty.adminapp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.BatteryManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.pixplicity.easyprefs.library.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainUtils {
    public static final String LAT = "lat";
    public static final String LONG = "lan";
    public static final long LOCATION_INTERVAL_MINUTES = 10 * 60 * 1000;
    private static final String TAG = "MainUtils";
    //Staging Server
//    public static final String BASE_URL = "http://183.177.126.33:6560";
//    public static final String BASE_URL = "http://202.65.157.254:5049/";
    public static final String BASE_URL = "http://202.65.157.254:6560";  //Temp url
//    public static final String BASE_URL = "http://202.65.157.253:6560";  //Main url

    //Live Server
//    public static final String BASE_URL = "https://ictsbm.com:30443";
    public static final String CONTENT_TYPE = "application/json";
    public static final String EMP_TYPE_ADMIN = "A";
    public static final String EMP_TYPE = "EmpType";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String APP_ID = "appId";
    public static final String EMP_ID = "qrEmpId";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String IS_ATTENDANCE_OFF = "isAttendenceOff";
    public static final String IS_ATTENDANCE_ON = "isAttendanceOn";
    public static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    public static final String SERVER_DATE_FORMATE_LOCAL = "yyyy-MM-dd";
    public static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMATE_LOCAL = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_ERROR = "error";
    private static final String EMP_SERVER_DATE_FORMATE = "dd-MM-yyyy";
    private static final String EMP_DATE_FORMAT = "dd/MM/yyyy";
    private static final String EMP_DATE_FORMAT_DASH = "dd-MM-yyyy";
    private static final String TITLE_DATE_FORMATE = "dd MMM yyyy";
    private static final String SEMI_MONTH_FORMATE = "MMM";
    private static final String DATE_VALUE_FORMATE = "dd";
    private static final String SERVER_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_TIME_24HR_FORMATE = "HH:mm";
    public static Context context;
    public static Application mainApplicationConstant;

    public static String getDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.SERVER_TIME_FORMATE, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getDateDash() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat(MainUtils.EMP_DATE_FORMAT_DASH, Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
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

    public static String serverDate(String date) {

        SimpleDateFormat local = new SimpleDateFormat(MainUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(MainUtils.EMP_DATE_FORMAT, Locale.ENGLISH);
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

    public interface PREFS {
        String APP_ID = "appId";
    }

    public static int getBatteryStatus() {
        BatteryManager batteryManager = (BatteryManager) mainApplicationConstant.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static boolean isMyServiceRunning(Application application, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) application.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d(TAG, service.toString());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Activity mActivity) {
        ActivityManager manager = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void showDialog(Context context, String message, DialogInterface.OnClickListener positiveListener, @Nullable DialogInterface.OnClickListener negativeListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", positiveListener);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", negativeListener);
        alertDialog.show();
    }

    public static Boolean isOnDuty() {

        if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF))
            return false;
        else
            return true;
    }
}
