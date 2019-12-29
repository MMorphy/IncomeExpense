package hr.petkovic.incomeexpense.service;

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
}
