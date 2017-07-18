package phone.tool.util;

import java.util.ArrayList;
import java.util.List;

import phone.tool.pojo.Employee;

public class TreeUtil {

	public static List<Employee> getShortestPath(List<Employee> list1, List<Employee> list2, Integer empId1,
			Integer empId2) {
		if (list1.size() == 0 && list2.size()!=0) {
			return list2.subList(0, list2.size() - 1);
		}

		if (list2.size() == 0 && list2.size()!=0) {
			return list1.subList(0, list1.size() - 1);
		}

		List<Employee> shortestPath = new ArrayList<Employee>();

		int indexOfEmp1InList2 = getIndex(list2, empId1);
		int indexOfEmp2InList1 = getIndex(list1, empId2);

		if (indexOfEmp1InList2 == 0 || indexOfEmp2InList1 == 0) {
			return shortestPath;
		}

		if (indexOfEmp2InList1 != -1) {
			shortestPath = list1.subList(0, indexOfEmp2InList1 - 1);
		}
		if (indexOfEmp1InList2 != -1) {
			shortestPath = list2.subList(0, indexOfEmp1InList2 - 1);
		} else {
			System.out.println("Entered loop");
			for (int i = 0; i < list1.size(); i++) {
				for (int j = 0; j < list2.size(); j++) {
					if (list1.get(i).getEmployeeId().equals(list2.get(j).getEmployeeId())) {						
						shortestPath = list1.subList(0, i);
						for (int k = j - 1; k >= 0; k--) {
							shortestPath.add(list2.get(k));
						}
						return shortestPath;
					}
				}
			}
		}
		return shortestPath;

	}

	public static int getIndex(List<Employee> list, Integer empId) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmployeeId().equals(empId)) {
				return i;
			}
		}
		return -1;
	}

}
