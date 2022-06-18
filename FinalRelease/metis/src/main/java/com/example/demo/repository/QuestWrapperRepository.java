package com.example.demo.repository;

import com.example.demo.entity.QuestWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestWrapperRepository extends JpaRepository<QuestWrapper, Integer> {
    @Query(value="select q from QuestWrapper q where q.field = :field")
    List<QuestWrapper> findByField(@Param("field") String field);
}
