package com.xattacker.android.rx

import android.content.Intent
import android.os.Bundle
import io.reactivex.subjects.PublishSubject

internal class RxActivityResultFragment : androidx.fragment.app.Fragment()
{
    private val REQUEST_CODE = 9999

    var publishSubject: PublishSubject<ActivityResultPack>? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE)
        {
            publishSubject?.onNext(ActivityResultPack(resultCode, data))
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()

        publishSubject?.onComplete()
    }

    internal fun startActivityForResultRx(intent: Intent)
    {
        this.startActivityForResult(intent, REQUEST_CODE)
        publishSubject = PublishSubject.create()
    }
}
