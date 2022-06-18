package com.example.demo.repository;

import com.example.demo.entity.Questadd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestaddRepository extends JpaRepository<Questadd,Integer> {
    @Query(value = "select q from Questadd q ")
    List<Questadd> getAllQuestadd();

}
