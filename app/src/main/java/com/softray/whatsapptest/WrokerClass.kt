package com.softray.whatsapptest

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WrokerClass(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "SendMessageWorkerClass"
    }

    override suspend fun doWork(): Result {
        return try {
            val number = "201015758694"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "this is an Automated Whatsapp message sent by ${applicationContext.getString(R.string.whatsapp_suffix)}"
            )
            intent.putExtra("jid", "$number@s.whatsapp.net")
            intent.setPackage("com.whatsapp")
            applicationContext.startActivity(intent)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}