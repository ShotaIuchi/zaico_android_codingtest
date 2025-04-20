package jp.co.zaico.codingtest

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.zaico.codingtest.model.Inventory
import jp.co.zaico.codingtest.usecase.GetInventoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InvtyListViewModelTest {

    private lateinit var getInventoriesUseCase: GetInventoriesUseCase
    private lateinit var viewModel: InvtyListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getInventoriesUseCase = mockk()
        viewModel = InvtyListViewModel(getInventoriesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadInvtyList-成功`() = runTest {
        val dummyList = listOf(
            Inventory(1, "A", "cat", "state"),
            Inventory(2, "B", "cat", "state")
        )

        coEvery {
            getInventoriesUseCase(
                title = any(), category = any(), place = any(), code = any()
            )
        } returns flowOf(Result.success(dummyList))

        viewModel.onEvent(InvtyListEvent.LoadInvtyList)

        // Flowのcollectが完了するまで待機
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(dummyList, state.inventoryList)
        assertNull(state.error)
    }

    @Test
    fun `LoadInvtyList-失敗`() = runTest {
        val exception = RuntimeException("NGNGNG")

        coEvery {
            getInventoriesUseCase(
                title = any(), category = any(), place = any(), code = any()
            )
        } returns flowOf(Result.failure(exception))

        viewModel.onEvent(InvtyListEvent.LoadInvtyList)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("NGNGNG", state.error)

        val toast = viewModel.uiEvent.first()
        assertTrue(toast is InvtyListUiEvent.ShowToast)
        assertEquals("NGNGNG", (toast as InvtyListUiEvent.ShowToast).message)
    }

    @Test
    fun `UpdateQuery-searchQueryマージ`() {
        val initQuery = InvtySearchQuery(title = "AAA", category = "BBB")
        viewModel.onEvent(InvtyListEvent.UpdateQuery(initQuery))

        val updatedQuery = InvtySearchQuery(place = "CCC")

        viewModel.onEvent(InvtyListEvent.UpdateQuery(updatedQuery))

        val state = viewModel.uiState.value
        assertEquals("AAA", state.searchQuery.title)
        assertEquals("BBB", state.searchQuery.category)
        assertEquals("CCC", state.searchQuery.place)
        assertNull(state.searchQuery.code)
    }

    @Test
    fun `ClearSearchQuery-検索条件が初期化+LoadInvtyList`() = runTest {
        val dummyList = listOf(Inventory(1, "dummy", "", ""))

        coEvery {
            getInventoriesUseCase(
                title = any(), category = any(), place = any(), code = any()
            )
        } returns flowOf(Result.success(dummyList))

        viewModel.onEvent(InvtyListEvent.ClearSearchQuery)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(InvtySearchQuery(), state.searchQuery)
        assertEquals(dummyList, state.inventoryList)
        assertFalse(state.isLoading)
    }
}
