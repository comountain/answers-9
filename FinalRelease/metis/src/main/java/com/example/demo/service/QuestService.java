package com.example.demo.service;

import com.example.demo.entity.Quest;
import com.example.demo.entity.Questadd;

import java.util.List;

public interface QuestService {
    List<Quest> getQuests(int wrapper_order, String field);

    List<Questadd> getQuestadds();

    void addQuestadd(String type, String title,
                     String optionA, String optionB, String optionC, String optionD,
                     String answer, String field);

    void addQuest(String type, String title,
                     String optionA, String optionB, String optionC, String optionD,
                     String answer, String field);
}
