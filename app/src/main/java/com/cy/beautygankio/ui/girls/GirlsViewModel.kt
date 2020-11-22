package com.cy.beautygankio.ui.girls

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.data.GirlRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class GirlsViewModel @ViewModelInject constructor(
    private val repository: GirlRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var girls = findGirls()

    fun findGirls():Flow<PagingData<Girl>>{
        Log.e(">>>","repository.getGirls().cachedIn(viewModelScope)")
        return repository.getGirls().cachedIn(viewModelScope)
    }

}