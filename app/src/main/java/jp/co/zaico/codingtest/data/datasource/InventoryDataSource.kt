package jp.co.zaico.codingtest.data.datasource

import jp.co.zaico.codingtest.common.PagedResult
import jp.co.zaico.codingtest.model.Inventory
import retrofit2.http.Query

interface InventoryDataSource {

    suspend fun getInventories(
        @Query("page") page: Int? = null,
        @Query("title") title: String? = null,
        @Query("category") category: String? = null,
        @Query("place") place: String? = null,
        @Query("code") code: String? = null
    ): Result<PagedResult<Inventory>>

    suspend fun getInventory(
        @Query("id") id: String
    ): Result<Inventory>

}