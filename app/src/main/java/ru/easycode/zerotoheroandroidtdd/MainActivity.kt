package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme
import java.io.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        var value: Count by rememberSaveable {
                            mutableStateOf(Count.Base(min = 0, max = 2, value = 1, step = 1))
                        }
                        Button(onClick = {
                            value = value.increment()
                        }, enabled = !value.isMax()) {
                            Text(text = "+")
                        }
                        Text(text = value.toString())
                        Button(onClick = {
                            value = value.decrement()
                        }, enabled = !value.isMin()) {
                            Text(text = "-")
                        }
                    }
                }
            }
        }
    }
}

interface Count : Serializable {

    fun isMax(): Boolean
    fun isMin(): Boolean
    fun increment(): Count
    fun decrement(): Count

    data class Base(
        private val min: Int,
        private val max: Int,
        private val value: Int,
        private val step: Int
    ) : Count {
        override fun isMax(): Boolean = value == max

        override fun isMin(): Boolean = value == min

        override fun increment(): Count = Count.Base(min, max, value + step, step)

        override fun decrement(): Count = Count.Base(min, max, value - step, step)

        override fun toString(): String = value.toString()
    }
}