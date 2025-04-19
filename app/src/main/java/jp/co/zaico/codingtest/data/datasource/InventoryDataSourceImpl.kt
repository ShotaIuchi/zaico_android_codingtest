package jp.co.zaico.codingtest.data.datasource

import jp.co.zaico.codingtest.common.PagedResult
import jp.co.zaico.codingtest.data.api.InventoryApi
import jp.co.zaico.codingtest.model.Inventory
import javax.inject.Inject
import androidx.core.net.toUri

class InventoryDataSourceImpl @Inject constructor(
    private val inventoryApi: InventoryApi
) : InventoryDataSource {

    override suspend fun getInventories(
        page: Int?,
        title: String?,
        category: String?,
        place: String?,
        code: String?
    ): Result<PagedResult<Inventory>> {
        return try {
            val response = inventoryApi.getInventories(
                page = page,
                title = title,
                category = category,
                place = place,
                code = code
            )
            if (response.isSuccessful) {
                val inventories = response.body().orEmpty()
                val nextPage = extractNextPage(response.headers()["Link"])
                Result.success(PagedResult(inventories, nextPage))
            } else {
                Result.failure(Exception("Failed to fetch inventories"))
            }
        } catch (e: Exception) {
            android.util.Log.e("InventoryDataSource", "Error fetching inventories", e)
            Result.failure(e)
        }
    }

    override suspend fun getInventory(id: String): Result<Inventory> {
        return try {
            val response = inventoryApi.getInventory(id)
            if (response.isSuccessful) {
                val inventory = response.body()
                if (inventory != null) {
                    Result.success(inventory)
                } else {
                    Result.failure(Exception("Inventory not found"))
                }
            } else {
                Result.failure(Exception("Failed to fetch inventory"))
            }
        } catch (e: Exception) {
            android.util.Log.e("InventoryDataSource", "Error fetching inventory", e)
            Result.failure(e)
        }
    }

    private fun extractNextPage(linkHeader: String?): Int? {
        return linkHeader
            ?.split(",")
            ?.map { it.trim() }
            ?.firstOrNull { it.contains("rel=\"next\"") }
            ?.let { Regex("<(.*?)>").find(it)?.groupValues?.get(1) }
            ?.let { it.toUri().getQueryParameter("page")?.toIntOrNull() }
    }

}