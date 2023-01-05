package cz.kobrifed.quizy.retrofit

class Question (
    var category: String,
    var id: String,
    var correctAnswer: String,
    var incorrectAnswers: List<String>,
    var question: String,
    var tags: List<String>,
    var type: String,
    var difficulty: String,
    var regions: List<String>
)