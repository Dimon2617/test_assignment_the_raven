package org.example.test_assignment.customer.repository;

import org.example.test_assignment.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT c FROM CustomerEntity c WHERE c.isActive = true")
    List<CustomerEntity> findAllActiveCustomers();

}
