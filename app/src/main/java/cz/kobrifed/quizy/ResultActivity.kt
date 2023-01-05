package cz.kobrifed.quizy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class ResultActivity : AppCompatActivity() {

    lateinit var tv_name: TextView
    lateinit var tv_score: TextView
    lateinit var btn_finish: Button
    lateinit var btn_save: Button
    lateinit var btn_continue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val sharedPref = getSharedPreferences("addplayer", Context.MODE_PRIVATE)
        var edit = sharedPref.edit()

        tv_name = findViewById(R.id.tv_name)
        tv_score = findViewById(R.id.tv_score)
        btn_finish = findViewById(R.id.btn_finish)
        btn_save = findViewById(R.id.btn_save)
        btn_continue = findViewById(R.id.button2)

        val username = intent.getStringExtra(Constants.user_name)
        tv_name.text = username

        val totalQuestions = intent.getIntExtra(Constants.total_questions, 0)
        val correctAnswers = intent.getIntExtra(Constants.correct_answers, 0)
        tv_score.text = "Your Score is $correctAnswers out of $totalQuestions"

        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btn_save.setOnClickListener{
            val name1 = sharedPref.getString("name1", "A")
            val result1 = sharedPref.getInt("result1", -1)
            val total1 = sharedPref.getInt("total1", 100)
            val score1 = result1 - (total1 - result1)

            val name2 = sharedPref.getString("name2", "A")
            val result2 = sharedPref.getInt("result2", -1)
            val total2 = sharedPref.getInt("total2", 100)
            val score2 = result2 - (total2 - result2)

            //val name3 = sharedPref.getString("name3", "A")
            val result3 = sharedPref.getInt("result3", -1)
            val total3 = sharedPref.getInt("total3", 100)
            val score3 = result3 - (total3 - result3)

            val scoreNow = correctAnswers - (totalQuestions - correctAnswers)

            if(scoreNow < score3){
                Toast.makeText(this, "Sorry, you result isn't Top 3. Not saved", Toast.LENGTH_SHORT).show()
            }else{
                when(username){
                    name1 ->{
                        edit.putString("name1", username.toString())
                        edit.putInt("result1", correctAnswers)
                        edit.putInt("total1", totalQuestions)
                        edit.commit()
                    }
                    name2 ->{
                        if(scoreNow >= score1){
                            edit.putString("name2", sharedPref.getString("name1", "A"))
                            edit.putInt("result2", sharedPref.getInt("result1", -1))
                            edit.putInt("total2", sharedPref.getInt("total1", 100))

                            edit.putString("name1", username.toString())
                            edit.putInt("result1", correctAnswers)
                            edit.putInt("total1", totalQuestions)
                            edit.commit()
                        }else{
                            edit.putString("name2", username.toString())
                            edit.putInt("result2", correctAnswers)
                            edit.putInt("total2", totalQuestions)
                            edit.commit()
                        }
                    }
                    else ->{
                        if(scoreNow >= score1){
                            edit.putString("name3", sharedPref.getString("name2", "A"))
                            edit.putInt("result3", sharedPref.getInt("result2", -1))
                            edit.putInt("total3", sharedPref.getInt("total2", 100))

                            edit.putString("name2", sharedPref.getString("name1", "A"))
                            edit.putInt("result2", sharedPref.getInt("result1", -1))
                            edit.putInt("total2", sharedPref.getInt("total1", 100))

                            edit.putString("name1", username.toString())
                            edit.putInt("result1", correctAnswers)
                            edit.putInt("total1", totalQuestions)
                            edit.commit()
                        }else{
                            if(scoreNow >= score2){
                                edit.putString("name3", sharedPref.getString("name2", "A"))
                                edit.putInt("result3", sharedPref.getInt("result2", -1))
                                edit.putInt("total3", sharedPref.getInt("total2", 100))

                                edit.putString("name2", username.toString())
                                edit.putInt("result2", correctAnswers)
                                edit.putInt("total2", totalQuestions)
                                edit.commit()
                            }else{
                                //if(correctAnswers >= result3){
                                edit.putString("name3", username.toString())
                                edit.putInt("result3", correctAnswers)
                                edit.putInt("total3", totalQuestions)
                                edit.commit()
                                //}
                            }
                        }
                    }
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }
        }

        btn_continue.setOnClickListener{
            val intent = Intent(this, QuestionsActivity::class.java)

            intent.putExtra(Constants.user_name, username)
            intent.putExtra(Constants.correct_answers, correctAnswers)
            intent.putExtra(Constants.total_questions, totalQuestions+10)
            when(Random.nextInt(1, 4)){
                1 ->{
                    intent.putExtra(Constants.game_mode, "hard")
                }
                2 ->{
                    intent.putExtra(Constants.game_mode, "medium")
                }
                3 ->{
                    intent.putExtra(Constants.game_mode, "easy")
                }
            }

            startActivity(intent)
            finish()
        }


    }
}