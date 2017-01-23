package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;

@WebServlet("/sfhn/admin/TrainingOrganizationJsonList.do")
public class TrainingOrganizationJsonList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingOrganizationJsonList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		String queryProvince = ParamKit.getParameter(request, "queryProvince", "").trim();
		if (StringKit.isValid(queryProvince)) {
			condMap.put("province", queryProvince);
		}
		String queryCity = ParamKit.getParameter(request, "queryCity", "").trim();
		if (StringKit.isValid(queryCity)) {
			condMap.put("city", queryCity);
		}
		String queryRegion = ParamKit.getParameter(request, "queryRegion", "").trim();
		if (StringKit.isValid(queryRegion)) {
			condMap.put("region", queryRegion);
		}
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		List<TrainingOrganizationInfoBean> list = trainingOrganizationInfoBusiness
				.getTrainingOrganizationInfoListByMap(condMap);

		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, List<TrainingOrganizationInfoBean>> map = new HashMap<String, List<TrainingOrganizationInfoBean>>();
		map.put("result", list);
		String result = JsonKit.toJson(map);
		writer.write(result);
		writer.flush();
		writer.close();

	}

}
