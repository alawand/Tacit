package io.gudcodes.tacit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class CallFilterViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: CallFilterRepository

    var filters: LiveData<List<CallFilter>>

    init {
        repo = CallFilterRepository(application)
        filters = repo.filters
    }

    fun insert(filter: CallFilter) {
        repo.insert(filter)
    }

    fun delete(filter: CallFilter) {
        repo.delete(filter)
    }
}