package com.cod.mymarket.question.service;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.question.entity.Question;
import com.cod.mymarket.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public void create( Member member, String content) {
        Question q = Question.builder()
                .member(member)
                .content(content)
                .createdDate(LocalDateTime.now())
                .build();

        questionRepository.save(q);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public void modify(Question question, String content) {
        Question modifyQuestion = question.toBuilder()
                .content(content)
                .build();

        questionRepository.save(modifyQuestion);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}