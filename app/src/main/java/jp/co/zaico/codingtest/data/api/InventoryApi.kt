package jp.co.zaico.codingtest.data.api

import jp.co.zaico.codingtest.model.Inventory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApi {

    @GET("/api/v1/inventories")
    suspend fun getInventories(
        @Query("page") page: Int? = null,
        @Query("title") title: String? = null,
        @Query("category") category: String? = null,
        @Query("place") place: String? = null,
        @Query("code") code: String? = null
    ): Response<List<Inventory>>

    @GET("/api/v1/inventories/{id}")
    suspend fun getInventory(
        @Path("id") id: String
    ): Response<Inventory>

}