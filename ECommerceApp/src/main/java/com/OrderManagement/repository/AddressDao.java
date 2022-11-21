package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.address.Addresses;

public interface AddressDao extends JpaRepository<Addresses, Integer>{

}
