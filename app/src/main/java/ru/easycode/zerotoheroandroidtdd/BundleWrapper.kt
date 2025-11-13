package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle

interface BundleWrapper {

    interface Mutable : Save, Restore

    interface Save : BundleWrapper {
        fun save(state: UiState)
    }

    interface Restore {
        fun restore(): UiState
    }

    class Base(
        private val bundle: Bundle
    ) : Mutable {

        override fun save(state: UiState) {
            bundle.putSerializable("KEY", state)
        }

        override fun restore(): UiState {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable("KEY", UiState::class.java)
            } else {
                bundle.getSerializable("KEY")
            } as UiState
        }
    }
}