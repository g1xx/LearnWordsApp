package com.example.learnwordsapp

const val NUMBER_OF_ANSWERS: Int = 4

data class Word (
    val word: String,
    val translate: String,
    var learned: Boolean = false
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word
)

class LearnWordsDictionary {
    private val dictionary = listOf(
        Word("vivid","żywy"),
        Word("gloomy","ponury"),
        Word("bustling","tętniący życiem"),
        Word("serene","spokojny"),
        Word("desolate","opuszczony"),
        Word("quaint","osobliwy"),
        Word("sleek","gładki"),
        Word("dilapidated","zniszczony"),
        Word("fragrant","pachnący"),
        Word("vibrant","dynamiczny"),
        Word("wellness","dobre samopoczucie"),
        Word("nutrition","odżywianie"),
        Word("hydration","nawodnienie"),
        Word("stamina","wytrzymałość"),
        Word("flexibility","elastyczność"),
        Word("workout","trening"),
        Word("metabolism","metabolizm"),
        Word("fatigue","zmęczenie"),
        Word("recovery","regeneracja"),
        Word("diet","dieta"),
        Word("lecture","wykład"),
        Word("graduate","absolwent"),
        Word("experience","doświadczenie"),
        Word("interview","rozmowa kwalifikacyjna"),
    )

    private var currentQuestion: Question? = null


    fun getNextQuestion(): Question? {

        val notLearnWords = dictionary.filter { !it.learned }
        if (notLearnWords.isEmpty()) return null

        val questionWords =
            if(notLearnWords.size < NUMBER_OF_ANSWERS) {
                val learnedList =  dictionary.filter { it.learned }.shuffled()
                notLearnWords.shuffled()
                    .take(NUMBER_OF_ANSWERS) + learnedList
                    .take(NUMBER_OF_ANSWERS - notLearnWords.size)
            } else {
                notLearnWords.shuffled().take(NUMBER_OF_ANSWERS)
            }.shuffled()

        val correctAnswer: Word = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer
        )
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {

        return currentQuestion?.let {

            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }

        } ?: false
    }

}
