package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.service.TransactionTypeService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

	@Autowired
	private UserService userService;
	@Autowired
	private TransactionTypeService typeService;

	public AdministrationController(UserService userService, TransactionTypeService typeService) {
		this.userService = userService;
		this.typeService = typeService;
	}
}
