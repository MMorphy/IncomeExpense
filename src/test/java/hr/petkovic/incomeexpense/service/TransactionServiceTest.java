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

import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.repository.FinancialTransactionRepository;
import hr.petkovic.incomeexpense.repository.RoleRepository;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;
import hr.petkovic.incomeexpense.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

	@Autowired
	private TransactionService tService;

	@Autowired
	private FinancialTransactionRepository ftRepo;

	private FinancialTransaction ft = new FinancialTransaction();

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

	@Autowired
	private TransactionTypeRepository typeRepo;

	private TransactionType type = new TransactionType();

	@Before
	public void init() {
		initUsers();
		initTransType();
		ft.setAmount(100F);
		ft.setCreateDate(new Date());
		ft.setCreatedBy(userOper);
		ft.setType(type);
		ft.setDescription("Test transaction");

		tService.saveTransaction(ft);
	}

	@Test
	@Order(1)
	public void getTransactions() {
		List<FinancialTransaction> fts = tService.findAllTransactions();
		assertThat(fts).isNotEmpty();
	}

	@Test
	@Order(2)
	public void updateTransaction() {
		ft.setAmount(200F);
		tService.updateTransaction(ft.getId(), ft);
		assertThat(tService.findTransactionById(ft.getId()).getAmount()).isEqualTo(200F);
	}

	@Test
	@Order(3)
	public void deleteTransaction() {
		tService.deleteTransactionById(ft.getId());
		assertThat(tService.findTransactionById(ft.getId())).isNull();
	}

	@After
	public void clean() {
		ftRepo.deleteAll();
		destroyUsers();
		destroyTransType();
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

	private void initTransType() {
		type.setName("Income");
		type.setSubtypeOne("Loans");

		typeRepo.save(type);
	}

	private void destroyUsers() {
		uRepo.deleteAll();
	}

	private void destroyTransType() {
		typeRepo.deleteAll();
	}
}
