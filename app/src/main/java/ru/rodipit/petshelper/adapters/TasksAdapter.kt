package ru.rodipit.petshelper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.CommonDiffUtilCallbackImpl
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.data.dao.TaskDao
import ru.rodipit.petshelper.data.db.TasksDb
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.databinding.TaskItemBinding
import ru.rodipit.petshelper.repository.TaskRepository
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.text.StringBuilder

class TasksAdapter(private val context: Context): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    var data = listOf<Task>()
        set(value){
            val callback = CommonDiffUtilCallbackImpl(
                oldData = field,
                newData = value,
                { oldItem: Task, newItem: Task -> oldItem.id == newItem.id },
                { oldItem: Task, newItem: Task -> oldItem == newItem }
            )
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)

        }

    private var taskDao: TaskDao = TasksDb.getInstance(context.applicationContext).getDao()
    private val taskRepository: TaskRepository = TaskRepository(taskDao)

    inner class TaskViewHolder(private val binding: TaskItemBinding) : ViewHolder(binding.root){
        fun onBind(task: Task){
            task.resetState(System.currentTimeMillis())
            CoroutineScope(Dispatchers.IO).launch{
                taskRepository.updateTask(task)
            }
            with(binding){
                taskTittle.text = task.title
                descriptionTv.text = task.description
                val typeIvId = when(task.type){
                    Task.EATING -> R.drawable.baseline_lunch_dining_24
                    Task.HEALTH -> R.drawable.baseline_healing_24
                    Task.OTHER -> R.drawable.baseline_pets_24
                    else -> R.drawable.baseline_pets_24
                }

                Picasso.get().load(typeIvId).fit().into(taskTypeIv)

                val time = GregorianCalendar.getInstance().apply{ timeInMillis = task.time}

                val showTime = StringBuilder().apply {
                    append(time.get(Calendar.HOUR_OF_DAY))
                    append(":")
                    append(time.get(Calendar.MINUTE))

                }
                timeTv.text = showTime.toString()
                doneChb.isChecked = task.state

                doneChb.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked) task.doTask() else task.undoTask()
                    CoroutineScope(Dispatchers.IO).launch{
                        taskRepository.updateTask(task)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val taskItemBinding = TaskItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(taskItemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}