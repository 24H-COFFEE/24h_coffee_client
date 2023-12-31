package com.example.a24h_coffee_client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.a24h_coffee_client.constant.AppConstants;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FormatUtils {
    public static String formatCurrency(double price) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedPrice = currencyFormat.format(price);
        formattedPrice = formattedPrice.replace("₫", "").trim() + "₫";
        return formattedPrice.replaceAll("\\s", "");
    }

    public static double parseCurrency(String formattedPrice) {

        formattedPrice = formattedPrice.replace("₫", "").replaceAll("\\.", "").trim().replaceAll(",", ".");
        double price = 0;
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            price = Objects.requireNonNull(numberFormat.parse(formattedPrice)).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }

    public static String formatDateCreate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }

    public static Date parseDateCreate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("vi", "VN"));
        return dateFormat.parse(dateString);
    }

    public static String formatDateOfBirth(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }


    public static String formatDatePayment(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }

    public static String formatTimeOut(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }


    public static String formatID(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

}
