package com.preethzcodez.ecommerce.webservice;

import com.preethzcodez.ecommerce.json.AccesProductsJSON;
import com.preethzcodez.ecommerce.json.DiscontJSON;
import com.preethzcodez.ecommerce.json.DiscontProducts;
import com.preethzcodez.ecommerce.json.DiscontTypeJSON;
import com.preethzcodez.ecommerce.json.OrderGiftsJSON;
import com.preethzcodez.ecommerce.json.OrderInfoJSON;
import com.preethzcodez.ecommerce.json.OrderResponse;
import com.preethzcodez.ecommerce.pojo.Auth;
import com.preethzcodez.ecommerce.pojo.CityNP;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.json.OrderJSON;
import com.preethzcodez.ecommerce.json.ResponseJSON;
import com.preethzcodez.ecommerce.pojo.PointNP;
import com.preethzcodez.ecommerce.pojo.RegNP;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //новая почта
    @GET("region")
    Call<ArrayList<RegNP>> getNPRegion(@Header("Authorization") String auth);
    @GET("city")
    Call<ArrayList<CityNP>> getNPCity(@Header("Authorization") String auth);
    @GET("point")
    Call<ArrayList<PointNP>> getNPPoint(@Header("Authorization") String auth);
    //@GET("data1.json?alt=media&token=ad19d805-92ef-448a-9000-0256851d4168")
    @GET("all")
    Call<ResponseJSON> getProducts(@Header("Authorization") String auth);
    @GET("sub_all")
    Call<ResponseJSON> getSubProducts(@Header("Authorization") String auth);
    @GET("all")
    Call<DiscontJSON> getDiscounts(@Header("Authorization") String auth);
    @GET("type")
    Call<DiscontTypeJSON> getDiscountsType(@Header("Authorization") String auth);
    @GET("auth")
    Call<Auth> getAuth(@Header("Authorization") String auth);
    @GET("offers")
    Call<ResponseJSON> getOffers(@Header("Authorization") String auth);
    @GET("client/{CodeClient}")
    Call<ResponseJSON> getPrices(@Header("Authorization") String auth, @Path("CodeClient") String CodeClient);
    @GET("{CodeProduct}/{CodeClient}")
    Call<ResponseJSON> getProductInfo(@Header("Authorization") String auth, @Path("CodeProduct") String CodeProduct, @Path("CodeClient") String CodeClient);
    @POST("{idOrder}")
    Call<DiscontProducts> setDiscoutOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder, @Query("id_discont") String idDiscont);
    @POST("gifts/{idOrder}")
    Call<ArrayList<OrderGiftsJSON>> setGiftsOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder, @Query("id_discont") String idDiscont, @Query("products") String products);
    @GET("find/{idManager}")
    Call<ArrayList<Client>> getClients(@Header("Authorization") String auth, @Path("idManager") String idManager);
    @GET("counts")
    Call<ResponseJSON> getCounts(@Header("Authorization") String auth);
    @GET("access")
    Call<AccesProductsJSON> getAccessProducts(@Header("Authorization") String auth);
    @POST("add/{idClient}")
    Call<ArrayList<OrderJSON>> addOrder(@Header("Authorization") String auth, @Path("idClient") String idClient, @Query("products") String products, @Query("store") String store, @Query("number") String number, @Query("comment") String comment, @Query("shipping_date") String shipping_date, @Query("address_code") String address_code);
    @GET("info/{idOrder}")
    Call<OrderInfoJSON> getInfoOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder);
    @POST("pickup/{idOrder}")
    Call<OrderResponse> setPickupOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder);
    @POST("logist/{idOrder}")
    Call<OrderResponse> setLogistOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder);
    @POST("bank/{idOrder}")
    Call<OrderResponse> setBankOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder);
    @POST("delivery_np/{idOrder}")
    Call<OrderResponse> setDeliveryNPOrder(@Header("Authorization") String auth, @Path("idOrder") String idOrder, @Query("city_code") String city_code, @Query("post_code") String post_code);
}
