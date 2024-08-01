package com.cod.mymarket.question.repository;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByProductId(Long productId);

    List<Question> findByMemberId(Long memberId);

}