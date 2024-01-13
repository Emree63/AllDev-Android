package fr.iut.alldev.allin.ui.betHistory.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import fr.iut.alldev.allin.data.model.bet.BetStatus
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.core.bet.BetCard
import fr.iut.alldev.allin.ui.preview.BetStatusPreviewProvider

@Composable
fun BetHistoryScreenCard(
    modifier: Modifier = Modifier,
    title: String,
    creator: String,
    category: String,
    date: String,
    time: String,
    status: BetStatus,
    nbCoins: Int,
) {
    BetCard(
        title = title,
        creator = creator,
        category = category,
        date = date,
        time = time,
        status = status,
        modifier = modifier
    ) {
        BetHistoryBetStatus(
            status = status,
            nbCoins = nbCoins
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BetHistoryScreenCardPreview(
    @PreviewParameter(BetStatusPreviewProvider::class) betStatus: BetStatus
) {
    AllInTheme {
        BetHistoryScreenCard(
            creator = "Creator",
            category = "Category",
            title = "Title",
            date = "Date",
            time = "Time",
            status = betStatus,
            nbCoins = 123
        )
    }
}