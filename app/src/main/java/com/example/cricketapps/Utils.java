package com.example.cricketapps;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Utils {
    private static final String TAGC =  Utils.class.getName();

    public static final int REQUEST_CODE_SMALL_IMAGE_PICKER =  1001;
    public static final int REQUEST_CODE_BIG_IMAGE_PICKER =  1002;

    public static DatabaseReference mDatabase;

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0 || str.equals("null");
    }

    // redirecting user to PlayStore
    public static void redirectToPlayStore(Context context) {
        final String appPackageName = "com.uclicks";
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException exception) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static boolean isBlankOrNull(String s) {
        return s == null || s.trim().equals("") || s.trim().equals("null");
    }

    public static boolean isUriBlankOrNull(Uri s) {
        return s == null || s.equals(Uri.EMPTY);
    }

    public static String isStringBlankOrNull(String s) {
        String blankString = "";
        if (s == null || s.trim().equals("") || s.trim().equals("null")) {
            return blankString;
        } else {
            blankString = s;
        }
        return blankString;
    }

    public static int isIntNull(Integer s) {
        Integer value = s;
        if (s == null) {
            value = 0;
        }
        return value;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getTime(int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static String printDate(String userDateFormat, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(userDateFormat);
        return dateFormat.format(date);
    }

    public static String printDateTime(String userDateFormat, String userTimeFormat, Date date) {
        if ( Utils.isBlankOrNull(userTimeFormat)) {
            userTimeFormat = "hh:mm a";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(userDateFormat + " " + userTimeFormat);
        return dateFormat.format(date);
    }

    public static String printTime(String userTimeFormat, Date date) {
        if ( Utils.isBlankOrNull(userTimeFormat)) {
            userTimeFormat = "hh:mm a";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(userTimeFormat);
        return dateFormat.format(date);
    }

    public static String printTime(String userTimeFormat, Date date, boolean IsUTC) {
        if ( Utils.isBlankOrNull(userTimeFormat)) {
            userTimeFormat = "hh:mm a";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(userTimeFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getISODateTimeFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getSimpleTimeFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getISOAbsoluteDate(Date date) {
        // this is sueful for date fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getISOAbsoluteTime() {
        // this is sueful for date fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getISOAbsoluteDate() {
        // ISO-8601 combined date and time format
        //2021-10-28'T'
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getISOAbsoluteCurrentDate() {
        // ISO-8601 combined date and time format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static int getAge(int year, int month, int day, int hourOfDay, int minite) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day, hourOfDay, minite);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

//        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();

        return age;
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static SortedMap<Currency, Locale> currencyLocaleMap;

    static {
        currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
            }
        });
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyLocaleMap.put(currency, locale);
            } catch (Exception e) {
            }
        }
    }


    public static String getCurrencySymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }

    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean isPro(Context context) {
        boolean monthly = false, yearly = false;
//        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
//        if (sharedpreferences.contains(Constants.IS_MONTHLY)) {
//            monthly = sharedpreferences.getBoolean(Constants.IS_MONTHLY, false);
//        }
//
//        if (sharedpreferences.contains(Constants.IS_YEARLY)) {
//            yearly = sharedpreferences.getBoolean(Constants.IS_YEARLY, false);
//        }

        return monthly || yearly;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String getTag() {
        String tag = "";
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            if (ste[i].getMethodName().equals("getTag")) {
                tag = "(" + ste[i + 1].getFileName() + ":" + ste[i + 1].getLineNumber() + ")";
            }
        }
        return tag;
    }

    public static String formatsNumber(double number) {

        return new DecimalFormat("#,###.00").format(number);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

//    public static File compressFile(Context mContext, File file){
//
//        try {
//
//            File compressFile = new Compressor(mContext)
//                    .setQuality(40)
//                    .compressToFile(file);
//            return compressFile;
//
//        } catch (IOException e) {
//            return file;
//        }
//    }

    public static List<String> getStringArray(String str) {
        String string = str.trim().replace("/ +(?= )/g", "");
        List<String> arr = new ArrayList<>();
        for (int i = 0; i < string.trim().length(); i++) {
            arr.add(string.substring(0, i + 1).toLowerCase());
        }

        String[] arr1 = string.split(" ");

        for (int i = 1; i < arr1.length; i++) {
            String string1 = arr1[i];
            for (int j = 0; j < string1.trim().length(); j++) {
                arr.add(string1.substring(0, j + 1).toLowerCase());
            }
        }

        return arr;
    }

    public static String getBetweenStrings(
            String text,
            String textFrom,
            String textTo) {

        String result = "";

        // Cut the beginning of the text to not occasionally meet a
        // 'textTo' value in it:
        result =
                text.substring(
                        text.indexOf(textFrom) + textFrom.length()
                );

        // Cut the excessive ending of the text:
        result =
                result.substring(
                        0,
                        result.indexOf(textTo));

        return result;
    }

    public static Bitmap prepareBitmap(final Bitmap sampleBitmap,
                                       final WallpaperManager wallpaperManager) {
        Bitmap changedBitmap = null;
        final int heightBm = sampleBitmap.getHeight();
        final int widthBm = sampleBitmap.getWidth();
        final int heightDh = wallpaperManager.getDesiredMinimumHeight();
        final int widthDh = wallpaperManager.getDesiredMinimumWidth();
        if (widthDh > widthBm || heightDh > heightBm) {
            final int xPadding = Math.max(0, widthDh - widthBm) / 2;
            final int yPadding = Math.max(0, heightDh - heightBm) / 2;
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            final int[] pixels = new int[widthBm * heightBm];
            sampleBitmap.getPixels(pixels, 0, widthBm, 0, 0, widthBm, heightBm);
            changedBitmap.setPixels(pixels, 0, widthBm, xPadding, yPadding,
                    widthBm, heightBm);
        } else if (widthBm > widthDh || heightBm > heightDh) {
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            int cutLeft = 0;
            int cutTop = 0;
            int cutRight = 0;
            int cutBottom = 0;
            final Rect desRect = new Rect(0, 0, widthDh, heightDh);
            Rect srcRect = new Rect();
            if (widthBm > widthDh) { // crop width (left and right)
                cutLeft = (widthBm - widthDh) / 2;
                cutRight = (widthBm - widthDh) / 2;
                srcRect = new Rect(cutLeft, 0, widthBm - cutRight, heightBm);
            } else if (heightBm > heightDh) { // crop height (top and bottom)
                cutTop = (heightBm - heightDh) / 2;
                cutBottom = (heightBm - heightDh) / 2;
                srcRect = new Rect(0, cutTop, widthBm, heightBm - cutBottom);
            }
            final Canvas canvas = new Canvas(changedBitmap);
            canvas.drawBitmap(sampleBitmap, srcRect, desRect, null);

        } else {
            changedBitmap = sampleBitmap;
        }
        return changedBitmap;
    }

    public static void setY(View v, int y) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.setMargins(params.leftMargin, y, params.rightMargin, params.bottomMargin);
        v.setLayoutParams(params);
    }

    public static void setX(View v, int x) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.setMargins(x, params.topMargin, params.rightMargin, params.bottomMargin);
        v.setLayoutParams(params);
    }

    public static int getX(View v) {
        Rect myViewRect = new Rect();
        v.getGlobalVisibleRect(myViewRect);
        return myViewRect.left;
    }

    public static int getY(View v) {
        Rect myViewRect = new Rect();
        v.getGlobalVisibleRect(myViewRect);
        return v.getTop();
    }

    public static void setAlpha(View v, float alpha) {
        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            v.startAnimation(animation);
        } else
            v.setAlpha(alpha);
    }

    public static Animation alphaAnim(int duration, float from, float to) {
        final AlphaAnimation animation = new AlphaAnimation(from, to);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        return animation;
    }

    public static Date getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    public static Date getWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
