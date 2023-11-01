package fr.iut.alldev.allin.ui.betcreation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.iut.alldev.allin.R
import fr.iut.alldev.allin.data.model.bet.BetType
import fr.iut.alldev.allin.ext.getIcon
import fr.iut.alldev.allin.ext.getTitle
import fr.iut.alldev.allin.ui.betcreation.tabs.BetCreationScreenAnswerTab
import fr.iut.alldev.allin.ui.betcreation.tabs.BetCreationScreenQuestionTab
import fr.iut.alldev.allin.ui.core.*
import fr.iut.alldev.allin.ui.main.MainViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun BetCreationScreen(
    viewModel: BetCreationViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val betTypes = remember { BetType.values().toList() }

    var theme by remember{ viewModel.theme }
    var phrase by remember{ viewModel.phrase }
    val (registerDate, setRegisterDate) = remember { viewModel.registerDate  }
    val (betDate, setBetDate) = remember { viewModel.betDate }
    var isPublic by remember{ viewModel.isPublic }
    var selectedBetType by remember{ viewModel.selectedBetType }

    val themeError by remember{ viewModel.themeError }
    val phraseError by remember{ viewModel.phraseError }
    val registerDateError by remember{ viewModel.registerDateError }
    val betDateError by remember{ viewModel.betDateError }

    val selectedFriends = remember { mutableListOf<Int>() }
    var selectionElements by remember { mutableStateOf(listOf<SelectionElement>()) }
    var selectedBetTypeElement by remember { mutableStateOf<SelectionElement?>(null)}
    val focus = LocalFocusManager.current

    val themeFieldName = stringResource(id = R.string.Theme)
    val phraseFieldName = stringResource(id = R.string.Bet_Phrase)
    val registerDateFieldName = stringResource(id = R.string.End_registration_date)
    val betDateFieldName = stringResource(id = R.string.End_bet_date)

    LaunchedEffect(key1 = betTypes) {
        selectionElements = betTypes.map {
            SelectionElement(
                textId = it.getTitle(),
                imageVector = it.getIcon()
            )
        }
        selectedBetTypeElement = selectionElements.getOrNull(0)
    }

    LaunchedEffect(key1 = selectedBetTypeElement){
        selectedBetType = betTypes[selectionElements.indexOf(selectedBetTypeElement)]
    }

    val (showRegisterDatePicker, setRegisterDatePicker) = remember { mutableStateOf(false) }
    val (showEndDatePicker, setEndDatePicker) = remember { mutableStateOf(false) }

    val (showRegisterTimePicker, setRegisterTimePicker) = remember { mutableStateOf(false) }
    val (showEndTimePicker, setEndTimePicker) = remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        AllInSections(
            onLoadSection = {
                focus.clearFocus()
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .padding(bottom = 90.dp),
            sections = listOf(
                SectionElement(stringResource(id = R.string.Question)){
                    BetCreationScreenQuestionTab(
                        isPublic = isPublic,
                        setIsPublic = { isPublic = it },
                        betPhrase = phrase,
                        setBetPhrase = { phrase = it },
                        betTheme = theme,
                        setBetTheme = { theme = it },
                        nbFriends = 42,
                        selectedFriends = selectedFriends,
                        registerDate = registerDate,
                        betDate = betDate,
                        setRegisterDateDialog = { setRegisterDatePicker(it) },
                        setEndDateDialog = { setEndDatePicker(it) },
                        setRegisterTimeDialog = { setRegisterTimePicker(it) },
                        setEndTimeDialog = { setEndTimePicker(it) },
                        interactionSource = interactionSource,
                        betThemeError = themeError.errorResource(),
                        betPhraseError = phraseError.errorResource(),
                        registerDateError = registerDateError.errorResource(),
                        betDateError = betDateError.errorResource(),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                    )
                },
                SectionElement(stringResource(id = R.string.Answer)){
                    BetCreationScreenAnswerTab(
                        selectedBetType = selectedBetType,
                        selected = selectedBetTypeElement,
                        setSelected = { selectedBetTypeElement = it },
                        elements = selectionElements,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                    )
                }
            )
        )

        RainbowButton(
            text = stringResource(id = R.string.Publish),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 14.dp)
                .padding(horizontal = 20.dp),
            onClick = {
                viewModel.createBet(
                    themeFieldName = themeFieldName,
                    phraseFieldName = phraseFieldName,
                    registerDateFieldName = registerDateFieldName,
                    betDateFieldName = betDateFieldName
                ){ mainViewModel.loading.value = it }
            }
        )
    }
    if (showRegisterDatePicker || showEndDatePicker) {
        val dateToEdit = if(showRegisterDatePicker) registerDate else betDate
        AllInDatePicker(
            currentDate = if(showRegisterDatePicker) registerDate else betDate,
            onSelectDate = { date ->
                val selectedDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
                val newDate = dateToEdit
                    .withYear(selectedDate.year)
                    .withMonth(selectedDate.month.value)
                    .withDayOfMonth(selectedDate.dayOfMonth)
                    .withDayOfYear(selectedDate.dayOfYear)
                if(showRegisterDatePicker) {
                    setRegisterDate(newDate)
                } else {
                    setBetDate(newDate)
                }
                setRegisterDatePicker(false)
                setEndDatePicker(false)
            },
            onDismiss = {
                setRegisterDatePicker(false)
                setEndDatePicker(false)
            }
        )
    }
    if (showRegisterTimePicker || showEndTimePicker) {
        val timeToEdit = if(showRegisterTimePicker) registerDate else betDate
        AllInTimePicker(
            hour = timeToEdit.hour,
            minutes = timeToEdit.minute,
            onSelectHour = { hour, min ->
                val time = (timeToEdit)
                    .withHour(hour)
                    .withMinute(min)
                if(showRegisterTimePicker) {
                    setRegisterDate(time)
                } else {
                    setBetDate(time)
                }
                setRegisterTimePicker(false)
                setEndTimePicker(false)
            },
            onDismiss = {
                setRegisterTimePicker(false)
                setEndTimePicker(false)
            }
        )
    }

}