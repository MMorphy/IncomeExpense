package hr.petkovic.incomeexpense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

	Optional<Buyer> findByContracts_Id(Long id);
}
