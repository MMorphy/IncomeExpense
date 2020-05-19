package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.MonthlyHoursWorked;
import hr.petkovic.incomeexpense.repository.MonthlyHoursWorkedRepository;

@Service
public class HoursService {

	@Autowired
	private MonthlyHoursWorkedRepository wRepo;

	public MonthlyHoursWorked findHoursById(Long id) {
		try {
			return wRepo.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<MonthlyHoursWorked> findAllHours() {
		return wRepo.findAll();
	}

	public List<MonthlyHoursWorked> findAllHoursForEmployeeId(Long id) {
		return wRepo.findAllByEmployee_Id(id);
	}

	public MonthlyHoursWorked saveHours(MonthlyHoursWorked hours) {
		return wRepo.save(hours);
	}

	public MonthlyHoursWorked updateHours(Long id, MonthlyHoursWorked hours) {
		Optional<MonthlyHoursWorked> optHours = wRepo.findById(id);
		if (optHours.isPresent()) {
			MonthlyHoursWorked h = optHours.get();
			h.setActualHours(hours.getActualHours());
			h.setYearAndMonth(hours.getYearAndMonth());
			if (!h.getEmployee().getId().equals(hours.getEmployee().getId())) {
				hours.getEmployee().addHours(h);
			}
			return saveHours(h);
		} else {
			return saveHours(hours);
		}
	}

	public void deleteHours(MonthlyHoursWorked hours) {
		wRepo.delete(hours);
	}

	public void deleteHoursById(Long id) {
		wRepo.deleteById(id);
	}
}
