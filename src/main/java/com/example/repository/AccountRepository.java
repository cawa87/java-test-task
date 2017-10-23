package com.example.repository;

import com.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author dasolovyov on 23.10.2017.
 */
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends JpaRepository<Account, Long> {
}
