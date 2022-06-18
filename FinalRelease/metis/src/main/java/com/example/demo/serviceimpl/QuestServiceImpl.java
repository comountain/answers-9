package com.example.demo.serviceimpl;

import com.example.demo.dao.QuestDao;
import com.example.demo.dao.QuestWrapperDao;
import com.example.demo.entity.Quest;
import com.example.demo.entity.QuestWrapper;
import com.example.demo.entity.Questadd;
import com.example.demo.repository.QuestaddRepository;
import com.example.demo.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestServiceImpl implements QuestService {
    @Autowired
    private QuestDao questDao;

    @Autowired
    private QuestWrapperDao questWrapperDao;

    @Autowired
    QuestaddRepository questaddRepository;

    @Override
    public List<Quest> getQuests(int wrapper_order, String field)
    {
        List<QuestWrapper> allwrapper = questWrapperDao.getWrapperByField(field);
        QuestWrapper togive = allwrapper.get(wrapper_order);
        List<Quest> ret = new ArrayList<>();
        ret.add(questDao.findById(togive.getId1()));
        ret.add(questDao.findById(togive.getId2()));
        ret.add(questDao.findById(togive.getId3()));
        ret.add(questDao.findById(togive.getId4()));
        ret.add(questDao.findById(togive.getId5()));
        ret.add(questDao.findById(togive.getId6()));
        ret.add(questDao.findById(togive.getId7()));
        return ret;
    }

    @Override
    public List<Questadd> getQuestadds()
    {
        return questDao.getAllQuestadd();
    }

    @Override
    public void addQuestadd(String type, String title,
                     String optionA, String optionB, String optionC, String optionD,
                     String answer, String field)
    {
        System.out.println("here1");
        Questadd add = new Questadd();
        add.setTitle(title);
        add.setOptionA(optionA);
        add.setOptionB(optionB);
        add.setOptionC(optionC);
        add.setOptionD(optionD);
        add.setType(type);
        add.setField(field);
        add.setAnswer(answer);
        questaddRepository.save(add);
    }

    @Override
    public void addQuest(String type, String title,
                            String optionA, String optionB, String optionC, String optionD,
                            String answer, String field)
    {
        questDao.addQuest(type, title, optionA, optionB, optionC, optionD, answer, field);
    }
}
