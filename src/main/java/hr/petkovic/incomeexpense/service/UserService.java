package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User findUserByUsername(String username) {
		try {
			return this.userRepo.findByUsername(username).get();
		} catch (Exception e) {
			return null;
		}
	}

	public User findUserById(Long id) {
		try {
			return this.userRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<User> findAllEnabledUsers() {
		return userRepo.findByEnabled(true);
	}

	public User disableUser(Long id) {
		Optional<User> optU = userRepo.findById(id);
		if (optU.isPresent()) {
			User u = optU.get();
			u.setEnabled(false);
			return userRepo.save(u);
		} else
			return null;
	}

	public User updateUser(Long id, User user) {
		Optional<User> optU = userRepo.findById(id);
		if (optU.isPresent()) {
			User u = optU.get();
			u.setEnabled(user.isEnabled());
			u.setPassword(user.getPassword());
			u.setUsername(user.getUsername());
			u.setRoles(user.getRoles());
			return userRepo.save(u);
		} else
			return userRepo.save(user);
	}

	public User saveUser(User user) {
		return userRepo.save(user);
	}
}
