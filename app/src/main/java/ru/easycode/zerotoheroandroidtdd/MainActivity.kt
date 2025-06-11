package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val count = Count.Base(2, 4)
    private lateinit var textView: TextView
    private lateinit var button: Button
    private var uiState: UiState = UiState.Base("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.countTextView)
        button = findViewById<Button>(R.id.incrementButton)

        button.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = (savedInstanceState.getSerializable(KEY) as UiState)
        uiState.apply(textView, button)
    }

    companion object {
        private const val KEY = "KEY"
    }
}


interface Count {
    fun increment(number: String): UiState

    class Base(private val step: Int, private val max: Int) : Count {

        init {
            if (step <= 0)
                throw IllegalStateException("step should be positive, but was $step")

            if (max <= 0)
                throw IllegalStateException("max should be positive, but was $max")

            if (max < step)
                throw IllegalStateException("max should be more than step")
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

interface UiState : Serializable {

    fun apply(textView: TextView, button: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
        }

    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }

    }

}
