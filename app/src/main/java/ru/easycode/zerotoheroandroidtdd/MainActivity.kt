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
                            mutableStateOf(Count.Base(0, 1))
                        }
                        Text(text = value.toString())
                        Button(onClick = {
                            value = value.increment()
                        }) {
                            Text(text = "increment")
                        }
                    }
                }
            }
        }
    }
}

interface Count : Serializable {

    fun increment(): Count

    data class Base(
        private val value: Int,
        private val step: Int
    ) : Count {
        override fun increment(): Count = Base(value + step, step)
        override fun toString(): String = value.toString()

    }
}