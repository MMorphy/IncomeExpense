package hr.petkovic.incomeexpense.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.DTO.BankDTO;
import hr.petkovic.incomeexpense.DTO.TimeAggDTO;

@Service
public class StatisticsService {

	public StatisticsService() {
	}

	public List<TimeAggDTO> getDifferencesAndSort(List<TimeAggDTO> dtos, boolean isYear) {

		if (isYear) {
			Collections.sort(dtos, new YearComparator());
		} else {
			Collections.sort(dtos, new YearMonthComparator());
		}
		for (int i = 0; i < dtos.size(); i++) {
			if (i == 0) {
				dtos.get(i).setDifference(0D);
				continue;
			}
			dtos.get(i).setDifference(dtos.get(i).getSum() - dtos.get(i - 1).getSum());
		}
		return dtos;
	}

	public List<BankDTO> getSumsYear(List<TimeAggDTO> incomes, List<TimeAggDTO> expenses) {
		List<BankDTO> dtos = new ArrayList<>();
		Integer firstYear = Math.min(incomes.get(0).getYear(), expenses.get(0).getYear());
		Integer lastYear = Math.max(incomes.get(incomes.size() - 1).getYear(),
				expenses.get(expenses.size() - 1).getYear());
		for (Integer i = firstYear; i <= lastYear; i++) {
			dtos.add(makeDTOForList(i, 1));
		}
		Double d = null;
		for (int x = 0; x < dtos.size(); x++) {
			if (x == 0) {
				d = 0d;
			}
			for (TimeAggDTO income : incomes) {
				if (income.getYear().equals(dtos.get(x).getYear())) {
					d += income.getSum();
					dtos.get(x).setSum(d);
				}
			}
			for (TimeAggDTO expense : expenses) {
				if (expense.getYear().equals(dtos.get(x).getYear())) {
					d -= expense.getSum();
					dtos.get(x).setSum(d);
				}
			}
		}
		for (Integer z = 0; z < dtos.size(); z++) {
			if (dtos.get(z).getSum().equals(new Double(0))) {
				if (z == 0) {
					continue;
				} else {
					dtos.get(z).setSum(dtos.get(z - 1).getSum());
				}
			}
		}
		return dtos;
	}

	// TODO napravi validaciju ako je jedna od listi prazna
	public List<BankDTO> getSumsYearMonth(List<TimeAggDTO> incomes, List<TimeAggDTO> expenses) {
		List<BankDTO> dtos = new ArrayList<>();
		Integer firstYear = Math.min(incomes.get(0).getYear(), expenses.get(0).getYear());
		Integer lastYear = Math.max(incomes.get(incomes.size() - 1).getYear(),
				expenses.get(expenses.size() - 1).getYear());
		Integer firstMonth = Math.min(incomes.get(0).getMonth(), expenses.get(0).getMonth());
		Integer lastMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		for (Integer i = firstYear; i <= lastYear; i++) {
			// First year months
			if (i.equals(firstYear)) {
				for (Integer j = firstMonth; j <= 12; j++) {
					dtos.add(makeDTOForList(i, j));
				}
			}
			// Middle years months
			else if (!i.equals(lastYear)) {
				for (Integer j = 1; j <= 12; j++) {
					dtos.add(makeDTOForList(i, j));
				}
			}
			// Last Year months
			else {
				for (Integer j = 1; j <= lastMonth; j++) {
					dtos.add(makeDTOForList(i, j));
				}

			}
		}
		Double d = null;
		for (int x = 0; x < dtos.size(); x++) {
			if (x == 0) {
				d = 0d;
			}
			for (TimeAggDTO income : incomes) {
				if (income.getYear().equals(dtos.get(x).getYear())
						&& income.getMonth().equals(dtos.get(x).getMonth())) {
					d += income.getSum();
					dtos.get(x).setSum(d);
				}
			}
			for (TimeAggDTO expense : expenses) {
				if (expense.getYear().equals(dtos.get(x).getYear())
						&& expense.getMonth().equals(dtos.get(x).getMonth())) {
					d -= expense.getSum();
					dtos.get(x).setSum(d);
				}
			}
		}
		for (Integer z = 0; z < dtos.size(); z++) {
			if (dtos.get(z).getSum().equals(new Double(0))) {
				if (z == 0) {
					continue;
				} else {
					dtos.get(z).setSum(dtos.get(z - 1).getSum());
				}
			}
		}
		return dtos;
	}

	private BankDTO makeDTOForList(Integer year, Integer month) {
		return new BankDTO(year, month, 0D);
	}

	public class YearMonthComparator implements Comparator<TimeAggDTO> {

		@Override
		public int compare(TimeAggDTO o1, TimeAggDTO o2) {
			int sComp = o1.getYear().compareTo(o2.getYear());
			if (sComp != 0) {
				return sComp;
			}
			return o1.getMonth().compareTo(o2.getMonth());
		}
	}

	public class YearComparator implements Comparator<TimeAggDTO> {

		@Override
		public int compare(TimeAggDTO o1, TimeAggDTO o2) {
			return o1.getYear().compareTo(o2.getYear());
		}
	}
}
