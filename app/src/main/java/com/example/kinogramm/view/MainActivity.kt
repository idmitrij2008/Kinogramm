package com.example.kinogramm.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kinogramm.R
import com.example.kinogramm.databinding.ActivityMainBinding
import com.example.kinogramm.view.catalog.FilmsCatalogFragment
import com.example.kinogramm.view.details.FilmDetailsFragment
import com.example.kinogramm.view.favourites.FavouriteFilmsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val lastBackStackEntry: FragmentManager.BackStackEntry
        get() = supportFragmentManager.run { getBackStackEntryAt(backStackEntryCount - 1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        savedInstanceState?.let {
            hideBottomNavIfNeeded()
        } ?: run {
            showFragment(
                FilmsCatalogFragment.newInstance(),
                FilmsCatalogFragment.NAME
            )
        }

        setBottomNav()

        supportFragmentManager.addOnBackStackChangedListener {
            when (lastBackStackEntry.name) {
                FilmDetailsFragment.NAME -> {
                    binding.bottomNavView?.visibility = View.GONE
                    supportActionBar?.hide()
                }
                else -> {
                    binding.bottomNavView?.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }

    private fun hideBottomNavIfNeeded() {
        if (lastBackStackEntry.name == FilmDetailsFragment.NAME) {
            binding.bottomNavView?.visibility = View.GONE
        }
    }

    private fun setBottomNav() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            binding.bottomNavView?.selectCorrectMenuItem()
        }

        binding.bottomNavView?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.catalog_fragment -> showFragment(
                    fragment = FilmsCatalogFragment.newInstance(),
                    backStackName = FilmsCatalogFragment.NAME
                )
                R.id.favourite_films_fragment -> {
                    showFragment(
                        fragment = FavouriteFilmsFragment.newInstance(),
                        backStackName = FavouriteFilmsFragment.NAME
                    )
                }
            }
            true
        }
    }

    private fun BottomNavigationView.selectCorrectMenuItem() {
        when (lastBackStackEntry.name) {
            FilmsCatalogFragment.NAME -> {
                selectedItemId = R.id.catalog_fragment
            }
            FavouriteFilmsFragment.NAME -> {
                selectedItemId = R.id.favourite_films_fragment
            }
        }
    }

    private fun showFragment(fragment: Fragment, backStackName: String) {
        if (supportFragmentManager.backStackEntryCount == 0 || lastBackStackEntry.name != backStackName) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.main_fragment_container, fragment)
                addToBackStack(backStackName)
                commit()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0 && lastBackStackEntry.name == FilmDetailsFragment.NAME) {
            supportFragmentManager.popBackStack()
        } else ExitDialogFragment().show(supportFragmentManager, ExitDialogFragment.NAME)
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
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_container,
                        FavouriteFilmsFragment.newInstance()
                    )
                    .addToBackStack(FavouriteFilmsFragment.NAME)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}