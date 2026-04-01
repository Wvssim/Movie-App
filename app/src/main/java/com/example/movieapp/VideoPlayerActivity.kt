package com.example.movieapp

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        val videoUrl = intent.getStringExtra("videoUrl")
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = android.webkit.WebViewClient()
        if (videoUrl.isNullOrEmpty()) {
            android.widget.Toast.makeText(this, "URL de la vidéo non disponible", android.widget.Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        // Vérification du format de l'URL
        if (!videoUrl.startsWith("https://www.youtube.com/embed/")) {
            android.widget.Toast.makeText(this, "URL YouTube invalide", android.widget.Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        try {
            webView.loadUrl(videoUrl)
        } catch (e: Exception) {
            android.widget.Toast.makeText(this, "Erreur lors du chargement de la vidéo", android.widget.Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            finish()
        }
    }
}


