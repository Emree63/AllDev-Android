package fr.iut.alldev.allin.ui.betStatus.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import fr.iut.alldev.allin.data.ext.toPercentageString
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.core.PercentagePositionnedElement
import fr.iut.alldev.allin.ui.core.StatBar

@Composable
fun BinaryStatBar(
    response1Percentage: Float,
    response1: String,
    response2: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row {
            Text(
                text = response1,
                color = AllInTheme.colors.allInBlue,
                style = AllInTheme.typography.h1,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = response2,
                style = AllInTheme.typography.h1,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp,
                color = AllInTheme.colors.allInBarPink
            )
        }
        StatBar(percentage = response1Percentage)
        PercentagePositionnedElement(
            percentage = response1Percentage
        ) {
            Text(
                text = response1Percentage.toPercentageString(),
                style = AllInTheme.typography.sm1,
                color = AllInTheme.colors.allInBarPurple
            )
        }
    }
}


private class YesNoStatBarPreviewProvider : PreviewParameterProvider<Float> {
    override val values = sequenceOf(0f, .33f, .5f, .66f, 1f)
}


@Preview
@Composable
private fun YesNoStatBarPreview(
    @PreviewParameter(YesNoStatBarPreviewProvider::class) percentage: Float,
) {
    AllInTheme {
        BinaryStatBar(
            percentage,
            "Answer 1",
            "Answer 2"
        )
    }
}