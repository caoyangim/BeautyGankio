package com.cy.beautygankio.ui.girls

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.data.GirlRepository
import kotlinx.coroutines.flow.Flow

class GirlsViewModel @ViewModelInject constructor(
    private val repository: GirlRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    var girls:LiveData<List<Girl>> = repository.getGirls().asLiveData()

    fun findGirls():Flow<PagingData<Girl>>{
        return repository.getGirls().cachedIn(viewModelScope)
    }

}