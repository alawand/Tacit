package io.gudcodes.tacit

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_card_details.*
import kotlinx.android.synthetic.main.content_card_details.*


class CardDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val save = findViewById<FloatingActionButton>(R.id.saveButton)

        save.setOnClickListener {
            Intent().also { intent ->
                intent.putExtra("pattern", filterEditText.text.toString())

                intent.putExtra("rejectCall", rejectCallSwitch.isChecked)
                intent.putExtra("skipCallLog", skipCallLogSwitch.isChecked)
                intent.putExtra("skipNotification", skipNotificationSwitch.isChecked)

                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}