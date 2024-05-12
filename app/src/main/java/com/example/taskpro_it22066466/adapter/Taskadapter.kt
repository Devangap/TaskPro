package com.example.taskpro_it22066466.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskpro_it22066466.model.Task
import com.example.taskpro_it22066466.databinding.TaskBinding
import com.example.taskpro_it22066466.fragments.MainFragmentDirections

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val itemBinding: TaskBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskDesc == newItem.taskDesc &&
                    oldItem.taskTitle == newItem.taskTitle &&
                    oldItem.taskPriority == newItem.taskPriority &&
                    oldItem.taskDate == newItem.taskDate

        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    fun submitList(list: List<Task>) {
        differ.submitList(list)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.taskTitle.text = currentNote.taskTitle
        holder.itemBinding.taskDesc.text = currentNote.taskDesc
        holder.itemBinding.taskPriority.text = currentNote.taskPriority
        holder.itemBinding.taskDate.text = currentNote.taskDate

        holder.itemView.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }
}
