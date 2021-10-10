package cos80019.core3.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.*

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    var medallists = mutableListOf<Medallist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?)called")
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.medalsRV)
        val bottomSheet = findViewById<ConstraintLayout>(R.id.bottomSheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


        //get data from csv file
        try {
              val myInputStream: InputStream = resources.openRawResource(R.raw.medallists)
              val reader = myInputStream.bufferedReader()
              reader.readLine()    //first line ignored as only titles of cols

              var line = reader.readLine()
              while( line != null) {
                  val cols: List<String> = line.split(",")
                  val listItem = Medallist(cols[0],
                                                  cols[1],
                                                  cols[2].toInt(),
                                                  cols[3].toInt(),
                                                  cols[4].toInt(),
                                                  cols[5].toInt())

                  medallists.add(listItem)
                  line = reader.readLine()
              }
              myInputStream.close()
        } catch (e: Exception) {
            Log.i("ERROR", "reader is null")
        }

        val adapter = MedalAdapter(medallists)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //https://www.youtube.com/watch?v=P5jDe7NogAM&t=471s
        //add onScrollListener to remove any bottomSheet
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //remove any bottomSheet when the recyclerView scrolled
                val frag = supportFragmentManager.findFragmentByTag("country_medals")
                    if(frag != null){
                            supportFragmentManager
                            .beginTransaction()
                            .remove(frag)
                            .commit()
                    }
            }
        })

    }



    //create the "Save" button on the top menu bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_to_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get saved data from shared preferences
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedTitle = sharedPreferences.getString("TITLE_KEY", null)
        val savedIOC = sharedPreferences.getString("IOC_KEY", null)

        val id = item.itemId
        if(id == R.id.action_save) {
            val i = Intent(this, SecondActivity::class.java).apply {
                putExtra("TITLE", savedTitle)
                putExtra("IOC", savedIOC)
                //Log.i("Intend", "From mainAct: ${savedTitle} ${savedIOC}")
            }
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }




}