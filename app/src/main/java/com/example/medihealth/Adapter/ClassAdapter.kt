package com.example.medihealth.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medihealth.DataClass.Classes
import com.example.medihealth.DetailActivity
import com.example.medihealth.R

class ClassAdapter(val context: Context, private val ClassList: ArrayList<Classes>) :
    RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassAdapter.ClassViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.class_list, parent, false)

        return ClassViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassAdapter.ClassViewHolder, position: Int) {
        val classes: Classes = ClassList[position]
        holder.class_name.text = classes.class_name
        holder.desc.text = classes.class_desc
        holder.desc.text = classes.class_desc!!.replace("\\n", "\n")

        holder.buttonView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("class_image", classes.class_image)
            intent.putExtra("class_name", classes.class_name)
            intent.putExtra("class_des", classes.class_desc)
            intent.putExtra("class_content", classes.class_content)
            intent.putExtra("class_audio", classes.class_audio)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return ClassList.size
    }

    public class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val class_name: TextView = itemView.findViewById(R.id.ClassName)
        val desc: TextView = itemView.findViewById(R.id.Desc)
        val buttonView: Button = itemView.findViewById(R.id.btnViewContent)

    }


}