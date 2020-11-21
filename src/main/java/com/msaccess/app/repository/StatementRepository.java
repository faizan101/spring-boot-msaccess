package com.msaccess.app.repository;

import com.msaccess.app.entity.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<StatementEntity, Long> {

    List<StatementEntity> findByAccountEntityIdAndDateFieldIn(Long id, List<String> date);

}
