package com.preethzcodez.ecommerce.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.internal.LinkedTreeMap;
import com.preethzcodez.ecommerce.comparators.SortByDate;
import com.preethzcodez.ecommerce.json.OrderGiftsJSON;
import com.preethzcodez.ecommerce.json.OrderJSON;
import com.preethzcodez.ecommerce.objects.Address;
import com.preethzcodez.ecommerce.objects.Delivery;
import com.preethzcodez.ecommerce.objects.DiscontType;
import com.preethzcodez.ecommerce.objects.FilterValue;
import com.preethzcodez.ecommerce.objects.Marker;
import com.preethzcodez.ecommerce.objects.OrdersDate;
import com.preethzcodez.ecommerce.objects.PicsDate;
import com.preethzcodez.ecommerce.pojo.Cart;
import com.preethzcodez.ecommerce.pojo.CartStore;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.pojo.CityNP;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Count;
import com.preethzcodez.ecommerce.pojo.Discont;
import com.preethzcodez.ecommerce.pojo.Order;
import com.preethzcodez.ecommerce.pojo.Pics;
import com.preethzcodez.ecommerce.pojo.PointNP;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.pojo.RegNP;
import com.preethzcodez.ecommerce.pojo.Store;
import com.preethzcodez.ecommerce.pojo.TypeDiscont;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.pojo.Variant;
import com.preethzcodez.ecommerce.pojo.VariantOrder;
import com.preethzcodez.ecommerce.utils.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class DB_Handler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "E-Commerce";

    // Column Names Static Variables
    private static final String ID = "id";
    private static final String CAT_ID = "category_id";
    private static final String CAT_LEVEL = "category_level";
    private static final String SKU = "sku";
    private static final String BARCODE = "barcode";
    private static final String XML_ID = "xml_id";
    private static final String SUB_ID = "subcategory_id";
    private static final String PDT_ID = "product_id";
    private static final String DOP_CAT_ID = "dop_category_id";
    private static final String PDT_GIFT = "product_gift";
    private static final String VAR_ID = "variant_id";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_TYPE = "client_type";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String NAME = "name";
    private static final String IS_DOP = "is_dop";

    private static final String DEPT = "dept";
    private static final String STORE_ID = "store_id";
    private static final String ORDER_NUMBER = "order_number";
    private static final String ORDER_NUMBER_1C = "order_number_1c";
    private static final String ORDER_DATE = "order_date";
    private static final String ORDER_TOTAL = "order_total";
    private static final String ORDER_TOTAL_1C = "order_total_1c";
    private static final String ORDER_IS_BASE = "order_is_base";
    private static final String ORDER_COMMENT = "order_comment";
    private static final String ORDER_STATUS = "order_status";

    //новая почта
    private static final String CODE_NP = "code_np";
    private static final String REGION_CODE_NP = "region_code_np";
    private static final String CITY_CODE_NP = "city_code_np";
    private static final String WEIGHT = "weight";

    private static final String ORDER_CLIENT = "order_client";
    private static final String ORDER_DISCONT = "order_discont";
    private static final String ORDER_SHIPPING_DATE = "order_shipping_date";
    private static final String ORDER_IS_BANK = "order_is_bank";
    private static final String PICS_URL = "pics_url";
    private static final String PICS_DATE = "pics_date";
    private static final String PICS_TIME = "pics_time";
    private static final String GPS_LAT = "gps_lat";
    private static final String GPS_LON = "gps_lon";
    private static final String PRICE_VALUE = "price_value";
    private static final String PRICE_VALUE_1C = "price_value_1c";
    private static final String PRICE_NAME = "price_name";
    private static final String PRICE_ID = "price_id";
    private static final String OPT_ID = "opt_id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String MOBILE = "mobile_no";
    private static final String QUANTITY = "quantity";
    private static final String QUANTITY_1C = "quantity_1c";
    private static final String MANUAL_DISCONT = "manual_discont";
    private static final String AUTO_DISCONT = "auto_discont";
    private static final String VIEW_COUNT = "view_count";
    private static final String ORDER_COUNT = "order_count";
    private static final String SHARE_COUNT = "share_count";
    private static final String COUNT_STORE = "count_store";
    private static final String OPT_VALUE = "opt_value";
    private static final String OPT_NAME = "opt_name";
    private static final String DISC_TYPE = "disc_type";

    private static final String ADDRESS_VID = "address_vid";
    private static final String ADDRESS_CODE = "address_code";
    private static final String ADDRESS_NAME = "address_name";

    // Table Names Static Variables
    private static final String UserTable = "user";
    private static final String DiscTable = "discounts";
    private static final String DiscTypeTable = "discounts_type";
    private static final String CategoriesTable = "categories";
    private static final String ClientTable = "clients";
    private static final String SubCategoriesTable = "subcategories";
    private static final String ProductsTable = "products";
    private static final String AccessProductsTable = "products_access";
    private static final String VariantsTable = "variants";
    private static final String WishListTable = "wishlist";
    private static final String OrderHistoryTable = "order_history";
    private static final String ShoppingCartTable = "shopping_cart";
    private static final String StorageTable = "storage";
    private static final String CountsTable = "counts";
    private static final String PriceVariantTable = "price_variant";
    private static final String PricesTable = "prices";
    private static final String OptionsTable = "options";
    private static final String VariantOptionsTable = "variant_options";
    private static final String OrdersTable = "orders";


    private static final String AddressTable = "client_address";
    private static final String PicsTable = "clients_pics";
    private static final String ParamsTable = "params_app";


    private static final String RegionNPTable = "region_np";
    private static final String CityNPTable = "city_np";
    private static final String PointNPTable = "point_np";

    //Доставка
    private static final String OrderDeliveryTable = "order_delivery";
    private static final String ORDER_IS_LOGIST = "order_logist";
    private static final String ORDER_IS_PICKUP = "order_pickup";
    private static final String ORDER_IS_NEW_POST = "order_new_post";
    private static final String ORDER_CITY_ID = "order_city_id";
    private static final String ORDER_POINT_ID = "order_point_id";
    private static final String ORDER_DELIVERY_ADDRESS = "order_delivery_address";

    private static final String CREATE_ACCESS_PDT_TABLE = "CREATE TABLE " + AccessProductsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " TEXT,"
            + OPT_ID + " TEXT,"
            + CLIENT_TYPE + " TEXT"
            + ")";

    private static final String CREATE_ADDRESS_TABLE = "CREATE TABLE " + AddressTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CLIENT_ID + " TEXT,"
            + ADDRESS_CODE + " TEXT,"
            + ADDRESS_VID + " TEXT,"
            + ADDRESS_NAME + " TEXT"
            + ")";

    private static final String CREATE_REGION_NP_TABLE = "CREATE TABLE " + RegionNPTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + OPT_ID + " TEXT,"
            + OPT_NAME + " TEXT,"
            + CODE_NP + " TEXT"
            + ")";

    private static final String CREATE_CITY_NP_TABLE = "CREATE TABLE " + CityNPTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + OPT_ID + " TEXT,"
            + OPT_NAME + " TEXT,"
            + CODE_NP + " TEXT,"
            + REGION_CODE_NP + " TEXT"
            + ")";

    private static final String CREATE_POINT_NP_TABLE = "CREATE TABLE " + PointNPTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CODE_NP + " TEXT,"
            + OPT_ID + " TEXT,"
            + OPT_NAME + " TEXT,"
            + WEIGHT + " INTEGER,"
            + CITY_CODE_NP + " TEXT"
            + ")";
    //конец новая почта

    private static final String CREATE_ORDER_DELIVERY_TABLE = "CREATE TABLE " + OrderDeliveryTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + ORDER_IS_LOGIST + " INTEGER,"
            + ORDER_IS_PICKUP + " INTEGER,"
            + ORDER_IS_NEW_POST + " INTEGER,"
            + ORDER_NUMBER + " TEXT,"
            + ORDER_CITY_ID + " TEXT,"
            + ORDER_POINT_ID + " TEXT,"
            + ORDER_DELIVERY_ADDRESS + " TEXT"
            + ")";

    // Create Params Table
    private static final String CREATE_PARAMS_TABLE = "CREATE TABLE " + ParamsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + OPT_NAME + " TEXT,"
            + OPT_VALUE + " TEXT"
            + ")";

    // Create Orders Table
    private static final String CREATE_ORDERS_TABLE = "CREATE TABLE " + OrdersTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + ORDER_NUMBER + " TEXT NOT NULL,"
            + ORDER_NUMBER_1C + " TEXT,"
            + ORDER_DATE + " TEXT NOT NULL,"
            + STORE_ID + " TEXT,"
            + ORDER_TOTAL + " DOUBLE DEFAULT 0.0,"
            + ORDER_TOTAL_1C + " DOUBLE DEFAULT 0.0,"
            + ORDER_CLIENT + " TEXT NOT NULL,"
            + ORDER_COMMENT + " TEXT,"
            + ORDER_IS_BASE + " INTEGER DEFAULT 0,"
            + ORDER_STATUS + " INTEGER,"
            + ORDER_DISCONT + " TEXT,"
            + ORDER_SHIPPING_DATE + " TEXT,"
            + ADDRESS_CODE + " TEXT,"
            + ORDER_IS_BANK + " INTEGER DEFAULT 0"
            + ")";

    // Create Count Table
    private static final String CREATE_VARIANT_OPTIONS_TABLE = "CREATE TABLE " + VariantOptionsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + VAR_ID + " TEXT NOT NULL,"
            + OPT_ID + " TEXT NOT NULL,"
            + OPT_VALUE + " TEXT"
            + ")";

    // Create Disc Table
    private static final String CREATE_DISC_TABLE = "CREATE TABLE " + DiscTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + XML_ID + " TEXT,"
            + NAME + " TEXT,"
            + DISC_TYPE + " TEXT,"
            + CLIENT_TYPE + " TEXT"
            + ")";

    // Create Type Disc Table
    private static final String CREATE_DISC_TYPE_TABLE = "CREATE TABLE " + DiscTypeTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + XML_ID + " TEXT,"
            + NAME + " TEXT"
            + ")";

    // Create Pisc Table
    private static final String CREATE_PICS_TABLE = "CREATE TABLE " + PicsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CLIENT_ID + " TEXT NOT NULL,"
            + PICS_URL + " TEXT NOT NULL,"
            + GPS_LAT + " TEXT,"
            + GPS_LON + " TEXT,"
            + PICS_DATE + " TEXT,"
            + PICS_TIME + " TEXT"
            + ")";

    // Create Count Table
    private static final String CREATE_COUNTS_TABLE = "CREATE TABLE " + CountsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + STORE_ID + " TEXT NOT NULL,"
            + VAR_ID + " TEXT NOT NULL,"
            + COUNT_STORE + " DOUBLE DEFAULT 0.0" + ")";

    // Create Options Table
    private static final String CREATE_OPTIONS_TABLE = "CREATE TABLE " + OptionsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + OPT_ID + " TEXT NOT NULL,"
            + NAME + " TEXT NOT NULL"
            + ")";

    // Create Price Variant Table
    private static final String CREATE_PRICE_VARIANT_TABLE = "CREATE TABLE " + PriceVariantTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PRICE_ID + " TEXT NOT NULL,"
            + VAR_ID + " TEXT NOT NULL,"
            + PRICE_VALUE + " DOUBLE DEFAULT 0.0" + ")";

    // Create Storage Table
    private static final String CREATE_STORAGE_TABLE = "CREATE TABLE " + StorageTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + STORE_ID + " TEXT NOT NULL,"
            + NAME + " TEXT NOT NULL" + ")";

    // Create Prices Table
    private static final String CREATE_PRICES_TABLE = "CREATE TABLE " + PricesTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PRICE_ID + " INTEGER NOT NULL,"
            + PRICE_NAME + " TEXT NOT NULL" + ")";

    // Create User Table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserTable + "("
            + EMAIL + " TEXT PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + MOBILE + " TEXT NOT NULL,"
            + PASSWORD + " TEXT NOT NULL" + ")";

    // Create Categories Table
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoriesTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT NOT NULL,"
            + IS_DOP + " INTEGER DEFAULT 0,"
            + CAT_LEVEL + " INTEGER"
            + ")";

    // Create Subcategories Mapping Table
    private static final String CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE " + SubCategoriesTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + CAT_ID + " INTEGER NOT NULL,"
            + IS_DOP + " INTEGER DEFAULT 0,"
            + SUB_ID + " INTEGER NOT NULL" + ")";

    // Create Products Table
    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " TEXT NOT NULL,"
            + CAT_ID + " INTEGER NOT NULL,"
            + DOP_CAT_ID + " TEXT,"
            + NAME + " TEXT NOT NULL,"
            + VIEW_COUNT + " INTEGER NOT NULL,"
            + ORDER_COUNT + " INTEGER NOT NULL,"
            + SHARE_COUNT + " INTEGER NOT NULL" + ")";

    // Create Variants Table
    private static final String CREATE_VARIANTS_TABLE = "CREATE TABLE " + VariantsTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + VAR_ID + " TEXT NOT NULL,"
            + SKU + " TEXT,"
            + BARCODE + " TEXT,"
            + PDT_ID + " TEXT" + ")";

    // Create Order History Table
    private static final String CREATE_ORDER_HISTORY_TABLE = "CREATE TABLE " + OrderHistoryTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " TEXT NOT NULL,"
            + VAR_ID + " TEXT NOT NULL,"
            + QUANTITY + " INTEGER NOT NULL,"
            + QUANTITY_1C + " INTEGER,"
            + PRICE_VALUE + " DOUBLE NOT NULL,"
            + PRICE_VALUE_1C + " DOUBLE,"
            + MANUAL_DISCONT + " INTEGER,"
            + AUTO_DISCONT + " INTEGER,"
            + ORDER_NUMBER + " TEXT NOT NULL,"
            + STORE_ID + " TEXT NOT NULL,"
            + PDT_GIFT + " INTEGER"
            + ")";

    // Create Shopping Cart Table
    private static final String CREATE_SHOPPING_CART_TABLE = "CREATE TABLE " + ShoppingCartTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " TEXT NOT NULL,"
            + VAR_ID + " TEXT NOT NULL,"
            + QUANTITY + " INTEGER NOT NULL,"
            + PRICE_VALUE + " DOUBLE,"
            + STORE_ID + " TEXT,"
            + ORDER_CLIENT + " TEXT"
            + ")";

    // Create Wish List Table
    private static final String CREATE_WISHLIST_TABLE = "CREATE TABLE " + WishListTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + PDT_ID + " TEXT NOT NULL,"
            + EMAIL + " TEXT NOT NULL" + ")";

    // Create Clients Table
    private static final String CREATE_CLIENTS_TABLE = "CREATE TABLE " + ClientTable + "("
            + ID + " INTEGER PRIMARY KEY,"
            + XML_ID + " TEXT NOT NULL,"
            + NAME + " TEXT,"
            + DEPT + " DOUBLE,"
            + PRICE_ID + " INTEGER,"
            + CLIENT_TYPE + " TEXT,"
            + ADDRESS + " TEXT,"
            + PHONE + " TEXT"
            + ")";

    public DB_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_VARIANTS_TABLE);
        db.execSQL(CREATE_SUBCATEGORIES_TABLE);
        db.execSQL(CREATE_WISHLIST_TABLE);
        db.execSQL(CREATE_ADDRESS_TABLE);
        db.execSQL(CREATE_CLIENTS_TABLE);
        db.execSQL(CREATE_COUNTS_TABLE);
        db.execSQL(CREATE_STORAGE_TABLE);
        db.execSQL(CREATE_PRICES_TABLE);
        db.execSQL(CREATE_PRICE_VARIANT_TABLE);
        db.execSQL(CREATE_OPTIONS_TABLE);
        db.execSQL(CREATE_VARIANT_OPTIONS_TABLE);
        db.execSQL(CREATE_DISC_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SHOPPING_CART_TABLE);
        db.execSQL(CREATE_ORDER_HISTORY_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_PICS_TABLE);
        db.execSQL(CREATE_PARAMS_TABLE);
        //новая почта
        db.execSQL(CREATE_REGION_NP_TABLE);
        db.execSQL(CREATE_CITY_NP_TABLE);
        db.execSQL(CREATE_POINT_NP_TABLE);
        db.execSQL(CREATE_DISC_TYPE_TABLE);
        db.execSQL(CREATE_ACCESS_PDT_TABLE);
        db.execSQL(CREATE_ORDER_DELIVERY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldIdBase, int newIdBase) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserTable);
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable);
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTable);
        db.execSQL("DROP TABLE IF EXISTS " + VariantsTable);
        db.execSQL("DROP TABLE IF EXISTS " + SubCategoriesTable);
        db.execSQL("DROP TABLE IF EXISTS " + OrderHistoryTable);
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingCartTable);
        db.execSQL("DROP TABLE IF EXISTS " + WishListTable);
        db.execSQL("DROP TABLE IF EXISTS " + AddressTable);
        db.execSQL("DROP TABLE IF EXISTS " + ClientTable);
        db.execSQL("DROP TABLE IF EXISTS " + StorageTable);
        db.execSQL("DROP TABLE IF EXISTS " + CountsTable);
        db.execSQL("DROP TABLE IF EXISTS " + PricesTable);
        db.execSQL("DROP TABLE IF EXISTS " + PriceVariantTable);
        db.execSQL("DROP TABLE IF EXISTS " + OptionsTable);
        db.execSQL("DROP TABLE IF EXISTS " + VariantOptionsTable);
        db.execSQL("DROP TABLE IF EXISTS " + OrdersTable);
        db.execSQL("DROP TABLE IF EXISTS " + PicsTable);
        db.execSQL("DROP TABLE IF EXISTS " + DiscTable);
        db.execSQL("DROP TABLE IF EXISTS " + ParamsTable);
        db.execSQL("DROP TABLE IF EXISTS " + RegionNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + CityNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + PointNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + OrderDeliveryTable);
        db.execSQL("DROP TABLE IF EXISTS " + AccessProductsTable);
        db.execSQL("DROP TABLE IF EXISTS " + DiscTypeTable);
        // Create tables again
        onCreate(db);
    }

    public void deleteData(){
        update1CInfo();
    }

    private void update1CInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        //удаляем
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable);
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTable);
        db.execSQL("DROP TABLE IF EXISTS " + VariantsTable);
        db.execSQL("DROP TABLE IF EXISTS " + SubCategoriesTable);
        db.execSQL("DROP TABLE IF EXISTS " + WishListTable);
        db.execSQL("DROP TABLE IF EXISTS " + AddressTable);
        db.execSQL("DROP TABLE IF EXISTS " + ClientTable);
        db.execSQL("DROP TABLE IF EXISTS " + StorageTable);
        db.execSQL("DROP TABLE IF EXISTS " + CountsTable);
        db.execSQL("DROP TABLE IF EXISTS " + PricesTable);
        db.execSQL("DROP TABLE IF EXISTS " + PriceVariantTable);
        db.execSQL("DROP TABLE IF EXISTS " + OptionsTable);
        db.execSQL("DROP TABLE IF EXISTS " + VariantOptionsTable);
        db.execSQL("DROP TABLE IF EXISTS " + DiscTable);
        //новая почта
        db.execSQL("DROP TABLE IF EXISTS " + RegionNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + CityNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + PointNPTable);
        db.execSQL("DROP TABLE IF EXISTS " + AccessProductsTable);
        db.execSQL("DROP TABLE IF EXISTS " + DiscTypeTable);
        //создаем
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_VARIANTS_TABLE);
        db.execSQL(CREATE_SUBCATEGORIES_TABLE);
        db.execSQL(CREATE_WISHLIST_TABLE);
        db.execSQL(CREATE_ADDRESS_TABLE);
        db.execSQL(CREATE_CLIENTS_TABLE);
        db.execSQL(CREATE_COUNTS_TABLE);
        db.execSQL(CREATE_STORAGE_TABLE);
        db.execSQL(CREATE_PRICES_TABLE);
        db.execSQL(CREATE_PRICE_VARIANT_TABLE);
        db.execSQL(CREATE_OPTIONS_TABLE);
        db.execSQL(CREATE_VARIANT_OPTIONS_TABLE);
        db.execSQL(CREATE_DISC_TABLE);
        //новая почта
        db.execSQL(CREATE_REGION_NP_TABLE);
        db.execSQL(CREATE_CITY_NP_TABLE);
        db.execSQL(CREATE_POINT_NP_TABLE);
        db.execSQL(CREATE_ACCESS_PDT_TABLE);
        db.execSQL(CREATE_DISC_TYPE_TABLE);
    }

    // Insert Categories
    public void insertCategories(int id, String name, int level, int is_dop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(CAT_LEVEL, level);
        values.put(IS_DOP, is_dop);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + CategoriesTable + " WHERE " + ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(CategoriesTable, values, ID + " = ?",
                    new String[]{String.valueOf(id)});
        } else {
            db.insert(CategoriesTable, null, values);
        }

    }

    // Insert Clients
    public void insertClient(String xml_id, String name, double dept, String price_id, String type, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(XML_ID, xml_id);
        values.put(NAME, name);
        values.put(DEPT, dept);
        values.put(PRICE_ID, price_id);
        values.put(CLIENT_TYPE, type);
        values.put(ADDRESS, address);
        values.put(PHONE, phone);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + ClientTable + " WHERE " + XML_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{xml_id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(ClientTable, values, XML_ID + " = ?", new String[]{xml_id});
        }else{
            db.insert(ClientTable, null, values);
        }

    }

    public void insertAccessProduct(String id, String opt_id, int client_type) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PDT_ID, id);
        values.put(OPT_ID, opt_id);
        values.put(CLIENT_TYPE, client_type);


        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + AccessProductsTable + " WHERE " + PDT_ID + "=?" + " AND " + OPT_ID + "=?" + " AND " + CLIENT_TYPE + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, opt_id, String.valueOf(client_type)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(AccessProductsTable, values, PDT_ID + "=?" + " AND " + OPT_ID + "=?" + " AND " + CLIENT_TYPE + "=?", new String[]{id,opt_id,String.valueOf(client_type)});
        }else{
            db.insert(AccessProductsTable, null, values);
        }

    }

    // Insert Region New Post
    public void insertRegionNP(String id, String name, String code) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OPT_ID, id);
        values.put(OPT_NAME, name);
        values.put(CODE_NP, code);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + RegionNPTable + " WHERE " + OPT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(RegionNPTable, values, OPT_ID + " = ?", new String[]{id});
        }else{
            db.insert(RegionNPTable, null, values);
        }

    }

    // Insert City New Post
    public void insertCityNP(String id, String name, String code, String id_region) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OPT_ID, id);
        values.put(OPT_NAME, name);
        values.put(CODE_NP, code);
        values.put(REGION_CODE_NP, id_region);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + CityNPTable + " WHERE " + CODE_NP + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(CityNPTable, values, CODE_NP + " = ?", new String[]{code});
        }else{
            db.insert(CityNPTable, null, values);
        }

    }

    // Insert City New Post
    public void insertPointNP(String name, String code, int weight, String id_city_code_nd) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OPT_NAME, name);
        values.put(CODE_NP, code);
        values.put(CITY_CODE_NP, id_city_code_nd);
        values.put(WEIGHT, weight);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + PointNPTable + " WHERE " + CODE_NP + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(PointNPTable, values, CODE_NP + " = ?", new String[]{code});
        }else{
            db.insert(PointNPTable, null, values);
        }

    }

    // Insert Disconts
    public void insertDiscont(String code, String name, String type, String clientType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(XML_ID, code);
        values.put(NAME, name);
        values.put(DISC_TYPE, type);
        values.put(CLIENT_TYPE, clientType);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + DiscTable + " WHERE " + XML_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(DiscTable, values, XML_ID + " = ?", new String[]{code});
        }else{
            db.insert(DiscTable, null, values);
        }

    }

    // Insert Disconts Type
    public void insertDiscontType(String code, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(XML_ID, code);
        values.put(NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + DiscTypeTable + " WHERE " + XML_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(DiscTypeTable, values, XML_ID + " = ?", new String[]{code});
        }else{
            db.insert(DiscTypeTable, null, values);
        }

    }

    public void insertParamsOrUpdate(String name, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OPT_NAME, name);
        values.put(OPT_VALUE, value);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + ParamsTable + " WHERE " + OPT_NAME + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(ParamsTable, values, OPT_NAME + " = ?", new String[]{name});
        }else{
            db.insert(ParamsTable, null, values);
        }
    }

    public String getParamByCode(String code) {

        String value = "";

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + ParamsTable + " WHERE " + OPT_NAME + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});
        if (cursor.moveToFirst()) {
            value = cursor.getString(cursor.getColumnIndex(OPT_VALUE));
        }
        cursor.close();

        return value;

    }

    public ArrayList<Address> getAddressesByClient(String client_id){

        ArrayList<Address> addressList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + AddressTable + " WHERE " + CLIENT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{client_id});

        Address addressClear = new Address();
        addressClear.setVid("");
        addressClear.setCode("");
        addressClear.setName("--- Выберите адрес доставки ---");
        addressList.add(addressClear);

        // перебрать все строки и добавить в список
        if (cursor.moveToFirst()) {
            do {
                String vid = cursor.getString(cursor.getColumnIndex(ADDRESS_VID));
                String code = cursor.getString(cursor.getColumnIndex(ADDRESS_CODE));
                String name = cursor.getString(cursor.getColumnIndex(ADDRESS_NAME));
                Address address = new Address();
                address.setVid(vid);
                address.setCode(code);
                address.setName(name);
                addressList.add(address);
            } while (cursor.moveToNext());
        }
        return addressList;
    }

    public void insertAddressClient(String client_id, Object address_mass){
        SQLiteDatabase db = this.getWritableDatabase();

        String code = String.valueOf(((LinkedTreeMap) address_mass).get("code"));
        String vid = String.valueOf(((LinkedTreeMap) address_mass).get("vid"));
        String name = String.valueOf(((LinkedTreeMap) address_mass).get("addres"));

        ContentValues values = new ContentValues();
        values.put(CLIENT_ID, client_id);
        values.put(ADDRESS_VID, vid);
        values.put(ADDRESS_CODE, code);
        values.put(ADDRESS_NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + AddressTable + " WHERE " + CLIENT_ID + "=?" + " AND " + ADDRESS_CODE + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{client_id, code});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(AddressTable, values, CLIENT_ID + " = ?" + " AND " + ADDRESS_CODE + "=?", new String[]{client_id, code});
        }else{
            db.insert(AddressTable, null, values);
        }
    }



    // Insert Options
    public void insertOprions(String opt_id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OPT_ID, opt_id);
        values.put(NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + OptionsTable + " WHERE " + OPT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{opt_id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OptionsTable, values, OPT_ID + " = ?", new String[]{opt_id});
            //db.close();
            //return;
        }else{
            db.insert(OptionsTable, null, values);
        }

        //db.close();
    }

    public void insertPics(String client_code, String filename, String lat, String lon, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CLIENT_ID, client_code);
        values.put(PICS_URL, filename);
        values.put(GPS_LAT, lat);
        values.put(GPS_LON, lon);
        values.put(PICS_DATE, date);
        values.put(PICS_TIME, time);

        db.insert(PicsTable, null, values);
    }

    // Insert Store
    public void insertStore(String store_id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STORE_ID, store_id);
        values.put(NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + StorageTable + " WHERE " + STORE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(store_id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(StorageTable, values, STORE_ID + " = ?", new String[]{String.valueOf(store_id)});
        }else{
            db.insert(StorageTable, null, values);
        }

        //db.close();
    }

    public void updateSubCategory(String ProductID, int CategoryID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String NewString = "";

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + ProductsTable + " WHERE " + PDT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ProductID});
        if (cursor.moveToFirst()) {
            isUpdate = true;
            NewString = cursor.getString(cursor.getColumnIndex(DOP_CAT_ID));
        }
        cursor.close();

        if (isUpdate) {


            if (NewString != null){
                NewString = NewString + ";" + Integer.toString(CategoryID);
            }else{
                NewString = Integer.toString(CategoryID);
            }

            values.put(DOP_CAT_ID, NewString);
            db.update(ProductsTable, values, PDT_ID + " = ?", new String[]{ProductID});
        }

        //db.close();
    }

    public void insertPrice(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRICE_ID, id);
        values.put(PRICE_NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + PricesTable + " WHERE " + PRICE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(PricesTable, values, PRICE_ID + " = ?", new String[]{String.valueOf(id)});
        }else{
            db.insert(PricesTable, null, values);
        }
        
        //db.close();
    }

    // Insert Products
    public void insertProducts(String pro_id, int cat_id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PDT_ID, pro_id);
        values.put(CAT_ID, cat_id);
        values.put(NAME, name);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + ProductsTable + " WHERE " + PDT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(pro_id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(ProductsTable, values, PDT_ID + " = ?", new String[]{pro_id});
        }else{
            // Set View / Order / Share Counts To 0
            values.put(VIEW_COUNT, 0);
            values.put(ORDER_COUNT, 0);
            values.put(SHARE_COUNT, 0);

            db.insert(ProductsTable, null, values);
        }
        //db.close();
    }

    // Insert Variants
    public void insertVariants(String var_id, String product_id, String sku, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VAR_ID, var_id);
        values.put(SKU, sku);
        values.put(BARCODE, barcode);
        values.put(PDT_ID, product_id);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + VariantsTable + " WHERE " + VAR_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(VariantsTable, values, VAR_ID + " = ?", new String[]{var_id});
        } else {
            db.insert(VariantsTable, null, values);
        }

        //db.close();
    }

    // Insert Child Category Mapping
    public void insertChildCategoryMapping(int category_id, int child_id, int is_dop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAT_ID, category_id);
        values.put(SUB_ID, child_id);
        values.put(IS_DOP, is_dop);

        // Check If Value Already Exists
        boolean isUpdate = false;
        int id = 0;
        String selectQuery = "SELECT * FROM " + SubCategoriesTable + " WHERE " + CAT_ID + "=? AND " + SUB_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(category_id), String.valueOf(child_id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
            id = cursor.getInt(cursor.getColumnIndex(ID));
        }
        cursor.close();

        if (isUpdate) {
            db.update(SubCategoriesTable, values, ID + " = ?", new String[]{String.valueOf(id)});
        } else {
            db.insert(SubCategoriesTable, null, values);
        }

        //db.close();
    }

    // Get Products List By Selected Filters
    public List<Product> getProductsList(int sortById, List<String> filterOptions, int cat_id, String client_type) {
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        // Create Query According To Filter
        String selectQuery = "SELECT DISTINCT " + PDT_ID + " FROM " + VariantsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // перебрать все строки и добавить в список
        if (cursor.moveToFirst()) {
            do {
                String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                // Adding product id to list
                productIdList.add(product_id);
            } while (cursor.moveToNext());
        }

        try {
            if (productIdList.size() > 0) {
                String inClause = Util.getInClauseString(productIdList);

                selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + PDT_ID + " IN " + inClause;
                if (cat_id > 0) {
                    selectQuery = selectQuery + " AND " + CAT_ID + "=?";
                }
                switch (sortById) {

                    case 0: // Most Orders
                        selectQuery = selectQuery + " ORDER BY " + ORDER_COUNT + " DESC";
                        break;

                    case 1: // Most Shares
                        selectQuery = selectQuery + " ORDER BY " + SHARE_COUNT + " DESC";
                        break;

                    case 2: // Most Viewed
                        selectQuery = selectQuery + " ORDER BY " + VIEW_COUNT + " DESC";
                        break;
                }

                if (cat_id > 0) {
                    cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});
                } else {
                    cursor = db.rawQuery(selectQuery, null);
                }

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        String id = cursor.getString(cursor.getColumnIndex(ID));
                        String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                        product.setId(id);
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        product.setProduct_code(product_id);

                        product.setVariants(getProductVariant(product_id, client_type));

                        // Get Price Range
                        String priceRange = getProductPriceRangeById(id);
                        product.setPrice_range(priceRange);

                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        // return products list
        return productList;
    }

    public List<Product> getProductsListFilser(int sortById, ArrayList<FilterValue> filterOptions, int cat_id, String client_type) {

        List<String> variantIdList = new ArrayList<>();
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        if (filterOptions.size() > 0) {
            for (int i = 0; i < filterOptions.size(); i++) {

                String id_opt = filterOptions.get(i).getId();
                String val_opt = filterOptions.get(i).getValue();

                // Select All Query
                String selectQuery = "SELECT * FROM " + VariantOptionsTable + " LEFT JOIN " + VariantsTable + " ON " + VariantOptionsTable + "." + VAR_ID + " = " +
                        VariantsTable + "." +  VAR_ID + " WHERE " + OPT_ID + "=?" + " AND " + OPT_VALUE + "=?";
                Cursor cursor = db.rawQuery(selectQuery, new String[]{id_opt, val_opt});

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        String variant_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                        variantIdList.add(variant_id);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

//        if (variantIdList.size() > 0) {
//            String inClauseVariants = Util.getInClauseString(variantIdList);
//
//            String selectQuery = "SELECT  * FROM " + VariantsTable + " WHERE " + VAR_ID + " IN " + inClauseVariants;
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
//                    productIdList.add(product_id);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }

        System.out.println("filter_catalog");

        try {
            if (variantIdList.size() > 0) {
                String inClause = Util.getInClauseString(variantIdList);

                String selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + PDT_ID + " IN " + inClause;
                if (cat_id > 0) {
                    selectQuery = selectQuery + " AND " + CAT_ID + "=?";
                }

                Cursor cursor = db.rawQuery(selectQuery, null);

                if (cat_id > 0) {
                    cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});
                }

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        String id = cursor.getString(cursor.getColumnIndex(ID));
                        String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                        product.setId(id);
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        product.setProduct_code(product_id);

                        product.setVariants(getProductVariant(product_id, client_type));

                        // Get Price Range
                        String priceRange = getProductPriceRangeById(id);
                        product.setPrice_range(priceRange);

                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return products list
        return productList;
    }


    public List<Product> getProductsTypeList(int sortById, List<String> filterOptions, int cat_id, String client_type) {
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        // Create Query According To Filter
        String selectQuery = "SELECT DISTINCT " + PDT_ID + " FROM " + VariantsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // перебрать все строки и добавить в список
        if (cursor.moveToFirst()) {
            do {
                String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                // Adding product id to list
                productIdList.add(product_id);
            } while (cursor.moveToNext());
        }

        try {
            if (productIdList.size() > 0) {
                String inClause = Util.getInClauseString(productIdList);

                selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + PDT_ID + " IN " + inClause;
                if (cat_id > 0) {
                    selectQuery = selectQuery + " AND " + DOP_CAT_ID + " LIKE '%" + String.valueOf(cat_id) + "%'";
                }
                switch (sortById) {

                    case 0: // Most Orders
                        selectQuery = selectQuery + " ORDER BY " + ORDER_COUNT + " DESC";
                        break;

                    case 1: // Most Shares
                        selectQuery = selectQuery + " ORDER BY " + SHARE_COUNT + " DESC";
                        break;

                    case 2: // Most Viewed
                        selectQuery = selectQuery + " ORDER BY " + VIEW_COUNT + " DESC";
                        break;
                }

//                if (cat_id > 0) {
//                    cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});
//                } else {
                    cursor = db.rawQuery(selectQuery, null);
                //}

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        String id = cursor.getString(cursor.getColumnIndex(ID));
                        String product_id = cursor.getString(cursor.getColumnIndex(PDT_ID));
                        product.setId(id);
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        product.setProduct_code(product_id);

                        product.setVariants(getProductVariant(product_id, client_type));

                        // Get Price Range
                        String priceRange = getProductPriceRangeById(id);
                        product.setPrice_range(priceRange);

                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        // return products list
        return productList;
    }

    // Get Category List
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + CategoriesTable + " WHERE " + CAT_LEVEL + "=?" + " AND " + IS_DOP + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(1), String.valueOf(0)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                int categoryId = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                int level = cursor.getInt(cursor.getColumnIndex(CAT_LEVEL));
                String selectCategory = "SELECT  * FROM " + SubCategoriesTable + " WHERE " + SUB_ID + "=?" + " AND " + IS_DOP + "=?";
                Cursor c = db.rawQuery(selectCategory, new String[]{String.valueOf(categoryId), String.valueOf(0)});
                //if (c.moveToFirst()) { // don't add if category has a sub category
                    category.setId(categoryId);
                    category.setName(name);
                    category.setLevel(level);
                    // Adding category to list
                    categoryList.add(category);
                //}
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return category list
        return categoryList;
    }

    // Get Category List
    public List<Category> getCategoryTypeList() {
        List<Category> categoryList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + CategoriesTable + " WHERE " + IS_DOP + "=?" + " AND " + CAT_LEVEL + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(1), String.valueOf(0)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                int categoryId = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                int level = cursor.getInt(cursor.getColumnIndex(CAT_LEVEL));
                String selectCategory = "SELECT  * FROM " + SubCategoriesTable + " WHERE " + IS_DOP + "=?" + " AND " + SUB_ID + "=?";
                Cursor c = db.rawQuery(selectCategory, new String[]{String.valueOf(1),String.valueOf(categoryId)});
                //if (c.moveToFirst()) { // don't add if category has a sub category
                category.setId(categoryId);
                category.setName(name);
                category.setLevel(level);
                // Adding category to list
                categoryList.add(category);
                //}
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return category list
        return categoryList;
    }



    // Get Subcategory By Category Id
    public List<Category> getSubcategoryList(int id) {
        List<Category> subcategoryList = new ArrayList<>();

        // Select Subcategories
        String selectQuery = "SELECT * FROM " + SubCategoriesTable + " WHERE " + SUB_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(CAT_ID)));

                String selectSubCategory = "SELECT  " + NAME + "," + CAT_LEVEL + " FROM " + CategoriesTable + " WHERE " + ID + "= ?";
                Cursor c = db.rawQuery(selectSubCategory, new String[]{String.valueOf(category.getId())});
                if (c.moveToFirst()) {
                    do {
                        category.setName(c.getString(c.getColumnIndex(NAME)));
                        category.setLevel(c.getInt(c.getColumnIndex(CAT_LEVEL)));
                    } while (c.moveToNext());

                    // Adding category to list
                    subcategoryList.add(category);
                }
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return category list
        return subcategoryList;
    }

    public List<Category> getSubcategoryTypeList(int id) {
        List<Category> subcategoryList = new ArrayList<>();

        // Select Subcategories
        String selectQuery = "SELECT * FROM " + SubCategoriesTable + " WHERE " + IS_DOP + "=?" + " AND " + SUB_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(1),String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(CAT_ID)));

                String selectSubCategory = "SELECT  " + NAME + "," + CAT_LEVEL + " FROM " + CategoriesTable + " WHERE "+ IS_DOP + "=?" + " AND " + ID + "= ?";
                Cursor c = db.rawQuery(selectSubCategory, new String[]{String.valueOf(1),String.valueOf(category.getId())});
                if (c.moveToFirst()) {
                    do {
                        category.setName(c.getString(c.getColumnIndex(NAME)));
                        category.setLevel(c.getInt(c.getColumnIndex(CAT_LEVEL)));
                    } while (c.moveToNext());

                    // Adding category to list
                    subcategoryList.add(category);
                }
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return category list
        return subcategoryList;
    }


    /*// Get Products List By Category
    public List<Product> getProductsListByCategory(int id) {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + CAT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return products list
        return productList;
    }*/


    // Get Markers
    public List<Marker> getMarkers(String DateStart, String DateEnd){
        List<Marker> markers = new ArrayList<Marker>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PicsTable + " WHERE " + PICS_DATE + " BETWEEN " +  DateStart  + " AND " + DateEnd;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Marker marker = new Marker();
                Double lat = cursor.getDouble(cursor.getColumnIndex(GPS_LAT));
                Double lon = cursor.getDouble(cursor.getColumnIndex(GPS_LON));
                String name = getClientById(cursor.getString(cursor.getColumnIndex(CLIENT_ID))).getName();
                String time = cursor.getString(cursor.getColumnIndex(PICS_TIME));
                LatLng latLng = new LatLng(lat, lon);
                marker.setLatLng(latLng);
                marker.setTime(time);
                marker.setName(name);
                markers.add(marker);
            } while (cursor.moveToNext());
        }
        return markers;
    }

    // Get Product Details By Id
    public Product getProductDetailsById(String id) {
        Product product = new Product();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + PDT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            product.setId(cursor.getString(cursor.getColumnIndex(ID)));
            product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            product.setProduct_code(cursor.getString(cursor.getColumnIndex(PDT_ID)));
        }
        cursor.close();
        //db.close();

        // return product
        return product;
    }

    // Get Variant Details By Id
    public Variant getVariantDetailsById(String id) {
        Variant variant = new Variant();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + VariantsTable + " WHERE " + VAR_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
            String var_id = cursor.getString(cursor.getColumnIndex(VAR_ID));
            variant.setId(cursor.getString(cursor.getColumnIndex(ID)));
            variant.setProductOptions(getOptionsVariant(id));
            variant.setPrice(getPriceVariant(id));
            variant.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE)));
            variant.setProduct_id(cursor.getString(cursor.getColumnIndex(PDT_ID)));
            variant.setVariant_id(var_id);
            variant.setSku(cursor.getString(cursor.getColumnIndex(SKU)));
            variant.setCount(getCountVariant(id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return variant
        return variant;
    }

    public VariantOrder getVariantOrderDetailsById(String id) {
        VariantOrder variantOrder = new VariantOrder();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + VariantsTable + " WHERE " + VAR_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                String var_id = cursor.getString(cursor.getColumnIndex(VAR_ID));
                variantOrder.setId(cursor.getString(cursor.getColumnIndex(ID)));
                variantOrder.setProductOptions(getOptionsVariant(id));
                variantOrder.setPrice(getPriceVariant(id));
                variantOrder.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE)));
                variantOrder.setProduct_id(cursor.getString(cursor.getColumnIndex(PDT_ID)));
                variantOrder.setVariant_id(var_id);
                variantOrder.setSku(cursor.getString(cursor.getColumnIndex(SKU)));
                variantOrder.setCount(getCountVariant(id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return variant
        return variantOrder;
    }

    // Get Variant Details By Barcode
    public Variant getVariantDetailsByBarcod(String barcod) {
        Variant variant = new Variant();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + VariantsTable + " WHERE " + BARCODE + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{barcod});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                String var_id = cursor.getString(cursor.getColumnIndex(VAR_ID));
                variant.setId(cursor.getString(cursor.getColumnIndex(ID)));
                variant.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE)));
                variant.setProduct_id(cursor.getString(cursor.getColumnIndex(PDT_ID)));
                variant.setVariant_id(var_id);
                variant.setSku(cursor.getString(cursor.getColumnIndex(SKU)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return variant
        return variant;
    }

    // Get Variant Details By Id
    private Client getClientById(String xml_id) {
        Client client = new Client();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ClientTable + " WHERE " + XML_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(xml_id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            client.setXml_id(cursor.getString(cursor.getColumnIndex(XML_ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            client.setDebt(cursor.getDouble(cursor.getColumnIndex(DEPT)));
            client.setPrice_id(cursor.getString(cursor.getColumnIndex(PRICE_ID)));
            client.setType(cursor.getString(cursor.getColumnIndex(CLIENT_TYPE)));
            client.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
        }
        cursor.close();
        //db.close();

        return client;
    }


    // получаем тип цены по коду клиента
    public String getNamePriceByCode(int code) {
        String price_name = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PricesTable + " WHERE " + PRICE_ID + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(code)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            price_name = cursor.getString(cursor.getColumnIndex(PRICE_NAME));
        }
        cursor.close();

        return price_name;
    }

    public String getDiscontCode(String discontName){
        String code = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DiscTable + " WHERE " + NAME + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{discontName});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            code = cursor.getString(cursor.getColumnIndex(XML_ID));
        }
        cursor.close();

        return code;
    }

    public Discont getDiscontByCode(String code){
        Discont discont = new Discont();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DiscTable + " WHERE " + XML_ID + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            discont.setCode(cursor.getString(cursor.getColumnIndex(XML_ID)));
            discont.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            discont.setType(cursor.getString(cursor.getColumnIndex(DISC_TYPE)));
        }
        cursor.close();

        return discont;
    }

    public Address getAddresOrderByClient(String client_id, String address_code) {
        Address adr = new Address();

        String selectQuery = "SELECT  * FROM " + AddressTable + " WHERE " + CLIENT_ID + "=?" + " AND " + ADDRESS_CODE + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{client_id,address_code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            adr.setName(cursor.getString(cursor.getColumnIndex(ADDRESS_NAME)));
            adr.setVid(cursor.getString(cursor.getColumnIndex(ADDRESS_VID)));
            adr.setCode(address_code);
        }
        cursor.close();

        return adr;
    }


    // получаем тип цены по коду клиента
    public int getPriceTypeByClient(String xml_id) {
        int type = 2;

//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + ClientTable + " WHERE " + XML_ID + "=?";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(xml_id)});
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            type = 2;
//        }
//        cursor.close();
//        db.close();

        // return variant
        return type;
    }

    // Get Product Price Range By Id
    public String getProductPriceRangeById(String id) {
        List<Double> priceList = new ArrayList<>();

        //TODO тут нужно сделать проверку по типу цені у клиента

        // Select All Query
        //TODO getPrice
//        String selectQuery = "SELECT DISTINCT " + PRICE1 + " FROM " + VariantsTable + " WHERE " + PDT_ID + "=?";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                //TODO getPrice
//                //String priceString = cursor.getString(cursor.getColumnIndex(PRICE1));
//                String priceString = "0.0";
//                double price = Double.parseDouble(priceString);
//                priceList.add(price);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();

//        double minimumPrice = Collections.min(priceList);
//        double maximumPrice = Collections.max(priceList);
//
//        if (minimumPrice == maximumPrice) {
//            return  formatDouble(maximumPrice) + " ₴";
//        } else {
//            return  formatDouble(minimumPrice) + " ₴ - " + formatDouble(maximumPrice) + " ₴";
//        }
        String price = "0.0";
        return price;
    }

    // Get Product Variant By Id, Size, Color
    public List<Variant> getProductVariant(String id, String client_type) {

        List<Variant> variantList = new ArrayList<Variant>();

        SQLiteDatabase db = this.getWritableDatabase();


        String selectQueryVariant = "SELECT * FROM " + VariantsTable + " WHERE " + PDT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQueryVariant, new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String var_id = cursor.getString(cursor.getColumnIndex(VAR_ID));
                String sku = cursor.getString(cursor.getColumnIndex(SKU));
                String barcode = cursor.getString(cursor.getColumnIndex(BARCODE));
                String pdt_id = cursor.getString(cursor.getColumnIndex(PDT_ID));

                //нужно проверить доступен товар для типа клиента
                String selectQueryAccessProduct = "SELECT * FROM " + AccessProductsTable + " WHERE " + PDT_ID + "=?" + " AND " + OPT_ID + "=?";
                Cursor cursor2 = db.rawQuery(selectQueryAccessProduct, new String[]{pdt_id, var_id});

                //000000023
                //57ffa6d9-65f8-11e6-be72-005056b36ed5
                //2

                // looping through all rows and adding to list
                boolean isAccess = false;
                List<String> typesClient = new ArrayList<>();
                if (cursor2.moveToFirst()) {
                    do {
                        isAccess = true;
                        typesClient.add(cursor2.getString(cursor2.getColumnIndex(CLIENT_TYPE)));
                    } while (cursor2.moveToNext());
                }
                cursor2.close();

                Variant variant = new Variant();
                variant.setPrice(getPriceVariant(var_id));
                variant.setProductOptions(getOptionsVariant(var_id));
                variant.setCount(getCountVariant(var_id));
                variant.setProduct_id(pdt_id);
                variant.setId(var_id);
                variant.setSku(sku);
                variant.setBarcode(barcode);
                //variantList.add(variant);

                //если не нашли в запрешенных добавляем в массив
                if (isAccess) {
                    for (int i = 0; i < typesClient.size(); i++) {
                        if (typesClient.get(i).equals(client_type)){
                            variantList.add(variant);
                        }
                    }
                }else{
                    variantList.add(variant);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return variantList;
    }

    public List<Count> getCountVariant(String var_id) {
        List<Count> countList = new ArrayList<Count>();
        String selectQuery;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        selectQuery = "SELECT * FROM " + CountsTable + " WHERE " + VAR_ID + "=?";
        cursor = db.rawQuery(selectQuery, new String[]{var_id});
        if (cursor.moveToFirst()) {
            do {
                Count count_item = new Count();
                String store_id = cursor.getString(cursor.getColumnIndex(STORE_ID));
                int count_store = cursor.getInt(cursor.getColumnIndex(COUNT_STORE));
                count_item.setStore(store_id);
                count_item.setCount(count_store);
                count_item.setName_store(getNameStore(store_id));

                countList.add(count_item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return countList;
    }

    public String getNameStore(String store_id){
        String name_store = "";
        // Select Query
        String selectQuery = "SELECT * FROM " + StorageTable + " WHERE " + STORE_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{store_id});

        if (cursor.moveToFirst()) {
            name_store = cursor.getString(cursor.getColumnIndex(NAME));
        }
        cursor.close();

        return name_store;
    }

    public String getNameOption(String opt_id){
        String name_opt = "";
        // Select Query
        String selectQuery = "SELECT * FROM " + OptionsTable + " WHERE " + OPT_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{opt_id});

        if (cursor.moveToFirst()) {
            name_opt = cursor.getString(cursor.getColumnIndex(NAME));
        }
        cursor.close();

        return name_opt;
    }

    public List<Store> getStore(){
        List<Store> storeList = new ArrayList<>();

        // Select Query
        String selectQuery = "SELECT * FROM " + StorageTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
            Store store = new Store();
            String id = cursor.getString(cursor.getColumnIndex(ID));
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String store_id = cursor.getString(cursor.getColumnIndex(STORE_ID));

            store.setId(id);
            store.setName(name);
            store.setStore_id(store_id);

            storeList.add(store);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return storeList;
    }

    public List<Price> getPriceVariant(String var_id) {
        List<Price> priceList = new ArrayList<Price>();

        String selectQuery;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        selectQuery = "SELECT * FROM " + PriceVariantTable + " WHERE " + VAR_ID + "=?";
        cursor = db.rawQuery(selectQuery, new String[]{var_id});
        if (cursor.moveToFirst()) {
            do {
                Price price_item = new Price();
                int price_type = cursor.getInt(cursor.getColumnIndex(PRICE_ID));
                Double price_value = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
                price_item.setType(price_type);
                price_item.setValue(price_value);
                priceList.add(price_item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return priceList;
    }

    public ArrayList<ProductOption> getOptionsVariant(String var_id) {
        ArrayList<ProductOption> optionsList = new ArrayList<ProductOption>();
        String selectQuery;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        selectQuery = "SELECT * FROM " + VariantOptionsTable + " WHERE " + VAR_ID + "=?";
        cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(var_id)});
        if (cursor.moveToFirst()) {
            do {
                ProductOption option_item = new ProductOption();
                String opt_id = cursor.getString(cursor.getColumnIndex(OPT_ID));
                String opt_value = cursor.getString(cursor.getColumnIndex(OPT_VALUE));

                option_item.setOpt_id(opt_id);
                option_item.setOpt_val(opt_value);
                option_item.setVar_id(var_id);

                optionsList.add(option_item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return optionsList;
    }

    //получаем уникальніе опции вариантов
    //TODO получение свойст для фильтра
    public List<ProductOption> getOptionsVariants(List<String> variants) {
        List<ProductOption> optionsList = new ArrayList<ProductOption>();

        SQLiteDatabase db = this.getWritableDatabase();
        String inClauseSizes = Util.getInClauseString(variants);
        String selectQuery = "SELECT DISTINCT " + OPT_VALUE + ", " + OPT_ID + " FROM " + VariantOptionsTable + " WHERE " + VAR_ID + " IN " + inClauseSizes;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ProductOption option_item = new ProductOption();
                String opt_id = cursor.getString(cursor.getColumnIndex(OPT_ID));
                String opt_value = cursor.getString(cursor.getColumnIndex(OPT_VALUE));
                option_item.setOpt_id(opt_id);
                option_item.setOpt_val(opt_value);
                optionsList.add(option_item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return optionsList;
    }

    // Register User
    public long registerUser(String name, String email, String mobile, String password) {
        long isTrue = 1;
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(MOBILE, mobile);
        values.put(PASSWORD, password);

        // Select Query
        String selectQuery = "SELECT * FROM " + UserTable + " WHERE " + EMAIL + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(UserTable, values, EMAIL + "=?", new String[]{email});
        }else{
            db.insert(UserTable, null, values);
        }
        return isTrue;
    }

    // Get User
    public Boolean getUser(String name, String pass) {

        Boolean isEmpty = false;

        // Select Query
        String selectQuery = "SELECT * FROM " + UserTable + " WHERE " + NAME + "=?" + " AND " + PASSWORD + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, pass});

        if (cursor.moveToFirst()) {
            isEmpty = true;
        }
        cursor.close();

        return isEmpty;
    }

    public User getFirstUser() {
        User user = new User();

        // Select Query
        String selectQuery = "SELECT * FROM " + UserTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            user.setMobile(cursor.getString(cursor.getColumnIndex(MOBILE)));
        }
        cursor.close();
        //db.close();

        // return user
        return user;
    }

    // Get User
    public List<Client> getClients() {
        List<Client> clientList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ClientTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String xml_id = cursor.getString(cursor.getColumnIndex(XML_ID));
                // Adding to list
                clientList.add(getClientById(xml_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return clientList;
    }

    // Get User
    public Client getClientsById(String client_code) {
        Client client = new Client();
        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ClientTable + " WHERE " + XML_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{client_code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String xml_id = cursor.getString(cursor.getColumnIndex(XML_ID));
                String price_code = cursor.getString(cursor.getColumnIndex(PRICE_ID));
                Double dept = cursor.getDouble(cursor.getColumnIndex(DEPT));
                String client_type = cursor.getString(cursor.getColumnIndex(CLIENT_TYPE));

                client.setName(name);
                client.setXml_id(xml_id);
                client.setPrice_id(price_code);
                client.setDebt(dept);
                client.setClient_type(client_type);

            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return category list
        return client;
    }

    public List<Client> findClientsByName(String client_name) {
        List<Client> clientList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ClientTable + " WHERE " + NAME + " LIKE '%" + client_name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String xml_id = cursor.getString(cursor.getColumnIndex(XML_ID));
                // Adding to list
                clientList.add(getClientById(xml_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return clientList;
    }

    // Insert Product Into Cart
    public long insertIntoCart(String pdt_id, String var_id, int quantity, double price, String id_store, String client_code) {

        long result = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Check If Value Already Exists
        String selectQuery = "SELECT * FROM " + ShoppingCartTable + " WHERE " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{pdt_id, var_id, id_store});
        boolean isUpdate = false;

        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        values.put(PDT_ID, pdt_id);
        values.put(VAR_ID, var_id);
        values.put(QUANTITY, quantity);
        values.put(PRICE_VALUE, price);
        values.put(STORE_ID, id_store);
        values.put(ORDER_CLIENT, client_code);

        if (isUpdate) {
            db.update(ShoppingCartTable, values, PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?" , new String[]{pdt_id, var_id, id_store});
            result = 2;
        } else {
            db.insert(ShoppingCartTable, null, values);
            result = 1;
        }

        return result;
    }

    public long removeFromCart(String pdt_id, String var_id, int quantity, double price, String id_store) {

        long result = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Check If Value Already Exists
        String selectQuery = "SELECT * FROM " + ShoppingCartTable + " WHERE " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{pdt_id, var_id, id_store});
        boolean isUpdate = false;

        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        values.put(PDT_ID, pdt_id);
        values.put(VAR_ID, var_id);
        values.put(QUANTITY, quantity);
        values.put(PRICE_VALUE, price);
        values.put(STORE_ID, id_store);

        if (isUpdate) {
            if (quantity > 0) {
                db.update(ShoppingCartTable, values, PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?", new String[]{pdt_id, var_id, id_store});
                result = 1;
            }else{
                db.delete(ShoppingCartTable, PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?", new String[]{pdt_id, var_id, id_store});
            }
        }

        return result;
    }

    // Get Shopping Cart Items
    public List<CartStore> getCartItems() {

        List<CartStore> cartStores = new ArrayList<>();
        List<Store> storeList = getStore();

        for (int k = 0; k < storeList.size(); k++) {

            List<Cart> cartList = getCartByStore(storeList.get(k));
            if (cartList.size() > 0) {
                CartStore cartStore = new CartStore();
                cartStore.setStore(storeList.get(k).getStore_id());
                cartStore.setCartList(cartList);
                cartStores.add(cartStore);
            }
        }

        return cartStores;
    }

    public List<Cart> getCartByStore(Store store){
        List<Cart> shoppingCart = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShoppingCartTable + " WHERE " + STORE_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{store.getStore_id()});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String productId = cursor.getString(cursor.getColumnIndex(PDT_ID));
                String variantId = cursor.getString(cursor.getColumnIndex(VAR_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
                double price_value = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
                String id_store = cursor.getString(cursor.getColumnIndex(STORE_ID));

                Product product = getProductDetailsById(productId);
                Variant variant = getVariantDetailsById(variantId);

                cart.setId(id);
                cart.setItemQuantity(quantity);
                cart.setProduct(product);
                cart.setVariant(variant);
                cart.setPrice_value(price_value);
                cart.setStore_id(id_store);
                // Adding to list
                shoppingCart.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return cart items list
        return shoppingCart;
    }

    //обновляем заказ после добавления в базу
    public void UpdateOrderBy1C(Order order, ArrayList<OrderJSON> orderJSONS){

        String discont = "";
        int is_gift = 0;
        int status = orderJSONS.get(0).getStatus();
        String number = orderJSONS.get(0).getNumber();
        ArrayList products = orderJSONS.get(0).getProducts();

        if (orderJSONS.get(0).getDiscont() != null) {
            discont = orderJSONS.get(0).getDiscont();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        double totalOrder = orderJSONS.get(0).getTotalSum();

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + ORDER_NUMBER + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order.getNumber()});

        boolean isUpdate = false;

        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            for (int i = 0; i < products.size(); i++) {

                LinkedTreeMap linkedTreeMap = ((LinkedTreeMap) products.get(i));
                Variant variant = getVariantDetailsByBarcod(linkedTreeMap.get("barcode").toString());

                //тут если этот товар не загружен в базу приложения, например это может быть когда он в удалленых товарах
                if (variant.getVariant_id() == null){
                    String var_id = linkedTreeMap.get("id").toString();
                    String sku = linkedTreeMap.get("sku").toString();
                    String barcode = linkedTreeMap.get("barcode").toString();
                    String pdc_id = linkedTreeMap.get("pdc_id").toString();

                    String pdc_name = linkedTreeMap.get("name").toString();
                    insertProducts(pdc_id, 0, pdc_name);

                    insertVariants(var_id, pdc_id, sku, barcode);

                    String opt_id = "";
                    String opt_val = "";
                    ArrayList options = (ArrayList) linkedTreeMap.get("option");
                    for (int f = 0; f < options.size(); f++) {
                        opt_id = (String) ((LinkedTreeMap) options.get(f)).get("opt_id");
                        opt_val = (String) ((LinkedTreeMap) options.get(f)).get("opt_val");
                    }

                    insertVariantOptions(var_id, opt_id, opt_val);

                    variant = getVariantDetailsByBarcod(linkedTreeMap.get("barcode").toString());
                }

                String pdt_id = variant.getProduct_id();
                String var_id = variant.getVariant_id();

                String store_id = linkedTreeMap.get("store_id").toString();
                int quantity =  (int)((double) linkedTreeMap.get("count"));
                double priceItem = (double) linkedTreeMap.get("price");

                if (orderJSONS.get(0).getDiscont() != null) {
                    is_gift = new Double((Double) linkedTreeMap.get("is_gift")).intValue();
                }
                addItemByOrder(order.getNumber(),var_id, pdt_id, quantity, priceItem, store_id, is_gift);

                updateCountPriceItemOrderHistory(quantity, priceItem, priceItem, 0, 0, pdt_id, var_id, store_id, order.getNumber(), is_gift);

            }

            updateTotal1COrderById(order.getNumber(), totalOrder, number);

            updateDiscont1cOrderById(order.getNumber(), discont);

            if (status == 1) {
                isBaseOrderById(order.getId(), true, number);
            }else{
                //updateStatusOrderById(order.getId(), status);
            }

        }

    }

    public void UpdateOrderGiftsBy1C(Order order, ArrayList<OrderGiftsJSON> orderJSONS){

        String discont = "";
        int status = orderJSONS.get(0).getStatus();
        String number = orderJSONS.get(0).getNumber();
        ArrayList products = orderJSONS.get(0).getProducts();

        if (orderJSONS.get(0).getDiscont() != null) {
            discont = orderJSONS.get(0).getDiscont();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        double totalOrder = orderJSONS.get(0).getTotalSum();

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + ORDER_NUMBER + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order.getNumber()});

        boolean isUpdate = false;

        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            for (int i = 0; i < products.size(); i++) {

                LinkedTreeMap linkedTreeMap = ((LinkedTreeMap) products.get(i));
                Variant variant = getVariantDetailsByBarcod(linkedTreeMap.get("barcode").toString());

                //тут если этот товар не загружен в базу приложения, например это может быть когда он в удалленых товарах
                if (variant.getVariant_id() == null){
                    String var_id = linkedTreeMap.get("id").toString();
                    String sku = linkedTreeMap.get("sku").toString();
                    String barcode = linkedTreeMap.get("barcode").toString();
                    String pdc_id = linkedTreeMap.get("pdc_id").toString();

                    String pdc_name = linkedTreeMap.get("name").toString();
                    insertProducts(pdc_id, 0, pdc_name);

                    insertVariants(var_id, pdc_id, sku, barcode);

                    String opt_id = "";
                    String opt_val = "";
                    ArrayList options = (ArrayList) linkedTreeMap.get("option");
                    for (int f = 0; f < options.size(); f++) {
                        opt_id = (String) ((LinkedTreeMap) options.get(f)).get("opt_id");
                        opt_val = (String) ((LinkedTreeMap) options.get(f)).get("opt_val");
                    }

                    insertVariantOptions(var_id, opt_id, opt_val);

                    variant = getVariantDetailsByBarcod(linkedTreeMap.get("barcode").toString());
                }

                String pdt_id = variant.getProduct_id();
                String var_id = variant.getVariant_id();

                String store_id = linkedTreeMap.get("store_id").toString();
                int quantity =  (int)((double) linkedTreeMap.get("count"));
                double priceItem = (double) linkedTreeMap.get("price");

                int is_gift = new Double((Double) linkedTreeMap.get("is_gift")).intValue();

                addItemByOrder(order.getNumber(),var_id, pdt_id, quantity, priceItem, store_id, is_gift);

                updateCountPriceItemOrderHistory(quantity, priceItem, priceItem, 0, 0, pdt_id, var_id, store_id, order.getNumber(), is_gift);

            }

            updateTotal1COrderById(order.getNumber(), totalOrder, number);

            updateDiscont1cOrderById(order.getNumber(), discont);

            if (status == 1) {
                isBaseOrderById(order.getId(), true, number);
            }else{
                //updateStatusOrderById(order.getId(), status);
            }

        }

    }

    private void addItemByOrder(String orderNumber,String var_id, String pdt_id, int quantity, double price, String store_id, int is_gift) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PDT_ID, pdt_id);
        values.put(VAR_ID, var_id);
        values.put(QUANTITY, quantity);
        values.put(PRICE_VALUE, price);
        values.put(ORDER_NUMBER, orderNumber);
        values.put(STORE_ID, store_id);
        values.put(PDT_GIFT, is_gift);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + ORDER_NUMBER + "=? AND " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + PDT_GIFT + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{orderNumber, pdt_id, var_id, String.valueOf(is_gift)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrderHistoryTable, values, ORDER_NUMBER + "=? AND " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + PDT_GIFT + "=?", new String[]{orderNumber, pdt_id, var_id, String.valueOf(is_gift)});
        } else {
            db.insert(OrderHistoryTable, null, values);
        }
    }

    // Get Count Shopping Cart Items
    public int getCountCartItem(String pdt_id, String var_id) {
        int countCart = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ShoppingCartTable + " WHERE " + PDT_ID + "=? AND " + VAR_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{pdt_id, var_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                countCart = countCart + cursor.getInt(cursor.getColumnIndex(QUANTITY));
            }while (cursor.moveToNext());
        }
        cursor.close();

        // return cart items list
        return countCart;
    }

    // Get Count Shopping Cart Items By Store
    public int getCountCartItemByStore(String pdt_id, String var_id, String store_id) {
        int countCart = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ShoppingCartTable + " WHERE " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{pdt_id, var_id, store_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                countCart = countCart + cursor.getInt(cursor.getColumnIndex(QUANTITY));
            }while (cursor.moveToNext());
        }
        System.out.println(countCart);
        cursor.close();

        // return cart items list
        return countCart;
    }

    public void updateCountPriceItemOrderHistory(int quantity, double price, double price_discont1c, int manual_discont, int auto_discont, String pdt_id, String var_id, String store_id, String odrer_number, int isGift){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(QUANTITY_1C, quantity);
            values.put(PRICE_VALUE_1C, price);
            values.put(MANUAL_DISCONT, manual_discont);
            values.put(AUTO_DISCONT, auto_discont);
            values.put(PRICE_VALUE_1C, price_discont1c);

            // Check If Value Already Exists
            boolean isUpdate = false;

            String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=? AND " + PDT_GIFT + "=? AND " + ORDER_NUMBER + "=?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdt_id, var_id, store_id, String.valueOf(isGift), odrer_number});

            if (cursor.moveToFirst()) {
                isUpdate = true;
            }
            cursor.close();

            if (isUpdate) {
                db.update(OrderHistoryTable, values, PDT_ID + "=? AND " + VAR_ID + "=? AND " + STORE_ID + "=? AND " + PDT_GIFT + "=? AND " + ORDER_NUMBER + "=?", new String[]{pdt_id, var_id, store_id, String.valueOf(isGift), odrer_number});
            }
    }

    // Delete Cart Item By Id
    public boolean deleteCartItemId(int id, String store_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ShoppingCartTable, ID + "=? AND " + STORE_ID + "=?", new String[]{String.valueOf(id), store_id}) > 0;
    }

    // Delete Cart Variant By Id
    public boolean deleteCartVariantId(String var_id, String store_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ShoppingCartTable, VAR_ID + "=? AND " + STORE_ID + "=?", new String[]{var_id, store_id}) > 0;
    }

    // Delete Cart Item By Id
    public boolean deleteOrderItem(String var_id, String store_id, String order_number, int isGift) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isGift > 0) {
            return db.delete(OrderHistoryTable, VAR_ID + "=? AND " + STORE_ID + "=? AND " + PDT_GIFT + "=? AND " + ORDER_NUMBER + "=?", new String[]{var_id, store_id, String.valueOf(isGift), order_number}) > 0;
        }else{
            return db.delete(OrderHistoryTable, VAR_ID + "=? AND " + STORE_ID + "=? AND " + ORDER_NUMBER + "=?", new String[]{var_id, store_id, order_number}) > 0;
        }
    }

    public boolean deleteOrder(String order_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(OrdersTable,  ORDER_NUMBER + "=?", new String[]{order_number}) > 0;
    }

    // Delete Cart Items
    public void deleteCartItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ShoppingCartTable, null, null);
    }

    // Get Cart Item Count
    public int getCartItemCount() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShoppingCartTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        //db.close();
        return count;
    }

    public String getCartClient() {
        String code_client = "";
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ShoppingCartTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            code_client = cursor.getString(cursor.getColumnIndex(ORDER_CLIENT));
        }
        cursor.close();
        //db.close();
        return code_client;
    }

    // Insert Order
    public void insertOrderHistory(List<Cart> shoppingCart, String orderNubmer, int isGift) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < shoppingCart.size(); i++) {
            values.put(PDT_ID, shoppingCart.get(i).getProduct().getProduct_code());
            values.put(VAR_ID, shoppingCart.get(i).getVariant().getVariant_id());
            values.put(QUANTITY, shoppingCart.get(i).getItemQuantity());
            values.put(PRICE_VALUE, shoppingCart.get(i).getPrice_value());
            values.put(PRICE_VALUE_1C, shoppingCart.get(i).getPrice_value());
            values.put(ORDER_NUMBER, orderNubmer);
            values.put(STORE_ID, shoppingCart.get(i).getStore_id());
            values.put(PDT_GIFT, isGift);

            db.insert(OrderHistoryTable, null, values);
        }
        //db.close();
    }

    // Insert Order
    public void insertOrder(String order_number, String date, String store_id, Double total, String code_client, String comment, String shippingDate, String address_code) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_NUMBER, order_number);
        values.put(ORDER_DATE, date);
        values.put(STORE_ID, store_id);
        values.put(ORDER_TOTAL, total);
        values.put(ORDER_CLIENT, code_client);
        values.put(ORDER_COMMENT, comment);
        values.put(ORDER_SHIPPING_DATE, shippingDate);
        values.put(ADDRESS_CODE, address_code);
        values.put(ORDER_STATUS, 1);

        db.insert(OrdersTable, null, values);

        setLineOrderDelivery(order_number);


    }

    public void insertCountStore(String var_id, String store_id, double count) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VAR_ID, var_id);
        values.put(STORE_ID, store_id);
        values.put(COUNT_STORE, count);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + CountsTable + " WHERE " + VAR_ID + "=? AND " + STORE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, store_id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(CountsTable, values, VAR_ID + "=? AND " + STORE_ID + "=?", new String[]{var_id, store_id});
        } else {
            db.insert(CountsTable, null, values);
        }

        //db.close();
    }

    public void setLineOrderDelivery(String order_number) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_IS_PICKUP, 0);
        values.put(ORDER_NUMBER, order_number);
        values.put(ORDER_IS_NEW_POST, 0);
        values.put(ORDER_IS_LOGIST, 0);
        values.put(ORDER_DELIVERY_ADDRESS, "");
        values.put(ORDER_CITY_ID, "");
        values.put(ORDER_POINT_ID, "");

        db.insert(OrderDeliveryTable, null, values);

    }

    //проставляем заказу признак есть он в базе или нет
    public void isBaseOrderById(int id, boolean isBase, String number) {

        ContentValues values = new ContentValues();
        values.put(ORDER_IS_BASE, isBase);
        values.put(ORDER_NUMBER_1C, number);
        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrdersTable, values, ID + "=?", new String[]{String.valueOf(id)});
        }

    }

    public void updateStatusOrderById(String number_order_1c, int status, String comment) {

        ContentValues values = new ContentValues();

        values.put(ORDER_STATUS, status);
        values.put(ORDER_COMMENT, comment);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER_1C + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{number_order_1c});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrdersTable, values, ORDER_NUMBER_1C + "=?", new String[]{number_order_1c});
        }
    }

    public void updateShippingDateOrderById(int idOrder, String shippingDate) {

        ContentValues values = new ContentValues();

        values.put(ORDER_SHIPPING_DATE, shippingDate);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(idOrder)});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrdersTable, values, ID + "=?", new String[]{String.valueOf(idOrder)});
        }
    }

    public void updateAllOrderById(String number_order_1c, int status, String comment, String discont, ArrayList products, String orderNubmer, String clientCode, double totalOrder, int isBank, String shipping_date, String address_code) {

        //обновляем таблицу заказов
        ContentValues values = new ContentValues();

        values.put(ORDER_STATUS, status);
        values.put(ORDER_COMMENT, comment);
        values.put(ORDER_DISCONT, discont);
        values.put(ORDER_TOTAL,totalOrder);
        values.put(ORDER_TOTAL_1C,totalOrder);
        values.put(ORDER_IS_BANK, isBank);
        values.put(ORDER_SHIPPING_DATE, shipping_date);
        values.put(ADDRESS_CODE, address_code);

        Order order = getOrderId(orderNubmer);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER_1C + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{number_order_1c});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrdersTable, values, ORDER_NUMBER_1C + "=?", new String[]{number_order_1c});
        }

        if (discont.equals("")){
            List<VariantOrder> variants = getVariantByOrderId(orderNubmer);
            for (int i = 0; i < variants.size(); i++) {
                deleteOrderItem(variants.get(i).getVariant_id(), order.getStore_id(), orderNubmer, 1 );
           }
        }

        if (products.size() > 0) {

            //обновляем товары по заказу
            for (int i = 0; i < products.size(); i++) {
                List<Cart> cartList = new ArrayList<>();
                LinkedTreeMap<Object, Object> prt = (LinkedTreeMap) products.get(i);
                String store_id = (String) prt.get("store_id");
                int is_gift = Double.valueOf((Double) prt.get("is_gift")).intValue();

                Variant variant = getVariantDetailsByBarcod((String) prt.get("barcode"));
                Product product = getProductDetailsById((String) prt.get("pdc_id"));

                deleteOrderItem(variant.getVariant_id(), store_id, orderNubmer, is_gift);

                Cart cart = new Cart();
                cart.setVariant(variant);
                cart.setProduct(product);

                Double price1c = Double.valueOf((Double) prt.get("price"));
                Double price_discont1c = Double.valueOf((Double) prt.get("price_discont"));
                int quantity1c = Double.valueOf((Double) prt.get("count")).intValue();
                int manual_discont = Double.valueOf((Double) prt.get("manual_discont")).intValue();
                int auto_discont = Double.valueOf((Double) prt.get("auto_discont")).intValue();


                cart.setItemQuantity(quantity1c);
                cart.setPrice_value((double) price1c);
                cart.setStore_id(store_id);
                cart.setOrder_client(clientCode);
                cartList.add(cart);

                insertOrderHistory(cartList, orderNubmer, is_gift);

                updateCountPriceItemOrderHistory(quantity1c, price1c, price_discont1c, manual_discont, auto_discont, product.getProduct_code(), variant.getVariant_id(), store_id, orderNubmer, is_gift);
            }
        }

        updateTotal1COrderById(orderNubmer, totalOrder, number_order_1c);

    }

    public void updateTotal1COrderById(String order_number, double totalOrder, String number1c){
        ContentValues values = new ContentValues();

        values.put(ORDER_TOTAL_1C, totalOrder);
        values.put(ORDER_NUMBER_1C, number1c);

        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER + "=?";
        boolean isUpdate = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_number});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();
        if (isUpdate) {
            db.update(OrdersTable, values, ORDER_NUMBER + "=?", new String[]{order_number});
        }
    }

    public void updateDiscont1cOrderById(String order_number, String discont){
        ContentValues values = new ContentValues();

        values.put(ORDER_DISCONT, discont);

        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER + "=?";
        boolean isUpdate = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_number});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();
        if (isUpdate) {
            db.update(OrdersTable, values, ORDER_NUMBER + "=?", new String[]{order_number});
        }
    }

    public int getCountOrders() {
        int count = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        count = cursor.getCount();
        //db.close();
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<OrdersDate> getOrders() {

        List<OrdersDate> ordersDateList = new ArrayList<>();

        // Select All Query
        String selectQueryDate = "SELECT " + ORDER_DATE + "," + ID + " FROM " + OrdersTable + " GROUP BY " + ORDER_DATE + " ORDER BY " + ORDER_DATE + " ASC" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQueryDate, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {

                String dateItem = cursor1.getString(cursor1.getColumnIndex(ORDER_DATE));
                int idOrder = cursor1.getInt(cursor1.getColumnIndex(ID));

                List<Order> orders = new ArrayList<>();

                String selectQueryOrder = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_DATE + "=?" + " ORDER BY " + ORDER_NUMBER + " DESC" ;

                Cursor cursor = db.rawQuery(selectQueryOrder, new String[]{dateItem});

                if (cursor.moveToFirst()) {
                    do {
                        Order order = new Order();
                        int id = cursor.getInt(cursor.getColumnIndex(ID));
                        String order_number = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER));
                        String order_date = cursor.getString(cursor.getColumnIndex(ORDER_DATE));
                        String store_id = cursor.getString(cursor.getColumnIndex(STORE_ID));
                        double order_total = cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL));
                        String order_client = cursor.getString(cursor.getColumnIndex(ORDER_CLIENT));
                        int order_isBase = cursor.getInt(cursor.getColumnIndex(ORDER_IS_BASE));
                        String order_number_1c = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_1C));
                        int order_status = cursor.getInt(cursor.getColumnIndex(ORDER_STATUS));
                        double order_total_1c = cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL_1C));
                        String comment = cursor.getString(cursor.getColumnIndex(ORDER_COMMENT));

                        order.setId(id);
                        order.setNumber(order_number);
                        order.setDate(order_date);
                        order.setStore_id(store_id);
                        order.setTotal(order_total);
                        order.setClient_code(order_client);
                        order.setBase(order_isBase);
                        order.setNumber_1c(order_number_1c);
                        order.setStatus(order_status);
                        order.setTotal_1c(order_total_1c);
                        order.setComment(comment);

                        // Adding to list
                        orders.add(order);
                    } while (cursor.moveToNext());
                }

                cursor.close();

                OrdersDate ordersDateNew = new OrdersDate();
                ordersDateNew.setDate(dateItem);
                ordersDateNew.setSort(idOrder);
                ordersDateNew.setOrderList(orders);

                ordersDateList.add(ordersDateNew);

            } while (cursor1.moveToNext());
        }
        cursor1.close();
        //db.close();

        SortByDate dateComparator = new SortByDate();
        ordersDateList.sort(dateComparator);

        // return order items list
        return ordersDateList;
    }

    // Get Category List
    public List<PicsDate> getPicsList(String client_code) {
        List<PicsDate> picsDataList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + PICS_DATE + " FROM " + PicsTable + " WHERE " + CLIENT_ID + "=?" + " GROUP BY "  + PICS_DATE + " ORDER BY " + PICS_DATE + " DESC";
        Cursor cursor1 = db.rawQuery(selectQuery, new String[]{client_code});
        if (cursor1.moveToFirst()) {
            do {

                String dateItem = cursor1.getString(cursor1.getColumnIndex(PICS_DATE));

                List<Pics> picsList = new ArrayList<>();
                String selectQueryPics = "SELECT * FROM " + PicsTable + " WHERE " + CLIENT_ID + "=?  AND " + PICS_DATE + "=? " +  " ORDER BY " + PICS_URL + " ASC" ;

                Cursor cursor = db.rawQuery(selectQueryPics, new String[]{client_code, dateItem});

                if (cursor.moveToFirst()) {
                    do {
                        Pics pics = new Pics();

                        String clent_id = cursor.getString(cursor.getColumnIndex(CLIENT_ID));
                        String file_name = cursor.getString(cursor.getColumnIndex(PICS_URL));
                        String gps_lat = cursor.getString(cursor.getColumnIndex(GPS_LAT));
                        String gps_lon = cursor.getString(cursor.getColumnIndex(GPS_LON));
                        String date = cursor.getString(cursor.getColumnIndex(PICS_DATE));
                        String time = cursor.getString(cursor.getColumnIndex(PICS_TIME));

                        pics.setClient_id(clent_id);
                        pics.setPics_name(file_name);
                        pics.setGps_lat(gps_lat);
                        pics.setGps_lon(gps_lon);
                        pics.setDate(date);
                        pics.setTime(time);

                        picsList.add(pics);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                PicsDate picsDate = new PicsDate();
                picsDate.setDate(dateItem);
                picsDate.setPicsList(picsList);

                picsDataList.add(picsDate);

            } while (cursor1.moveToNext());
        }
        cursor1.close();

        return picsDataList;
    }

    // Get Order History
    public Order getOrderId(String order_number) {
        Order order = new Order();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            order.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            order.setNumber(cursor.getString(cursor.getColumnIndex(ORDER_NUMBER)));
            order.setDate(cursor.getString(cursor.getColumnIndex(ORDER_DATE)));
            order.setStore_id(cursor.getString(cursor.getColumnIndex(STORE_ID)));
            order.setTotal(cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL)));
            order.setClient_code(cursor.getString(cursor.getColumnIndex(ORDER_CLIENT)));
            order.setComment(cursor.getString(cursor.getColumnIndex(ORDER_COMMENT)));
            order.setBase(cursor.getInt(cursor.getColumnIndex(ORDER_IS_BASE)));
            order.setNumber_1c(cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_1C)));
            order.setTotal_1c(cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL_1C)));
            order.setDiscont(cursor.getString(cursor.getColumnIndex(ORDER_DISCONT)));
            order.setShipping_date(cursor.getString(cursor.getColumnIndex(ORDER_SHIPPING_DATE)));
            order.setAddress_code(cursor.getString(cursor.getColumnIndex(ADDRESS_CODE)));
            order.setIs_bank(cursor.getInt(cursor.getColumnIndex(ORDER_IS_BANK)));
            order.setStatus(cursor.getInt(cursor.getColumnIndex(ORDER_STATUS)));
        }

        cursor.close();
        //db.close();

        // return order items list
        return order;
    }

    public Order getOrderBy1C(String order_number_1) {
        Order order = new Order();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER_1C + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_number_1});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            order.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            order.setNumber(cursor.getString(cursor.getColumnIndex(ORDER_NUMBER)));
            order.setDate(cursor.getString(cursor.getColumnIndex(ORDER_DATE)));
            order.setStore_id(cursor.getString(cursor.getColumnIndex(STORE_ID)));
            order.setTotal(cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL)));
            order.setClient_code(cursor.getString(cursor.getColumnIndex(ORDER_CLIENT)));
            order.setComment(cursor.getString(cursor.getColumnIndex(ORDER_COMMENT)));
            order.setBase(cursor.getInt(cursor.getColumnIndex(ORDER_IS_BASE)));
            order.setNumber_1c(cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_1C)));
            order.setTotal_1c(cursor.getDouble(cursor.getColumnIndex(ORDER_TOTAL_1C)));
            order.setDiscont(cursor.getString(cursor.getColumnIndex(ORDER_DISCONT)));
        }
        cursor.close();
        //db.close();

        // return order items list
        return order;
    }

    public List<VariantOrder> getVariantByOrderId(String order_id) {
        List<VariantOrder> variantOrderList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String variantId = cursor.getString(cursor.getColumnIndex(VAR_ID));
                VariantOrder variantOrder = getVariantOrderDetailsById(variantId);
                int is_gift = cursor.getInt(cursor.getColumnIndex(PDT_GIFT));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
                int quantity_1c = cursor.getInt(cursor.getColumnIndex(QUANTITY_1C));
                int manual_discont = cursor.getInt(cursor.getColumnIndex(MANUAL_DISCONT));
                int auto_discont = cursor.getInt(cursor.getColumnIndex(AUTO_DISCONT));
                double price_value = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
                double price_value_1c = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE_1C));

                variantOrder.setIsGift(is_gift);
                variantOrder.setQuantity(quantity);
                variantOrder.setQuantity_1c(quantity_1c);
                variantOrder.setManual_discont(manual_discont);
                variantOrder.setAuto_discont(auto_discont);
                variantOrder.setPrice_value(price_value);
                variantOrder.setPrice_value_1c(price_value_1c);
                variantOrderList.add(variantOrder);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return order items list
        return variantOrderList;
    }

    public List<Cart> getCartItemByOrderId(String order_id) {
        List<Cart> itemOrderList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + ORDER_NUMBER + "=?";
        Order order = getOrderId(order_id);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String productId = cursor.getString(cursor.getColumnIndex(PDT_ID));
                String variantId = cursor.getString(cursor.getColumnIndex(VAR_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
                double price_value = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
                String id_store = cursor.getString(cursor.getColumnIndex(STORE_ID));
                String order_number = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER));
                String order_client = order.getClient_code();

                Product product = getProductDetailsById(productId);
                Variant variant = getVariantDetailsById(variantId);

                cart.setId(id);
                cart.setItemQuantity(quantity);
                cart.setProduct(product);
                cart.setVariant(variant);
                cart.setPrice_value(price_value);
                cart.setStore_id(id_store);
                cart.setOrder_number(order_number);
                cart.setOrder_client(order_client);
                // Adding to list
                itemOrderList.add(cart);

            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return itemOrderList;
    }

    public int getQuantityByVariantId(String var_id, String order_number) {
        int countOrder = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                countOrder = cursor.getInt(cursor.getColumnIndex(QUANTITY));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return countOrder;
    }

    public Delivery getDeliveryOrderNumber(String order_number) {

        Delivery delivery = new Delivery();

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderDeliveryTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                delivery.setIsNewPost(cursor.getInt(cursor.getColumnIndex(ORDER_IS_NEW_POST)));
                delivery.setIsLogist(cursor.getInt(cursor.getColumnIndex(ORDER_IS_LOGIST)));
                delivery.setIsPickup(cursor.getInt(cursor.getColumnIndex(ORDER_IS_PICKUP)));
                delivery.setCity(cursor.getString(cursor.getColumnIndex(ORDER_CITY_ID)));
                delivery.setPoint(cursor.getString(cursor.getColumnIndex(ORDER_POINT_ID)));
                delivery.setAddress(cursor.getString(cursor.getColumnIndex(ORDER_DELIVERY_ADDRESS)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return delivery;
    }

    public int getOrderByPeriod(String dataStart, String dataEnd){
        int status = 0;

        ArrayList<String> mass_orders = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + OrdersTable + " WHERE " + ORDER_DATE + " BETWEEN " +  dataStart  + " AND " + dataEnd;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                mass_orders.add(cursor.getString(cursor.getColumnIndex(ORDER_NUMBER)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = 0; i < mass_orders.size(); i++) {
            String order_number = mass_orders.get(i);

            //удаляем товары которые в заказе
            db.delete(OrderHistoryTable, ORDER_NUMBER + "=?", new String[]{order_number});
            //удаляем информацию по доставке
            db.delete(OrderDeliveryTable, ORDER_NUMBER + "=?", new String[]{order_number});
            //удаляем сам заказ
            deleteOrder(order_number);
        }

        status = 1;

        return status;
    }

    public int getQuantity1СByVariantId(String var_id, String order_number) {
        int count1сOrder = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                count1сOrder = cursor.getInt(cursor.getColumnIndex(QUANTITY_1C));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return count1сOrder;
    }

    public int getManualDiscont1СByVariantId(String var_id, String order_number) {
        int percent = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                percent = cursor.getInt(cursor.getColumnIndex(MANUAL_DISCONT));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return percent;
    }

    public int getAutoDiscont1СByVariantId(String var_id, String order_number) {
        int percent = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                percent = cursor.getInt(cursor.getColumnIndex(AUTO_DISCONT));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return order items list
        return percent;
    }

    public int getIsGiftByOrder(String var_id, String order_number) {

        int isGift = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + PRICE_VALUE_1C + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, String.valueOf(0.01), order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            isGift = cursor.getInt(cursor.getColumnIndex(PDT_GIFT));
        }
        cursor.close();
        //db.close();

        return isGift;
    }

    public String getCityByCode(String city_code) {

        String cityName = "";

        // Select All Query
        String selectQuery = "SELECT * FROM " + CityNPTable + " WHERE " + CODE_NP + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{city_code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cityName = cursor.getString(cursor.getColumnIndex(OPT_NAME));
        }
        cursor.close();
        //db.close();

        return cityName;
    }

    public String getPointByCode(String point_code) {

        String pointName = "";

        // Select All Query
        String selectQuery = "SELECT * FROM " + PointNPTable + " WHERE " + CODE_NP + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{point_code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            pointName = cursor.getString(cursor.getColumnIndex(OPT_NAME));
        }
        cursor.close();
        //db.close();

        return pointName;
    }


    public double getPriceOrderVariantId(String var_id, String order_number) {
        double priceItem = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                priceItem = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return priceItem;
    }

    public double getPrice1COrderVariantId(String var_id, String order_number) {
        double priceItem = 0;

        // Select All Query
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=?  AND " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, order_number});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                priceItem = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE_1C));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return priceItem;
    }

    // Update Cart Item Quantity
    public void updateItemQuantity(int quantity, String var_id, String store_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUANTITY, quantity);
        db.update(ShoppingCartTable, values, VAR_ID + "=? AND " + STORE_ID + "=?", new String[]{var_id, store_id});
    }

    public void updateItemQuantityOrder(int quantity, String var_id, String store_id, String order_number) {
        SQLiteDatabase db = this.getWritableDatabase();

        //получаем цену в заказе
        double price_value = 0.0;
        String selectQuery = "SELECT * FROM " + OrderHistoryTable + " WHERE " + VAR_ID + "=? AND " + STORE_ID + "=? AND " + ORDER_NUMBER + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, store_id, order_number});
        if (cursor.moveToFirst()) {
            price_value = cursor.getDouble(cursor.getColumnIndex(PRICE_VALUE));
        }

        ContentValues values = new ContentValues();
        values.put(QUANTITY, quantity);
        //values.put(PRICE_VALUE, quantity * price_value);

        db.update(OrderHistoryTable, values, VAR_ID + "=? AND " + STORE_ID + "=? AND " + ORDER_NUMBER + "=?", new String[]{var_id, store_id, order_number});
    }

    //Записываем отцию для варианта
    public void insertVariantOptions(String var_id, String opt_id, String opt_val){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VAR_ID, var_id);
        values.put(OPT_ID, opt_id);
        values.put(OPT_VALUE, opt_val);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + VariantOptionsTable + " WHERE " + VAR_ID + "=? AND " + OPT_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, opt_id});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(VariantOptionsTable, values, VAR_ID + "=? AND " + OPT_ID + "=?", new String[]{var_id, opt_id});
        } else {
            db.insert(VariantOptionsTable, null, values);
        }
        //db.close();
    }

    public void insertPriceVariant(String var_id, int type_id, double value) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(VAR_ID, var_id);
        values.put(PRICE_ID, type_id);
        values.put(PRICE_VALUE, value);

        // Check If Value Already Exists
        boolean isUpdate = false;
        String selectQuery = "SELECT * FROM " + PriceVariantTable + " WHERE " + VAR_ID + "=? AND " + PRICE_ID + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{var_id, String.valueOf(type_id)});
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(PriceVariantTable, values, VAR_ID + "=? AND " + PRICE_ID + "=?", new String[]{var_id, String.valueOf(type_id)});
        } else {
            db.insert(PriceVariantTable, null, values);
        }

        //db.close();
    }

    public List<Product> getSearchProductsList(String search, String client_type) {
        List<String> productIdList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        // Create Query According To Filter
        String selectQuery = "SELECT DISTINCT " + PDT_ID + " FROM " + VariantsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String var_id = cursor.getString(cursor.getColumnIndex(PDT_ID));

                // Adding product id to list
                productIdList.add(var_id);
            } while (cursor.moveToNext());
        }

        try {
            if (productIdList.size() > 0) {
                String inClause = Util.getInClauseString(productIdList);
                selectQuery = "SELECT  * FROM " + ProductsTable + " WHERE " + PDT_ID + " IN " + inClause;
                if (!search.isEmpty()) {
                    selectQuery = selectQuery + " AND " + NAME + " LIKE '%" + search + "%'";
                }

                cursor = db.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        String id = cursor.getString(cursor.getColumnIndex(ID));
                        product.setId(id);
                        product.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                        product.setProduct_code(cursor.getString(cursor.getColumnIndex(PDT_ID)));
                        product.setVariants(getProductVariant(cursor.getString(cursor.getColumnIndex(PDT_ID)), client_type));
                        // Adding product to list
                        productList.add(product);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        // return products list
        return productList;
    }

    public List<String> getDiscounts() {

        // Select Query
        String selectQuery = "SELECT * FROM " + DiscTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<String> newArray = new ArrayList<String>();

        newArray.add("Выберите акцию.....");

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                newArray.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return newArray;
    }

    public String getTypeDiscountById(String id) {

        String name = "";
        // Select Query
        String selectQuery = "SELECT * FROM " + DiscTypeTable + " WHERE " + XML_ID + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(NAME));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return name;
    }

    public List<DiscontType> getListDiscounts() {

        List<DiscontType> discontMass = new ArrayList<>();
        List<String> allR = new ArrayList<>();
        List<Discont> disconts = new ArrayList<>();

        // Select Query
        String selectQuery = "SELECT * FROM " + DiscTable + " GROUP BY " + DISC_TYPE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String clientType = cursor.getString(cursor.getColumnIndex(CLIENT_TYPE));
                String[] arrStrings = clientType.split(";");
                allR.addAll(Arrays.asList(arrStrings));

                Discont discontItem = new Discont();
                discontItem.setCode(cursor.getString(cursor.getColumnIndex(XML_ID)));
                discontItem.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                discontItem.setClient_type(cursor.getString(cursor.getColumnIndex(CLIENT_TYPE)));
                disconts.add(discontItem);

                //newArray.add(discontItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        List<String> uniqueList = new ArrayList<String>(new LinkedHashSet<String>(allR) );


        for (int i = 0; i < uniqueList.size(); i++) {
            // ищем акции по типу
            List<TypeDiscont> massDis = new ArrayList<>();
            for (int t = 0; t < disconts.size(); t++) {
                String[] tempArrStrings = disconts.get(t).getClient_type().split(";");
                for (int y = 0; y < tempArrStrings.length; y++) {
                    if (uniqueList.get(i).equals(tempArrStrings[y])){
                        TypeDiscont newTypeDiscont = new TypeDiscont();
                        newTypeDiscont.setId(disconts.get(t).getCode());
                        newTypeDiscont.setName(disconts.get(t).getName());
                        massDis.add(newTypeDiscont);
                    }
                }
            }
            DiscontType discontType = new DiscontType();
            discontType.setType(uniqueList.get(i));
            discontType.setTypeDiscont(massDis);
            discontMass.add(discontType);

            System.out.println(uniqueList.get(i));

        }


        //Discont discontFirst = new Discont();
        //discontFirst.setName("---- Выберите акцию ----");
        //newArray.add(discontFirst);



        return discontMass;
    }

    public ArrayList<RegNP> getListRegions() {

        // Select Query
        String selectQuery = "SELECT * FROM " + RegionNPTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<RegNP> newArray = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(OPT_NAME));
                RegNP regItem = new RegNP();
                regItem.setName(name);
                newArray.add(regItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return newArray;
    }

    public ArrayList<CityNP> getListCity() {

        // Select Query
        String selectQuery = "SELECT * FROM " + CityNPTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<CityNP> newArray = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(OPT_NAME));
                String id_region = cursor.getString(cursor.getColumnIndex(REGION_CODE_NP));
                String code_np = cursor.getString(cursor.getColumnIndex(CODE_NP));
                CityNP cityItem = new CityNP();
                cityItem.setName(name);
                cityItem.setId_region(id_region);
                cityItem.setCode_np(code_np);
                newArray.add(cityItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return newArray;
    }

    public ArrayList<PointNP> getListPoint(String selectCityId) {

        // Select Query
        String selectQuery = "SELECT * FROM " + PointNPTable + " WHERE " + CITY_CODE_NP + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{selectCityId});

        ArrayList<PointNP> newArray = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(OPT_NAME));
                int weight = cursor.getInt(cursor.getColumnIndex(WEIGHT));
                String code = cursor.getString(cursor.getColumnIndex(CODE_NP));
                PointNP pointItem = new PointNP();
                pointItem.setName(name);
                pointItem.setCode_np(code);
                pointItem.setWeight(weight);
                newArray.add(pointItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return newArray;
    }

    public void setPickupOrder(String orderNumber, int isPickup) {
        ContentValues values = new ContentValues();

        values.put(ORDER_IS_PICKUP, isPickup);

        String selectQuery = "SELECT  * FROM " + OrderDeliveryTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{orderNumber});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrderDeliveryTable, values, ORDER_NUMBER + "=?", new String[]{orderNumber});
        }
    }

    public void setLogistOrder(String orderNumber, int isLogist, String address) {
        ContentValues values = new ContentValues();

        values.put(ORDER_IS_LOGIST, isLogist);
        values.put(ORDER_DELIVERY_ADDRESS, address);

        String selectQuery = "SELECT  * FROM " + OrderDeliveryTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{orderNumber});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrderDeliveryTable, values, ORDER_NUMBER + "=?", new String[]{orderNumber});
        }
    }

    //записываем доставку новой почты
    public void setNewPostOrder(String orderNumber, int isNewPost, String city, String point) {
        ContentValues values = new ContentValues();

        values.put(ORDER_IS_NEW_POST, isNewPost);
        values.put(ORDER_CITY_ID, city);
        values.put(ORDER_POINT_ID, point);

        String selectQuery = "SELECT  * FROM " + OrderDeliveryTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{orderNumber});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrderDeliveryTable, values, ORDER_NUMBER + "=?", new String[]{orderNumber});
        }
    }

    public void setBankOrder(String orderId) {
        ContentValues values = new ContentValues();

        values.put(ORDER_IS_BANK, 1);

        String selectQuery = "SELECT  * FROM " + OrdersTable + " WHERE " + ORDER_NUMBER + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{orderId});
        boolean isUpdate = false;
        if (cursor.moveToFirst()) {
            isUpdate = true;
        }
        cursor.close();

        if (isUpdate) {
            db.update(OrdersTable, values, ORDER_NUMBER + "=?", new String[]{orderId});
        }

    }

    public void ImportOrderToCart(String orderId) {

        List<Cart> cart = getCartItemByOrderId(orderId);
        for (int i = 0; i < cart.size(); i++) {
            insertIntoCart(cart.get(i).getProduct().getProduct_code(), cart.get(i).getVariant().getVariant_id(), cart.get(i).getItemQuantity(), cart.get(i).getPrice_value(), cart.get(i).getStore_id(), cart.get(i).getOrder_client());
            deleteOrderItem(cart.get(i).getVariant().getVariant_id(), cart.get(i).getStore_id(), cart.get(i).getOrder_number(), 0);
            deleteOrder(cart.get(i).getOrder_number());
        }

    }
}
