package br.edu.infnet.enigma.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.enigma.R
import kotlinx.android.synthetic.main.sent_message_card.view.*
import java.util.*

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    val items = listOf<ChatMessage>(
        ChatMessage("hbsjhsdfs 1", Date().time, 0),
        ChatMessage("hbsjhsdfs 2", Date().time, 1),
        ChatMessage("hbsjhsdfs 3", Date().time, 0)
    )
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {
        // lê XML com descrição da View que exibe o item da lista
        val card = LayoutInflater.from(parent.context)
            .inflate(R.layout.sent_message_card, parent, false)
        // retorna um ViewHolder construído a partir da view
        return ChatViewHolder(card)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ChatViewHolder, position: Int) {
    }

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val messageTextView: TextView = itemView.findViewById(R.id.message_textview)
        val momentTextView: TextView = itemView.findViewById(R.id.moment_textview)
    }
}