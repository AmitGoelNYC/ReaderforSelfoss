package apps.amine.bou.readerforselfoss.api.selfoss

import retrofit2.Call
import retrofit2.http.*


// codebeat:disable[ARITY]
internal interface SelfossService {

    @GET("login")
    fun loginToSelfoss(@Query("username") username: String, @Query("password") password: String): Call<SuccessResponse>


    @GET("items")
    fun getItems(@Query("type") type: String,
                 @Query("tag") tag: String?,
                 @Query("source") source: Long?,
                 @Query("search") search: String?,
                 @Query("username") username: String,
                 @Query("password") password: String,
                 @Query("items") items: Int,
                 @Query("offset") offset: Int): Call<List<Item>>


    @Headers("Content-Type: application/json")
    @POST("mark/{id}")
    fun markAsRead(@Path("id") id: String,
                   @Query("username") username: String,
                   @Query("password") password: String): Call<SuccessResponse>


    @Headers("Content-Type: application/json")
    @POST("unmark/{id}")
    fun unmarkAsRead(@Path("id") id: String,
                     @Query("username") username: String,
                     @Query("password") password: String): Call<SuccessResponse>


    @FormUrlEncoded
    @POST("mark")
    fun markAllAsRead(@Field("ids[]") ids: List<String>,
                      @Query("username") username: String,
                      @Query("password") password: String): Call<SuccessResponse>


    @Headers("Content-Type: application/json")
    @POST("starr/{id}")
    fun starr(@Path("id") id: String,
              @Query("username") username: String,
              @Query("password") password: String): Call<SuccessResponse>


    @Headers("Content-Type: application/json")
    @POST("unstarr/{id}")
    fun unstarr(@Path("id") id: String,
                @Query("username") username: String,
                @Query("password") password: String): Call<SuccessResponse>


    @GET("stats")
    fun stats(@Query("username") username: String,
              @Query("password") password: String): Call<Stats>


    @GET("tags")
    fun tags(@Query("username") username: String,
             @Query("password") password: String): Call<List<Tag>>


    @GET("update")
    fun update(@Query("username") username: String,
               @Query("password") password: String): Call<String>


    @GET("sources/spouts")
    fun spouts(@Query("username") username: String,
               @Query("password") password: String): Call<Map<String, Spout>>


    @GET("sources/list")
    fun sources(@Query("username") username: String,
                @Query("password") password: String): Call<List<Sources>>


    @DELETE("source/{id}")
    fun deleteSource(@Path("id") id: String,
                     @Query("username") username: String,
                     @Query("password") password: String): Call<SuccessResponse>


    @FormUrlEncoded
    @POST("source")
    fun createSource(@Field("title") title: String,
                     @Field("url") url: String,
                     @Field("spout") spout: String,
                     @Field("tags") tags: String,
                     @Field("filter") filter: String,
                     @Query("username") username: String,
                     @Query("password") password: String): Call<SuccessResponse>
}
// codebeat:disable[ARITY]