package com.example.taskpro_it22066466.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.provider.ContactsContract
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
import androidx.navigation.fragment.navArgs
import com.example.taskpro_it22066466.MainActivity
import com.example.taskpro_it22066466.R
import com.example.taskpro_it22066466.adapter.TaskAdapter
import com.example.taskpro_it22066466.databinding.FragmentAddfragBinding
import com.example.taskpro_it22066466.databinding.FragmentEditfragBinding
import com.example.taskpro_it22066466.viewmodel.TaskViewModel
import com.example.taskpro_it22066466.model.Task


class EditNoteFragment : Fragment(R.layout.fragment_editfrag),MenuProvider {
    private var editNoteBinding: FragmentEditfragBinding?=null
    private val binding get()=editNoteBinding!!

    private lateinit var notesViewModel: TaskViewModel
    private lateinit var currentNote:Task

    private val args:EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding= FragmentEditfragBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).taskViewModel
        currentNote=args.note!!

        binding.editNoteTitle.setText(currentNote.taskTitle)
        binding.editNotePriority.setText(currentNote.taskPriority)
        binding.editNoteDate.setText(currentNote.taskDate)
        binding.editNoteDesc.setText(currentNote.taskDesc)

        binding.editNoteFab.setOnClickListener{

            val taskTitle=binding.editNoteTitle.text.toString().trim()
            val taskPriority= binding.editNotePriority.text.toString().trim()
            val taskDate= binding.editNoteDate.text.toString().trim()
            val taskDesc= binding.editNoteTitle.text.toString().trim()

            if (taskTitle.isNotEmpty()){
                val note=Task(currentNote.id,taskTitle,taskPriority,taskDate,taskDesc)
                notesViewModel.updateTask(note)
                view.findNavController().popBackStack(R.id.mainFragment,false)

            }else{
                Toast.makeText(context,"Please enter note title", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteTask(currentNote)
                Toast.makeText(context,"Note deleted successfully", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.mainFragment,false)

            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.editmenu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            }else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }


}