package com.example.neti_back.repository;

import com.example.neti_back.entity.SessionSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionSubjectRepository extends JpaRepository<SessionSubject, UUID> {
    @Query("select ses from SessionSubject ses where ses.groupName = :group")
    List<SessionSubject> getSessionSubjectByGroupName(@Param("group") String group);
}
