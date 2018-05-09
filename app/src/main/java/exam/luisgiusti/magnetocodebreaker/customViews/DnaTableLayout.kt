package exam.luisgiusti.magnetocodebreaker.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class DnaTableLayout(context: Context?, attr: AttributeSet?) : LinearLayout(context, attr) {
    private val rows: MutableList<DnaTableRow> = ArrayList()
    private val minSize:Int = 4
    private val maxSize:Int = 10
    var size:Int = minSize

    init {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL
        layoutParams = params
        resetTable()
    }

    fun resetTable() {
        while(rows.isNotEmpty()) {
            removeView(rows.removeAt(0))
        }

        size = minSize
        repeat(size) {
            val newTableRow = DnaTableRow(context, size)
            rows.add(newTableRow)
            addView(newTableRow)
        }
    }

    fun increaseSize() {
        if(size < maxSize) {
            size++
            val newTableRow = DnaTableRow(context, size)
            for (row in rows) {
                while (row.size < size) {
                    row.increaseSize()
                }
            }
            rows.add(newTableRow)
            addView(newTableRow)
        }
    }

    fun decreaseSize() {
        if(size > minSize) {
            size--
            val oldTableRow = rows.removeAt(size)
            removeView(oldTableRow)
            for (row in rows) {
                while (row.size > size) {
                    row.decreaseSize()
                }
            }
        }
    }

    fun addValue(value: String) {
        if(isNotFilled()) {
            rows.first { it.isNotFilled() }.addValue(value)
        }
    }

    fun removeLastValue() {
        if(!isEmpty()) {
            rows.last { !it.isEmpty() }.removeLastValue()
        }
    }

    fun isNotFilled(): Boolean {
        return rows.any{ it.isNotFilled() }
    }

    fun isEmpty(): Boolean {
        return rows.all{ it.isEmpty() }
    }

    fun getDnaString():Array<String> {
        val result = arrayOfNulls<String>(size)
        rows.forEachIndexed { index, row ->  result[index] = row.getDnaString() }
        return result.requireNoNulls()
    }

}