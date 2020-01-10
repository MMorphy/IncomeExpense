package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.petkovic.incomeexpense.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByToUser_Username(String username);

	@Query("SELECT t "
		 + "FROM Task t "
		 + "WHERE t.dueDate = CURRENT_DATE "
		 + "AND t.finished = ?1")
	List<Task> findByTodaysDateAndFinished(boolean finished);

	@Query("SELECT t "
		+  "FROM Task t "
		+  "WHERE t.dueDate = CURRENT_DATE "
		+  "AND t.toUser.username = ?1 "
		+  "AND t.finished = ?2")
	List<Task> findByTodaysDateAndToUsername(String username, boolean finished);

	@Query("SELECT t "
			 + "FROM Task t "
			 + "WHERE t.dueDate < CURRENT_DATE "
			 + "AND t.finished = false")
	List<Task> findOverdue();
	@Query("SELECT t "
			 + "FROM Task t "
			 + "WHERE t.dueDate < CURRENT_DATE "
			 + "AND t.finished = false "
			 + "AND t.toUser.username = ?1")
	List<Task> findOverdueForToUsername(String username);

	List<Task> findByFinished(boolean finished);
}
