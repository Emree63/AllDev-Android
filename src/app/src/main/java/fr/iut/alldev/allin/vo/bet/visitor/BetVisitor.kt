package fr.iut.alldev.allin.vo.bet.visitor

import androidx.compose.runtime.Composable
import fr.iut.alldev.allin.data.model.bet.MatchBet
import fr.iut.alldev.allin.data.model.bet.YesNoBet
import fr.iut.alldev.allin.vo.Visitor


interface DisplayBetVisitor : Visitor {
    @Composable
    fun VisitYesNoBet(b: YesNoBet)

    @Composable
    fun VisitMatchBet(b: MatchBet)
}