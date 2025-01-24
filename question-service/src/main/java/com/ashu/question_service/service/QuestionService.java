package com.ashu.question_service.service;


import com.ashu.question_service.dao.QuestionDao;
import com.ashu.question_service.model.Question;
import com.ashu.question_service.model.QuestionWrapper;
import com.ashu.question_service.model.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<List<Question>>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace(); //prints the error
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
         try {
             questionDao.save(question);
             return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         return new ResponseEntity<>("Question not added", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Question not deleted", HttpStatus.NOT_ACCEPTABLE);
    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions=questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<QuestionWrapper> wrapper=new ArrayList<>();
        List<Question> questions=new ArrayList<>();

        for (Integer questionId : questionIds) {
            questions.add(questionDao.findById(questionId).get());
        }

        for (Question question : questions) {
            QuestionWrapper questionWrapper=new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            wrapper.add(questionWrapper);
        }

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Responses> responses) {

        int right=0;

        for(Responses r:responses){
            Question question=questionDao.findById(r.getId()).get();
            if (r.getResponse().equals(question.getRightAnswer())) right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
