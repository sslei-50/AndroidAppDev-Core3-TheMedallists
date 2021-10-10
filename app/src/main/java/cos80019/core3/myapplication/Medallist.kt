package cos80019.core3.myapplication

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Medallist(
    var name: String = "",
    var ioc: String = "",
    var timesCompeted: Int = 0,
    var gold: Int = 0,
    var silver: Int = 0,
    var bronze: Int = 0
) {
    fun getTotal(): Int{
        return this.gold + this.silver + this.bronze
    }
}