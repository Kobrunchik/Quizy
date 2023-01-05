package cz.kobrifed.quizy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import cz.kobrifed.quizy.retrofit.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //lateinit var textv: TextView
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var btn_leaders: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn_main)
        editText = findViewById(R.id.et_main)
        btn_leaders = findViewById(R.id.btn_leaders)

        button.setOnClickListener{

            if(editText.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                if(editText.text.length < 3){
                    Toast.makeText(this, "Minimum 3 character name", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this, QuestionsActivity::class.java)
                    intent.putExtra(Constants.user_name, editText.text.toString())
                    intent.putExtra(Constants.game_mode, "easy")
                    startActivity(intent)
                    finish()
                }
            }
        }

        btn_leaders.setOnClickListener{
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
            //finish()
        }

    }
}