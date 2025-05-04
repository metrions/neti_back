package com.example.neti_back.entity;

public class Lesson {
    public String day;
    public String time;
    public String subject;
    public String teacher;
    public String type;
    public String room;

    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s", day, time, subject, type, teacher, room);
    }
}