package com.example.taskpro_it22066466.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskpro_it22066466.MainActivity
import com.example.taskpro_it22066466.R
import com.example.taskpro_it22066466.adapter.TaskAdapter
import com.example.taskpro_it22066466.databinding.FragmentMainfragementBinding
import com.example.taskpro_it22066466.viewmodel.TaskViewModel
import com.example.taskpro_it22066466.model.Task

class MainFragment : Fragment(R.layout.fragment_mainfragement), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentMainfragementBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentMainfragementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel
        setupHomeRecyclerView()
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }
    }

    private fun updateUI(notes: List<Task>?) {
        if (notes != null) {
            if (notes.isNotEmpty()) {
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView() {
        taskAdapter = TaskAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = taskAdapter
        }
        taskViewModel.getAllTasks().observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
            updateUI(tasks)
        }
    }

    private fun searchTask(query: String?) {
        query?.let { searchQuery ->
            val searchQueryFormatted = "%$searchQuery"
            taskViewModel.searchTask(searchQueryFormatted).observe(viewLifecycleOwner) { tasks ->
                taskAdapter.submitList(tasks)
                updateUI(tasks)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchTask(newText)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.mainmenu, menu)

        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}
