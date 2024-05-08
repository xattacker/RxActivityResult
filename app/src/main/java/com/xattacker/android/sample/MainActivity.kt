package com.xattacker.android.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xattacker.android.rx.RxActivityResult

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View)
    {
        val intent = Intent(this, ResultActivity::class.java)

        val rx = RxActivityResult(this)

        rx.startActivityForResult(intent).
            subscribe {
            result ->
                android.util.Log.d("aaa", "result: " + result.resultCode)

                if (result.resultCode == Activity.RESULT_OK && result.data != null)
                {
                    android.util.Log.d("aaa", "return intent result: " + result.data!!.extras!!.getString("result"))
                }
        }
    }
}
