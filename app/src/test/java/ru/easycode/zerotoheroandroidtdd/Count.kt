package ru.easycode.zerotoheroandroidtdd

interface Count {
    fun decrement(number: String): UiState
    fun initial(number: String): UiState
    fun increment(number: String): UiState

    data class Base(private val step: Int, private val max: Int, private val min: Int) : Count {

        init {
            if (max <= 0)
                throw IllegalStateException("max should be positive, but was $max")
            if (max < min)
                throw IllegalStateException("max should be more than min")
            if (max < step)
                throw IllegalStateException("max should be more than step")
            if (step <= 0)
                throw IllegalStateException("step should be positive, but was $step")

        }

        override fun decrement(number: String): UiState {
            val value = number.toInt()
            val result = value - step
            return if (result - step >= min)
                UiState.Base(result.toString())
            else
                UiState.Min(result.toString())
        }

        override fun initial(number: String): UiState {
            if (number.toInt() == max)
                return UiState.Max(number)
            if (number.toInt() == min)
                return UiState.Min(number)
            return UiState.Base(number)
        }

        override fun increment(number: String): UiState {
            val value = number.toInt()
            val result = value + step
            return if (result + step <= max)
                UiState.Base(result.toString())
            else
                UiState.Max(result.toString())
        }

    }

}
