package com.shpp.eorlov.rickandmorty.ui.characters

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import javax.inject.Inject

import android.util.TypedValue
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.model.CharactersList
import com.shpp.eorlov.rickandmorty.utils.Results


class CharacterFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[CharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        gridLayout = GridLayout(context)
        binding.constraintLayoutCharacters.addView(gridLayout)
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }

    private fun setObservers() {
        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            addItem(it)
            if(it.info?.next != null) {
                viewModel.getAllCharacters()
            }
        }

        viewModel.loadEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                Results.OK -> {
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.LOADING -> {
                    lockUI()
                    binding.contentLoadingProgressBar.isVisible = true
                }
                Results.INITIALIZE_DATA_ERROR -> {
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.initialize_data_error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Results.INTERNET_ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_error),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.UNEXPECTED_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.unexpected_response),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.NOT_SUCCESSFUL_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_successful_response),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                else -> {
                }
            }
        }
    }
    private lateinit var gridLayout : GridLayout
    private fun addItem(charactersList: CharactersList) {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.rick_and_morty)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        val width = convertPxToDp(150f).toInt()
        val height = convertPxToDp(150f).toInt()
        val parms = LinearLayout.LayoutParams(width, height)
        imageView.layoutParams = parms
        gridLayout.addView(imageView)
    }

    private fun setListeners() {
    }

    private fun convertPxToDp(dip: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }
}