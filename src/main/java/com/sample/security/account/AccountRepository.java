package com.sample.security.account;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bumblebee
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String Username);
}
