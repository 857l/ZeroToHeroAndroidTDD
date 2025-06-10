package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var linerLayout: LinearLayout
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linerLayout = findViewById<LinearLayout>(R.id.rootLayout)
        textView = findViewById<TextView>(R.id.titleTextView)
        button = findViewById<Button>(R.id.removeButton)

        button.setOnClickListener {
            linerLayout.removeView(textView)
            button.isEnabled = false
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (findViewById<TextView>(R.id.titleTextView) == null) {
            outState.putBoolean(KEYTEXTVIEW, false)
        }
        outState.putBoolean(KEYBUTTON, button.isEnabled)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (!savedInstanceState.getBoolean(KEYTEXTVIEW)) {
            linerLayout.removeView(textView)
        }
        button.isEnabled = savedInstanceState.getBoolean(KEYBUTTON)
    }

    companion object {
        private const val KEYTEXTVIEW = "isTextViewExist"
        private const val KEYBUTTON = "isButtonEnable"
    }
}