package com.example.movieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.text.Editable
import android.text.TextWatcher
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var myMovieAdapter: MyMovieAdapter
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private var allMovies: Array<MyMovieData> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        searchEditText = findViewById(R.id.editTextSearch)
        searchButton = findViewById(R.id.buttonSearch)

        val apiKey = "c19056a84691980cd60e11ee37d5ab7d"
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey"
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    val moviesList = mutableListOf<MyMovieData>()
                    for (i in 0 until results.length()) {
                        val movieObject = results.getJSONObject(i)
                        val id = movieObject.getInt("id")
                        val title = movieObject.getString("title")
                        val releaseDate = movieObject.getString("release_date")
                        val imageUrl = movieObject.getString("poster_path")
                        moviesList.add(MyMovieData(id, title, releaseDate, imageUrl))
                    }
                    allMovies = moviesList.toTypedArray()
                    myMovieAdapter = MyMovieAdapter(allMovies, this)
                    recyclerView.adapter = myMovieAdapter
                    Toast.makeText(this, "Films récupérés : ${allMovies.size}", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Toast.makeText(this, "Erreur de parsing JSON", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                Toast.makeText(this, "Erreur réseau : ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (::myMovieAdapter.isInitialized) {
                    myMovieAdapter.filter.filter(s)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}