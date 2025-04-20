package jp.co.zaico.codingtest

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.zaico.codingtest.data.repository.InventoryRepositoryImpl
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.usecase.GetInventoriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetInventoriesUseCaseTest {

    private lateinit var repository: InventoryRepositoryImpl
    private lateinit var useCase: GetInventoriesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetInventoriesUseCase(repository)
    }

    @Test
    fun `一覧取得-成功`() = runTest {
        val dummy = listOf(
            Inventory(1, "A", "cat", "state"),
            Inventory(2, "B", "cat", "state")
        )
        coEvery {
            repository.getInventories(
                page = 1,
                title = null,
                category = null,
                place = null,
                code = null
            )
        } returns flowOf(Result.success(dummy))

        val result = useCase(page = 1).first()

        assertTrue(result.isSuccess)
        assertEquals(dummy, result.getOrNull())
    }

    @Test
    fun `一覧取得-失敗`() = runTest {
        val exception = RuntimeException("NGNGNG")
        coEvery {
            repository.getInventories(
                page = 1,
                title = null,
                category = null,
                place = null,
                code = null
            )
        } returns flowOf(Result.failure(exception))

        val result = useCase(page = 1).first()

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }
}