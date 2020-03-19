package hr.petkovic.incomeexpense.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.petkovic.incomeexpense.entity.TransactionType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTypeRepoTest {

	@Autowired
	private TransactionTypeRepository typeRepo;

	private TransactionType type = new TransactionType();

	private static Logger logger = LoggerFactory.getLogger(BuyerRepoTest.class);

	@Before
	public void init() {

		type.setName("Income");
		type.setSubtypeOne("Loans");

		typeRepo.save(type);

	}

	@Test
	@Order(1)
	public void getType() {
		List<TransactionType> types = typeRepo.findAll();
		assertThat(types).isNotEmpty();
	}

	@Test
	@Order(2)
	public void delteType() {
		typeRepo.deleteAll();
		List<TransactionType> types = typeRepo.findAll();
		assertThat(types).isEmpty();
	}

	@After
	public void clean() {
		typeRepo.deleteAll();
	}
}
