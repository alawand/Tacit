package io.gudcodes.tacit

import android.annotation.TargetApi
import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import java.net.URLDecoder

@TargetApi(Build.VERSION_CODES.O)
class CallScreeningService : CallScreeningService() {

    companion object {
        private const val LOG_TAG = "Tacit"
    }

    override fun onScreenCall(details: Call.Details) {
        val responseBuilder = CallScreeningService.CallResponse.Builder()
        var response = responseBuilder.build()

        val db = CallFilterDatabase.getInstance(applicationContext)
        val tel = details.handle.schemeSpecificPart
        val filter = db.callFilterDao().getAllByFilter(tel)

        // If a filter was tripped, reject the call.
        /// TODO allow different behavior tied to filters
        if (filter != null) {
            response = responseBuilder
                .setDisallowCall(true)
                .setRejectCall(filter.rejectCall)
                .setSkipCallLog(filter.skipCallLog)
                .setSkipNotification(filter.skipNotification)
                .build()
        }

        respondToCall(details, response)
    }
}
