package fr.iut.alldev.allin.ui.navigation

import fr.iut.alldev.allin.R

sealed class TopLevelDestination(
    val route: String,
    val title: Int,
    val subtitle: Int,
    val emoji: Int,
) {
    data object PublicBets : TopLevelDestination(
        route = Routes.PUBLIC_BETS,
        title = R.string.public_bets,
        subtitle = R.string.public_bets_subtitle,
        emoji = R.drawable.globe
    )

    data object BetCreation : TopLevelDestination(
        route = Routes.BET_CREATION,
        title = R.string.create_a_bet,
        subtitle = R.string.create_a_bet_subtitle,
        emoji = R.drawable.video_game
    )

    data object BetHistory : TopLevelDestination(
        route = "${Routes.BET_HISTORY}/false",
        title = R.string.bet_history,
        subtitle = R.string.bet_history_subtitle,
        emoji = R.drawable.eyes
    )

    data object Friends : TopLevelDestination(
        route = Routes.FRIENDS,
        title = R.string.friends,
        subtitle = R.string.friends_subtitle,
        emoji = R.drawable.holding_hands
    )

    data object CurrentBets : TopLevelDestination(
        route = "${Routes.BET_HISTORY}/true",
        title = R.string.current_bets,
        subtitle = R.string.current_bets_subtitle,
        emoji = R.drawable.money_with_wings
    )
}