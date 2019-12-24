package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
