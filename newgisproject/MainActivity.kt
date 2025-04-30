

package com.example.newgisproject

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ProjectAdapter
    private lateinit var allProjects: List<Project>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        allProjects = listOf(
            Project("North Region Housing", "Type A Housing", "North District", "Block B, Sector 4", 45, 100, "Completed"),
            Project("East Village Homes", "Type B Housing", "East District", "Village Junction", 32, 68, "In Progress"),
            Project("South Community", "Type B Housing", "South District", "Block C, Area 7", 28, 0, "Delayed"),
            Project("Western Residences", "Type C Housing", "West District", "Hillside Area", 18, 30, "In Progress"),
            Project("Central Village", "Type A Housing", "Central District", "Main Road, Block 2", 36, 55, "Completed"),

            Project("Harbor Heights", "Type C Housing", "Coast District", "Harbor Road", 20, 95, "Completed"),
            Project("Mountain View Homes", "Type A Housing", "Hill District", "Peak Lane", 27, 80, "In Progress"),
            Project("Greenfields", "Type B Housing", "Rural District", "Meadow Block", 24, 10, "Delayed"),
            Project("Sunset Residency", "Type C Housing", "West District", "Sunset Strip", 15, 60, "In Progress"),
            Project("Riverdale Estates", "Type B Housing", "East District", "River Lane", 17, 20, "Delayed")
        )
        val rv = findViewById<RecyclerView>(R.id.rvProjects)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ProjectAdapter(allProjects)
        rv.adapter = adapter

        // Setup Spinners
        val districts = arrayOf("All Districts") + allProjects.map { it.district }.distinct().sorted()
        val types = arrayOf("All Types") + allProjects.map { it.type }.distinct().sorted()
        val statuses = arrayOf("All Statuses") + allProjects.map { it.status }.distinct().sorted()

        val spinnerDistrict = findViewById<Spinner>(R.id.spinnerDistrict)
        val spinnerType = findViewById<Spinner>(R.id.spinnerType)
        val spinnerStatus = findViewById<Spinner>(R.id.spinnerStatus)

        spinnerDistrict.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districts)
        spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
        spinnerStatus.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statuses)

        val btnFilter = findViewById<Button>(R.id.btnApplyFilter)
        btnFilter.setOnClickListener {
            val selectedDistrict = spinnerDistrict.selectedItem.toString()
            val selectedType = spinnerType.selectedItem.toString()
            val selectedStatus = spinnerStatus.selectedItem.toString()
            var filtered = allProjects
            if (selectedDistrict != "All Districts") {
                filtered = filtered.filter { it.district == selectedDistrict }
            }
            if (selectedType != "All Types") {
                filtered = filtered.filter { it.type == selectedType }
            }
            if (selectedStatus != "All Statuses") {
                filtered = filtered.filter { it.status == selectedStatus }
            }
            adapter.updateData(filtered)
            updateStatistics(filtered)
        }

        updateStatistics(allProjects)
    }

    private fun updateStatistics(projects: List<Project>) {
        val completion = if (projects.isNotEmpty()) projects.map { it.progress }.average().toInt() else 0
        findViewById<ProgressBar>(R.id.pbCompletion).progress = completion
        findViewById<TextView>(R.id.tvCompletionPercent).text = "$completion%"
        // Demo: budget is always 0, timeline is random/hardcoded for now
        findViewById<ProgressBar>(R.id.pbBudget).progress = 0
        findViewById<TextView>(R.id.tvBudgetPercent).text = "0%"
        val timeline = if (projects.isNotEmpty()) (completion + 26) % 100 else 0
        findViewById<ProgressBar>(R.id.pbTimeline).progress = timeline
        findViewById<TextView>(R.id.tvTimelinePercent).text = "$timeline%"

        findViewById<TextView>(R.id.tvTotalProjects).text = projects.size.toString()
        findViewById<TextView>(R.id.tvTotalBeneficiaries).text = projects.sumOf { it.beneficiaries }.toString()
        findViewById<TextView>(R.id.tvTypeACount).text = projects.count { it.type == "Type A Housing" }.toString()
        findViewById<TextView>(R.id.tvTypeBCount).text = projects.count { it.type == "Type B Housing" }.toString()
        findViewById<TextView>(R.id.tvTypeCCount).text = projects.count { it.type == "Type C Housing" }.toString()
    }
}