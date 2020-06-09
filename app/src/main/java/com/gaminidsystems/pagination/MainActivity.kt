package com.gaminidsystems.pagination

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private val viewItems: MutableList<Study> = mutableListOf()

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view_id.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_id.layoutManager = layoutManager
        mAdapter = RecyclerAdapter(this,viewItems)
        recycler_view_id.adapter = mAdapter
        addItemsFromJSON()
    }

    private fun addItemsFromJSON() {
        try {
            val jsonDataString: String = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val nct_id = itemObj.getString("nct_id")
                val brief_title = itemObj.getString("brief_title")
                val phase = itemObj.getString("phase")
                val overall_status = itemObj.getString("overall_status")
                val source = itemObj.getString("source")
                val study_type = itemObj.getString("study_type")
                val studies = Study(nct_id, brief_title, phase, overall_status, source, study_type)
                viewItems.add(studies)
            }
        } catch (e: JSONException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String? = null
            inputStream = resources.openRawResource(R.raw.csvjson)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }
}
