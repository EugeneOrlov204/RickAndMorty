package com.shpp.eorlov.rickandmorty.base

import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    /**
     * Prints action logs.
     *
     * @param action name of the action to log
     */
    protected fun printLog(action: String) {
        Log.d("BaseFragment", "${javaClass.simpleName} $action")
    }
}