package com.cy.beautygankio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.cy.beautygankio.dao.GirlDao
import com.cy.beautygankio.gloable.DATABASE_GIRL_NAME

@Database(entities = [Girl::class],version = 1)
abstract class GirlDatabase :RoomDatabase(){
    abstract fun girlDao():GirlDao

    companion object{
        @Volatile private var instance:GirlDatabase? = null

        fun getInstance(context:Context):GirlDatabase{
            return instance ?: synchronized(this){
                instance ?:buildDatabase(context).also {
                    instance = it
                }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): GirlDatabase {
            return Room.databaseBuilder(context, GirlDatabase::class.java, DATABASE_GIRL_NAME)
                .build()
        }
    }
}