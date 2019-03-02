package io.gudcodes.tacit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class CallFilterViewModel(application: Application) : AndroidViewModel(application) {

    private val db: CallFilterDatabase
    var filters: LiveData<List<CallFilter>>

    init {
        db = CallFilterDatabase.getInstance(application)
        filters = db.callFilterDao().getAll()
    }

    fun insert(filter: CallFilter) {
        db.callFilterDao().insertAll(filter)
    }

    fun delete(filter: CallFilter) {
        db.callFilterDao().delete(filter)
    }
}