package hr.petkovic.incomeexpense.service;

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
import hr.petkovic.incomeexpense.repository.PlanRepository;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanServiceTest {

	@Autowired
	private PlanService pService;

	@Autowired
	private PlanRepository pRepo;

	@Autowired
	private TransactionTypeRepository typeRepo;

	private Plan p = new Plan();

	private TransactionType t = new TransactionType();

	@Before
	public void init() {
		if (typeRepo.findAllByName("Income").size() == 0) {
			t.setName("Income");
			t.setSubtypeOne("Loans");
			typeRepo.save(t);
		} else {
			t = typeRepo.findAllByName("Income").get(0);
		}
		p.setAmount(10000F);
		p.setType(t);
		p.setFromDate(new Date());
		p.setToDate(new Date(2020, 03, 20, 20, 00));

		p.setId(pService.savePlan(p).getId());
	}

	@Test
	@Order(1)
	public void findPlan() {
		List<Plan> pls = pService.findAllPlans();
		assertThat(pls).isNotEmpty();
	}

	@Test
	@Order(2)
	public void updatePlan() {
		p.setAmount(20000F);
		pService.updatePlan(p.getId(), p);
		assertThat(pService.findPlanById(p.getId()).getAmount()).isEqualTo(20000F);
	}

	@Test
	@Order(3)
	public void deletePlan() {
		pService.deletePlanById(p.getId());
		assertThat(pService.findPlanById(p.getId())).isNull();
	}

	@After
	public void clean() {
		pRepo.deleteAll();
		typeRepo.deleteAll();
	}
}
