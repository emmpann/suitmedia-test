package com.github.emmpann.first_question.thirdpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.emmpann.first_question.data.DataItem
import com.github.emmpann.first_question.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {
    val user: LiveData<PagingData<DataItem>> =
        userRepository.getAllUser().cachedIn(viewModelScope)
}