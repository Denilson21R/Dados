package denilson.learn.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import denilson.learn.dados.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var activitySettingsBinding : ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        activitySettingsBinding.salvarBt.setOnClickListener {

            val novaConfig : Configuracao = Configuracao(
                (activitySettingsBinding.numeroDadosSp.selectedView as TextView).text.toString().toInt(),
                activitySettingsBinding.numeroFacesEt.text.toString().toInt()
            )

            val retornIntent : Intent = Intent();
            retornIntent.putExtra(Intent.EXTRA_USER, novaConfig)
            setResult(RESULT_OK, retornIntent)
            finish()
        }
    }
}