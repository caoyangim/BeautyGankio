package com.cy.beautygankio.data

import androidx.paging.PagingSource
import com.cy.beautygankio.net.GirlService
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val GIRL_STARTING_PAGE_INDEX = 1

class GirlPagingSource(
    private val service: GirlService,
):PagingSource<Int,Girl>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Girl> {
        val pos = params.key ?:GIRL_STARTING_PAGE_INDEX
        return try {
            val response = service.getGirls(pos, params.loadSize)
            val girls = response.data
            LoadResult.Page(
                data = girls,
                prevKey = if (pos == GIRL_STARTING_PAGE_INDEX) null else pos-1,
                nextKey = if (girls.isEmpty()) null else pos + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}