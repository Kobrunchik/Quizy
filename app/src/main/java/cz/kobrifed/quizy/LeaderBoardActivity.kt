package cz.kobrifed.quizy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class LeaderBoardActivity : AppCompatActivity() {

    lateinit var editText1: TextView
    lateinit var editText2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        editText1 = findViewById(R.id.textView)
        editText2 = findViewById(R.id.textView2)
        val editText3 = findViewById<TextView>(R.id.textView3)
        val editText4 = findViewById<TextView>(R.id.textView4)
        val editText5 = findViewById<TextView>(R.id.textView5)
        val editText6 = findViewById<TextView>(R.id.textView6)

        val sharedPref = getSharedPreferences("addplayer", Context.MODE_PRIVATE)

        var name1 = sharedPref.getString("name1", "A")
        var result1 = sharedPref.getInt("result1", -1).toString()
        var total1 = sharedPref.getInt("total1", 100).toString()
        if(name1 == "A"){
            name1 = "-"
            result1 = "-"
            total1 = "-"
        }

        var name2 = sharedPref.getString("name2", "A")
        var result2 = sharedPref.getInt("result2", -1).toString()
        var total2 = sharedPref.getInt("total2", 100).toString()
        if(name2 == "A"){
            name2 = "-"
            result2 = "-"
            total2 = "-"
        }

        var name3 = sharedPref.getString("name3", "A")
        var result3 = sharedPref.getInt("result3", -1).toString()
        var total3 = sharedPref.getInt("total3", 100).toString()
        if(name3 == "A"){
            name3 = "-"
            result3 = "-"
            total3 = "-"
        }

        editText1.text = name1
        editText2.text = "$result1/$total1"

        editText3.text = name2
        editText4.text = "$result2/$total2"

        editText5.text = name3
        editText6.text = "$result3/$total3"
    }
}