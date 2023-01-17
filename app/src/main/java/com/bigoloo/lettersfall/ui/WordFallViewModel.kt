package com.bigoloo.lettersfall.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domian.repository.WordRepository
import kotlinx.coroutines.launch

class WordFallViewModel(private val wordRepository: WordRepository) : ViewModel() {

    init {
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
            runCatching {
                wordRepository.getWordList()
            }.onSuccess {
                Log.d("xxxxx", it.toString())
            }.onFailure {
                Log.d("xxxxx", it.message ?: "")
            }
        }
    }
}