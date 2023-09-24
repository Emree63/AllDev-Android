package fr.iut.alldev.allin.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.iut.alldev.allin.R
import fr.iut.alldev.allin.ui.core.AllInChip
import fr.iut.alldev.allin.ui.home.components.HomeBetCard
import fr.iut.alldev.allin.ui.home.components.HomePopularCards
import fr.iut.alldev.allin.ui.theme.AllInTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(){

    val horizontalPadding = 23.dp

    LazyColumn{
        item {
            HomePopularCards(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .padding(top = 23.dp),
                nbPlayers = 12,
                points = 2.35f,
                pointUnit = "k",
                title = "Emre va réussir son TP de CI/CD mercredi?"
            )
        }
        stickyHeader {
                LazyRow(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                0.5f to AllInTheme.colors.allIn_White,
                                1f to Color.Transparent
                            )
                        )
                        .padding(vertical = 19.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.width(horizontalPadding))
                    }
                    items(items) {
                        var isSelected by remember { mutableStateOf(false) }
                        AllInChip(
                            text = stringResource(id = it),
                            isSelected = isSelected,
                            onClick = { isSelected = !isSelected })
                    }
                    item {
                        Spacer(modifier = Modifier.width(horizontalPadding))
                    }
            }
        }
        items(5){
            HomeBetCard(
                creator = "Lucas",
                category = "Études",
                title = "Emre va réussir son TP de CI/CD mercredi?",
                date = "11 Sept.",
                time = "13:00",
                nbPlayer = 4,
                onClickParticipate = { /* TODO */ },
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

val items = listOf(
    R.string.Public,
    R.string.Invitation,
    R.string.Current,
    R.string.Finished
)