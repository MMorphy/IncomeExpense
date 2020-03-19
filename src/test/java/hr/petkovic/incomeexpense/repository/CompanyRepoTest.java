package hr.petkovic.incomeexpense.repository;

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

import hr.petkovic.incomeexpense.entity.Company;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyRepoTest {

	@Autowired
	private CompanyRepository compRepo;

	private Company c = new Company();

	@Before
	public void init() {
		c.setLocation("Test lokacija");
		c.setCurrentCash(10000F);
		c.setName("Test companija");
		c.setTransactions(new ArrayList<>());
		c.setBuyers(new ArrayList<>());

		compRepo.save(c);
	}

	@Test
	@Order(1)
	public void findCompany() {
		List<Company> cs = compRepo.findAll();
		assertThat(cs).isNotEmpty();
	}

	@Test
	@Order(2)
	public void delte() {
		compRepo.deleteAll();
		List<Company> cs = compRepo.findAll();
		assertThat(cs).isEmpty();
	}
	@After
	public void clean() {
		compRepo.deleteAll();
	}
}
