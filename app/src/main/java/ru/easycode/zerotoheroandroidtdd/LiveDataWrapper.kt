package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper : ProvideLiveData {

    interface Save {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Update {
        fun update(value: UiState)
    }

    interface Mutable : Save, Update, LiveDataWrapper

    class Base(
        private val liveData: MutableLiveData<UiState> = SingleLiveEvent()
    ) : LiveDataWrapper.Mutable {

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let { bundleWrapper.save(it) }
        }

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return liveData
        }
    }
}

interface ProvideLiveData {
    fun liveData(): LiveData<UiState>
}