package com.app.magicsound

import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

fun FragmentActivity.permissions(
    list: List<String>,
    listener: (Boolean, List<String>, List<String>) -> Unit
) {

    PermissionX.init(this)
        .permissions(list)
        .explainReasonBeforeRequest()
        .onForwardToSettings { scope, deniedList ->
            scope.showForwardToSettingsDialog(
                deniedList,
                "You need to allow necessary permissions in Settings manually",
                "OK"
            )
        }
        .request { allGranted, grantedList, deniedList ->
            listener.invoke(allGranted, grantedList, deniedList)
        }
}