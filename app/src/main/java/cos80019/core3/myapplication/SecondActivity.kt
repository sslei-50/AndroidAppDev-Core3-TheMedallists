package cos80019.core3.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textview = findViewById<TextView>(R.id.lastMedallist_tv)

        //received the details of the last clicked item from the Intent sent by MainActivity
        val titleText = intent.getStringExtra("TITLE").toString()
        val iocText = intent.getStringExtra("IOC").toString()

        //alternative: getting the data directly from shared preferences
//       val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//       val titleText = sharedPreferences.getString("TITLE_KEY", null)
//       val iocText = sharedPreferences.getString("IOC_KEY", null)

        val str = "The last country clicked was ${titleText} (${iocText})"
        textview.text = str

    }
}