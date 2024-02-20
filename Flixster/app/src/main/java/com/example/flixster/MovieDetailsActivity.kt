package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flixster.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val movieId = intent.getIntExtra("movie_id", -1)
        Log.d(movieId.toString(), "MOVIE ID");
        if (movieId != -1) {
            loadMovieDetails(movieId)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        ApiClient.instance.getMovieDetails(movieId, "a07e22bc18f5cb106bfe4cc1f83ad8ed")
            .enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(call: retrofit2.Call<Movie>, response: retrofit2.Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        movie?.let {
                            supportActionBar?.title = it.title
                            Glide.with(this@MovieDetailsActivity)
                                .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                                .into(binding.movieBackdrop)
                            binding.movieTitle.text = it.title
                            binding.movieOverview.text = it.overview
                            binding.movieStatus.text = "Status: ${it.status}"
                            binding.movieTagline.text = "Tagline: ${it.tagline}"
                            binding.movieGenre.text = "Genre: ${it.firstGenre}"
                        }
                    } else {
                        Log.e("MovieDetailsActivity", "Error fetching movie details")
                    }
                }

                override fun onFailure(call: retrofit2.Call<Movie>, t: Throwable) {
                    Log.e("MovieDetailsActivity", "Failed to fetch movie details", t)
                }
            })
    }

}
