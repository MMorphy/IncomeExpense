package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
