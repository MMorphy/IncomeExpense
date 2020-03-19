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

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.repository.CompanyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {

	@Autowired
	private CompanyService cService;
	@Autowired
	private CompanyRepository cRepo;

	private Company c = new Company();

	@Before
	public void init() {
		c.setLocation("Test lokacija");
		c.setCurrentCash(10000F);
		c.setName("Test companija");
		c.setTransactions(new ArrayList<>());
		c.setBuyers(new ArrayList<>());
		cService.saveCompany(c);
	}

	@Test
	@Order(1)
	public void findCompany() {
		List<Company> cs = cService.findAllCompanies();
		assertThat(cs).isNotEmpty();
	}

	@Test
	@Order(2)
	public void updateCompany() {
		c.setName("Novo ime");
		cService.updateCompany(c.getId(), c);
		assertThat(cService.findCompanyById(c.getId()).getName()).isEqualTo("Novo ime");
	}

	@Test
	@Order(3)
	public void deleteCompany() {
		cService.deleteCompanyById(c.getId());
		assertThat(cService.findAllCompanies()).hasSize(0);
	}

	@After
	public void clean() {
		cRepo.deleteAll();
	}

}
