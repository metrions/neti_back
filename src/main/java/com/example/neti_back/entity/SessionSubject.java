package com.example.neti_back.entity;

import com.example.neti_back.utils.ListToIntegerConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session_subject")
public class SessionSubject {

    public enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Column
    @Convert(converter = ListToIntegerConverter.class)
    private List<Integer> weeks;

    @Column
    private String room;

    @Column
    private String groupName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subject sessionSubject;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private QueueSubject queueSubject;
}
