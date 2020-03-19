package hr.petkovic.incomeexpense.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTypeServiceTest {

	@Autowired
	private TransactionTypeService tService;
	@Autowired
	private TransactionTypeRepository tRepo;

	private TransactionType t = new TransactionType();

	@Before
	public void init() {
		t.setName("Income");
		t.setSubtypeOne("Loans");

		t.setId(tService.saveTransactionType(t).getId());
	}

	@Test
	@Order(1)
	public void getTransType() {
		List<TransactionType> ts = tService.findAllTransactionTypes();
		assertThat(ts).isNotEmpty();
	}

	@Test
	@Order(2)
	public void getIncomes() {
		List<TransactionType> ts = tService.findAllIncomeTransactionTypes();
		assertThat(ts).isNotEmpty();
	}

	@Test
	@Order(3)
	public void update() {
		t.setName("Expenses");
		tService.updateTransactionType(t.getId(), t);
		List<TransactionType> ts = tService.findAllExpensesTransactionTypes();
		assertThat(ts).isNotEmpty();
	}

	@Test
	@Order(4)
	public void delete() {
		tService.deleteTransactionTypeById(t.getId());
		List<TransactionType> ts = tService.findAllTransactionTypes();
		assertThat(ts).isEmpty();
	}

	@After
	public void clean() {
		tRepo.deleteAll();
	}
}
