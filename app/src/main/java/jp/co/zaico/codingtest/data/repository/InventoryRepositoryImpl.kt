package jp.co.zaico.codingtest.data.repository

import jp.co.zaico.codingtest.data.datasource.InventoryDataSource
import jp.co.zaico.codingtest.model.Inventory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDataSource: InventoryDataSource
) : InventoryRepository {

    override suspend fun getInventories(
        page: Int?,
        title: String?,
        category: String?,
        place: String?,
        code: String?
    ): Flow<Result<List<Inventory>>> = flow {
        var currentPage = 1
        var hasNext = true

        while (hasNext) {
            val result = inventoryDataSource.getInventories(
                page = currentPage,
                title = title,
                category = category,
                place = place,
                code = code)

            result
                .onSuccess { pagedResult ->
                    emit(Result.success(pagedResult.data))
                    currentPage = pagedResult.nextPage ?: run {
                        hasNext = false
                        return@onSuccess
                    }
                }
                .onFailure { exception ->
                    emit(Result.failure(exception))
                    hasNext = false
                }
        }
    }

    override suspend fun getInventory(id: String): Result<Inventory> {
        val result = inventoryDataSource.getInventory(id)
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}