package br.edu.infnet.enigma

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val TAG = "ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.d(TAG, "on resquestPermissionResult")
//        if (requestCode == 71){
//            Log.d(TAG, "passou resquestCode")
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Log.d(TAG, "permissão concedida")
//                // solicitar localização
//                Toast.makeText(this, "Permissão concedida", Toast.LENGTH_LONG).show()
//            } else {
//                Log.d(TAG, "permissão negada")
//                    Snackbar.make(
//                        findViewById(R.id.root_view),
//                        getString(R.string.location_denyed),
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
}
