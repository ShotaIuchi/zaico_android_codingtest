package jp.co.zaico.codingtest.usecase

import jp.co.zaico.codingtest.data.repository.InventoryRepository
import jp.co.zaico.codingtest.model.Inventory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInventoriesUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    suspend operator fun invoke(
        page: Int? = null,
        title: String? = null,
        category: String? = null,
        place: String? = null,
        code: String? = null
    ): Flow<Result<List<Inventory>>> {
        return repository.getInventories(page, title, category, place, code)
    }
}
