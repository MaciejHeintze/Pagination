package com.gaminidsystems.pagination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val viewItems: MutableList<Study> = mutableListOf()

    private lateinit var studyViewModel: StudyViewModel
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress_bar_id.visibility = View.VISIBLE
        recycler_view_id.setHasFixedSize(true)
        recycler_view_id.layoutManager = LinearLayoutManager(applicationContext)
        val mAdapter = RecyclerAdapter(this,viewItems)
        recycler_view_id.adapter = mAdapter

        studyViewModel = ViewModelProviders.of(this).get(StudyViewModel::class.java)

        studyViewModel.studies.observe(this, Observer { study ->
            study?.let {
                mAdapter.setStudies(study)
                progress_bar_id.visibility = View.INVISIBLE
            }
        })

//        recycler_view_id.addOnScrollListener(object : RecyclerViewPaginator(recycler_view_id) {
//            override val isLastPage: Boolean
//                get() = viewModel.isLastPage()
//
//            override fun loadMore(start: Long, count: Long) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun loadMore(page: Long) {
//                viewModel.loadData(page)
//            }
//        })


        searchDB(mAdapter)
        addItemsFromJSON()
    }
    private fun searchDB(mAdapter: RecyclerAdapter){
        search_button_id.setOnClickListener {
            var searchtext = study_edit_text.text.toString()

            studyViewModel.getStudyById(searchtext).observe(this , Observer { study ->
                study?.let {
                    mAdapter.getStudiesById(study)
                    mAdapter.notifyDataSetChanged()
                }
            })
        }
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

                var study = Study(nct_id, brief_title, phase, overall_status, source, study_type)
                studyViewModel.insert(study)
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
