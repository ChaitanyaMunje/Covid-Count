package com.gtappdevelopers.covidcount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateRVAdapter (private val stateList: List<StateModal>) :
    RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>() {

    class StateRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateNameTV: TextView = itemView.findViewById(R.id.idTVStateName)
        val stateCasesTV: TextView = itemView.findViewById(R.id.idTVCases)
        val stateRecoveredTV: TextView = itemView.findViewById(R.id.idTVRecovered)
        val stateDeathsTV: TextView = itemView.findViewById(R.id.idTVDeaths)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.state_rv_item,
            parent, false
        )
    return StateRVViewHolder(itemView)
    }

    override fun getItemCount(): Int {
    return  stateList.size
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
    val stateData = stateList[position]
        holder.stateNameTV.text = stateData.state
        holder.stateCasesTV.text = stateData.cases.toString()
        holder.stateRecoveredTV.text = stateData.recovered.toString()
        holder.stateDeathsTV.text = stateData.deaths.toString()
    }
}