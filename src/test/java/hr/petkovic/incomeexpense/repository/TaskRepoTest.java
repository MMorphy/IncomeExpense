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

import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.entity.Task;
import hr.petkovic.incomeexpense.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepoTest {

	@Autowired
	private TaskRepository tRepo;

	private Task t = new Task();

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
		initUsers();
		t.setCreatedAt(new Date());
		t.setDescription("Test description");
		t.setFinished(false);
		t.setDueDate(new Date(2020, 03, 21, 16, 00, 00));
		t.setFromUser(userAdmin);
		t.setToUser(userOper);

		tRepo.save(t);
	}

	@Test
	@Order(1)
	public void findTask() {
		List<Task> ts = tRepo.findAll();
		assertThat(ts).isNotEmpty();
	}

	@Test
	@Order(2)
	public void findTaskForUser() {
		List<Task> ts = tRepo.findByToUser_Username(userOper.getUsername());
		assertThat(ts).isNotEmpty();
	}

	@Test
	@Order(3)
	public void findTaskForUserEmpty() {
		List<Task> ts = tRepo.findByToUser_Username(userAdmin.getUsername());
		assertThat(ts).isEmpty();
	}

	@Test
	@Order(4)
	public void changeDate() {
		t.setDueDate(new Date());
		tRepo.save(t);
		List<Task> ts = tRepo.findByTodaysDateAndFinished(false);
		assertThat(ts).isNotEmpty();
	}

	@After
	public void clean() {
		tRepo.deleteAll();
		destroyUsers();
	}

	private void initUsers() {
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

	private void destroyUsers() {
		uRepo.deleteAll();
	}

}
