package com.example.movieapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyMovieAdapter(private val myMovieData: Array<MyMovieData>, private val context: Context) : RecyclerView.Adapter<MyMovieAdapter.ViewHolder>(), Filterable {
    private var filteredMovieData: List<MyMovieData> = myMovieData.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = filteredMovieData[position]
        holder.textViewName.text = movie.movieName
        holder.textViewDate.text = movie.movieDate
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500" + movie.movieImage)
            .into(holder.movieImage)
        holder.itemView.setOnClickListener {
            val intent = android.content.Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieId", movie.movieId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filteredMovieData.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.imageview)
        val textViewName: TextView = itemView.findViewById(R.id.textName)
        val textViewDate: TextView = itemView.findViewById(R.id.textdate)
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint?.toString()?.lowercase()?.trim() ?: ""
            val results = FilterResults()
            results.values = if (filterString.isEmpty()) {
                myMovieData.toList()
            } else {
                myMovieData.filter { it.movieName.lowercase().contains(filterString) }
            }
            return results
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredMovieData = results?.values as List<MyMovieData>
            notifyDataSetChanged()
        }
    }
}
