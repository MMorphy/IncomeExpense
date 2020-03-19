package hr.petkovic.incomeexpense.repository;

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
import org.springframework.transaction.annotation.Transactional;

import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAndRoleRepoTest {

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

		uRepo.save(userAdmin);

		adminRoles.add(roleAdm);
		adminRoles.add(roleOper);
		userAdmin.setRoles(adminRoles);

		uRepo.save(userAdmin);

		userOper.setCreatedAt(new Date());
		userOper.setEnabled(true);
		userOper.setPassword("oper");
		userOper.setUsername("oper");
		userOper.setRoles(operRoles);

		uRepo.save(userOper);

		operRoles.add(roleOper);
		userOper.setRoles(operRoles);
		uRepo.save(userOper);
	}

	@Test
	@Order(1)
	public void getUsers() {
		List<User> users = uRepo.findAll();
		assertThat(users).hasSize(2);
	}

	@Test
	@Order(2)
	public void getAdminUser() {
		User u = uRepo.findByUsername("admin").get();
		assertThat(u).isNotNull();
	}

	@Test
	@Order(3)
	public void getOperUser() {
		User u = uRepo.findByUsername("oper").get();
		assertThat(u).isNotNull();
	}

	@Test
	@Order(4)
	public void getEnabled() {
		List<User> users = uRepo.findByEnabled(true);
		assertThat(users).hasSize(2);
	}

	@Test
	@Order(5)
	public void deleteOper() {
		uRepo.delete(userOper);
		List<User> users = uRepo.findAll();
		assertThat(users).hasSize(1);
	}
	@After
	public void clean() {
		uRepo.deleteAll();
	}
}
