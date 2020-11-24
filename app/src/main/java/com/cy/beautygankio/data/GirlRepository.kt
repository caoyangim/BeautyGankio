package com.cy.beautygankio.data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cy.beautygankio.dao.GirlDao
import com.cy.beautygankio.net.GirlService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GirlRepository @Inject constructor(
    private val girlService: GirlService,
    private val girlDatabase: GirlDatabase
){
    fun getGirls(): Flow<PagingData<Girl>> {

        val pagingSourceFactory =  { girlDatabase.girlDao().getGirlsPaging()}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE * 2
            ),
            remoteMediator = GirlRemoteMediator(
                service = girlService,
                girlDatabase = girlDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
            /*{
                GirlPagingSource(girlService)
            }*/
        ).flow
    }

    private suspend fun refreshGirl() {
        try {
            val response = girlService.getGirls(1,10)
            val rowIds = girlDatabase.girlDao().save(response.data)
        }catch (e:Exception){
            Log.e("GirlRepository","请求失败，${e.message}")
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

}