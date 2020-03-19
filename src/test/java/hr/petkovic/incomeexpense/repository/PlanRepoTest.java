package hr.petkovic.incomeexpense.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

import hr.petkovic.incomeexpense.entity.Plan;
import hr.petkovic.incomeexpense.entity.TransactionType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanRepoTest {

	@Autowired
	private PlanRepository planRepo;

	@Autowired
	private TransactionTypeRepository typeRepo;

	private Plan pl = new Plan();

	private TransactionType type = new TransactionType();

	@Before
	public void init() {
		type.setName("Income");
		type.setSubtypeOne("Loans");
		typeRepo.save(type);

		pl.setAmount(10000F);
		pl.setType(type);
		pl.setFromDate(new Date());
		pl.setToDate(new Date(2020, 03, 20, 20, 00));

		planRepo.save(pl);
	}

	@Test
	@Order(1)
	public void findPlan() {
		List<Plan> pls = planRepo.findAll();
		assertThat(pls).isNotEmpty();
	}

	@Test
	@Order(2)
	public void deletePlan() {
		planRepo.deleteAll();
		List<Plan> pls = planRepo.findAll();
		assertThat(pls).isEmpty();
	}

	@After
	public void clean() {
		planRepo.deleteAll();
		typeRepo.deleteAll();
	}
}
