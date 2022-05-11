package denilson.learn.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import denilson.learn.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private var configuracao : Configuracao = Configuracao()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random

    private lateinit var settingsActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadoBt.setOnClickListener {
            val resultado : Int = geradorRandomico.nextInt(1..configuracao.numeroFaces)
            var resultado2 : Int? = null;
            if (configuracao.numeroDados == 2){
                resultado2 = geradorRandomico.nextInt(1..configuracao.numeroFaces)
            }

            var textoDados = "A(s) face(s) sorteada(s) foi(ram) $resultado"

            if(resultado2 !== null){
                textoDados += " e $resultado2"
            }

            //mostra o resultado no textview
            activityMainBinding.resultadoTv.text = textoDados

            if(configuracao.numeroFaces <= 6) {
                exibeImagemResultado(resultado, 1)

                if (resultado2 != null){
                    exibeImagemResultado(resultado2, 2)
                }
            }else{
                //esconde os dois dados
                activityMainBinding.resultadoIv.visibility = View.GONE
                activityMainBinding.resultado2Iv.visibility = View.GONE
            }
        }

        settingsActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                //recebe nova configuracao e, se for valida, atualiza a atual
                recebeNovaConfig(result)
            }
        }
    }

    private fun recebeNovaConfig(result: ActivityResult) {
        if (result.data != null) {
            val novaConfiguracao: Configuracao? =
                result.data?.getParcelableExtra(Intent.EXTRA_USER)
            if (novaConfiguracao != null) {
                configuracao = novaConfiguracao
            }
        }
    }

    private fun exibeImagemResultado(resultado: Int, imagem: Int) {
        val nomeImagem = "dice_$resultado"

        when(imagem){
            1 -> {
                activityMainBinding.resultadoIv.setImageResource(
                    resources.getIdentifier(nomeImagem, "mipmap", packageName)
                )
                activityMainBinding.resultadoIv.visibility = View.VISIBLE
            }
            2 -> {
                activityMainBinding.resultado2Iv.setImageResource(
                    resources.getIdentifier(nomeImagem, "mipmap", packageName)
                )
                activityMainBinding.resultado2Iv.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMi){
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingsActivityResultLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}