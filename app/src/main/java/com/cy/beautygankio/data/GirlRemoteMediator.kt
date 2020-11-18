package com.cy.beautygankio.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cy.beautygankio.net.GirlService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


private const val GIRL_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GirlRemoteMediator(
    val service: GirlService,
    val girlDatabase:GirlDatabase
):RemoteMediator<Int,Girl>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Girl>): RemoteMediator.MediatorResult {
        val page:Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GIRL_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {
            val apiResponse = service.getGirls(page, state.config.pageSize)

            val repos = apiResponse.data
            val endOfPaginationReached = repos.isEmpty()
            girlDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    girlDatabase.remoteKeysDao().clearRemoteKeys()
                    girlDatabase.girlDao().clearGirl()
                }
                val prevKey = if (page == GIRL_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it._id, prevKey = prevKey, nextKey = nextKey)
                }
                girlDatabase.remoteKeysDao().insertAll(keys)
                girlDatabase.girlDao().save(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Girl>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                girlDatabase.remoteKeysDao().remoteKeysRepoId(repo._id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Girl>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                girlDatabase.remoteKeysDao().remoteKeysRepoId(repo._id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Girl>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { repoId ->
                girlDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
}