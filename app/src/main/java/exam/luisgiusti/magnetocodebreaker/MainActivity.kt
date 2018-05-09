package exam.luisgiusti.magnetocodebreaker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import exam.luisgiusti.magnetocodebreaker.customViews.DnaTableLayout
import exam.luisgiusti.magnetocodebreaker.model.CarbonUnitType
import exam.luisgiusti.magnetocodebreaker.networkTasks.GetNetworkAsyncTask
import exam.luisgiusti.magnetocodebreaker.networkTasks.PostDnaAsyncTask
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dnaMatrix = findViewById<DnaTableLayout>(R.id.dnaTable)
        updateStats()
        initDnaMatrix(dnaMatrix)

        val testButton = findViewById<Button>(R.id.testDnaButton)

        updateTestButton()
        testButton.setOnClickListener { sendDna(dnaMatrix) }
    }

    private fun initDnaMatrix(dnaMatrix: DnaTableLayout) {
        val dnaSizeText = findViewById<TextView>(R.id.dnaSizeTextView)
        val increaseDnaSize = findViewById<ImageButton>(R.id.increaseDnaSizeButton)
        val decreaseDnaSize = findViewById<ImageButton>(R.id.reduceDnaSizeButton)
        val resetDnaMatrix = findViewById<ImageButton>(R.id.resetDnaButton)

        dnaSizeText.text = dnaMatrix.size.toString()
        increaseDnaSize.setOnClickListener {
            dnaMatrix.increaseSize()
            dnaSizeText.text = dnaMatrix.size.toString()
            updateTestButton()
        }
        decreaseDnaSize.setOnClickListener {
            dnaMatrix.decreaseSize()
            dnaSizeText.text = dnaMatrix.size.toString()
            updateTestButton()
        }
        resetDnaMatrix.setOnClickListener {
            dnaMatrix.resetTable()
            dnaSizeText.text = dnaMatrix.size.toString()
            updateTestButton()
        }

        val dnaAButton = find<Button>(R.id.dnaAButton)
        val dnaCButton = find<Button>(R.id.dnaCButton)
        val dnaGButton = find<Button>(R.id.dnaGButton)
        val dnaTButton = find<Button>(R.id.dnaTButton)
        val dnaDeleteButton = find<Button>(R.id.dnaDeleteButton)

        dnaAButton.setOnClickListener {
            dnaMatrix.addValue("A")
            updateTestButton()
        }
        dnaCButton.setOnClickListener {
            dnaMatrix.addValue("C")
            updateTestButton()
        }
        dnaGButton.setOnClickListener {
            dnaMatrix.addValue("G")
            updateTestButton()
        }
        dnaTButton.setOnClickListener {
            dnaMatrix.addValue("T")
            updateTestButton()
        }
        dnaDeleteButton.setOnClickListener {
            dnaMatrix.removeLastValue()
            updateTestButton()
        }
    }

    fun updateTestButton() {
        val dnaMatrix = findViewById<DnaTableLayout>(R.id.dnaTable)
        val dnaTestButton = findViewById<Button>(R.id.testDnaButton)
        dnaTestButton.isEnabled = !dnaMatrix.isNotFilled()
    }

    private fun sendDna(dnaMatrix: DnaTableLayout) {
        val dnaTestButton = findViewById<Button>(R.id.testDnaButton)
        dnaTestButton.isEnabled = false;
        doAsync {
            val dna = dnaMatrix.getDnaString()
            val carbonUnitType = PostDnaAsyncTask().execute(dna).get()
            val toastText = when(carbonUnitType) {
                 CarbonUnitType.MUTANT -> getText(R.string.mutants_message)
                 CarbonUnitType.HUMAN -> getText(R.string.humans_message)
                 CarbonUnitType.OTHER -> getText(R.string.strange_message)
                 else -> getText(R.string.error_message)

            }
            uiThread {
                applicationContext.toast(toastText).show()
                dnaMatrix.resetTable()
                updateTestButton()
                updateStats()
            }
        }
    }

    private fun updateStats() {
        doAsync {
            val stats = GetNetworkAsyncTask().execute().get()

            uiThread {
                val progressBar = findViewById<ProgressBar>(R.id.mutantPercentProgressBar)
                val mutantPercentText = findViewById<TextView>(R.id.mutantPercentTextView)
                val mutantCountText = findViewById<TextView>(R.id.mutantsCount)
                val humanCountText = findViewById<TextView>(R.id.humansCount)

                progressBar.max = 100

                progressBar.progress = stats.mutantPercent()
                mutantPercentText.text = "${stats.mutantPercent()}%"
                mutantCountText.text = stats.mutantCount.toString()
                humanCountText.text = stats.humanCount.toString()
            }

        }
    }

}
