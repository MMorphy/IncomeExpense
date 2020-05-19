package hr.petkovic.incomeexpense.controller;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.UserDTO;
import hr.petkovic.incomeexpense.entity.Role;
import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.RoleService;
import hr.petkovic.incomeexpense.service.TransactionTypeService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

	@Autowired
	private UserService userService;
	@Autowired
	private TransactionTypeService typeService;
	@Autowired
	private RoleService rolesService;

	public AdministrationController(UserService userService, TransactionTypeService typeService,
			RoleService rolesService) {
		this.userService = userService;
		this.typeService = typeService;
		this.rolesService = rolesService;
	}

	@GetMapping("/home")
	public String home() {
		return "administration/adminHome";
	}

	@GetMapping("/user")
	public String getUserAdmin(Model model) {
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "administration/users";
	}

	@GetMapping("/user/add")
	public String getUserAdding(Model model) {
		model.addAttribute("addUser", new UserDTO());
		return "administration/usersAdd";
	}

	@PostMapping("/user/add")
	public String getAddUser(Model model, UserDTO addUser) {
		User u = addUser.getUser();
		u.setCreatedAt(new Date());
		if (addUser.getIsAdmin()) {
			u.setRoles(rolesService.findAllRoles());
		} else {
			u.setRoles(Arrays.asList(rolesService.findUserRole()));
		}
		userService.saveUser(u);
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "redirect:/admin/user/";
	}

	@GetMapping("/user/edit/{id}")
	public String getUserEditing(@PathVariable("id") Long id, Model model) {
		UserDTO uDto = new UserDTO();
		User u = userService.findUserById(id);
		uDto.setUser(u);
		if (u == null) {
			return "redirect:/admin/user/";
		}
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				uDto.setIsAdmin(true);
			}
		}
		model.addAttribute("editUser", uDto);
		return "administration/usersEdit";
	}

	@PostMapping("/user/edit/{id}")
	public String EditUser(@PathVariable("id") Long id, Model model, UserDTO editUser) {
		User u = editUser.getUser();
		User toUpdate = userService.findUserById(id);
		toUpdate.setUsername(u.getUsername());
		toUpdate.setPassword(u.getPassword());
		if (editUser.getIsAdmin() && !toUpdate.isAdmin()) {
			toUpdate.addRole(rolesService.findAdminRole());
		} else if (!editUser.getIsAdmin() && toUpdate.isAdmin()) {
			toUpdate.removeRole(rolesService.findAdminRole());
		}
		userService.updateUser(id, toUpdate);
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "redirect:/admin/user/";
	}

	@PostMapping("/user/disable/{id}")
	public String deteleBuyer(@PathVariable("id") Long id, Model model) {
		userService.disableUser(id);
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "redirect:/admin/user/";
	}

	@GetMapping("/type")
	public String getTypeAdmin(Model model) {
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "administration/types";
	}

	@GetMapping("/type/add")
	public String getTypeAdding(Model model) {
		model.addAttribute("addType", new TransactionType());
		return "administration/typesAdd";
	}

	@PostMapping("/type/add")
	public String saveType(Model model, TransactionType addType) {
		typeService.saveTransactionType(addType);
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "redirect:/admin/type";
	}

	@GetMapping("/type/edit/{id}")
	public String getTypeEditing(@PathVariable("id") Long id, Model model) {
		TransactionType t = typeService.findTransactionTypeById(id);
		model.addAttribute("editType", t);
		return "administration/typesEdit";
	}

	@PostMapping("/type/edit/{id}")
	public String EditType(@PathVariable("id") Long id, Model model, TransactionType editType) {
		typeService.updateTransactionType(id, editType);
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "redirect:/admin/type/";
	}

	// TODO popravi da se izbrisu sve transakcije tog tipa
	@PostMapping("/type/delete/{id}")
	public String deteleType(@PathVariable("id") Long id, Model model) {
		typeService.deleteTransactionTypeById(id);
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "redirect:/admin/type/";
	}
}
