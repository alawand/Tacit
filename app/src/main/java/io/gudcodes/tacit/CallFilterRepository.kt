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

    fun insert(vararg filter: CallFilter) {
        insertAsyncTask(dao).execute(*filter)
    }

    fun delete(vararg filter: CallFilter) {
        deleteAsyncTask(dao).execute(*filter)
    }

    private class insertAsyncTask internal constructor(private val filterDao: CallFilterDao) :
        AsyncTask<CallFilter, Void, Void>() {

        override fun doInBackground(vararg filter: CallFilter): Void? {
            filterDao.insertMultiple(*filter)
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val filterDao: CallFilterDao) :
        AsyncTask<CallFilter, Void, Void>() {

        override fun doInBackground(vararg filter: CallFilter): Void? {
            filterDao.deleteMultiple(*filter)
            return null
        }
    }
}