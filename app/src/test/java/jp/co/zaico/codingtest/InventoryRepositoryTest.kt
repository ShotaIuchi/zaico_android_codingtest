package jp.co.zaico.codingtest

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.zaico.codingtest.common.PagedResult
import jp.co.zaico.codingtest.data.datasource.InventoryDataSource
import jp.co.zaico.codingtest.data.repository.InventoryRepositoryImpl
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.model.InventoryInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InventoryRepositoryTest {

    private val dataSource: InventoryDataSource = mockk()
    private lateinit var repository: InventoryRepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = InventoryRepositoryImpl(dataSource)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test fun `一覧取得-成功`() = runTest {
        val p1 = PagedResult(listOf(Inventory(1, "A", "", "", "")), nextPage = 2)
        val p2 = PagedResult(listOf(Inventory(2, "B", "", "", "")), nextPage = null)
        coEvery { dataSource.getInventories(page = 1, any(), any(), any(), any()) } returns Result.success(p1)
        coEvery { dataSource.getInventories(page = 2, any(), any(), any(), any()) } returns Result.success(p2)

        val result = repository.getInventories().toList()

        assertEquals(2, result.size)
        assertEquals("A", result[0].getOrThrow()[0].title)
        assertEquals("B", result[1].getOrThrow()[0].title)
    }

    @Test fun `一覧取得-失敗`() = runTest {
        coEvery { dataSource.getInventories(page = 1, any(), any(), any(), any()) } returns Result.failure(Exception("NGNGNG"))

        val result = repository.getInventories().toList()

        assertEquals(1, result.size)
        assertTrue(result[0].isFailure)
        assertEquals("NGNGNG", result[0].exceptionOrNull()?.message)
    }

    @Test fun `在庫取得-成功`() = runTest {
        val inv = Inventory(1, "A", "", "", "")
        coEvery { dataSource.getInventory("1") } returns Result.success(inv)

        val result = repository.getInventory("1")

        assertTrue(result.isSuccess)
        assertEquals("A", result.getOrThrow().title)
    }

    @Test fun `在庫取得-失敗`() = runTest {
        coEvery { dataSource.getInventory("x") } returns Result.failure(Exception("NGNGNG"))

        val result = repository.getInventory("x")

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }

    @Test fun `在庫作成-成功`() = runTest {
        val input = InventoryInput("ABC", "", "", "")
        val created = Inventory(9, "ABC", "", "", "")
        coEvery { dataSource.createInventory(input) } returns Result.success(created)

        val result = repository.createInventory(input)

        assertTrue(result.isSuccess)
        assertEquals("ABC", result.getOrThrow().title)
    }

    @Test fun `在庫作成-失敗`() = runTest {
        val input = InventoryInput("ABC", "", "", "")
        coEvery { dataSource.createInventory(input) } returns Result.failure(Exception("NGNGNG"))

        val result = repository.createInventory(input)

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }

    @Test fun `在庫編集-成功`() = runTest {
        val input = InventoryInput("DEF", "", "", "")
        val updated = Inventory(1, "DEF", "", "", "")
        coEvery { dataSource.editInventory("1", input) } returns Result.success(updated)

        val result = repository.editInventory("1", input)

        assertTrue(result.isSuccess)
        assertEquals("DEF", result.getOrThrow().title)
    }

    @Test fun `在庫編集-失敗`() = runTest {
        val input = InventoryInput("DEF", "", "", "")
        coEvery { dataSource.editInventory("1", input) } returns Result.failure(Exception("NGNGNG"))

        val result = repository.editInventory("1", input)

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }
}