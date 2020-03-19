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

import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.repository.ContractRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractServiceTest {

	@Autowired
	private ContractService cService;

	@Autowired
	private ContractRepository cRepo;

	private Contract c = new Contract();

	@Before
	public void init() {
		c.setCurrentAmount(0F);
		c.setCode("1111");
		c.setAgreedAmount(1000F);
		c.setId(cService.saveContract(c).getId());
	}

	@Test
	@Order(1)
	public void getContract() {
		List<Contract> cons = cService.findAllContracts();
		assertThat(cons).isNotEmpty();
	}

	@Test
	@Order(2)
	public void updateContract() {
		c.setCode("2222");
		cService.updateContract(c.getId(), c);
		assertThat(cService.findContractById(c.getId()).getCode()).isEqualTo("2222");
	}

	@Test
	@Order(3)
	public void delteContract() {
		cService.deleteContractById(c.getId());
		assertThat(cService.findAllContracts()).isEmpty();
	}

	@After
	public void clean() {
		cRepo.deleteAll();
	}
}
