package com.jakelaurie.squadspook.ui

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakelaurie.squadspook.R
import kotlinx.android.synthetic.main.include_progress.*

abstract class BaseActivity: AppCompatActivity() {
    fun showError(message: String?) {
        showError(message, null, null)
    }

    fun showError(message: String?, actionTitle: String?, actionCallback: ((v: View) -> Unit)?) {
        val contentView = findViewById<View>(android.R.id.content)
        if(contentView != null) {
            val snackBar: Snackbar = Snackbar.make(
                    contentView,
                    message ?: contentView.context.getString(R.string.unknown_error),
                    Snackbar.LENGTH_LONG
            )

            if(actionTitle != null && actionCallback != null) {
                snackBar.setAction(actionTitle, actionCallback)
            }

            snackBar.show()
        }
    }

    fun showLoadingIndicator(show: Boolean) {
        if(progress != null){
            progress.visibility = if (show) VISIBLE else GONE
        }
    }
}