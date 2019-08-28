package br.edu.infnet.enigma

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpListeners()
    }

    private fun setUpListeners(){
        bottom_navigationview.setOnNavigationItemSelectedListener {
            val fragment = when(it.itemId){
                R.id.chats_item -> ImageFragment()
                else -> TextFragment()
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
            true
        }
    }
}
