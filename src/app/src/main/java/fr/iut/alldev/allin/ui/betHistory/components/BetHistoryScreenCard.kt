package fr.iut.alldev.allin.ui.betHistory.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import fr.iut.alldev.allin.data.model.bet.BetStatus
import fr.iut.alldev.allin.ext.getDateStartLabelId
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.core.AllInCard
import fr.iut.alldev.allin.ui.core.bet.BetDateTimeRow
import fr.iut.alldev.allin.ui.core.bet.BetTitleHeader
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
    AllInCard(
        modifier = modifier.fillMaxWidth(),
        radius = 16.dp
    ) {
        Column(
            Modifier.padding(horizontal = 19.dp, vertical = 11.dp)
        ) {
            BetTitleHeader(
                title = title,
                category = category,
                creator = creator,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(11.dp))
            BetDateTimeRow(
                label = stringResource(id = status.getDateStartLabelId()),
                date = date,
                time = time
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = AllInTheme.themeColors.border
        )
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