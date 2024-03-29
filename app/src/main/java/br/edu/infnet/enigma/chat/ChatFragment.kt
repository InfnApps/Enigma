package br.edu.infnet.enigma.chat


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.enigma.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

const val USER_ID = 0
const val BOT_ID = 1

/**
 * A simple [Fragment] subclass.
 *
 */
class ChatFragment : Fragment() {

    val LOCATION_REQUEST_CODE = 71
    val TAG = "LOCATION"

    var messagesCount = 0
    // valor aleatório entre 1 e 4
    var botWait = (1..4).random()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // configuração da RecyclerView
        message_list.adapter = ChatAdapter()
        message_list.layoutManager = LinearLayoutManager(context)
        context?.let {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
        }

        setUpListeners()
    }

    private fun setUpListeners(){
        // configura botão de enviar
        send_button.setOnClickListener {
            val text = message_edittext.text.toString()
            message_edittext.setText("")
            //incrementa contador de mensagens
            ++messagesCount //equivale a messagesCount = messagesCount + 1

            val timestamp = Date().time
            val message = ChatMessage(text, timestamp, USER_ID)

            // adiciona mensagem nova ao ChatAdapter da RecyclerView
            val chatAdapter = message_list.adapter
            if (chatAdapter is ChatAdapter){
                chatAdapter.addMessage(message)
                if (messagesCount == botWait){
                    val botMessage = ChatMessage("Sou um bot", timestamp, BOT_ID)
                    chatAdapter.addMessage(botMessage)
                    messagesCount = 0
                    botWait = (1..4).random()
                }
                message_list.smoothScrollToPosition(chatAdapter.itemCount-1)
            }
        }

        location_button.setOnClickListener {
            // verificar se eu já tenho a permissão de Location
            //1. Se não tenho permissão: solicita permissão

            context?.let {
                if (ContextCompat.checkSelfPermission(it,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){

                    // solicita permissão ACCESS_FINE_LOCATION em tempo de execução
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION),
                        LOCATION_REQUEST_CODE)

                } else { //2. Caso contrário: solicita a última localização conhecida do usuário
                    //Toast.makeText(context, "JÁ Tenho Permissão", Toast.LENGTH_LONG).show()
                    requestUserLocation()
                }
            }






        }

    }

    @SuppressWarnings("MissingPermission")
    private fun requestUserLocation(){
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->

            val message =  if (location != null){
                "Lat:${location.latitude};Long:${location.longitude}"
            } else {
                "Localização desconhecida"
            }

            val chatMessage = ChatMessage(message, Date().time, USER_ID)
            val chatAdapter = message_list.adapter
            if (chatAdapter is ChatAdapter){
                chatAdapter.addMessage(chatMessage)
                // scroll para a última posição da recyclerview
                message_list.smoothScrollToPosition(chatAdapter.itemCount-1)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "on resquestPermissionResult")
        if (requestCode == LOCATION_REQUEST_CODE){
            Log.d(TAG, "passou resquestCode")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "permissão concedida")
                // solicitar localização
                //Toast.makeText(context, "Permissão concedida", Toast.LENGTH_LONG).show()
                requestUserLocation()
            } else {
                val view = this.view
                Log.d(TAG, "permissão negada")
                if(view != null) {
                    Snackbar.make(
                        view,
                        getString(R.string.location_denyed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}
