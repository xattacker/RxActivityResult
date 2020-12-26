package com.xattacker.android.rx

import android.content.Intent
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function

class RxActivityResult
{
    @FunctionalInterface
    interface Lazy<V>
    {
        fun get(): V
    }

    @VisibleForTesting internal var fragment: Lazy<RxActivityResultFragment>

    companion object
    {
        private val TAG = RxActivityResult::class.java.getSimpleName()
        private val TRIGGER = Any()
    }

    constructor(activity: androidx.fragment.app.FragmentActivity)
    {
        fragment = getLazySingleton(activity.supportFragmentManager)
    }

    constructor(fragment: androidx.fragment.app.Fragment)
    {
        this.fragment = getLazySingleton(fragment.childFragmentManager)
    }

    fun startActivityForResult(intent: Intent): Observable<ActivityResultPack>
    {
        return Observable.just(TRIGGER).compose(ensure(intent))
    }

    private fun <T> ensure(intent: Intent): ObservableTransformer<T, ActivityResultPack>
    {
        return ObservableTransformer {
            o ->
            request(o, intent)
                // Transform Observable<ActivityResultPack> to ObservableSource<ActivityResultPack>
                .buffer(1)
                .flatMap(Function<List<ActivityResultPack>, ObservableSource<ActivityResultPack>> {
                     item: List<ActivityResultPack> ->
                     Observable.just(item[0])
                })
        }
    }

    private fun request(trigger: Observable<*>, intent: Intent): Observable<ActivityResultPack>
    {
        return requestImplementation(intent)
    }

    private fun requestImplementation(intent: Intent): Observable<ActivityResultPack>
    {
        fragment.get().startActivityForResultRx(intent)

        return Observable.concat(Observable.fromArray(fragment.get().publishSubject))
    }

    private fun getLazySingleton(fragmentManager: androidx.fragment.app.FragmentManager): Lazy<RxActivityResultFragment>
    {
        return object : Lazy<RxActivityResultFragment>
        {
            private var fragment: RxActivityResultFragment? = null

            @Synchronized
            override fun get(): RxActivityResultFragment
            {
                if (fragment == null)
                {
                    fragment = getFragment(fragmentManager)
                }

                return fragment!!
            }
        }
    }

    private fun getFragment(fragmentManager: androidx.fragment.app.FragmentManager): RxActivityResultFragment
    {
        var fragment = findFragment(fragmentManager)
        if (fragment == null)
        {
            fragment = RxActivityResultFragment()
            fragmentManager.beginTransaction().add(fragment, TAG).commitNow()
        }

        return fragment
    }

    private fun findFragment(fragmentManager: androidx.fragment.app.FragmentManager): RxActivityResultFragment?
    {
        return fragmentManager.findFragmentByTag(TAG) as RxActivityResultFragment?
    }
}