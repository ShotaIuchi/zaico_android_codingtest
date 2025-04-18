package jp.co.zaico.codingtest

import android.content.Context
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

class ThirdViewModel(
    val context: Context
) : ViewModel() {

    @OptIn(DelicateCoroutinesApi::class)
    fun addInventory(
        title: String,
        category: String,
        state: String,
    ): Boolean = runBlocking {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        return@runBlocking GlobalScope.async {
            try {
                val response: HttpResponse = client.post(
                    String.format("%s/api/v1/inventories", context.getString(R.string.api_endpoint))
                ) {
                    header("Authorization", "Bearer ${context.getString(R.string.api_token)}")
                    contentType(ContentType.Application.Json)
                    setBody(
                        buildJsonObject {
                            put("title", JsonPrimitive(title))
                            put("category", JsonPrimitive(category))
                            put("state", JsonPrimitive(state))
                        }
                    )
                }

                return@async response.status.value in 200..299
            } catch (e: Exception) {
                e.printStackTrace()
                return@async false
            }
        }.await()
    }

}