package com.gtappdevelopers.covidcount

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var worldCasesTV: TextView
    lateinit var worldDeathsTV: TextView
    lateinit var worldRecoveredCasesTV: TextView
    lateinit var indianCasesTV: TextView
    lateinit var indianDeathsTV: TextView
    lateinit var indianRecoveredTV: TextView
    lateinit var stateRV: RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<StateModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worldCasesTV = findViewById(R.id.idTVWorldCases)
        worldDeathsTV = findViewById(R.id.isTVWorldDeaths)
        worldRecoveredCasesTV = findViewById(R.id.idTVWorldRecovered)
        indianCasesTV = findViewById(R.id.idTVIndiaCases)
        indianDeathsTV = findViewById(R.id.isTVIndiaDeaths)
        indianRecoveredTV = findViewById(R.id.idTVIndiaRecovered)
        stateRV = findViewById(R.id.idRVStates)
        stateList = ArrayList<StateModal>()
        getWorldCasesInfo()
        getStateData()

    }

    private fun getStateData() {
        val url =
            "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    val dataObj = response.getJSONObject("data")
                    val summaryObj = dataObj.getJSONObject("summary")
                    val cases: Int = summaryObj.getInt("total")
                    val deaths: Int = summaryObj.getInt("deaths")
                    val recovered = summaryObj.getInt("discharged")

                    indianCasesTV.text = cases.toString()
                    indianDeathsTV.text = deaths.toString()
                    indianRecoveredTV.text = recovered.toString()

                    val regionalArray = dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()) {
                        val regionObj = regionalArray.getJSONObject(i)
                        val stateName: String = regionObj.getString("loc")
                        val stateCases: Int = regionObj.getInt("totalConfirmed")
                        val stateRecovered: Int = regionObj.getInt("discharged")
                        val stateDeaths: Int = regionObj.getInt("deaths")

                        val stateModal =
                            StateModal(stateName, stateRecovered, stateDeaths, stateCases)
                        stateList = stateList + stateModal
                    }
                    stateRVAdapter = StateRVAdapter(stateList)
                    stateRV.layoutManager = LinearLayoutManager(this)
                    stateRV.adapter = stateRVAdapter


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, { error ->
                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

    private fun getWorldCasesInfo() {
        val url =
            "https://corona.lmao.ninja/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    Log.e("TAG", "SUCCESS RESPONSE IS $response")
                    val worldCases: Int = response.getInt("cases")
                    val worldDeaths: Int = response.getInt("deaths")
                    val worldRecovered: Int = response.getInt("recovered")
                    worldCasesTV.text = worldCases.toString()
                    worldDeathsTV.text = worldDeaths.toString()
                    worldRecoveredCasesTV.text = worldRecovered.toString()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                Toast.makeText(this@MainActivity, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            })
        queue.add(request)
    }
}