package jp.co.zaico.codingtest.data.repository

import jp.co.zaico.codingtest.model.Inventory
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {

    suspend fun getInventories(
        page: Int? = null,
        title: String? = null,
        category: String? = null,
        place: String? = null,
        code: String? = null
    ): Flow<Result<List<Inventory>>>

    suspend fun getInventory(id: String): Result<Inventory>

}