package com.preethzcodez.ecommerce.utils;

public class Constants {

    public static String appFolder;

    public static final String USER_API = "api";

    public static final String VERSION_API = "v2";

    public static final String URL_API = "http://api.detta.com.ua:8091";

    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    public static final String AUTH_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/user/";
    public static final String NEWPOST_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/newpost/";
    public static final String CLIENTS_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/clients/";
    public static final String ORDER_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/order/";
    public static final String PRODUCTS_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/products/";
    public static final String PRODUCT_INFO_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/product/";
    public static final String PRODUCTS_PRICE_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/price/";
    public static final String DISCONTS_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/discounts/";
    public static final String SET_DISCONTS_PRODU_URL = URL_API + "/" + USER_API + "/hs/" + VERSION_API + "/order/discont/";

    // Bundle Argument Keys
    public static final String CAT_ID_KEY = "category_id";
    public static final String PRODUCT_ID_KEY = "product_id";
    public static final String DELIVERY_ID_KEY = "delivery_id";
    public static final String CAT_KEY = "subcategories";
    public static final String CAT_LEVEL = "level";
    public static final String TITLE = "title";
    public static final String SEARCH_TEXT = "search_title";
    public static final String ORDER_ID = "order_id";

    // Session Key
    public static final String SESSION_EMAIL = "session_email";
    public static final String SESSION_PASSWORD = "session_password";
    public static final String SESSION_CLIENT_CODE = "session_client_code";
    public static final String SESSION_CLIENT_POSITION = "session_client_position";
    public static final String SESSION_LOADING_PRICE = "session_loading_price";

    // Fragment Tags
    public static final String FRAG_HOME = "HOME";
    public static final String FRAG_SUBCAT = "SUB_CAT";
    public static final String FRAG_PDT = "PRODUCTS";
    public static final String FRAG_CLIENTS = "CLIENTS";
    public static final String FRAG_SEARCH = "SEARCH";
    public static final String FRAG_ACCOUNT = "ACCOUNT";

    //Params app
    public static final String DATA_UPDATE = "DATA_UPDATE";
    public static final String ORDER_SHIPPING_DATE = "ORDER_SHIPPING_DATE";
    public static final String ORDER_ADDRESS_CODE = "ORDER_ADDRESS_CODE";
    public static final String ORDER_IS_EDIT = "ORDER_IS_EDIT";
    public static final String ORDER_CLIENT = "ORDER_CLIENT";
    public static final String ORDER_COMMENT = "ORDER_COMMENT";
    public static final String TYPE_CATALOG = "TYPE_CATALOG";
}
