package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
    Optional<Customer> findByUserId(String username);
}