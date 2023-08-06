//AppModule.kt
package com.example.weatherapp

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(FlowAdapterFactory())
            .build()
    }

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}

class FlowAdapterFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: Set<Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        if (Types.getRawType(type) != Flow::class.java) {
            return null
        }
        val responseType = Types.collectionElementType(type, Flow::class.java)
        val delegate = moshi.adapter<Any>(responseType, annotations)
        return FlowAdapter(delegate)
    }

    private class FlowAdapter<T>(
        private val delegate: JsonAdapter<T>
    ) : JsonAdapter<Flow<T>>() {
        override fun fromJson(reader: JsonReader): Flow<T> {
            return flow {
                val value = delegate.fromJson(reader)
                value?.let { emit(it) }
            }
        }

        override fun toJson(writer: JsonWriter, value: Flow<T>?) {
            throw UnsupportedOperationException("Serialization of Flow is not supported")
        }
    }
}
