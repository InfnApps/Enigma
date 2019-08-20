package br.edu.infnet.enigma.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.enigma.R
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

const val USER_ID = 0
const val BOT_ID = 1

/**
 * A simple [Fragment] subclass.
 *
 */
class ChatFragment : Fragment() {

    var messagesCount = 0
    // valor aleatório entre 1 e 4
    var botWait = (1..4).random()

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
    }


}
