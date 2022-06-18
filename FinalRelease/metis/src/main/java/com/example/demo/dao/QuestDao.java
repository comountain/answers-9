package com.example.demo.dao;

import com.example.demo.entity.Quest;
import com.example.demo.entity.Questadd;

import java.util.List;

public interface QuestDao {
    List<Quest> findByType(String type, String field);

    Quest findById(int id);

    List<Questadd> getAllQuestadd();

    void addQuest(String type, String title,
                  String optionA, String optionB, String optionC, String optionD,
                  String answer, String field);
}
