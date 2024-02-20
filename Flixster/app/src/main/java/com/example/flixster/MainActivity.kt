package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flixster.databinding.ActivityMainBinding // Make sure this import matches your package and binding class name
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchMovies()
    }

    private fun fetchMovies() {
        ApiClient.instance.getPopularMovies("a07e22bc18f5cb106bfe4cc1f83ad8ed")
            .enqueue(object : retrofit2.Callback<MovieResponse> {
                override fun onResponse(call: retrofit2.Call<MovieResponse>, response: retrofit2.Response<MovieResponse>) {
                    val movies = response.body()?.results ?: emptyList()
                    binding.moviesRecyclerView.adapter = MovieAdapter(movies) { movie ->
                        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java).apply {
                            putExtra("movie_id", movie.id)
                        }
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}
