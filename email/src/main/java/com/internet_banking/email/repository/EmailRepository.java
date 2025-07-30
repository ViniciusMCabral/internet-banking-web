package com.internet_banking.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internet_banking.email.models.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{

}
