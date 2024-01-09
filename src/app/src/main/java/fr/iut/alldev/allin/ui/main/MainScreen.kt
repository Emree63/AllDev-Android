package fr.iut.alldev.allin.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.betStatus.BetStatusBottomSheet
import fr.iut.alldev.allin.ui.betStatus.visitor.BetStatusBottomSheetDisplayBetVisitor
import fr.iut.alldev.allin.ui.core.AllInLoading
import fr.iut.alldev.allin.ui.core.snackbar.AllInSnackbarVisualsImpl
import fr.iut.alldev.allin.ui.main.components.AllInScaffold
import fr.iut.alldev.allin.ui.navigation.AllInDrawerNavHost
import fr.iut.alldev.allin.ui.navigation.Routes
import fr.iut.alldev.allin.ui.navigation.TopLevelDestination
import fr.iut.alldev.allin.ui.navigation.drawer.AllInDrawer
import fr.iut.alldev.allin.ui.navigation.popUpTo
import fr.iut.alldev.allin.vo.bet.factory.toBetVO
import kotlinx.coroutines.launch

private val topLevelDestinations = listOf(
    TopLevelDestination.PublicBets,
    TopLevelDestination.BetCreation,
    TopLevelDestination.BetHistory,
    TopLevelDestination.Friends,
    TopLevelDestination.CurrentBets
)

@Composable
private fun rememberBetStatusVisibilities()
        : Triple<MutableState<Boolean>, MutableState<Boolean>, (Boolean) -> Unit> {
    val statusVisibility = remember {
        mutableStateOf(false)
    }

    val sheetBackVisibility = remember {
        mutableStateOf(false)
    }

    val setStatusVisibility = { it: Boolean ->
        statusVisibility.value = it
        if (it) sheetBackVisibility.value = true
    }
    return Triple(
        statusVisibility,
        sheetBackVisibility,
        setStatusVisibility,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = Routes.PUBLIC_BETS,
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToWelcomeScreen: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var loading by remember { mainViewModel.loading }
    val currentUser = remember { mainViewModel.currentUserState }
    val (selectedBet, setSelectedBet) = remember { mainViewModel.selectedBet }
    val (statusVisibility, sheetBackVisibility, setStatusVisibility) = rememberBetStatusVisibilities()

    val betStatusDisplayVisitor = remember {
        BetStatusBottomSheetDisplayBetVisitor(
            userCoinAmount = currentUser.userCoins,
            onParticipate = {
                mainViewModel.participateToBet(it)
            }
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarContent by remember { mainViewModel.snackbarContent }

    LaunchedEffect(snackbarContent) {
        snackbarContent?.let {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    AllInSnackbarVisualsImpl(
                        message = it.text,
                        withDismissAction = false,
                        duration = SnackbarDuration.Short,
                        type = it.type
                    )
                )
                snackbarHostState.currentSnackbarData
                snackbarContent = null
            }
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            if (it == SheetValue.Hidden) {
                sheetBackVisibility.value = false
            }
            true
        }
    )

    AllInDrawer(
        drawerState = drawerState,
        destinations = topLevelDestinations,
        scope = scope,
        username = currentUser.user.username,
        nbFriends = 5,
        nbBets = 35,
        bestWin = 362,
        navigateTo = { route ->
            navController.popUpTo(route, startDestination)
        },
        logout = {
            mainViewModel.deleteToken()
            navigateToWelcomeScreen()
        }
    ) {
        AllInScaffold(
            onMenuClicked = { scope.launch { drawerState.open() } },
            coinAmount = currentUser.userCoins.value,
            drawerState = drawerState,
            snackbarHostState = snackbarHostState
        ) {
            LaunchedEffect(key1 = it) {
                betStatusDisplayVisitor.paddingValues.value = it
            }
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
                    .background(AllInTheme.themeColors.mainSurface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AllInDrawerNavHost(
                    navController = navController,
                    selectBet = { bet, participate ->
                        setSelectedBet(bet)
                        betStatusDisplayVisitor.participateBottomSheetVisibility.value = participate
                        setStatusVisibility(true)
                    },
                    setLoading = { loading = it },
                    putSnackbarContent = { mainViewModel.putSnackbarContent(it) }
                )
            }
        }
    }

    BetStatusBottomSheet(
        state = bottomSheetState,
        sheetVisibility = statusVisibility.value,
        sheetBackVisibility = sheetBackVisibility.value,
        onDismiss = {
            setStatusVisibility(false)
        },
        bet = selectedBet?.toBetVO(),
        visitor = betStatusDisplayVisitor
    )
    AllInLoading(visible = loading)
    BackHandler(
        enabled = drawerState.isOpen
    ) {
        scope.launch {
            drawerState.close()
        }
    }

}


