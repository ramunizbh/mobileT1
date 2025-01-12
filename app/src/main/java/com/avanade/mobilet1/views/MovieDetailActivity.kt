package com.avanade.mobilet1.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avanade.mobilet1.R
import com.avanade.mobilet1.databinding.ActivityMovieDetailBinding
import com.avanade.mobilet1.viewmodels.MovieDetailViewMobel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.lang.Integer.parseInt

class MovieDetailActivity : AppCompatActivity() {

    lateinit var viewModel: MovieDetailViewMobel
    lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val movieId = intent.getStringExtra("id")

        viewModel = ViewModelProvider.NewInstanceFactory().create(MovieDetailViewMobel::class.java)
        viewModel.getId(movieId!!)

        viewModel.getMovie.observe(this, Observer {
            binding.tvGenre.text = " • ${it.category} • "
            binding.tvSinopse.text = it.sinopse
            binding.tvYear.text = it.year
            binding.tvCreatedBy.text = "Criado por: ${it.author}"
            binding.textLike.text = "${it.likes} Curtidas"
            binding.textCommit.text = "${it.comment} Comentários"

            viewModel._likes.value = it.likes

            Picasso.with(this)
                .load(it.poster)
                .into(binding.imageMovie)
        })

        binding.ivBack.setOnClickListener {
            finish()
        }

        //viewModel.number = intent.getIntExtra("likes", 0)
        viewModel.countLikes.observe( this, {
            binding.textLike.text = it.toString()
        })

        binding.imgLike.setOnClickListener {
            viewModel.getLikes()
            viewModel.updateLikes(movieId!!)
        }

    }
}

