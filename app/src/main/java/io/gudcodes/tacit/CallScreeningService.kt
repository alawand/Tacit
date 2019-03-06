package io.gudcodes.tacit

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.telecom.Call
import android.telecom.CallScreeningService


@TargetApi(Build.VERSION_CODES.O)
class CallScreeningService : CallScreeningService() {

    companion object {
        private const val LOG_TAG = "Tacit"
    }

    private fun matchesContact(tel: String): Boolean {
        val query = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(tel))

        contentResolver.query(query,
            null,
            null,
            null,
            null
        ).use { results ->
            if (results?.count ?: 0 > 0) {
                return true
            }
        }

        return false
    }

    override fun onScreenCall(details: Call.Details) {
        val responseBuilder = CallScreeningService.CallResponse.Builder()
        var response = responseBuilder.build()

        val tel = details.handle.schemeSpecificPart

        if (matchesContact(tel)) {
            respondToCall(details, response)
        }

        val db = CallFilterDatabase.getInstance(applicationContext)
        val filters = db.callFilterDao().match(tel)

        // If a filter was tripped, reject the call.
        // TODO allow different behavior tied to filters
        // TODO OR results from multiple filters together to get final action
        if (!filters.isEmpty()) {
            response = responseBuilder
                .setDisallowCall(true)
                .setRejectCall(filters[0].rejectCall)
                .setSkipCallLog(filters[0].skipCallLog)
                .setSkipNotification(filters[0].skipNotification)
                .build()
        }

        // In all unhandled cases, allow the call through.
        respondToCall(details, response)
    }
}
