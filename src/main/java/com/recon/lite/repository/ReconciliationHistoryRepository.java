package com.recon.lite.repository;

import com.recon.lite.dao.ReconciliationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReconciliationHistoryRepository extends JpaRepository<ReconciliationHistory , Long> {
}
