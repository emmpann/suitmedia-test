package com.github.emmpann.first_question.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.Dispatchers

class UserRepository (
    private var apiService: ApiService
) {

    fun getAllUser(perPage: Int): LiveData<Result<PagingData<DataItem>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)

        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = perPage
                ),
                pagingSourceFactory = {
                    UserPagingSource(apiService)
                }
            ).liveData
            emit(Result.Success(pager))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error"))
        }
    }

//    fun getAllUser(): LiveData<PagingData<DataItem>> {
//        Log.d("repository", "called")
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                UserPagingSource(apiService)
//            }
//        ).liveData
//    }
}