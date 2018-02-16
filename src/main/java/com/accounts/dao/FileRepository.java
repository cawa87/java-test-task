package com.accounts.dao;

import com.accounts.api.model.FileRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pasha on 15.02.18.
 */
@Repository
public interface FileRepository extends JpaRepository<FileRec, Long> {
}
