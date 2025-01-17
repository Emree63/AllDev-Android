package fr.iut.alldev.allin.ui.core

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.iut.alldev.allin.theme.AllInColorToken
import fr.iut.alldev.allin.theme.AllInTheme

@Composable
fun AllInRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    unCheckedColor: Color = Color.Transparent,
) {
    AllInCard(
        radius = 100.dp,
        borderColor = AllInColorToken.allInMint,
        borderWidth = if (!checked) 1.dp else null,
        backgroundColor = if (checked) AllInColorToken.allInPurple else unCheckedColor,
        modifier = modifier.size(12.dp)
    ) {}
}

@Preview
@Composable
private fun AllInRadioButtonNotCheckedPreview() {
    AllInTheme {
        AllInRadioButton(checked = false)
    }
}

@Preview
@Composable
private fun AllInRadioButtonNotCheckedFilledPreview() {
    AllInTheme {
        AllInRadioButton(checked = false, unCheckedColor = AllInColorToken.allInMint)
    }
}

@Preview
@Composable
private fun AllInRadioButtonCheckedPreview() {
    AllInTheme {
        AllInRadioButton(checked = true)
    }
}