package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.service.CompanyService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping()
	public String getAllCompanies(Model model) {
		model.addAttribute("companies", companyService.findAllCompanies());
		return "companies/companies";
	}

	@GetMapping("/add")
	public String getCompanyAdding(Model model) {
		model.addAttribute("addCompany", new Company());
		return "companies/companiesAdd";
	}

	@PostMapping("/add")
	public String addCompany(Company addCompany) {
		companyService.saveCompany(addCompany);
		return "redirect:/companies";
	}

	@GetMapping("/edit/{id}")
	public String getCompanyEditing(@PathVariable("id") Long id, Model model) {
		model.addAttribute("editCompany", companyService.findCompanyById(id));
		return "companies/companiesEdit";
	}

	@PostMapping("/edit/{id}")
	public String editCompany(@PathVariable("id") Long id, Company editCompany, Model model) {
		companyService.updateCompany(id, editCompany);
		model.addAttribute("companies", companyService.findAllCompanies());
		return "redirect:/companies";
	}

	@PostMapping("/delete/{id}")
	public String deteleCompany(@PathVariable("id") Long id, Model model) {
		companyService.deleteCompanyById(id);
		model.addAttribute("companies", companyService.findAllCompanies());
		return "redirect:/companies";
	}
}
