package exam.luisgiusti.magnetocodebreaker.customViews

import android.content.Context
import android.widget.LinearLayout

class DnaTableRow(context: Context?, var size: Int = 4) : LinearLayout(context) {
    private val chars: MutableList<DnaCharView>
    private val minSize:Int = 4
    private val maxSize:Int = 10


    init {
        chars = ArrayList()
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        repeat(size) {
            val newDnaCharView = DnaCharView(context)
            chars.add(newDnaCharView)
            addView(newDnaCharView)
        }
    }

    fun increaseSize() {
        if(size < maxSize) {
            size++
            val newDnaCharView = DnaCharView(context)
            chars.add(newDnaCharView)
            addView(newDnaCharView)
        }
    }

    fun decreaseSize() {
        if(size > minSize) {
            size--
            val oldDnaCharView = chars.removeAt(size)
            removeView(oldDnaCharView)
        }
    }

    fun addValue(txt: String) {
        chars.first{ it.empty }.text = txt
    }

    fun removeLastValue() {
        chars.last{ dcv -> !dcv.empty }.deleteValue()
    }

    fun isNotFilled(): Boolean {
        return chars.any{ it.empty }
    }

    fun isEmpty(): Boolean {
        return chars.all{ it.empty }
    }

    fun getDnaString():String {
        var result = ""
        chars.stream()
                .forEachOrdered({ dnc -> result += dnc.text })
        return result
    }

}