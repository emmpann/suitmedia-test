package com.github.emmpann.first_question.secondpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SecondViewModel @Inject constructor() : ViewModel() {

    private val _currentName = MutableLiveData<String>()

    val currentName: LiveData<String> = _currentName

    fun setCurrentName(name: String) {
        _currentName.value = name
    }

    private val _selectedName = MutableLiveData<String>()

    val selectedName: LiveData<String> = _selectedName

    fun setSelectedName(name: String) {
        _selectedName.value = name
    }
}