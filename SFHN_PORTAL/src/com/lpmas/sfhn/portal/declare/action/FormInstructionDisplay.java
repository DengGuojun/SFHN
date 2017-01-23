package com.lpmas.sfhn.portal.declare.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.declare.config.FormInstructionConfig;

/**
 * Servlet implementation class FormInstructionDisplay
 */
@WebServlet("/sfhn/FormInstructionDisplay.do")
public class FormInstructionDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormInstructionDisplay() {
		super();
		// TODO Auto-generated constructor stub
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

		int declareType = ParamKit.getIntParameter(request, "declareType", 0);
		if (declareType <= 0) {
			HttpResponseKit.alertMessage(response, "申报类型必须填写", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (!DeclareInfoConfig.DECLARE_TYPE_MAP.containsKey(declareType)) {
			HttpResponseKit.alertMessage(response, "申报类型非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		request.setAttribute("Instruction",
				MapKit.getValueFromMap(declareType, FormInstructionConfig.FORM_INSTRUCTION_MAP));
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "declare/FormInstructionDisplay.jsp");
	}

}
