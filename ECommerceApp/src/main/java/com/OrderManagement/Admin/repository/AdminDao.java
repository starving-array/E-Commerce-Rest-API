package com.OrderManagement.Admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.Admin.module.AdminCredential;

public interface AdminDao extends JpaRepository<AdminCredential, Integer>{

}
