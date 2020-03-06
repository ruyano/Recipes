package br.com.udacity.ruyano.recipes.views.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.udacity.ruyano.recipes.R
import br.com.udacity.ruyano.recipes.databinding.ActivityMainBinding
import br.com.udacity.ruyano.recipes.utils.NetworkUtil
import br.com.udacity.ruyano.recipes.views.recipe.details.RecipeDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding(savedInstanceState)
        setupRecyclerVIew()
        setupRecipesList()
        setupOnItemSelected()
        checkInternetConnection()
    }

    private fun setupViewBinding(savedInstanceState: Bundle?) {
        val activityMainBinding: ActivityMainBinding? =
                DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            viewModel.init()
        }
        activityMainBinding?.model = viewModel
    }

    private fun setupRecyclerVIew() {
        if (resources.getBoolean(R.bool.isTablet)) {
            setupGridRecyclerView()
        } else {
            setupLinearRecyclerView()
        }
    }

    private fun checkInternetConnection() {
        viewModel.hasInternetConnection(NetworkUtil.isConnected(this))
    }

    private fun setupLinearRecyclerView() {
        recipes_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewModel.adapter
        }
    }

    private fun setupGridRecyclerView() {
        recipes_recyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = viewModel.adapter
        }
    }

    private fun setupRecipesList() {
        viewModel.recipesLiveData.observe(this, Observer { recipes ->
            viewModel.received(recipes)
        })
    }

    private fun setupOnItemSelected() {
        viewModel.selectedRecipeMutableLiveData?.observe(this, Observer { recipe ->
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