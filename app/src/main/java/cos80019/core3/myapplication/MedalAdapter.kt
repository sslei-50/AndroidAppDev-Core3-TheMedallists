package cos80019.core3.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.medal_layout.view.*


//https://www.youtube.com/watch?v=HtwDXRWjMcU&t=1295s

class MedalAdapter(var medallists: List<Medallist>
): RecyclerView.Adapter<MedalAdapter.MedalViewHolder>() {

    class MedalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val medallistTitleTV: TextView = itemView.findViewById(R.id.medallistTitle_TV)
        val medallistIOCTV: TextView = itemView.findViewById(R.id.medallistIOC_TV)
        val medalCountTV: TextView = itemView.findViewById(R.id.medalCount_TV)
        val iconIV: ImageView = itemView.findViewById(R.id.icon_IV)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.medal_layout, parent, false)
        return MedalViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MedalViewHolder, position: Int) {

        val medallistData: Medallist = medallists[position]
        val medalCount = medallists[position].getTotal()
        holder.medallistTitleTV.text = medallists[position].name
        holder.medallistIOCTV.text = medallists[position].ioc
        holder.medalCountTV.text = medalCount.toString()

        //highlight the top ten medallists with diff background color and icon color
        val topTen = getTopTen()
        if(medallists[position].ioc in topTen) {
            holder.itemView.medalListItem_LO.setBackgroundResource(R.color.topTenItem)
            holder.iconIV.setImageResource(R.drawable.ic_medaltopten)
        } else {
            holder.itemView.medalListItem_LO.setBackgroundResource(R.color.normalItem)
            holder.iconIV.setImageResource(R.drawable.ic_medal)
        }


        //save the data of the curr item to shared preferences which is accessible across activities
        fun savedData() {
            val activity = holder.itemView.context as AppCompatActivity
            val sharedPreferences = activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply{
                putString("TITLE_KEY", medallistData.name)
                putInt("GOLD_KEY", medallistData.gold)
                putInt("SILVER_KEY", medallistData.silver)
                putInt("BRONZE_KEY", medallistData.bronze)
                putString("IOC_KEY", medallistData.ioc)
            }.apply()
        }

        //set an onTouchListener on medallist's Title
        holder.itemView.medallistTitle_TV.setOnTouchListener { v, event ->
            val activity = holder.itemView.context as AppCompatActivity
            val bottomFragment = BottomSheetFragment.newInstance()
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {     //onClick DOWN a bottomSheet will show with medallist's data
                    savedData()
                    activity.supportFragmentManager
                        .beginTransaction()
                        .add(R.id.root_layout, bottomFragment, "country_medals")
                        .commit()
                }

                MotionEvent.ACTION_UP -> {      //onClick RELEASE the bottomSheet will be removed
                   val frag = activity.supportFragmentManager.findFragmentByTag("country_medals")
                   if(frag != null){
                       activity.supportFragmentManager
                           .beginTransaction()
                           .remove(frag)
                           .commit()
                   }
                }
                else -> {
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return medallists.size
    }


    //get the top ten countries with the highest numbers of medals
    fun getTopTen() : MutableList<String>
    {
        val topTenMedallists = mutableListOf<String>()
        val myList = mutableListOf<MedallistTotalCount>()

        //get the total count of medal for each country on the list
        val c = medallists.size - 1
        for (i in 0..c) {
            val ioc = medallists[i].ioc
            val totalMedalCount = medallists[i].getTotal()
            myList.add(MedallistTotalCount(ioc, totalMedalCount))
        }

        //sort the list by total medal count on descending order
        myList.sortByDescending { it.totalMedalCount }

        //get the top ten
        for (i in 0..9) {
            topTenMedallists.add(myList[i].ioc)
        }

        //return a list of IOC codes for the top ten countries
        return topTenMedallists
    }

}