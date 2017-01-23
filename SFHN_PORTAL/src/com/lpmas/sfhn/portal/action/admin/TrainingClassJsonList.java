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
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

@WebServlet("/sfhn/admin/TrainingClassJsonList.do")
public class TrainingClassJsonList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassJsonList() {
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
		condMap.put("classStatusSelection", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
		String organizationId = ParamKit.getParameter(request, "organizationId", "").trim();
		if (StringKit.isValid(organizationId)) {
			condMap.put("organizationId", organizationId);
		}
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		List<TrainingClassInfoBean> list = trainingClassInfoBusiness.getTrainingClassInfoListByMap(condMap);

		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, List<TrainingClassInfoBean>> map = new HashMap<String, List<TrainingClassInfoBean>>();
		map.put("result", list);
		String result = JsonKit.toJson(map);
		writer.write(result);
		writer.flush();
		writer.close();

	}

}
