package com.example.demo.dao;

import com.example.demo.entity.QuestWrapper;

import java.util.List;

public interface QuestWrapperDao {
    List<QuestWrapper> getWrapperByField(String field);
}
