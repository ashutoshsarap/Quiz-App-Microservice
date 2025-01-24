package com.ashu.quiz_service.service;


import com.ashu.quiz_service.dao.QuizDao;
import com.ashu.quiz_service.feign.QuizInterface;
import com.ashu.quiz_service.model.QuestionWrapper;
import com.ashu.quiz_service.model.Quiz;
import com.ashu.quiz_service.model.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Successfully created Quiz", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Quiz quiz=quizDao.findById(id).get();
        List<Integer> questionIds=quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromIds(questionIds);

        return questions;
    }


    public ResponseEntity<Integer> calculateResult(int id, List<Responses> responses) {
        ResponseEntity<Integer> score=quizInterface.getScore(responses);
        return score;
    }
}
