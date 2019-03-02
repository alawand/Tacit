package io.gudcodes.tacit

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask


class CallFilterRepository internal constructor(application: Application) {

    private val dao: CallFilterDao
    internal val filters: LiveData<List<CallFilter>>

    init {
        val db = CallFilterDatabase.getInstance(application)
        dao = db.callFilterDao()
        filters = dao.getAll()
    }

    fun insert(filter: CallFilter) {
        insertAsyncTask(dao).execute(filter)
    }

    private class insertAsyncTask internal constructor(private val filter: CallFilterDao) :
        AsyncTask<CallFilter, Void, Void>() {

        override fun doInBackground(vararg params: CallFilter): Void? {
            filter.insertAll(params[0])
            return null
        }
    }
}