package hr.petkovic.incomeexpense.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.repository.RoleRepository;
import hr.petkovic.incomeexpense.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAndRoleServiceTest {

	@Autowired
	private UserService uService;

	@Autowired
	private UserRepository uRepo;

	@Autowired
	private RoleRepository rRepo;

	private User userAdmin = new User();

	private List<Role> adminRoles = new ArrayList<>();

	private User userOper = new User();

	private List<Role> operRoles = new ArrayList<>();

	private Role roleAdm = new Role();

	List<User> adminUsers = new ArrayList<>();

	private Role roleOper = new Role();

	List<User> operUsers = new ArrayList<>();

	@Before
	public void init() {
		initUsersAndRoles();
	}

	@Test
	@Order(1)
	public void getUsers() {
		List<User> us = uService.findAllEnabledUsers();
		assertThat(us).hasSize(2);
	}

	@Test
	@Order(2)
	public void disableUser() {
		uService.disableUser(userOper.getId());
		List<User> us = uService.findAllEnabledUsers();
		assertThat(us).hasSize(1);
	}

	@Test
	@Order(3)
	public void updateUser() {
		userOper.setUsername("OperTest");
		uService.updateUser(userOper.getId(), userOper);
		assertThat(uService.findUserByUsername("OperTest")).isNotNull();
	}

	@After
	public void clean() {
		uRepo.deleteAll();
	}

	private void initUsersAndRoles() {

		List<Role> roles = rRepo.findAll();
		if (roles.isEmpty()) {
			roleAdm.setName("ROLE_ADMIN");
			rRepo.save(roleAdm);
			roleOper.setName("ROLE_OPER");
			rRepo.save(roleOper);
		} else {
			roleAdm = rRepo.findByName("ROLE_ADMIN").get();
			roleOper = rRepo.findByName("ROLE_OPER").get();
		}

		userAdmin.setCreatedAt(new Date());
		userAdmin.setEnabled(true);
		userAdmin.setPassword("admin");
		userAdmin.setUsername("admin");
		userAdmin.setRoles(adminRoles);

		uService.saveUser(userAdmin);

		adminRoles.add(roleAdm);
		adminRoles.add(roleOper);
		userAdmin.setRoles(adminRoles);

		userAdmin.setId(uService.saveUser(userAdmin).getId());

		userOper.setCreatedAt(new Date());
		userOper.setEnabled(true);
		userOper.setPassword("oper");
		userOper.setUsername("oper");
		userOper.setRoles(operRoles);

		userOper.setId(uService.saveUser(userOper).getId());

		operRoles.add(roleOper);
		userOper.setRoles(operRoles);
		uService.saveUser(userOper);

	}
}
