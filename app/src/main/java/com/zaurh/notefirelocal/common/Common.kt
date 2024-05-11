package com.zaurh.notefirelocal.common

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Parcelable
import android.text.format.DateFormat
import androidx.navigation.NavController
import com.google.android.play.core.review.ReviewManagerFactory


//Convert from currentMillis to Readable Date
fun convertDate(dateInMilliseconds: String, dateFormat: String): String {
    return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
}

data class NavParam(
    val name: String,
    val value: Parcelable
)

fun navigateTo(navController: NavController, dest: String, vararg params: NavParam) {
    for (param in params) {
        navController.currentBackStackEntry?.savedStateHandle?.set(param.name, param.value)
    }
    navController.navigate(dest)
}


//send mail
fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        //Handle case where no email app is available
    } catch (t: Throwable) {
        //Handle potential other type of exceptions
    }
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

fun showFeedbackDialog(context: Context, activity: Activity, onComplete: () -> Unit){
    val reviewManager = ReviewManagerFactory.create(context)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful){
            reviewManager.launchReviewFlow(activity, it.result)
        }
        if(it.isComplete){
            onComplete()
        }
    }
}