package com.example.demo.controller;

import com.example.demo.entity.Quest;
import com.example.demo.entity.Questadd;
import com.example.demo.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestController {
    @Autowired
    private QuestService questService;


    @RequestMapping("/debug")
    public String toDebug()
    {
        System.out.println("here");
        return "here";
    }

    @RequestMapping("/getQuests")
    public List<Quest> getQuests(@RequestParam("order") int order, @RequestParam("field") String field)
    {
        return questService.getQuests(order,field);
    }

    @RequestMapping("/getQuestadds")
    public List<Questadd> getQuestadds()
    {
        return questService.getQuestadds();
    }

    @RequestMapping("/addQuestadds")
    public String addQuestadds(@RequestParam("type") String type, @RequestParam("title") String title,
                               @RequestParam("optionA") String optionA,
                               @RequestParam("optionB") String optionB, @RequestParam("optionC") String optionC, @RequestParam("optionD") String optionD,
                               @RequestParam("answer") String answer, @RequestParam("field") String field)
    {
        System.out.println("here2");
        questService.addQuestadd(type, title, optionA, optionB, optionC, optionD, answer, field);
        return "add";
    }

    @RequestMapping("/addQuests")
    public String addQuests(@RequestParam("type") String type, @RequestParam("title") String title,
                               @RequestParam("optionA") String optionA,
                               @RequestParam("optionB") String optionB, @RequestParam("optionC") String optionC, @RequestParam("optionD") String optionD,
                               @RequestParam("answer") String answer, @RequestParam("field") String field)
    {
        questService.addQuest(type, title, optionA, optionB, optionC, optionD, answer, field);
        return "quest";
    }
}
