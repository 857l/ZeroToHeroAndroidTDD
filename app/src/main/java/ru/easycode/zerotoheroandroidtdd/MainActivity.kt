package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val count = Count.Base(2, 4, 0)
    private lateinit var textView: TextView
    private lateinit var decrementButton: Button
    private lateinit var incrementButton: Button
    private var uiState: UiState = count.initial("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.countTextView)
        decrementButton = findViewById<Button>(R.id.decrementButton)
        incrementButton = findViewById<Button>(R.id.incrementButton)

        uiState.apply(textView, incrementButton, decrementButton)

        decrementButton.setOnClickListener {
            uiState = count.decrement(textView.text.toString())
            uiState.apply(textView, incrementButton, decrementButton)
        }

        incrementButton.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, incrementButton, decrementButton)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = savedInstanceState.getSerializable(KEY) as UiState
        uiState.apply(textView, incrementButton, decrementButton)
    }

    companion object {
        private const val KEY = "KEY"
    }
}

interface UiState : Serializable {

    fun apply(textView: TextView, incrementButton: Button, decrementButton: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = true
        }

    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }

    }

    data class Min(private val text: String) : UiState {
        override fun apply(textView: TextView, incrementButton: Button, decrementButton: Button) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }


    }
}

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
            return when (number.toInt()) {
                max -> UiState.Max(number)
                min -> UiState.Min(number)
                else -> UiState.Base(number)
            }
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
