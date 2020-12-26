package com.xattacker.android.rx

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

// extension functions with RxActivityResult

fun FragmentActivity.rxStartActivityForResult(intent: Intent, resultCompletion: (result: ActivityResultPack) -> Unit)
{
    val rx = RxActivityResult(this)
    rx.startActivityForResult(intent)
        .subscribe {
             result ->
             resultCompletion(result)
        }
}

fun Fragment.rxStartActivityForResult(intent: Intent, resultCompletion: (result: ActivityResultPack) -> Unit)
{
    val rx = RxActivityResult(this)
    rx.startActivityForResult(intent)
        .subscribe {
                result ->
            resultCompletion(result)
        }
}