package com.example.demo.daoimpl;

import com.example.demo.dao.QuestWrapperDao;
import com.example.demo.entity.QuestWrapper;
import com.example.demo.repository.QuestWrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestWrapperDaoImpl implements QuestWrapperDao {
    @Autowired
    private QuestWrapperRepository questWrapperRepository;

    @Override
    public List<QuestWrapper> getWrapperByField(String field)
    {
        return questWrapperRepository.findByField(field);
    }
}
