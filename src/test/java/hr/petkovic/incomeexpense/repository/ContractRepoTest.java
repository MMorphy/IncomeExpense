package hr.petkovic.incomeexpense.repository;

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

import hr.petkovic.incomeexpense.entity.Contract;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractRepoTest {

	@Autowired
	private ContractRepository contractRepo;

	private Contract con = new Contract();

	@Before
	public void init() {
		con.setCurrentAmount(0F);
		con.setCode("1111");
		con.setAgreedAmount(1000F);
		contractRepo.save(con);
	}

	@Test
	@Order(1)
	public void findContract() {
		List<Contract> cons = contractRepo.findAll();
		assertThat(cons).isNotEmpty();
	}

	@Test
	@Order(2)
	public void deleteContract() {
		contractRepo.deleteAll();
		List<Contract> cons = contractRepo.findAll();
		assertThat(cons).isEmpty();
	}

	@After
	public void clean() {
		contractRepo.deleteAll();
	}
}
