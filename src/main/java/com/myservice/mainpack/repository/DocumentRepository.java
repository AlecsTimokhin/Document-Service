package com.myservice.mainpack.repository;

import com.myservice.mainpack.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT COUNT(d.id) FROM Document d")
    int getAllCount();

    @Query("SELECT d FROM Document d ORDER BY d.id DESC")
    List<Document> getAll();

}