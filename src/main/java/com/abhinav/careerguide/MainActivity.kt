package com.abhinav.careerguide

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        // Enable JavaScript
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        // Custom WebViewClient to inject CSS after page loads
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // Inject CSS to hide Lovable edit badge / watermark
                val hideLovableBadge = """
                    javascript:(function() {
                        var style = document.createElement('style');
                        style.innerHTML = `
                            .lovable-edit-button,
                            .lovable-watermark,
                            .lovable-ui,
                            .lovable-ui *,
                            [class*="lovable"],
                            [id*="lovable"] {
                                display: none !important;
                                opacity: 0 !important;
                                visibility: hidden !important;
                                pointer-events: none !important;
                            }
                        `;
                        document.head.appendChild(style);
                    })();
                """.trimIndent()

                view?.evaluateJavascript(hideLovableBadge, null)
            }
        }

        // Needed for animations, dialogs, etc.
        webView.webChromeClient = WebChromeClient()

        // Your Lovable app URL
        webView.loadUrl("https://guidance-sparkle-way.lovable.app")
    }
}
