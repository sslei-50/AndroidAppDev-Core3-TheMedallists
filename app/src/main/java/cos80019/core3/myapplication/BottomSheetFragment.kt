package cos80019.core3.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val TAG = "BottomSheetFragment"

class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.bottomsheet_fragment, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTV = view.findViewById<TextView>(R.id.title_BS_TV)
        val goldTV = view.findViewById<TextView>(R.id.gold_BS_TV)
        val silverTV = view.findViewById<TextView>(R.id.silver_BS_TV)
        val bronzeTV = view.findViewById<TextView>(R.id.bronze_BS_TV)
        val iocTV = view.findViewById<TextView>(R.id.ioc_BS_TV)
        val activity = activity as Context


        //getting the data directly from shared preferences
       val sharedPreferences = activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
       val titleText = sharedPreferences.getString("TITLE_KEY", null)
       val goldText = sharedPreferences.getInt("GOLD_KEY", 0)
       val silverText = sharedPreferences.getInt("SILVER_KEY", 0)
       val bronzeText = sharedPreferences.getInt("BRONZE_KEY", 0)
       val iocText = sharedPreferences.getString("IOC_KEY", null)

        titleTV.text = titleText
        goldTV.text = goldText.toString().plus(" gold medals")
        silverTV.text = silverText.toString().plus(" silver medals")
        bronzeTV.text = bronzeText.toString().plus(" bronze medals")
        iocTV.text = iocText

    }

}