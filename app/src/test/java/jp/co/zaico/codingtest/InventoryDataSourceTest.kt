package jp.co.zaico.codingtest

import io.mockk.coEvery
import org.junit.Test
import org.junit.Assert.*
import io.mockk.mockk
import jp.co.zaico.codingtest.data.api.InventoryApi
import jp.co.zaico.codingtest.data.datasource.InventoryDataSourceImpl
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.model.InventoryInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class InventoryDataSourceImplTest {

    private val inventoryApi: InventoryApi = mockk()
    private lateinit var dataSource: InventoryDataSourceImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dataSource = InventoryDataSourceImpl(inventoryApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---- 一覧取得 ----

    @Test
    fun `一覧取得-成功`() = runTest {
        val list = listOf(
            Inventory(1, "ABC", "", "", ""),
            Inventory(2, "DEF", "", "", "")
        )
        val response = Response.success(list)
        coEvery { inventoryApi.getInventories(any(), any(), any(), any(), any()) } returns response

        val result = dataSource.getInventories(page = 1)

        assertTrue(result.isSuccess)
        assertEquals("ABC", result.getOrNull()?.data?.firstOrNull()?.title)
        assertEquals("DEF", result.getOrNull()?.data?.get(1)?.title)
    }

    @Test
    fun `一覧取得-API失敗`() = runTest {
        val response = Response.error<List<Inventory>>(500, "".toResponseBody(null))
        coEvery {
            inventoryApi.getInventories(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns response

        val result = dataSource.getInventories(page = 1)

        assertTrue(result.isFailure)
    }

    @Test
    fun `一覧取得-例外`() = runTest {
        coEvery {
            inventoryApi.getInventories(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws IOException("NGNGNG")

        val result = dataSource.getInventories(page = 1)

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }

    // ---- 詳細取得 ----

    @Test
    fun `詳細取得-成功`() = runTest {
        val inventory = Inventory(1, "A", "", "", "")
        coEvery { inventoryApi.getInventory("1") } returns Response.success(inventory)

        val result = dataSource.getInventory("1")

        assertTrue(result.isSuccess)
        assertEquals("A", result.getOrNull()?.title)
    }

    @Test
    fun `詳細取得-API失敗`() = runTest {
        coEvery { inventoryApi.getInventory("1") } returns Response.error(
            404,
            "".toResponseBody(null)
        )

        val result = dataSource.getInventory("1")

        assertTrue(result.isFailure)
    }

    @Test
    fun `詳細取得-例外`() = runTest {
        coEvery { inventoryApi.getInventory("1") } throws RuntimeException("NGNGNG")

        val result = dataSource.getInventory("1")

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }

    // ---- 作成 ----

    @Test
    fun `作成-成功`() = runTest {
        val input = InventoryInput("ABC", "", "", "")
        val created = Inventory(99, "ABC", "", "", "")
        coEvery { inventoryApi.createInventory(input) } returns Response.success(created)

        val result = dataSource.createInventory(input)

        assertTrue(result.isSuccess)
        assertEquals("ABC", result.getOrNull()?.title)
    }

    @Test
    fun `作成-API失敗`() = runTest {
        val input = InventoryInput("A", "", "", "")
        coEvery { inventoryApi.createInventory(input) } returns Response.error(
            400,
            "".toResponseBody(null)
        )

        val result = dataSource.createInventory(input)

        assertTrue(result.isFailure)
    }

    @Test
    fun `作成-例外`() = runTest {
        val input = InventoryInput("A", "", "", "")
        coEvery { inventoryApi.createInventory(input) } throws IOException("NGNGNG")

        val result = dataSource.createInventory(input)

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }

    // ---- 編集 ----

    @Test
    fun `編集-成功`() = runTest {
        val input = InventoryInput("DEF", "", "", "")
        val updated = Inventory(1, "DEF", "", "", "")
        coEvery { inventoryApi.editInventory("1", input) } returns Response.success(updated)

        val result = dataSource.editInventory("1", input)

        assertTrue(result.isSuccess)
        assertEquals("DEF", result.getOrNull()?.title)
    }

    @Test
    fun `編集-API失敗`() = runTest {
        val input = InventoryInput("DEF", "", "", "")
        coEvery { inventoryApi.editInventory("1", input) } returns Response.error(
            400,
            "".toResponseBody(null)
        )

        val result = dataSource.editInventory("1", input)

        assertTrue(result.isFailure)
    }

    @Test
    fun `在庫編集-例外`() = runTest {
        val input = InventoryInput("ABC", "", "", "")
        coEvery { inventoryApi.editInventory("1", input) } throws Exception("NGNGNG")

        val result = dataSource.editInventory("1", input)

        assertTrue(result.isFailure)
        assertEquals("NGNGNG", result.exceptionOrNull()?.message)
    }
}
