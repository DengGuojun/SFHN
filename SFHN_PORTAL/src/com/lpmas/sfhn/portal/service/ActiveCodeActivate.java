package com.lpmas.sfhn.portal.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.portal.business.ActiveCodeInfoBusiness;
import com.lpmas.sfhn.portal.service.config.ActiveCodeActivateResponseConfig;

/**
 * Servlet implementation class ActiveCodeActivate
 */
@WebServlet("/sfhn/admin/ActiveCodeActivate.action")
public class ActiveCodeActivate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ActiveCodeActivate.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActiveCodeActivate() {
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
		String activeCode = ParamKit.getParameter(request, "activeCode", "").trim();
		logger.debug("尝试激活激活码:" + activeCode);
		ReturnMessageBean messageBean = new ReturnMessageBean();
		// 查询是否存在这个激活码
		ActiveCodeInfoBusiness business = new ActiveCodeInfoBusiness();
		ActiveCodeInfoBean bean = business.getActiveCodeInfoByActiveCode(activeCode);
		if (bean == null || bean.getStatus() == Constants.STATUS_NOT_VALID) {
			logger.debug("激活码:" + activeCode + "不存在");
			messageBean.setCode(ActiveCodeActivateResponseConfig.RESPONSE_CODE_NOT_FOUND);
			messageBean.setMessage("激活码不存在");
			HttpResponseKit.printJson(request, response, messageBean, "");
			return;
		}
		if (bean.getUsageStatus().equals(ActiveCodeInfoConfig.USAGE_STATUS_UNUSE)) {
			logger.debug("激活码:" + activeCode + "未被使用");
			messageBean.setCode(ActiveCodeActivateResponseConfig.RESPONSE_CODE_UNUSE);
			messageBean.setMessage("激活码未被使用");
			HttpResponseKit.printJson(request, response, messageBean, "");
			return;
		}

		int result = -1;
		// 更新状态
		bean.setUsageStatus(ActiveCodeInfoConfig.USAGE_STATUS_ACTIVATED);
		result = business.updateActiveCodeInfo(bean);
		if (result > 0) {
			logger.debug("激活码:" + activeCode + "成功激活");
			messageBean.setCode(ActiveCodeActivateResponseConfig.RESPONSE_CODE_SUCCESS);
			messageBean.setMessage("操作成功");
		} else {
			messageBean.setCode(ActiveCodeActivateResponseConfig.RESPONSE_CODE_FAIL);
			messageBean.setMessage("操作失败");
		}
		HttpResponseKit.printJson(request, response, messageBean, "");
		return;
	}

}
