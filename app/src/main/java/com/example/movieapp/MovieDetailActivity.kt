package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONException

class MovieDetailActivity : AppCompatActivity() {
    // private lateinit var mapFragment: SupportMapFragment
    private lateinit var descriptionTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var img: ImageView
    private lateinit var playButton: Button
    private var trailerKey: String? = null
    // private var mMap: GoogleMap? = null
    // private val cinemaLocation = LatLng(33.596460, -7.615480)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Toast.makeText(this, "MovieDetailActivity démarrée", Toast.LENGTH_SHORT).show()
            setContentView(R.layout.activity_movie_detail)
            descriptionTextView = findViewById(R.id.Details)
            img = findViewById(R.id.imageview)
            nameTextView = findViewById(R.id.textName)
            playButton = findViewById(R.id.playButton)
            val movieId = intent.getIntExtra("movieId", -1)
            if (movieId != -1) {
                fetchMovieDetails(movieId)
            } else {
                descriptionTextView.text = "No movie ID provided"
            }
            playButton.setOnClickListener {
                playTrailer()
            }
            // Carte désactivée temporairement pour diagnostic crash
            // try {
            //     mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            //     mapFragment.getMapAsync(this)
            // } catch (e: Exception) {
            //     Toast.makeText(this, "Erreur lors de l'initialisation de la carte", Toast.LENGTH_SHORT).show()
            //     e.printStackTrace()
            // }
        } catch (e: Exception) {
            Toast.makeText(this, "Erreur dans onCreate: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        val apiKey = "c19056a84691980cd60e11ee37d5ab7d"
        val movieDetailsUrl = "https://api.themoviedb.org/3/movie/$movieId?api_key=$apiKey"
        val movieVideosUrl = "https://api.themoviedb.org/3/movie/$movieId/videos?api_key=$apiKey"
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val movieDetailsRequest = JsonObjectRequest(Request.Method.GET, movieDetailsUrl, null, { response ->
            try {
                val movieName = response.optString("title", "Titre inconnu")
                val movieDescription = response.optString("overview", "Pas de description")
                var imageUrl: String? = null
                if (response.has("poster_path") && !response.isNull("poster_path")) {
                    imageUrl = "https://image.tmdb.org/t/p/w500" + response.getString("poster_path")
                }
                nameTextView.text = movieName
                descriptionTextView.text = movieDescription
                if (imageUrl != null) {
                    Glide.with(this).load(imageUrl).into(img)
                } else {
                    img.setImageResource(android.R.drawable.ic_menu_report_image)
                }
            } catch (e: JSONException) {
                Toast.makeText(this, "Erreur parsing JSON", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            } catch (e: Exception) {
                Toast.makeText(this, "Erreur inattendue", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }, { error ->
            Toast.makeText(this, "Erreur lors de la récupération des détails", Toast.LENGTH_SHORT).show()
        })
        val movieVideosRequest = JsonObjectRequest(Request.Method.GET, movieVideosUrl, null, { response ->
            try {
                trailerKey = null // reset
                if (response.has("results")) {
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val video = results.getJSONObject(i)
                        if (video.optString("site") == "YouTube" && video.optString("type") == "Trailer" && video.has("key")) {
                            trailerKey = video.getString("key")
                            break
                        }
                    }
                }
                // Afficher ou masquer le bouton play selon la présence d'une bande-annonce
                playButton.isEnabled = !trailerKey.isNullOrEmpty()
                playButton.alpha = if (!trailerKey.isNullOrEmpty()) 1.0f else 0.5f
            } catch (e: JSONException) {
                playButton.isEnabled = false
                playButton.alpha = 0.5f
                e.printStackTrace()
            }
        }, { error ->
            playButton.isEnabled = false
            playButton.alpha = 0.5f
            Toast.makeText(this, "Trailer non disponible", Toast.LENGTH_SHORT).show()
        })
        queue.add(movieDetailsRequest)
        queue.add(movieVideosRequest)
    }

    private fun playTrailer() {
        if (!trailerKey.isNullOrEmpty()) {
            val trailerUrl = "https://www.youtube.com/embed/$trailerKey"
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("videoUrl", trailerUrl)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Trailer non disponible", Toast.LENGTH_SHORT).show()
        }
    }
}
