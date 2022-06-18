package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="questwrapper")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class QuestWrapper {
    @Id
    @Column(name="id")
    private Integer id;

    private Integer id1;
    private Integer id2;
    private Integer id3;
    private Integer id4;
    private Integer id5;
    private Integer id6;
    private Integer id7;
    private String field;

}
