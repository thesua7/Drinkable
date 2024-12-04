package com.thesua7.drinkable.di

import android.content.Context
import com.thesua7.drinkable.model.OnnxConnector
import com.thesua7.drinkable.repository.PredictionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): OnnxConnector {
        return OnnxConnector(context)
    }

    @Provides
    @Singleton
    fun providePredictionRepository(onnxConnector: OnnxConnector): PredictionRepository {
        return PredictionRepository(onnxConnector)
    }
}
