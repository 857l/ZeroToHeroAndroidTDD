package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView : TextView
    private lateinit var linerLayout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linerLayout = findViewById<LinearLayout>(R.id.rootLayout)
        textView = findViewById<TextView>(R.id.titleTextView)
        val button = findViewById<Button>(R.id.removeButton)

        button.setOnClickListener{
            linerLayout.removeView(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY, false)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (!savedInstanceState.getBoolean(KEY)){
            linerLayout.removeView(textView)
        }
    }

    companion object {
        private const val KEY = "isTextViewExist"
    }
}