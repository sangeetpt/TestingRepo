package com.infosys.kbcmovies.uicomponents.ui.login.biometric

interface ResultCallBack {
    fun onSuccess(s: String?)
    fun onError(s: String?)
}