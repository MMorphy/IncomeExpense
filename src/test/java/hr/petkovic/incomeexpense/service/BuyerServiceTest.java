package hr.petkovic.incomeexpense.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.repository.BuyerRepository;
import hr.petkovic.incomeexpense.repository.ContractRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyerServiceTest {

	@Autowired
	private BuyerService bService;

	@Autowired
	private ContractRepository contractRepo;

	@Autowired
	private BuyerRepository bRepo;

	private Buyer b = new Buyer();

	private Long contractId = 0L;

	private List<Contract> contracts = new ArrayList<Contract>();

	@Before
	public void init() {
		b.setCode("Test code");
		b.setDescription("Test buyer 1");
		b.setName("Test name");
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
		b.setContracts(contracts);
		Buyer bb = bService.saveBuyer(b);
		contractId = bb.getContracts().iterator().next().getId();
		b.setId(bb.getId());
	}

	@Test
	@Order(1)
	public void getBuyer() {
		Buyer bb = bService.findBuyerById(b.getId());
		assertThat(bb).isNotNull();
	}

	@Test
	@Order(2)
	public void findByContract() {
		assertThat(bService.findBuyerByContractId(contractId)).isNotNull();
	}

	@Test
	@Order(3)
	public void update() {
		b.setCode("2222");
		bService.updateBuyer(b.getId(), b);
		assertThat(b.getCode()).isEqualTo("2222");
	}

	@Test
	@Order(4)
	public void delete() {
		bService.deleteBuyerById(b.getId());
		assertThat(bService.findAllBuyers()).isEmpty();
	}

	@After
	public void clean() {
		contractRepo.deleteAll();
		bRepo.deleteAll();
	}

}
