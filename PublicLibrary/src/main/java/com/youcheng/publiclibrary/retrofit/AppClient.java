package com.youcheng.publiclibrary.retrofit;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.youcheng.publiclibrary.BuildConfig;
import com.youcheng.publiclibrary.utils.UIUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit client 网络框架
 * @author qiaozhenxin
 */
public class AppClient {
    public static Retrofit mRetrofit;
    /**
     * 服务端域名或ip
     */
    private static String API_SERVER_URL;
    /**
     * 连接超时时间,单位  秒
     */
    private static final int CONN_TIMEOUT = 30;
    /**
     * 读取超时时间,单位  秒
     */
    private static final int READ_TIMEOUT = 20;
    /**
     * 设置写的超时时间,单位  秒
     */
    private static final int WRITE_TIMEOUT = 20;


    public static void setApiServerUrl(String apiServerUrl) {
        API_SERVER_URL = apiServerUrl;
    }

    public static Retrofit retrofit() {

        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptorM loggingInterceptor = new HttpLoggingInterceptorM();
                loggingInterceptor.setLevel(HttpLoggingInterceptorM.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            builder.connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                    .addHeader("ver", "666-0-ANDROID")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .cache(new Cache(UIUtil.getContext().getCacheDir(), 20 * 1024 * 1024));
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(AppClient.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

            GsonBuilder builderTime = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builderTime.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            builderTime.create();
        }
        return mRetrofit;
    }

}
