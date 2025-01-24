package com.ashu.quiz_service.dao;


import com.ashu.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
    Integer id(Integer id);
}
