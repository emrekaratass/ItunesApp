package com.example.itunesapp.data.service

import com.example.itunesapp.data.entity.CollectionResponse
import com.example.itunesapp.shared.dispatcher.ErrorDispatcher
import com.example.itunesapp.shared.dispatcher.SuccessDispatcher
import com.example.itunesapp.shared.dispatcher.TimeoutDispatcher
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class CollectionServiceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var collectionService: CollectionService

    @Before
    fun setUp() {
        mockWebServer.start()

        val client = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor())
            connectTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(client)
            baseUrl(mockWebServer.url("/").toString())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        collectionService = retrofit.create()

        mockWebServer.dispatcher = SuccessDispatcher(SUCCESS_RESPONSE_FILE_NAME)
    }

    @Test
    fun `collection should be fetched`() = runBlocking {
        // Given
        val queryMap: Map<String, String> = mapOf()

        // When
        val response = collectionService.getCollection(queryMap)

        // Then
        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.resultCount).isEqualTo(20)
    }

    @Test
    fun `collection service should throw an error`() = runBlocking {
        mockWebServer.dispatcher = ErrorDispatcher

        // Given
        val queryMap: Map<String, String> = mapOf()
        var response: CollectionResponse? = null

        // When
        try {
            response = collectionService.getCollection(queryMap)
        } catch (e: Exception) {
            Truth.assertThat(e.message).contains("HTTP 404 Client Error")
        }

        // Then
        Truth.assertThat(response).isNull()
        Truth.assertThat(response?.results).isNull()
    }

    @Test
    fun `request should be timed out`() = runBlocking {
        mockWebServer.dispatcher = TimeoutDispatcher

        // Given
        val queryMap: Map<String, String> = mapOf()
        var response: CollectionResponse? = null

        // When
        try {
            response = collectionService.getCollection(queryMap)
        } catch (e: Exception) {
            Truth.assertThat(e.message).isEqualTo("timeout")
        }

        // Then
        Truth.assertThat(response).isNull()
        Truth.assertThat(response?.results).isNull()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private companion object {
        private const val TIMEOUT_IN_MS = 1000L
        private const val SUCCESS_RESPONSE_FILE_NAME = "collection_success_response.json"
    }
}
