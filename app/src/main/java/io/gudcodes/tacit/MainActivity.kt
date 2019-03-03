package io.gudcodes.tacit

import android.app.Activity
import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling.*
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v4.content.ContextCompat.startActivity
import android.view.*
import android.widget.LinearLayout
import android.support.v7.widget.*
import android.view.LayoutInflater


const val CREATE_FILTER_REQUEST = 1

class MainActivity : AppCompatActivity() {

    private lateinit var vm : CallFilterViewModel

    fun add(filter: CallFilter) {
        vm.insert(filter)
    }

    fun delete(filter: CallFilter) {
        vm.delete(filter)
    }

    private fun createFilter() {
        Intent(applicationContext, CardDetailsActivity::class.java).also { intent ->
            startActivityForResult(intent, CREATE_FILTER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CREATE_FILTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                CallFilter(0, data!!.dataString).also {filter ->
                    add(filter)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        vm = ViewModelProviders.of(this).get(CallFilterViewModel::class.java)

        fab.setOnClickListener {
            // TODO start details activity, add returned filter
            add(CallFilter(0, "tel:256*"))
            add(CallFilter(0, "tel:+256*"))
            add(CallFilter(0, "tel:1256*"))
            createFilter()
        }

        vm.filters.observe(this, object: Observer<List<CallFilter>> {

            override fun onChanged(filters: List<CallFilter>?) {

                val cards = include.findViewById<LinearLayout>(R.id.card_layout)

                cards.removeAllViews()
                for (filter in filters.orEmpty()) {
                    val inflater = LayoutInflater.from(applicationContext)
                    val card = inflater.inflate(R.layout.template_card, cards, false) as CardView

                    card.setOnClickListener {
                        // TODO expand the card to set details
                    }

                    card.setOnLongClickListener {
                        delete(filter)
                        true
                    }

                    val text = card.findViewById<AppCompatTextView>(R.id.filter_text)
                    text.text = filter.filter.removePrefix("tel:")

                    val stats = card.findViewById<AppCompatTextView>(R.id.filter_stats)
                    stats.text = "0 calls blocked"

                    cards.addView(card)
                }
            }
        })
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