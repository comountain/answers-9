package com.example.demo.daoimpl;

import com.example.demo.dao.QuestDao;
import com.example.demo.entity.Quest;
import com.example.demo.entity.Questadd;
import com.example.demo.repository.QuestRepository;
import com.example.demo.repository.QuestaddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestDaoImpl implements QuestDao {
    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private QuestaddRepository questaddRepository;

    @Override
    public List<Quest> findByType(String type, String field)
    {
        return questRepository.findByType(type,field);
    }

    @Override
    public Quest findById(int id){return questRepository.findById(id);}

    @Override
    public List<Questadd> getAllQuestadd()
    {
        return questaddRepository.getAllQuestadd();
    }

    @Override
    public void addQuest(String type, String title,
                  String optionA, String optionB, String optionC, String optionD,
                  String answer, String field)
    {
        Quest add = new Quest();
        add.setTitle(title);
        add.setOptionA(optionA);
        add.setOptionB(optionB);
        add.setOptionC(optionC);
        add.setOptionD(optionD);
        add.setType(type);
        add.setField(field);
        add.setAnswer(answer);
        questRepository.save(add);
    }
}
