package com.example.learnwordsapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.learnwordsapp.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLeatnWordBinding must not be null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsDictionary()
        showNextMessage(trainer)

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markAnswerNeutral(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                markAnswerNeutral(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                markAnswerNeutral(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                markAnswerNeutral(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                showNextMessage(trainer)
            }

            btnSkip.setOnClickListener {
                showNextMessage(trainer)
            }
        }

//        binding.layoutAnswer1.setOnClickListener{
//            markAnswerWrong(
//                binding.layoutAnswer1,
//                binding.tvVariantNumber1,
//                binding.tvVariantValue1
//            )
//            showResultMessage(false)
//        }
//        binding.layoutAnswer2.setOnClickListener{
//            markAnswerWrong(
//                binding.layoutAnswer2,
//                binding.tvVariantNumber2,
//                binding.tvVariantValue2
//            )
//            showResultMessage(false)
//        }
//        binding.layoutAnswer3.setOnClickListener{
//            markAnswerWrong(
//                binding.layoutAnswer3,
//                binding.tvVariantNumber3,
//                binding.tvVariantValue3
//            )
//            showResultMessage(false)
//        }
//        binding.layoutAnswer4.setOnClickListener{
//            markAnswerCorrect(
//                binding.layoutAnswer4,
//                binding.tvVariantNumber4,
//                binding.tvVariantValue4
//            )
//            showResultMessage(true)
//        }
//        binding.btnContinue.setOnClickListener {
//            markAnswerNeutral(
//                binding.layoutAnswer1,
//                binding.tvVariantNumber1,
//                binding.tvVariantValue1
//            )
//            markAnswerNeutral(
//                binding.layoutAnswer2,
//                binding.tvVariantNumber2,
//                binding.tvVariantValue2
//            )
//            markAnswerNeutral(
//                binding.layoutAnswer3,
//                binding.tvVariantNumber3,
//                binding.tvVariantValue3
//            )
//            markAnswerNeutral(
//                binding.layoutAnswer4,
//                binding.tvVariantNumber4,
//                binding.tvVariantValue4
//            )
//        }
    }

    private fun blockLayoutAnswer(layoutAnswer1: LinearLayout, layoutAnswer2: LinearLayout, layoutAnswer3: LinearLayout) {

        layoutAnswer1.setOnClickListener {  }
        layoutAnswer2.setOnClickListener {  }
        layoutAnswer3.setOnClickListener {  }
    }

    private fun showCorrectAnswer(currentQuestion: Question){
        with (binding) {
            if (tvVariantValue1.text == currentQuestion.correctAnswer.translate)
                markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
            else if (tvVariantValue2.text == currentQuestion.correctAnswer.translate)
                markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
            else if (tvVariantValue3.text == currentQuestion.correctAnswer.translate)
                markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
            else
                markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
        }


    }

    private fun showNextMessage(trainer: LearnWordsDictionary) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding){
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS){
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                btnSkip.text = "Complete"
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.word

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    blockLayoutAnswer(layoutAnswer2, layoutAnswer3, layoutAnswer4)
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(true)

                    } else {
                        markAnswerWrong(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(false)
                        showCorrectAnswer(firstQuestion)

                    }
                }

                layoutAnswer2.setOnClickListener {
                    blockLayoutAnswer(layoutAnswer1, layoutAnswer3, layoutAnswer4)
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(false)
                        showCorrectAnswer(firstQuestion)
                    }
                }

                layoutAnswer3.setOnClickListener {
                    blockLayoutAnswer(layoutAnswer1, layoutAnswer2, layoutAnswer4)
                    if (trainer.checkAnswer(2)) {
                        markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(false)
                        showCorrectAnswer(firstQuestion)
                    }
                }

                layoutAnswer4.setOnClickListener {
                    blockLayoutAnswer(layoutAnswer1, layoutAnswer2, layoutAnswer3)
                    if (trainer.checkAnswer(3)) {
                        markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(false)
                        showCorrectAnswer(firstQuestion)
                    }
                }






            }
        }

    }



    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ){
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )

        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants
            )
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                )
            )
        }
    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswearColor
            )
        )



    }



    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {

        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )

       tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswearColor
            )
        )



    }




    private fun showResultMessage(isCorrect: Boolean){
        val color: Int
        val messageText: String
        val resultIconResource: Int
        if(isCorrect){
            color = ContextCompat.getColor(
                this,
                R.color.correctAnswearColor
            )
            messageText = "Correct!" // TODO get from string resources
            resultIconResource = R.drawable.ic_correct

        } else {
            color = ContextCompat.getColor(
                this,
                R.color.wrongAnswearColor
            )
            messageText = "Wrong!" // TODO get from string resources
            resultIconResource = R.drawable.ic_wrong
        }

            with(binding){
                btnSkip.isVisible = false

                btnContinue.setTextColor(color)
                layoutResult.setBackgroundColor(color)
                tvResultMessage.text = messageText
                ivResultIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,resultIconResource))
                layoutResult.isVisible= true


            }

    }
}