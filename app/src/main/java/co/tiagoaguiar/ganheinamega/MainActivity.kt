package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Importing editText
        val editText: EditText = findViewById(R.id.edit_number)
        // Importing texview
        val txtResult: TextView = findViewById(R.id.txt_result)
        // Importing main button (generate)
        val btnGenerate: Button = findViewById(R.id.btn_generate)


        // Preferences db
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let{
            txtResult.text = "Ultima aposta: $it"
        }

        btnGenerate.setOnClickListener {

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }
    private fun numberGenerator(text: String, txtResult: TextView){
        // Fails
        if (text.isEmpty()){
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val qtd = text.toInt() // Convert Str to Int

        if (qtd < 6 || qtd > 15){
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        // Sucesses
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while(true){
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd){
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }
}