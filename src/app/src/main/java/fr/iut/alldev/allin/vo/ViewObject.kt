package fr.iut.alldev.allin.vo

import androidx.compose.runtime.Composable

interface ViewObject<V : Visitor>{
    @Composable
    fun Accept(v: V)
}