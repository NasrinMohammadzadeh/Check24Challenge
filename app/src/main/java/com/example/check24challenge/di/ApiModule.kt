package com.example.check24challenge.di

import com.example.check24challenge.network.api.ProductListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideProductListApi(retrofit: Retrofit): ProductListApi {
        return retrofit.create(ProductListApi::class.java)
    }

}