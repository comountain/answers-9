package com.example.demo.repository;

import com.example.demo.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest,Integer> {
    @Query(value = "select q from Quest q where q.type = :type and q.field = :field")
    List<Quest> findByType(@Param("type") String type, @Param("field") String field);

    @Query(value = "select q from Quest q where q.id = :id")
    Quest findById(@Param("id") int id);
}
