package br.com.udacity.ruyano.recipes.views.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.udacity.ruyano.recipes.R
import br.com.udacity.ruyano.recipes.databinding.ActivityMainBinding
import br.com.udacity.ruyano.recipes.utils.NetworkUtil
import br.com.udacity.ruyano.recipes.views.recipe.details.RecipeDetailsActivity

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    private var viewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewBinding(savedInstanceState)
    }

    private fun setupViewBinding(savedInstanceState: Bundle?) {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel!!.init()
        }
        activityMainBinding!!.model = viewModel
        if (resources.getBoolean(R.bool.isTablet)) {
            setupGridRecyclerView()
        } else {
            setupLinearRecyclerView()
        }
        setupRecipesList()
        setupOnItemSelected()
        if (NetworkUtil.isConected(this)) {
            viewModel!!.getRecipes()
        } else {
            viewModel!!.showNoInternetView()
        }
    }

    private fun setupLinearRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        activityMainBinding!!.recipesRecyclerview.layoutManager = linearLayoutManager
        activityMainBinding!!.recipesRecyclerview.adapter = viewModel!!.adapter
    }

    private fun setupGridRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        activityMainBinding!!.recipesRecyclerview.layoutManager = gridLayoutManager
        activityMainBinding!!.recipesRecyclerview.adapter = viewModel!!.adapter
    }

    private fun setupRecipesList() {
        viewModel!!.recipesLiveData.observe(this, Observer { recipes ->
            if (recipes != null && recipes.size > 0) {
                viewModel!!.adapter.notifyDataSetChanged()
                viewModel!!.showRecipesList()
            } else {
                viewModel!!.showEmptyView()
            }
        })
    }

    private fun setupOnItemSelected() {
        viewModel!!.selectedRecipeMutableLiveData.observe(this, Observer { recipe ->
            startActivity(RecipeDetailsActivity.getIntent(this@MainActivity, recipe))
            overridePendingTransition(R.anim.enter, R.anim.exit)
            finish()
        })
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}