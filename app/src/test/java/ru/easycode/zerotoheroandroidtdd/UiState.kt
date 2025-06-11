package ru.easycode.zerotoheroandroidtdd

interface UiState {
    data class Base(private val text: String) : UiState {

    }

    data class Max(private val text: String) : UiState {

    }

}
