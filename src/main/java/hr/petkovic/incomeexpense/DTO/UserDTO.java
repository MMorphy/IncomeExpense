package hr.petkovic.incomeexpense.DTO;

import hr.petkovic.incomeexpense.entity.User;

public class UserDTO {

	private User user;

	private boolean isAdmin;

	public UserDTO() {
		this.user = new User();
	}

	public UserDTO(User user, boolean isAdmin) {
		super();
		this.user = user;
		this.isAdmin = isAdmin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
