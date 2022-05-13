package com.zohre.symmetry.di

import androidx.multidex.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.zohre.data.discovery.RemoteDataSource

@InstallIn(SingletonComponent::class)
@Module
class SymmetryApiModule {
    @Provides
    fun provideRetrofitService(): RemoteDataSource {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        val client = builder.build()
        return Retrofit.Builder()
            .baseUrl("http://www.mocky.io")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(client)
            .build()
            .create(RemoteDataSource::class.java)
    }
}