package com.shpp.eorlov.rickandmorty.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentStartBinding
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import com.shpp.eorlov.rickandmorty.ui.characters.CharactersViewModel
import com.shpp.eorlov.rickandmorty.utils.Results
import javax.inject.Inject

class StartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: FragmentStartBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[StartViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }


    private fun setObservers() {
        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                viewModel.loadEventLiveData.value = Results.OK
            }
        }

        viewModel.loadEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                Results.OK -> {
                    val charactersArray = viewModel.charactersListLiveData.value?.toTypedArray()
                    if (charactersArray != null) {
                        val action =
                            StartFragmentDirections.actionStartFragmentToCharacterFragment(
                                charactersArray
                            )
                        findNavController().navigate(action)
                    }
                }
                Results.INITIALIZE_DATA_ERROR -> {
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
                }
                Results.UNEXPECTED_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.unexpected_response),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Results.NOT_SUCCESSFUL_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_successful_response),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                }
            }
        }
    }
}