package com.shpp.eorlov.rickandmorty.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.CharactersGridAdapter
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners.CharacterClickListener
import com.shpp.eorlov.rickandmorty.ui.details.DetailFragmentArgs
import javax.inject.Inject


class CharacterFragment : BaseFragment(), CharacterClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val charactersGridAdapter: CharactersGridAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersGridAdapter(characterClickListener = this)
    }
    private val args: CharacterFragmentArgs by navArgs()

    private lateinit var viewModel: CharactersViewModel
    private lateinit var binding: FragmentCharacterBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]
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
        viewModel.setCharactersList(args.charactersArray.toMutableList())
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
            val sortedCharacters = getSortedCharacters(charactersGridAdapter.currentList)
            viewModel.charactersListLiveData.value = sortedCharacters

            binding.buttonSortCharacters.run{
                setText(R.string.sorted)
                isEnabled = false
            }
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
        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            charactersGridAdapter.submitList(it.toMutableList())
        }
    }
}