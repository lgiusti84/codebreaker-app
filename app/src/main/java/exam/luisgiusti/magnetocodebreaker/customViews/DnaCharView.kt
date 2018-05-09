package exam.luisgiusti.magnetocodebreaker.customViews

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import android.widget.LinearLayout.LayoutParams as LinearLayoutParams

class DnaCharView(context: Context?) : TextView(context) {
    var empty = true

    init {
        val params = LinearLayoutParams(LinearLayoutParams.WRAP_CONTENT, LinearLayoutParams.WRAP_CONTENT)
        params.setMargins(10, 10, 10, 10)
        layoutParams = params
        typeface = Typeface.MONOSPACE
        setPadding(10, 10, 10, 10)

        deleteValue()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        empty = false
    }

    fun deleteValue() {
        text = "_"
        empty = true
    }
}