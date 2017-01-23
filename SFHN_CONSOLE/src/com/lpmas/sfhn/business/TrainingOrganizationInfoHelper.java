package com.lpmas.sfhn.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrainingOrganizationInfoHelper {
	// 培育年份
	public List<Integer> getTrainingYearList() {
		List<Integer> trainingYearList = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance();
		// 获得当前时间的年份
		int year = calendar.get(Calendar.YEAR);
		for (int i = year; i >= 2013; i--) {
			trainingYearList.add(i);
		}
		return trainingYearList;
	}
}
