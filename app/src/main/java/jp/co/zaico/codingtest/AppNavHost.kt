package jp.co.zaico.codingtest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object InvtyList : Screen("invty_list")
    data object InvtyDetail : Screen("invty_detail") {
        const val ARG_ID = "id"
        val fullRoute = "$route/{$ARG_ID}"
        fun createRoute(id: String) = "invty_detail/$id"
    }
    data object InvtyCreate : Screen("invty_create")
    data object InvtyEdit : Screen("invty_edit") {
        const val ARG_ID = "id"
        val fullRoute = "$route/{$ARG_ID}"
        fun createRoute(id: String) = "invty_edit/$id"
    }
}

sealed class AppNavEvent {
    data object Back : AppNavEvent()
    data class ToDetail(val id: String) : AppNavEvent()
    data object ToCreate : AppNavEvent()
    data class ToEdit(val id: String) : AppNavEvent()
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val onUiEvent = remember(navController) { createOnUiEventHandler(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.InvtyList.route,
        modifier = modifier
    ) {
        composable(Screen.InvtyList.route) {
            InvtyListScreen(onEvent = onUiEvent)
        }

        composable(
            route = Screen.InvtyDetail.fullRoute,
            arguments = listOf(
                navArgument(Screen.InvtyDetail.ARG_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Screen.InvtyDetail.ARG_ID).orEmpty()
            InvtyDetailScreen(id = id, onEvent = onUiEvent)
        }

        composable(Screen.InvtyCreate.route) {
            InvtyCreateScreen(onEvent = onUiEvent)
        }


        composable(
            route = Screen.InvtyEdit.fullRoute,
            arguments = listOf(
                navArgument(Screen.InvtyEdit.ARG_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Screen.InvtyEdit.ARG_ID).orEmpty()
            InvtyEditScreen(id = id, onEvent = onUiEvent)
        }
    }
}


private fun createOnUiEventHandler(navController: NavHostController): (AppNavEvent) -> Unit {
    return { event ->
        when (event) {
            is AppNavEvent.Back -> navController.popBackStack()
            is AppNavEvent.ToDetail -> navController.navigate(Screen.InvtyDetail.createRoute(event.id))
            is AppNavEvent.ToCreate -> navController.navigate(Screen.InvtyCreate.route)
            is AppNavEvent.ToEdit -> navController.navigate(Screen.InvtyEdit.createRoute(event.id))
        }
    }
}