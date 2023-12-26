package com.example.rep


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rep.data.Recipe
import com.example.rep.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

   private lateinit var binding: ActivityDetailBinding

   private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.start(intent.getIntExtra("id", -1))

        setupObservers()
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.recipe.observe(this@DetailActivity) { result ->
                if (result.data != null) {
                    updateUI(result.data)
                }
                when (result) {
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        errorRep.isVisible = false
                    }
                    is Resource.Loading -> {
                        if (result.data == null) {
                            progressBar.isVisible = true
                        }
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        errorRep.isVisible = true

                        errorRep.text = if (result.error?.message != null) {
                            result.error.message
                        } else {
                            getString(R.string.error_msg)
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(data: Recipe) {
        binding.apply {
            titleRep.text = data.title
            servingsRep.text = data.servings
        }
    }
}