package fr.iut.alldev.allin.ui.betCreation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.iut.alldev.allin.R
import fr.iut.alldev.allin.data.model.User
import fr.iut.alldev.allin.theme.AllInTheme
import fr.iut.alldev.allin.ui.betCreation.BetCreationViewModel
import fr.iut.alldev.allin.ui.betCreation.tabs.BetCreationScreenAnswerTab
import fr.iut.alldev.allin.ui.betCreation.tabs.BetCreationScreenQuestionTab
import fr.iut.alldev.allin.ui.core.AllInSections
import fr.iut.alldev.allin.ui.core.RainbowButton
import fr.iut.alldev.allin.ui.core.SectionElement
import fr.iut.alldev.allin.ui.core.SelectionElement
import java.time.ZonedDateTime

@Composable
fun BetCreationScreenContent(
    betTheme: String,
    betThemeError: String?,
    setBetTheme: (String) -> Unit,
    betPhrase: String,
    betPhraseError: String?,
    setBetPhrase: (String) -> Unit,
    isPrivate: Boolean,
    setIsPrivate: (Boolean) -> Unit,
    registerDate: ZonedDateTime,
    registerDateError: String?,
    betDate: ZonedDateTime,
    betDateError: String?,
    friends: List<User>,
    selectedFriends: List<String>,
    setRegisterDateDialog: (Boolean) -> Unit,
    setEndDateDialog: (Boolean) -> Unit,
    setRegisterTimeDialog: (Boolean) -> Unit,
    setEndTimeDialog: (Boolean) -> Unit,
    selectedBetTypeElement: SelectionElement?,
    selectedBetType: BetCreationViewModel.BetTypeState,
    typeError: String?,
    setSelectedBetTypeElement: (SelectionElement) -> Unit,
    selectionBetType: List<SelectionElement>,
    addAnswer: (String) -> Unit,
    deleteAnswer: (String) -> Unit,
    onCreateBet: () -> Unit,
    toggleFriend: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focus = LocalFocusManager.current

    Box(Modifier.fillMaxSize()) {
        AllInSections(
            onLoadSection = { focus.clearFocus() },
            modifier = Modifier.align(Alignment.TopCenter),
            sections = listOf(
                SectionElement(stringResource(id = R.string.bet_creation_question)) {
                    BetCreationScreenQuestionTab(
                        isPrivate = isPrivate,
                        setIsPrivate = setIsPrivate,
                        betPhrase = betPhrase,
                        setBetPhrase = setBetPhrase,
                        betTheme = betTheme,
                        setBetTheme = setBetTheme,
                        friends = friends,
                        selectedFriends = selectedFriends,
                        registerDate = registerDate,
                        betDate = betDate,
                        setRegisterDateDialog = setRegisterDateDialog,
                        setEndDateDialog = setEndDateDialog,
                        setRegisterTimeDialog = setRegisterTimeDialog,
                        setEndTimeDialog = setEndTimeDialog,
                        interactionSource = interactionSource,
                        betThemeError = betThemeError,
                        betPhraseError = betPhraseError,
                        registerDateError = registerDateError,
                        betDateError = betDateError,
                        toggleFriend = toggleFriend
                    )
                },
                SectionElement(stringResource(id = R.string.bet_creation_answer)) {
                    BetCreationScreenAnswerTab(
                        selectedBetType = selectedBetType,
                        selected = selectedBetTypeElement,
                        setSelected = setSelectedBetTypeElement,
                        elements = selectionBetType,
                        addAnswer = addAnswer,
                        deleteAnswer = deleteAnswer,
                        typeError = typeError
                    )
                }
            )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.50f to AllInTheme.colors.mainSurface
                    )
                ),
        ) {
            RainbowButton(
                text = stringResource(id = R.string.bet_creation_publish),
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .padding(horizontal = 20.dp)
                    .safeContentPadding(),
                onClick = onCreateBet,
                enabled = !isPrivate || selectedFriends.isNotEmpty()
            )
        }
    }
}

@Preview
@Composable
private fun BetCreationScreenContentPreview() {
    AllInTheme {
        BetCreationScreenContent(
            friends = emptyList(),
            betTheme = "Ina",
            betThemeError = null,
            setBetTheme = { },
            betPhrase = "Bryon",
            typeError = null,
            betPhraseError = null,
            setBetPhrase = { },
            isPrivate = false,
            setIsPrivate = { },
            registerDate = ZonedDateTime.now(),
            registerDateError = null,
            betDate = ZonedDateTime.now(),
            betDateError = null,
            selectedFriends = mutableListOf(),
            setRegisterDateDialog = { },
            setEndDateDialog = { },
            setRegisterTimeDialog = { },
            setEndTimeDialog = { },
            selectedBetTypeElement = null,
            selectedBetType = BetCreationViewModel.BetTypeState.Binary,
            setSelectedBetTypeElement = { },
            selectionBetType = listOf(),
            onCreateBet = { },
            addAnswer = { },
            deleteAnswer = { },
            toggleFriend = { }
        )
    }
}