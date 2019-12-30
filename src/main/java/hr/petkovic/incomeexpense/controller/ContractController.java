package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.ContractDTO;
import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.service.BuyerService;
import hr.petkovic.incomeexpense.service.ContractService;
import hr.petkovic.incomeexpense.service.CurrencyService;

@Controller
@RequestMapping("/contracts")
public class ContractController {

	@Autowired
	private ContractService contractService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private BuyerService buyerService;

	public ContractController(ContractService contractService, CurrencyService currencyService,
			BuyerService buyerService) {
		this.contractService = contractService;
		this.currencyService = currencyService;
		this.buyerService = buyerService;
	}

	@GetMapping()
	public String getAllContracts(Model model) {
		model.addAttribute("contracts", contractService.findAllContracts());
		return "contracts/contracts";
	}

	@GetMapping("/{id}")
	public String getAllContractsForBuyer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("contracts", contractService.findAllContractsForBuyerId(id));
		return "contracts/contracts";
	}

	@GetMapping("/add")
	public String getContractAdding(Model model) {
		model.addAttribute("addContract", new ContractDTO());
		model.addAttribute("currencies", currencyService.findAllCurrencies());
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "contracts/contractsAdd";
	}

	@PostMapping("/add")
	public String addContract(Model model, ContractDTO addContract) {
		Buyer buyer = addContract.getBuyer();
		buyer.addContract(addContract.getContract());
		buyerService.updateBuyer(buyer.getId(), buyer);

		model.addAttribute("contracts", contractService.findAllContracts());
		return "redirect:/contracts";
	}

	@GetMapping("/edit/{id}")
	public String getContractEditing(@PathVariable("id") Long id, Model model) {
		ContractDTO editContract = new ContractDTO();
		editContract.setContract(contractService.findContractById(id));
		editContract.setBuyer(buyerService.findBuyerByContractId(id));
		model.addAttribute("editContract", editContract);
		model.addAttribute("currencies", currencyService.findAllCurrencies());
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "contracts/contractsEdit";

	}

	@PostMapping("/edit/{id}")
	public String editContract(@PathVariable("id") Long id, ContractDTO editContract, Model model) {
		Buyer oldBuyer = buyerService.findBuyerByContractId(id);
		Buyer newBuyer = editContract.getBuyer();
		if ((oldBuyer != null && newBuyer.getId() != null) || !oldBuyer.equals(newBuyer)
				|| !oldBuyer.getId().equals(newBuyer.getId())) {
			Contract oldContract = contractService.findContractById(id);
			oldBuyer.removeContract(oldContract);
			buyerService.updateBuyer(id, oldBuyer);

			newBuyer.addContract(editContract.getContract());
			buyerService.updateBuyer(id, newBuyer);
			contractService.deleteContractById(oldContract.getId());
		} else {
			contractService.updateContract(id, editContract.getContract());
		}
		model.addAttribute("contracts", contractService.findAllContracts());
		return "redirect:/contracts";
	}

	@PostMapping("/delete/{id}")
	public String deleteContract(@PathVariable("id") Long id, Model model) {
		contractService.deleteContractById(id);
		model.addAttribute("contracts", contractService.findAllContracts());
		return "redirect:/contracts";
	}
}
