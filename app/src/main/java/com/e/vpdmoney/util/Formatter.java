package com.e.vpdmoney.util;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    private static SimpleDateFormat formatter;

    public static String formatDate(Date date) {
        formatter = new SimpleDateFormat("EEE, d MMM Y");
        return formatter.format(date);
    }
    public static String formatDate(String strDate, String pattern) throws ParseException{
        formatter = new SimpleDateFormat(pattern);
        return formatter.format(stringToDate(strDate));
    }

    public static String formatDate(Date strDate, String pattern) throws ParseException{
        formatter = new SimpleDateFormat(pattern);
        return formatter.format(strDate);
    }
    public static String formatTime(String timeStr) throws ParseException {
       SimpleDateFormat simpleDateFormat= new    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") /*SimpleDateFormat("H:mm")*/;

       Date date= simpleDateFormat.parse(timeStr);
       return  new SimpleDateFormat("hh:mm a").format(date);
    }

    public static  String formatTimH(String timeStr) throws  ParseException{
        SimpleDateFormat  simpleDateFormat= new SimpleDateFormat("H");
        Date date= simpleDateFormat.parse(timeStr);
        return  new SimpleDateFormat("K:mm a").format(date);
    }
    public static String formatTimeBM(String timeStr) throws ParseException {
        SimpleDateFormat  simpleDateFormat= new SimpleDateFormat("K:mm a");
        Date date= simpleDateFormat.parse(timeStr);
        return  new SimpleDateFormat("mm").format(date);
    }

    public static String formatTimeBH(String timeStr) throws ParseException {
        SimpleDateFormat  simpleDateFormat= new SimpleDateFormat("K:mm a");
        Date date= simpleDateFormat.parse(timeStr);
        return  new SimpleDateFormat("H").format(date);
    }

    public static String formatDate(String dateStr) throws ParseException {
        formatter = new SimpleDateFormat("EEE, d MMM y");
        return formatter.format(stringToDate(dateStr));
    }

    public static String formatDateNoDay(String dateStr) throws ParseException {
        formatter = new SimpleDateFormat(" d MMM y");
        return formatter.format(stringToDate(dateStr));
    }
    public static String formatDayMonth(Date date) {
        formatter = new SimpleDateFormat("d MMM");
        return formatter.format(date);
    }
    public static String formatDay(Date date) {
        formatter = new SimpleDateFormat("d");
        return formatter.format(date);
    }
    public static String formatMonth(Date date) {
        formatter = new SimpleDateFormat("MMM");
        return formatter.format(date);
    }

    public static String formatYear(Date date) {
        formatter = new SimpleDateFormat("Y");
        return formatter.format(date);
    }
    public static String formatMonth(int m) {
        m = m -1;
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[m];
    }

    public static String bytesToString(byte[] bytes) {
        String string = "";
        string =  new String(bytes, StandardCharsets.UTF_8);
        return string;
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        String pattern = "";
        if (dateStr.contains("-"))
            pattern = "yyyy-MM-dd";
        else if (dateStr.contains("/"))
            pattern = "dd/MM/yyyy";
        DateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.parse(dateStr);
    }

    public static String getMonthStr(String month){

        Calendar cal=Calendar.getInstance();

        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        int monthnum=Integer.parseInt(month);
        cal.set(Calendar.MONTH,monthnum);
        String month_name = month_date.format(cal.getTime());
        return month_name;

    }

}
