package com.example.neti_back.repository;

import com.example.neti_back.entity.QueueSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueueSubjectRepository extends JpaRepository<QueueSubject, UUID> {
}
