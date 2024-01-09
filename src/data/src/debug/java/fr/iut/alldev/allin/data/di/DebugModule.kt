package fr.iut.alldev.allin.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DebugNetworkModule::class])
@InstallIn(SingletonComponent::class)
object DebugModule