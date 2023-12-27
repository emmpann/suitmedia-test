package com.github.emmpann.first_question.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

class UserRepository (
    private val apiService: ApiService
) {
    fun getAllUser(): LiveData<PagingData<DataItem>> {
        Log.d("repository", "called")
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }
}