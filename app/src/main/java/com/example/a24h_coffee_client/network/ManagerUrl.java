package com.example.a24h_coffee_client.network;

public class ManagerUrl {
    public static final String BASE_URL = "http://172.20.10.5:3000/api/";

    // account
    public static final String LOGIN = "login";
    // table
    public static final String READ_TABLE = "tables";
    // product
    public static final String READ_PRODUCT = "products";
    // banner
    public static final String READ_BANNER = "banners";
    // category
    public static final String READ_CATEGORY = "category";
    // bill
    public static final String INSERT_BILL = "insert/bill";
    // notification
    // bill detail
    public static final String READ_TABLE_BILL = "table/bill/{tableID}";
    public static final String READ_DETAIL_BILL = "detail/bills/{billID}";
}
