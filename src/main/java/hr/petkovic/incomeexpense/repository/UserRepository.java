package hr.petkovic.incomeexpense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	List<User> findByEnabled(boolean enabled);
}
