package com.shpp.eorlov.rickandmorty.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.CharactersGridAdapter
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners.CharacterClickListener
import com.shpp.eorlov.rickandmorty.utils.Results
import javax.inject.Inject


class CharacterFragment : BaseFragment(), CharacterClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val charactersGridAdapter: CharactersGridAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersGridAdapter(characterClickListener = this)
    }

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
        setObservers()
        setListeners()
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }

    override fun goToDetailView(characterModel: CharacterModel) {
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToDetailFragment(characterModel)
        findNavController().navigate(action)
    }

    private fun setListeners() {
        binding.buttonSortCharacters.setOnClickListener {
            charactersGridAdapter.submitList(getSortedCharacters(charactersGridAdapter.currentList))
        }
    }

    private fun getSortedCharacters(currentList: List<CharacterModel>): MutableList<CharacterModel> {
        val listOfNames: List<String> = currentList.map {
            it.name
        }

        val sortedListOfNames = listOfNames.sorted()
        val sortedListOfCharacters = mutableListOf<CharacterModel>()
        for (value in sortedListOfNames) {
            sortedListOfCharacters.add(currentList.first {
                it.name == value
            })
        }

        return sortedListOfCharacters
    }


    private fun initRecycler() {
        binding.recyclerViewMyContacts.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )

            adapter = charactersGridAdapter
        }
    }

    private fun setObservers() {
        viewModel.charactersFromCurrentPageLiveData.observe(viewLifecycleOwner) {
            if (it.info?.next != null) {
                viewModel.getAllCharacters()
            }
        }

        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            charactersGridAdapter.submitList(it.toMutableList())
        }

        viewModel.loadEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                Results.OK -> {
                    binding.buttonSortCharacters.isEnabled = true
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.LOADING -> {
                    binding.buttonSortCharacters.isEnabled = false
                    binding.contentLoadingProgressBar.isVisible = true
                }
                Results.INITIALIZE_DATA_ERROR -> {
                    binding.buttonSortCharacters.isEnabled = true
                    binding.contentLoadingProgressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.initialize_data_error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Results.INTERNET_ERROR -> {
                    binding.buttonSortCharacters.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_error),
                        Toast.LENGTH_LONG
                    ).show()

                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.UNEXPECTED_RESPONSE -> {
                    binding.buttonSortCharacters.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.unexpected_response),
                        Toast.LENGTH_LONG
                    ).show()

                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.NOT_SUCCESSFUL_RESPONSE -> {
                    binding.buttonSortCharacters.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_successful_response),
                        Toast.LENGTH_LONG
                    ).show()

                    binding.contentLoadingProgressBar.isVisible = false
                }
                else -> {
                }
            }
        }
    }
}