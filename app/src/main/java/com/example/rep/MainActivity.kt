package com.example.rep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.rep.data.Recipe
import com.example.rep.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        setupObservers()
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.recipes.observe(this@MainActivity) { result ->
                if(result.data != null) {
                    adapter.submitList(result.data)
                }

                when (result) {
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        errorRep.isVisible = false
                    }
                    is Resource.Loading -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = true
                        }
                    }
                    is Resource.Error -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = false
                            errorRep.isVisible = true
                            errorRep.text = result.error?.localizedMessage
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter = RecipeAdapter(object : RecipeAdapter.OnItemClickListener {
                override fun onClickedItem(recipe: Recipe) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("id", recipe.id)
                    startActivity(intent)
                }
            })
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_cache -> {
                viewModel.deleteAll()
            }
            R.id.action_refresh -> {
                viewModel.refresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}