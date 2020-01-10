package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.BuyerDTO;
import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.service.BuyerService;
import hr.petkovic.incomeexpense.service.CompanyService;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService buyerService;
	@Autowired
	private CompanyService companyService;

	public BuyerController(BuyerService buyerService, CompanyService companyService) {
		this.buyerService = buyerService;
		this.companyService = companyService;
	}

	@GetMapping()
	public String getAllBuyers(Model model) {
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "buyers/buyers";
	}

	@GetMapping("/{id}")
	public String getAllBuyers(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buyers", companyService.findCompanyById(id).getBuyers());
		return "buyers/buyers";
	}

	@GetMapping("/add")
	public String getBuyerAdding(Model model) {
		model.addAttribute("addBuyer", new BuyerDTO());
		model.addAttribute("companies", companyService.findAllCompanies());
		return "buyers/buyersAdd";
	}

	@PostMapping("/add")
	public String addBuyer(BuyerDTO addBuyer) {
		Company c = addBuyer.getCompany();
		c.getBuyers().add(addBuyer.getBuyer());
		companyService.updateCompany(c.getId(), c);
		return "redirect:/buyer";
	}

	@GetMapping("/edit/{id}")
	public String getBuyerEditing(@PathVariable("id") Long id, Model model) {
		BuyerDTO buyer = new BuyerDTO();
		buyer.setBuyer(buyerService.findBuyerById(id));
		buyer.setCompany(companyService.findCompanyByBuyerId(id));
		model.addAttribute("editBuyer", buyer);
		model.addAttribute("companies", companyService.findAllCompanies());
		return "buyers/buyersEdit";

	}

	@PostMapping("/edit/{id}")
	public String editBuyer(@PathVariable("id") Long id, BuyerDTO editBuyer, Model model) {
		Company oldCompany = companyService.findCompanyByBuyerId(id);
		editBuyer.getBuyer().setId(id);
		if (!editBuyer.getCompany().equals(oldCompany)) {
			Buyer oldBuyer = buyerService.findBuyerById(id);
			oldCompany.getBuyers().remove(oldBuyer);
			companyService.updateCompany(oldCompany.getId(), oldCompany);
		}
		Company newCompany = editBuyer.getCompany();
		newCompany.getBuyers().add(editBuyer.getBuyer());
		companyService.updateCompany(newCompany.getId(), newCompany);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "redirect:/buyer";
	}

	@PostMapping("/delete/{id}")
	public String deleteBuyer(@PathVariable("id") Long id, Model model) {
		buyerService.deleteBuyerById(id);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "redirect:/buyer";
	}

	//TODO ovo ne radi dobro - pogledaj na netu kako se u thymeleafu iterira kroz listu koja se nalazi u listi nekih objekata
	@GetMapping("/contracts/{id}")
	public String getContractDetailsForBuyer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buyer", buyerService.findBuyerById(id));
		return "buyers/contracts";
	}
}
