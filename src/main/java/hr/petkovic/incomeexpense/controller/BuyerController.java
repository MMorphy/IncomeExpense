package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Buyer;
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
	public String getAllBuyers(@PathVariable("id") Long id,Model model) {
		model.addAttribute("buyers", companyService.findCompanyById(id).getBuyers());
		return "buyers/buyers";
	}

	@GetMapping("/add")
	public String getBuyerAdding(Model model) {
		model.addAttribute("addBuyer", new Buyer());
		return "buyers/buyersAdd";
	}

	@PostMapping("/add")
	public String addBuyer(Buyer addBuyer) {
		buyerService.saveBuyer(addBuyer);
		return "redirect:/buyer";
	}

	@GetMapping("/edit/{id}")
	public String getBuyerEditing(@PathVariable("id") Long id, Model model) {
		model.addAttribute("editBuyer", buyerService.findBuyerById(id));
		return "buyers/buyersEdit";

	}

	@PostMapping("/edit/{id}")
	public String editBuyer(@PathVariable("id") Long id, Buyer editBuyer, Model model) {
		buyerService.updateBuyer(id, editBuyer);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "redirect:/buyer";
	}

	@PostMapping("/delete/{id}")
	public String deteleBuyer(@PathVariable("id") Long id, Model model) {
		buyerService.deleteBuyerById(id);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "redirect:/buyer";
	}
}
