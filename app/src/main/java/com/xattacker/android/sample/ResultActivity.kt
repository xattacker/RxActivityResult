package com.xattacker.android.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)
    }

    fun onButtonClick(view: View)
    {
        val intent = Intent()

        val bundle = Bundle()
        bundle.putString("result", "call succeed " + System.currentTimeMillis())
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}