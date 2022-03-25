package mx.edu.ittepic.ladm_u2_p2_loteria_mexicana_17400988_brayan_adan_gutierrez_rodriguez

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.widget.ImageView


class Hilo(et: ImageView, este : MainActivity, cartacompleta:ArrayList<Cartacompleta>) : Thread() {
    lateinit var mp : MediaPlayer
    private var ejecutar=true
    private var pausar = false
    var este =este
    val etlocal = et
    var cartacompleta = cartacompleta
   // var cartas = cartasMa
   //var cartasS = cartasSo





    var variable =0

    override fun run() {
        super.run()

        while(ejecutar){
            if(!pausar){
                try{
                    mp = MediaPlayer.create(este,cartacompleta[variable].sonido)
                    etlocal.setImageResource(cartacompleta[variable].imagen)
                    sleep(500)
                    mp.start()
                    variable++
                    if (variable==53){
                        AlertDialog.Builder(este,)
                            .setMessage("JUEGO TERMINADO")
                            .show()
                        este.setTitle("TERMINO EL JUEGO")
                        ejecutar=false

                    }

                }catch (e:Exception){
                }
            }
            sleep(2500)
        }
    }

    fun terminarHilo(){
        ejecutar=false
    }
    fun pausarHilo(){
        pausar=true
    }
    fun pausarHiloymas(){
        pausar=true
        variable=0
    }
    fun despausarHilo(){
        pausar=false
    }
    fun estaPausado(): Boolean {
        return  pausar
    }

}