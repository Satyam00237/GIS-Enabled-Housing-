package com.example.newgisproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(
    private var projects: List<Project>
) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projects[position])
    }

    fun updateData(newProjects: List<Project>) {
        projects = newProjects
        notifyDataSetChanged()
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(project: Project) {
            itemView.findViewById<TextView>(R.id.tvProjName).text = project.name
            itemView.findViewById<TextView>(R.id.tvProjLocation).text = "${project.district} - ${project.location}"
            itemView.findViewById<TextView>(R.id.tvProjBeneficiaries).text = "${project.beneficiaries} families"
            itemView.findViewById<TextView>(R.id.tvProjProgress).text = "${project.progress}%"
            val pb = itemView.findViewById<ProgressBar>(R.id.pbProjProgress)
            pb.progress = project.progress
            itemView.findViewById<TextView>(R.id.tvProjStatus).text = project.status
            itemView.findViewById<TextView>(R.id.tvProjType).text = project.type
        }
    }
}