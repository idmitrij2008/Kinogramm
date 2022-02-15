package com.example.kinogramm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.R
import com.example.kinogramm.databinding.ActivityMainBinding
import com.example.kinogramm.util.Constants
import com.example.kinogramm.view.catalog.FilmsCatalogFragmentDirections
import com.example.kinogramm.view.exit.ExitViewModel
import javax.inject.Inject

private const val TAG = "MainActivity"

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private var destinationListener: NavController.OnDestinationChangedListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val exitViewModel: ExitViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExitViewModel::class.java]
    }

    private val component by lazy {
        (application as KinogrammApp).component
            .activityComponentFactory()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        binding.bottomNavView?.setupWithNavController(navHostFragment.navController)

        observeNavDestinations()

        exitViewModel.exit.observe(this) {
            finish()
        }

        checkIfShouldOpenFilmDetails()
    }

    private fun checkIfShouldOpenFilmDetails() {
        val stringId = intent.getStringExtra(Constants.EXTRA_FILM_REMOTE_ID) ?: ""
        if (stringId.isNotBlank()) {
            stringId.toIntOrNull()?.let { id ->
                navHostFragment.navController.navigate(
                    FilmsCatalogFragmentDirections.actionCatalogFragmentToFilmDetailsFragment(
                        id
                    )
                )
            } ?: run {
                Log.w(TAG, "Can't parse stringId.")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        destinationListener?.let { listener ->
            navHostFragment.navController.removeOnDestinationChangedListener(
                listener
            )
        }
    }

    private fun observeNavDestinations() {
        destinationListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.film_details_fragment -> {
                        binding.bottomNavView?.visibility = View.GONE
                        supportActionBar?.hide()
                    }
                    else -> {
                        binding.bottomNavView?.visibility = View.VISIBLE
                        supportActionBar?.show()
                    }
                }
            }.apply { navHostFragment.navController.addOnDestinationChangedListener(this) }
    }

    override fun onBackPressed() {
        when (navHostFragment.navController.currentDestination?.id) {
            R.id.catalog_fragment, R.id.favourite_films_fragment -> showExitDialog()
            else -> navHostFragment.navController.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_share -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Let's use Kinogramm together!")
                }.also { intent -> startActivity(intent) }
                true
            }
            R.id.action_show_favourites -> {
                navHostFragment.navController.navigate(R.id.favourite_films_fragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showExitDialog() {
        navHostFragment.navController.navigate(
            R.id.exit_dialog_fragment
        )
    }
}