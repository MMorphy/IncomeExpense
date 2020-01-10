package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	public RoleService(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

	public Role findAdminRole() {
		Optional<Role> optR = roleRepo.findByName("ROLE_ADMIN");
		if (optR.isPresent()) {
			return optR.get();
		} else
			return null;
	}

	public Role findUserRole() {
		Optional<Role> optR = roleRepo.findByName("ROLE_USER");
		if (optR.isPresent()) {
			return optR.get();
		} else
			return null;
	}

	public List<Role> findAllRoles() {
		return roleRepo.findAll();
	}
}
