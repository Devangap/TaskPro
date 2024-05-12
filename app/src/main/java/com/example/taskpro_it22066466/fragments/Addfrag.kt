package com.example.taskpro_it22066466.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.taskpro_it22066466.MainActivity
import com.example.taskpro_it22066466.R
import com.example.taskpro_it22066466.adapter.TaskAdapter
import com.example.taskpro_it22066466.databinding.FragmentAddfragBinding
import com.example.taskpro_it22066466.viewmodel.TaskViewModel
import com.example.taskpro_it22066466.model.Task




class AddNoteFragment : Fragment(R.layout.fragment_addfrag),MenuProvider {

    private var addNoteBinding: FragmentAddfragBinding?=null
    private val binding get()=addNoteBinding!!

    private lateinit var notesViewModel: TaskViewModel
    private lateinit var addNoteView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding=FragmentAddfragBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).taskViewModel
        addNoteView=view
    }

    private fun saveNote(view: View){
        val taskTitle=binding.addNoteTitle.text.toString().trim()
        val taskPriority= binding.addNotePriority.text.toString().trim()
        val taskDate= binding.addNoteDate.text.toString().trim()
        val taskDesc= binding.addNoteDesc.text.toString().trim()

        if(taskTitle.isNotEmpty()){
            val note=Task(0,taskTitle,taskPriority,taskDate,taskDesc)
            notesViewModel.addTask(note)

            Toast.makeText(addNoteView.context,"Note Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.mainFragment,false)


        }else{
            Toast.makeText(addNoteView.context,"Please enter note title",Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.addmenu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveNote(addNoteView)
                true
            }
            else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }


}