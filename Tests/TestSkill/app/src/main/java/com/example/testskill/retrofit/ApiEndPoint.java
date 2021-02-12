package com.example.testskill.retrofit;
import com.example.testskill.Response.Data_Response;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiEndPoint {

    @GET("api/article")
    Call<Data_Response> daftar_article();

    @FormUrlEncoded
    @POST("article/add")
    Call<Data_Response> tambah_article(
            @Field("namaArticle") String namaArticle,
            @Field("isiArticle") String isiArticle
    );

    @DELETE("article/delete/{idarticle}")
    Call<Data_Response> hapus_article(@Path("idarticle") String id
    );


    @FormUrlEncoded
    @PUT("article/update/{idarticle}")
    Call<Data_Response> update_article(@Path("idarticle") String id,
                                       @Field("namaArticle") String namaArticle,
                                       @Field("isiArticle") String isiArticle
    );



}
