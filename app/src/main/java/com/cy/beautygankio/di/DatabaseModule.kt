package com.cy.beautygankio.di


import android.content.Context
import com.cy.beautygankio.dao.GirlDao
import com.cy.beautygankio.data.GirlDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGirlDatabase(@ApplicationContext context: Context):GirlDatabase{
        return GirlDatabase.getInstance(context)
    }


    @Provides
    @Singleton
    fun provideGirlDao(@ApplicationContext context: Context): GirlDao {
        return GirlDatabase.getInstance(context).girlDao()
    }

}