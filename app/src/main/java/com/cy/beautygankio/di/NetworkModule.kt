package com.cy.beautygankio.di

import com.cy.beautygankio.dao.GirlDao
import com.cy.beautygankio.data.GirlDatabase
import com.cy.beautygankio.net.GirlService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGirlService():GirlService{
        return GirlService.create()
    }


}