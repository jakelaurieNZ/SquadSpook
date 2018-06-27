package com.jakelaurie.squadspook.ui

import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment: Fragment() {
    fun showError(message: String) {
        getBaseActivity()?.showError(message)
    }

    fun showError(message: String, actionTitle: String?, actionCallback: ((v: View) -> Unit)?) {
        getBaseActivity()?.showError(message, actionTitle, actionCallback)
    }

    fun getBaseActivity(): BaseActivity? {
        return activity as? BaseActivity
    }
}
