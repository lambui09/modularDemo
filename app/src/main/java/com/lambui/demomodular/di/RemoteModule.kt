package com.lambui.demomodular.di

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lambui.demomodular.api.ApiService
import com.lambui.demomodular.common.Constants
import com.lambui.demomodular.utils.isDebugMode
import dagger.Module
import dagger.Provides
import dagger.Lazy
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideLoggingInterceptor(@ApplicationContext context: Context): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.d("ApiService", message) }.apply {
            level =
                if (context.isDebugMode) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @SuppressLint("TrustAllX509TrustManager")
    @Singleton
    @Provides
    fun providerX509TrustManager(): X509TrustManager {
        // Create a trust manager that does not validate certificate chains
        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

    @Singleton
    @Provides
    fun providerSslSocketFactory(trust: X509TrustManager): SSLSocketFactory {
        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trust), java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

    @Provides
    fun createOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        sslSocketFactory: SSLSocketFactory,
        trustAllCerts: X509TrustManager,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .sslSocketFactory(sslSocketFactory, trustAllCerts)
            .hostnameVerifier { _, _ -> true }
    }


    @Provides
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient {
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.APP_BASE_URL)
            .callFactory { request -> okHttpClient.get().newCall(request) }
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}