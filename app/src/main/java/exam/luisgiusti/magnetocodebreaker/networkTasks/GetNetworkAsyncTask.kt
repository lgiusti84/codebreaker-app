package exam.luisgiusti.magnetocodebreaker.networkTasks

import android.os.AsyncTask
import exam.luisgiusti.magnetocodebreaker.model.Stats

class GetNetworkAsyncTask : AsyncTask <Void, Void, Stats>() {

    override fun doInBackground(vararg p0: Void?): Stats {
        try {
            val response = khttp.get("http://meli.giusti.net.ar/stats/")
            val result = response.jsonObject
            return when {
                response.statusCode == 200 -> Stats(result["count_mutant_dna"].toString().toLong(), result["count_human_dna"].toString().toLong())
                else -> Stats()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Stats()
    }

}