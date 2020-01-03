package hr.petkovic.incomeexpense.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

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
