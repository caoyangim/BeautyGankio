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
    private val girlDao: GirlDao
){
    fun getGirls(): Flow<PagingData<Girl>> {
/*        GlobalScope.launch {
            refreshGirl()
        }
        girlDao.getGirls()*/

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory ={
                GirlPagingSource(girlService)
            }
        ).flow
    }

    private suspend fun refreshGirl() {
        try {
            val response = girlService.getGirls(1,10)
            val rowIds = girlDao.save(response.data)
            Log.e(">>>","girlDao INSERT COMPLETE,${rowIds.size}")
        }catch (e:Exception){
            Log.e("GirlRepository","请求失败，${e.message}")
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
        val girl = Girl( "5e9591c60bd5529b54e712af",
        "鸢媛","Girl", "2020-05-20 08:00:00","希望下一次，能喜欢上一个也喜欢自己的人 ​​​​。",
        listOf("http://gank.io/images/d237f507bf1946d2b0976e581f8aab9b")
        ,0,"2020-05-20 08:00:00", 1,"第91期","Girl","http://gank.io/images/d237f507bf1946d2b0976e581f8aab9b", 1650)
    }

}