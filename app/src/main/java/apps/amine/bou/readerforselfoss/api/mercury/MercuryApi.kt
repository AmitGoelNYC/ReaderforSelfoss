package apps.amine.bou.readerforselfoss.api.mercury


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MercuryApi(private val key: String) {
    private val service: MercuryService

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson = GsonBuilder()
                .setLenient()
                .create()
        val retrofit = Retrofit.Builder().baseUrl("https://mercury.postlight.com").client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
        service = retrofit.create(MercuryService::class.java)
    }

    fun parseUrl(url: String): Call<ParsedContent> {
        return service.parseUrl(url, this.key)
    }
}
