package fr.iut.alldev.allin.ui.betStatus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.iut.alldev.allin.R
import fr.iut.alldev.allin.data.model.bet.BetStatus
import fr.iut.alldev.allin.ext.getColor
import fr.iut.alldev.allin.ext.getTextColor
import fr.iut.alldev.allin.ext.getTitleId
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.betStatus.SHEET_HEIGHT
import fr.iut.alldev.allin.ui.preview.BetStatusPreviewProvider

@Composable
fun BetStatusBottomSheetBack(
    status: BetStatus,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier
            .fillMaxSize()
            .background(status.getColor())
            .padding(horizontal = 25.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight(1 - SHEET_HEIGHT)
        ) {
            Text(
                text = stringResource(id = status.getTitleId()),
                color = status.getTextColor(),
                style = AllInTheme.typography.h2.copy(
                    fontStyle = FontStyle.Italic
                ),
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.allin_exit),
                tint = AllInTheme.colors.white,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun BackStatusScreenBackPreview(
    @PreviewParameter(BetStatusPreviewProvider::class) betStatus: BetStatus,
) {
    AllInTheme {
        BetStatusBottomSheetBack(status = betStatus)
    }
}