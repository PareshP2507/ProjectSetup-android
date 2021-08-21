package com.psquare.setup.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.psquare.setup.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)
    }
}