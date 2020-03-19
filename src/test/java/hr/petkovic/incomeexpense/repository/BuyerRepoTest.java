package hr.petkovic.incomeexpense.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Contract;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyerRepoTest {

	@Autowired
	private BuyerRepository buyerRepo;

	@Autowired
	private ContractRepository contractRepo;

	private Buyer buyer = new Buyer();

	private static Logger logger = LoggerFactory.getLogger(BuyerRepoTest.class);

	private List<Contract> contracts = new ArrayList<Contract>();
	
	@Before
	public void init() {
		buyer.setCode("Test code");
		buyer.setDescription("Test buyer 1");
		buyer.setName("Test name");
		Contract c = new Contract();
		c.setCurrentAmount(0F);
		c.setCode("1111");
		c.setAgreedAmount(1000F);
		c.setTransactions(new ArrayList<>());
		contracts.add(c);
		Contract cc = new Contract();
		cc.setCurrentAmount(100F);
		cc.setCode("2222");
		cc.setAgreedAmount(2000F);
		cc.setTransactions(new ArrayList<>());
		contracts.add(cc);
		buyer.setContracts(contracts);

		buyerRepo.save(buyer);
	}

	@Test
	@Order(1)
	public void getBuyer() {
		List<Buyer> buyers = buyerRepo.findAll();
		buyer.setId(buyers.get(0).getId());
		logger.debug(buyers.toString());
		assertThat(buyers).isNotEmpty();
	}

	@Test
	@Order(2)
	public void findContrac() {
		Contract c = contractRepo.findAll().get(0);
		assertThat(c.getCode()).isEqualTo("1111");
	}
	@Test
	@Order(3)
	public void deleteBuyer() {
		buyerRepo.delete(buyer);
		List<Buyer> buyers = buyerRepo.findAll();
		assertThat(buyers).isEmpty();
	}
	@After
	public void clean() {
		buyerRepo.deleteAll();
	}

}
