package cz.kobrifed.quizy

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import cz.kobrifed.quizy.retrofit.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1
    private lateinit var mQuestionsList:Array<Question>
    private var questions10:Call<Array<Question>>? = null
    private var mSelectedOptionPosition:Int = 0
    private var correct:Int = 0
    private var mCorrectAnswersCounter:Int = 0
    private var mUserName: String? = null
    private var mMode: String = "easy"
    private var mTotalQuestions: Int = 10

    lateinit var textv: TextView
    lateinit var textansw1: TextView
    lateinit var textansw2: TextView
    lateinit var textansw3: TextView
    lateinit var textansw4: TextView
    lateinit var button: Button
    lateinit var ibutton: ImageButton
    lateinit var progressBar: ProgressBar
    lateinit var tv_progress: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)



        mUserName = intent.getStringExtra(Constants.user_name)
        mCorrectAnswersCounter = intent.getIntExtra(Constants.correct_answers, 0)
        mTotalQuestions = intent.getIntExtra(Constants.total_questions, 10)
        mMode = intent.getStringExtra(Constants.game_mode).toString()

        textv = findViewById(R.id.tv_qa)
        textansw1 = findViewById(R.id.tv_1)
        textansw2 = findViewById(R.id.tv_2)
        textansw3 = findViewById(R.id.tv_3)
        textansw4 = findViewById(R.id.tv_4)
        button = findViewById(R.id.btn_submit)
        ibutton = findViewById(R.id.ibtn)
        progressBar = findViewById(R.id.progressBar)
        tv_progress = findViewById(R.id.tv_progress)

        questions10= App.service.getQuestion(10, "CZ", "$mMode")

        setQuestions()

        textansw1.setOnClickListener(this)
        textansw2.setOnClickListener(this)
        textansw3.setOnClickListener(this)
        textansw4.setOnClickListener(this)
        button.setOnClickListener(this)
        ibutton.setOnClickListener(this)
    }

    private fun setQuestions(){
        questions10!!.enqueue(object : Callback<Array<Question>> {
           override fun onResponse(call: Call<Array<Question>>, response: Response<Array<Question>>) {
               mQuestionsList = response.body()!!
               setQuestion()
            }

            override fun onFailure(call: Call<Array<Question>>, t: Throwable) {
                Toast.makeText(this@QuestionsActivity, "Something wrong, Maybe check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setQuestion(){
        val question = mQuestionsList!![mCurrentPosition-1]

        val options = arrayOf<TextView>(textansw1, textansw2, textansw3, textansw4)
        for(option in options){
            option.visibility = View.VISIBLE
        }
        defaultOptionsView()
        button.text = "SUBMIT"

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max
        //Log.i("ASSAAS", mQuestionsList.toString())

        correct = Random.nextInt(1, 5)
        Log.i("ASSAAS", correct.toString())
        textv.text = question.question;

        when(correct){
            1 ->{
                textansw1.text = question.correctAnswer;
                textansw2.text =question.incorrectAnswers[0];
                textansw3.text =question.incorrectAnswers[1];
                textansw4.text =question.incorrectAnswers[2];
            }
            2 ->{
                textansw2.text = question.correctAnswer;
                textansw1.text =question.incorrectAnswers[0];
                textansw3.text =question.incorrectAnswers[1];
                textansw4.text =question.incorrectAnswers[2];
            }
            3 ->{
                textansw3.text = question.correctAnswer;
                textansw1.text =question.incorrectAnswers[0];
                textansw2.text =question.incorrectAnswers[1];
                textansw4.text =question.incorrectAnswers[2];
            }
            4 ->{
                textansw4.text = question.correctAnswer;
                textansw1.text =question.incorrectAnswers[0];
                textansw2.text =question.incorrectAnswers[1];
                textansw3.text =question.incorrectAnswers[2];
            }
        }
    }

    private fun defaultOptionsView(){
        val options = arrayOf<TextView>(textansw1, textansw2, textansw3, textansw4)

        for(option in options){
            option.setTextColor(Color.parseColor("#9d9fa3"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.tv_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_1 ->{
                selectedOptionView(textansw1, 1)
            }
            R.id.tv_2 ->{
                selectedOptionView(textansw2, 2)
            }
            R.id.tv_3 ->{
                selectedOptionView(textansw3, 3)
            }
            R.id.tv_4 ->{
                selectedOptionView(textansw4, 4)
            }

            R.id.btn_submit ->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()

                        }else ->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.user_name, mUserName)
                            intent.putExtra(Constants.correct_answers, mCorrectAnswersCounter)
                            intent.putExtra(Constants.total_questions, mTotalQuestions)
                            startActivity(intent)
                            finish()
                            //Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    if(mSelectedOptionPosition != correct){
                        answerView(mSelectedOptionPosition, R.drawable.tv_incorrect_bg)
                    }else{
                        mCorrectAnswersCounter++
                    }
                    answerView(correct, R.drawable.tv_correct_bg)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        button.text = "FINISH"
                    }else{
                        button.text = "NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }

            R.id.ibtn ->{
                minusTwoIncorrectAnswers()
                //Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                textansw1.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                textansw2.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                textansw3.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                textansw4.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        if((button.text != "FINISH") and (button.text != "NEXT QUESTION")) {
            defaultOptionsView()
            mSelectedOptionPosition = selectedOptionNum
            tv.setTextColor(Color.parseColor("#2c2f36"))
            tv.typeface = Typeface.DEFAULT_BOLD
            tv.background = ContextCompat.getDrawable(this, R.drawable.tv_selected_bg)
        }
    }

    private fun minusTwoIncorrectAnswers(){
        ibutton.background = ContextCompat.getDrawable(this, R.drawable.classic5050used)
        ibutton.isEnabled = false

        when(correct){
            1, 2 ->{
                textansw3.visibility = View.INVISIBLE
                textansw4.visibility = View.INVISIBLE
            }
            3, 4 ->{
                textansw1.visibility = View.INVISIBLE
                textansw2.visibility = View.INVISIBLE
            }

        }
    }

}