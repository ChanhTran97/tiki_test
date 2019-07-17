package com.example.tiki

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    var arrayCourse: ArrayList<String> = ArrayList()
    var adapterCourse: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlJSON = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json"
        ReadJSON().execute(urlJSON)

        adapterCourse = ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayCourse)
        lvCourse.adapter = adapterCourse
    }

    inner class ReadJSON : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {

            var content: StringBuilder = StringBuilder()
            val url: URL = URL(params[0])
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

            var line: String = ""
            try {
                do {
                    line = bufferedReader.readLine()
                    if(line != null){
                        content.append(line)
                    }
                }while (line != null)

                bufferedReader.close()

            }catch ( e: Exception){
                Log.d("AAA", e.toString())
            }
            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            var ten: String
            val jsonArr  = JSONArray(result)

            val collectionType = object : TypeToken<Collection<keyword>>() {}.type

            arrayCourse = Gson().fromJson(result,collectionType)

//            for (course in 0 .. jsonArr.length()-1) {
//                var objCourse: JSONObject = jsonArr.getJSONObject(course)
//                ten = objCourse.getString("keywords")
//               arrayCourse.add(ten)
//
//            }
//            adapterCourse?.notifyDataSetChanged()
        }

    }
}
