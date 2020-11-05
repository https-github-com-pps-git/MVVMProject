package com.example.mvvmproject.activity.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.base.activity.BaseActivity
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    private var loadUrl: String? = null
    override fun initData() {
        super.initData()
        loadUrl = intent?.getStringExtra("link")

        var webView = WebView(mContext)
        webView.loadUrl(loadUrl!!)

        mDataBinding.parent.addView(webView)
    }
}