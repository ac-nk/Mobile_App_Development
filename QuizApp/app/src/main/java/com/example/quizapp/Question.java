package com.example.quizapp;

public class Question {
    public QA[] question_answer;
    private int indx = 0;


    public String getNextQuestion() {
        indx++;
        if(indx >= question_answer.length-1)
        {
            return "end";
        }

        return question_answer[indx].question;
    }
    public String getAnswer(int index) {
        if(index == 0){
            return question_answer[question_answer.length-1].answer;
        }
        return question_answer[index-1].answer;
    }


    public class QA {
        public String question;
        public String answer;
    }
}
