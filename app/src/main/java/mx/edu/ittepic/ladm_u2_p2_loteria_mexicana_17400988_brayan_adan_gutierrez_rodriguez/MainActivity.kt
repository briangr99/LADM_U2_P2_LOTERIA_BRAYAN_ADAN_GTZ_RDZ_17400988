package mx.edu.ittepic.ladm_u2_p2_loteria_mexicana_17400988_brayan_adan_gutierrez_rodriguez

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.edu.ittepic.ladm_u2_p2_loteria_mexicana_17400988_brayan_adan_gutierrez_rodriguez.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var primeravez = true
    var segundavez = true
    lateinit var mp: MediaPlayer
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var cartas = arrayOf(
            R.drawable.carta1,R.drawable.carta2,R.drawable.carta3,R.drawable.carta4,R.drawable.carta5,
            R.drawable.carta6,R.drawable.carta7,R.drawable.carta8,R.drawable.carta9,R.drawable.carta10,
            R.drawable.carta11,R.drawable.carta12,R.drawable.carta13,R.drawable.carta14,R.drawable.carta15,
            R.drawable.carta16,R.drawable.carta17,R.drawable.carta18,R.drawable.carta19,R.drawable.carta20,
            R.drawable.carta21,R.drawable.carta22,R.drawable.carta23,R.drawable.carta24,R.drawable.carta25,
            R.drawable.carta26,R.drawable.carta27,R.drawable.carta28,R.drawable.carta29,R.drawable.carta30,
            R.drawable.carta31,R.drawable.carta32,R.drawable.carta33,R.drawable.carta34,R.drawable.carta35,
            R.drawable.carta36,R.drawable.carta37,R.drawable.carta38,R.drawable.carta39,R.drawable.carta40,
            R.drawable.carta41,R.drawable.carta42,R.drawable.carta43,R.drawable.carta44,R.drawable.carta45,
            R.drawable.carta46,R.drawable.carta47,R.drawable.carta48,R.drawable.carta49,R.drawable.carta50,
            R.drawable.carta51,R.drawable.carta52,R.drawable.carta53,R.drawable.carta54
        )

        var cartasS = arrayOf(
            R.raw.carta1,R.raw.carta2,R.raw.carta3,R.raw.carta4,R.raw.carta5,R.raw.carta6,R.raw.carta7,
            R.raw.carta8,R.raw.carta9,R.raw.carta10,R.raw.carta11,R.raw.carta12,R.raw.carta13,R.raw.carta14,
            R.raw.carta15,R.raw.carta16,R.raw.carta17,R.raw.carta18,R.raw.carta19,R.raw.carta20,R.raw.carta21,
            R.raw.carta22,R.raw.carta23,R.raw.carta24,R.raw.carta25,R.raw.carta26,R.raw.carta27,R.raw.carta28,
            R.raw.carta29,R.raw.carta30,R.raw.carta31,R.raw.carta32,R.raw.carta33,R.raw.carta34,R.raw.carta35,
            R.raw.carta36,R.raw.carta37,R.raw.carta38,R.raw.carta39,R.raw.carta40,R.raw.carta41,R.raw.carta42,
            R.raw.carta43,R.raw.carta44,R.raw.carta45,R.raw.carta46,R.raw.carta47,R.raw.carta48,R.raw.carta49,
            R.raw.carta50,R.raw.carta51,R.raw.carta52,R.raw.carta53,R.raw.carta54
        )

        var cartascompletas = ArrayList<Cartacompleta>(53)


        //PRIMER IMAGEN PANTALLA PRINCIPAL
        binding.imagen.setImageResource(R.drawable.carta1pro);
        binding.terminar.setVisibility(View.INVISIBLE);
        binding.pausar.setVisibility(View.INVISIBLE);
        binding.fin.setVisibility(View.INVISIBLE);
        binding.iniciar.setVisibility(View.INVISIBLE);

        this.setTitle("LOTERIA V3.3")

        llenar(cartascompletas, cartas, cartasS)


        //CREACION DEL HILO
        val hilo = Hilo(binding.imagen, this, cartascompletas)


        //BOTON PARA INICIAR EL HILO CON BANDERA PARA EL PAUSE
        binding.iniciar.setOnClickListener {
            if (segundavez == true) {
                if (primeravez == true) {
                    try {
                        binding.terminar.setVisibility(View.VISIBLE);
                        binding.pausar.setVisibility(View.VISIBLE);
                        binding.iniciar.setVisibility(View.INVISIBLE);
                        binding.barajear.setVisibility(View.INVISIBLE);
                        this.setTitle("LOTERIA V3.3")
                        hilo.start()


                    } catch (e: Exception) {
                        AlertDialog.Builder(this)
                            .setMessage("A OCURRIDO UN PROBLEMA PORFAVOR VUELVA A CORRER LA APLICACION")
                            .show()
                    }
                } else {

                    binding.pausar.setVisibility(View.VISIBLE);
                    binding.terminar.setVisibility(View.VISIBLE);
                    binding.iniciar.setVisibility(View.INVISIBLE);
                    this.setTitle("LOTERIA V3.3")
                    try {
                        hilo.despausarHilo()
                    } catch (e: Exception) {
                    }
                }
            } else {
                binding.fin.setVisibility(View.VISIBLE);
                binding.fin.setVisibility(View.VISIBLE);
                binding.iniciar.setVisibility(View.INVISIBLE)
                binding.terminar.setVisibility(View.INVISIBLE)
                binding.pausar.setVisibility(View.INVISIBLE)
                try {
                    hilo.despausarHilo()
                } catch (e: Exception) {
                }
            };
        }

        //BOTON DE PAUSAR CON BANDERA
        binding.pausar.setOnClickListener {
            binding.terminar.setVisibility(View.VISIBLE);
            binding.iniciar.setVisibility(View.VISIBLE);
            binding.pausar.setVisibility(View.INVISIBLE);
            primeravez = false
            hilo.pausarHilo()


        }

        //BOTON PARA BARAJEAR
        binding.barajear.setOnClickListener {
            corru(cartascompletas)
            //cartascompletas.shuffle() este podria usarlo para revolver mas veces
            this.setTitle("Cartas Barajeadas");
            binding.iniciar.setVisibility(View.VISIBLE);
            binding.barajear.setVisibility(View.INVISIBLE);

        }

        //BOTON PARA TERMINAR
        binding.terminar.setOnClickListener {
            hilo.pausarHilo()
            primeravez = false
            segundavez = false
            mp = MediaPlayer.create(this, R.raw.termino)
            mp.start()
            binding.pausar.setVisibility(View.INVISIBLE);
            binding.terminar.setVisibility(View.INVISIBLE);
            binding.iniciar.setText("CHECAR CARTAS")
            binding.iniciar.setVisibility(View.VISIBLE);

        }

            //BOTON PARA SALIR DEL JUEGO
        binding.fin.setOnClickListener {
            System.exit(0)
        }

    }

    fun corru(cartas: ArrayList<Cartacompleta>) = GlobalScope.launch {
        var contador = true
        var cartas = cartas
        while (contador) {
            runOnUiThread {
                cartas.shuffle()
                binding.barajear.setVisibility(View.INVISIBLE);
                contador=false
            }
            delay(1000L)
        }
    }


    private fun llenar(cartascompletas: ArrayList<Cartacompleta>, cartas: Array<Int>, cartasS: Array<Int>) {
        var contador = 0
        while (contador <= 53) {
            cartascompletas.add(Cartacompleta(cartas[contador], cartasS[contador]))
            contador++
        }
    }

}