package com.demo.pokemon.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.demo.pokemon.R
import com.demo.pokemon.databinding.ActivityHomeBinding
import com.demo.pokemon.datasource.DataRepository
import com.demo.pokemon.ui.adapter.PokemonAdapter
import kotlinx.coroutines.flow.StateFlow


class HomeActivity : AppCompatActivity() {

    private var mBinding: ActivityHomeBinding? = null
    private val dataRepository: DataRepository by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mBinding?.apply {
            presenter = dataRepository
            viewData = dataRepository
            val adapter = PokemonAdapter(dataRepository)
            rv.adapter = adapter
            dataRepository.apply {
                editText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        dataRepository.fetch()
                        hideSoftKeyboard(this@HomeActivity, editText)
                    }
                    false
                }
                loading.observeWhenResumed {
                    loadingView.visibility = if (it) View.VISIBLE else View.GONE
                }
                empty.observeWhenResumed {
                    rv.visibility = if (it) View.GONE else View.VISIBLE
                    tvEmpty.visibility = if (it) View.VISIBLE else View.GONE
                }
                pokemonListData.observeWhenResumed {
                    it.map { color ->
                        when (color.name) {
                            "red" -> ContextCompat.getColor(this@HomeActivity, R.color.color_red)
                            "blue" -> ContextCompat.getColor(this@HomeActivity, R.color.color_blue)
                            "green" -> ContextCompat.getColor(
                                this@HomeActivity,
                                R.color.color_green
                            )

                            "brown" -> ContextCompat.getColor(
                                this@HomeActivity,
                                R.color.color_brown
                            )

                            else -> ContextCompat.getColor(this@HomeActivity, R.color.color_red)
                        }
                    }
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun hideSoftKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun <T> StateFlow<T>.observeWhenResumed(block: (T) -> Unit) =
        lifecycleScope.launchWhenResumed { collect { block(it) } }
}

