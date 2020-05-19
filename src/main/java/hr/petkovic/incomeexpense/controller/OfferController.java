package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.ContractDTO;
import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.Offer;
import hr.petkovic.incomeexpense.service.BuyerService;
import hr.petkovic.incomeexpense.service.OfferService;

@Controller
@RequestMapping("/offers")
public class OfferController {

	@Autowired
	private OfferService oService;
	@Autowired
	private BuyerService buyerService;

	@GetMapping("/home")
	public String home() {
		return "offers/offersHome";
	}

	// TODO napravi 3 tablice
	@GetMapping()
	public String getAllOffers(Model model) {
		model.addAttribute("offers", oService.findAllOffers());
		return "offers/offersAll";
	}

	@GetMapping("/user")
	public String getAllOffersForUser(Model model) {
		model.addAttribute("offers",
				oService.findAllOffersForUser(SecurityContextHolder.getContext().getAuthentication().getName()));
		return "offers/offers";
	}

	@GetMapping("/accepted")
	public String getAllAccepted(Model model) {
		model.addAttribute("heading", "Accepted offers");
		model.addAttribute("offers", oService.findByAcceptedOrDeclined(true));
		return "offers/offersAccDec";
	}

	@GetMapping("/declined")
	public String getAllDeclined(Model model) {
		model.addAttribute("heading", "Declined offers");
		model.addAttribute("offers", oService.findByAcceptedOrDeclined(false));
		return "offers/offersAccDec";
	}

	@GetMapping("/pending")
	public String getAllPending(Model model) {
		model.addAttribute("offers", oService.findAllPendingOffers());
		return "offers/offers";
	}

	@GetMapping("/user/accepted")
	public String getAllAcceptedForUser(Model model) {
		model.addAttribute("heading", "Accepted offers");
		model.addAttribute("offers", oService.findByAcceptedOrDeclinedForUsername(true,
				SecurityContextHolder.getContext().getAuthentication().getName()));
		return "offers/offersAccDec";
	}

	@GetMapping("/user/declined")
	public String getAllDeclinedForUser(Model model) {
		model.addAttribute("heading", "Declined offers");
		model.addAttribute("offers", oService.findByAcceptedOrDeclinedForUsername(false,
				SecurityContextHolder.getContext().getAuthentication().getName()));
		return "offers/offersAccDec";
	}

	@GetMapping("user/pending")
	public String getAllPendingForUser(Model model) {
		model.addAttribute("offers",
				oService.findAllPendingOffersForUser(SecurityContextHolder.getContext().getAuthentication().getName()));
		return "offers/offers";
	}

	@GetMapping("/add")
	public String getContractAdding(Model model) {
		model.addAttribute("addOffer", new Offer());
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "offers/offersAdd";
	}

	@PostMapping("/add")
	public String addOffer(Model model, Offer addOffer) {
		addOffer.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Buyer b = addOffer.getBuyer();
		b.addOffer(addOffer);
		buyerService.saveBuyer(b);
		return "offers/offersHome";
	}

	@GetMapping("/edit/{id}")
	public String getOfferEditing(@PathVariable("id") Long id, Model model) {
		Offer o = oService.findOfferById(id);
		model.addAttribute("editOffer", o);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "offers/offersEdit";
	}

	@PostMapping("/edit/{id}")
	public String editOffer(@PathVariable("id") Long id, Offer editOffer) {
		oService.updateOffer(id, editOffer);
		return "offers/offersHome";
	}

	@GetMapping("/reject/{id}")
	public String getOfferRejecting(@PathVariable("id") Long id, Model model) {
		Offer o = oService.findOfferById(id);
		model.addAttribute("editOffer", o);
		model.addAttribute("buyers", buyerService.findAllBuyers());
		return "offers/offersRejected";
	}

	@PostMapping("/reject/{id}")
	public String rejectOffer(@PathVariable("id") Long id, Offer editOffer) {
		Offer o = oService.findOfferById(id);
		o.setDescription(editOffer.getDescription());
		oService.saveOffer(o);
		return "offers/offersHome";
	}

	@PostMapping("/delete/{id}")
	public String deleteOffer(@PathVariable("id") Long id, Model model) {
		Offer o = oService.findOfferById(id);
		Buyer b = buyerService.findBuyerById(o.getBuyer().getId());
		b.removeOffer(o);
		buyerService.saveBuyer(b);
		model.addAttribute("offers", oService.findAllOffers());
		return "redirect:/offers";
	}

	// TODO napravi kada se actually napravi contract da se offer accepta, a ne kada
	// se krene prema kreaciji contract-a
	@PostMapping("/contract/{id}")
	public String makeContractFromOffer(@PathVariable("id") Long id, Model model) {
		Offer o = oService.findOfferById(id);
		Contract c = new Contract();
		c.setCurrentAmount(0F);
		c.setAgreedAmount(o.getBrutoPrice() - (o.getBrutoPrice() * o.getDiscount() / 100));
		ContractDTO editContract = new ContractDTO();
		editContract.setContract(c);
		editContract.setBuyer(o.getBuyer());
		o.setAccepted(true);
		oService.saveOffer(o);
		model.addAttribute("addContract", editContract);
		model.addAttribute("buyers", o.getBuyer());
		return "contracts/contractsAdd";
	}
}
