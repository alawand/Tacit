package io.gudcodes.tacit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling.*
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.support.v7.widget.*
import android.view.LayoutInflater


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        val cards = include.findViewById<LinearLayout>(R.id.card_layout)

        fab.setOnClickListener {
            add(applicationContext, cards)
        }

        fab.setOnLongClickListener {
            delete(applicationContext, cards)
            true
        }


        setup(applicationContext, cards)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun setup(context: Context, cards: LinearLayout) {
    val filters = CallFilterDatabase.getInstance(context).callFilterDao().getAll()
    GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
        for (filter in filters) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var card = inflater.inflate(R.layout.template_card, null) as CardView

            card.setOnClickListener {
                // TODO expand the card to set details
                card.height
            }

            var text = card.findViewById<AppCompatTextView>(R.id.filter_text)
            text.text = "${filter.filter}"

            cards.addView(card)
        }
    }
}

fun add(context: Context, cards: LinearLayout) {
    val db = CallFilterDatabase.getInstance(context)

    // TODO remove this and implement UI
    db.callFilterDao().insertAll(
        CallFilter(0, "tel:256*"),
        CallFilter(0, "tel:+256*"),
        CallFilter(0, "tel:1256*"),
        CallFilter(0, "tel:+1256*")
    )

    // TODO remove me and just add the one
    val filters = CallFilterDatabase.getInstance(context).callFilterDao().getAll()
    GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
        for (filter in filters) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var card = inflater.inflate(R.layout.template_card, null) as CardView

            var text = card.findViewById<AppCompatTextView>(R.id.filter_text)
            text.text = "${filter.filter}"

            cards.addView(card)
        }
    }
}

fun delete(context: Context, cards: LinearLayout) {
    CallFilterDatabase.getInstance(context).callFilterDao().deleteAll()
    cards.removeAllViews()
}
