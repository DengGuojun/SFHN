package com.lpmas.sfhn.portal.declare.action;

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

import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.portal.declare.business.JobInfoBusiness;

/**
 * Servlet implementation class JobInfoJsonList
 */
@WebServlet("/sfhn/JobInfoJsonList.do")
public class JobInfoJsonList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobInfoJsonList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		int typeId = ParamKit.getIntParameter(request, "typeId", 0);
		JobInfoBusiness business = new JobInfoBusiness();
		List<JobInfoBean> list = business.getJobInfoListByTypeId(typeId);
		if (list.size() > 0) {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			Map<String, List<JobInfoBean>> map = new HashMap<String, List<JobInfoBean>>();
			map.put("result", list);
			String result = JsonKit.toJson(map);
			writer.write(result);
			writer.flush();
			writer.close();
		}
	}

}
