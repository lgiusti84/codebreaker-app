package exam.luisgiusti.magnetocodebreaker.networkTasks

import android.os.AsyncTask
import exam.luisgiusti.magnetocodebreaker.model.CarbonUnitType

class PostDnaAsyncTask : AsyncTask<Array<String>, Void, CarbonUnitType>() {

    override fun doInBackground(vararg dna: Array<String>): CarbonUnitType {
        val response = khttp.post(
                url = "http://meli.giusti.net.ar/mutant/",
                json = mapOf("dna" to dna[0])
        )
        return when {
            response.statusCode == 200 -> CarbonUnitType.MUTANT // 200 = OK
            response.statusCode == 403 -> CarbonUnitType.HUMAN // 403 = Forbidden
            response.statusCode == 400 -> CarbonUnitType.OTHER // 400 = Bad Request
            else -> CarbonUnitType.ERROR
        }
    }
}