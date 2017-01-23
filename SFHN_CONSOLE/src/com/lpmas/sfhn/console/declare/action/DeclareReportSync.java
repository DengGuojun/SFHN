package com.lpmas.sfhn.console.declare.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.console.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.console.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.console.declare.handler.DeclareReportHandler;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;

@WebServlet("/sfhn/DeclareReportSync.do")
public class DeclareReportSync extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(DeclareReportSync.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareReportSync() {
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
		DeclareReportBusiness business = new DeclareReportBusiness();
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		List<DeclareInfoBean> list = declareInfoBusiness.getDeclareInfoAllList();
		int result = 0;
		try {
			for (DeclareInfoBean declareInfoBean : list) {
				// 从Mongo中获取相应的数据
				DeclareReportBean declareReportBean = business
						.getDeclareReportByKey(String.valueOf(declareInfoBean.getDeclareId()));
				if (declareReportBean == null) {
					DeclareReportHandler handler = new DeclareReportHandler();
					handler.createDeclareReport(declareInfoBean);
					result = Constants.STATUS_VALID;
					declareReportBean = business.getDeclareReportByKey(String.valueOf(declareInfoBean.getDeclareId()));
				}
				TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness
						.getTrainingClassUserByDeclare(declareInfoBean.getDeclareId());
				if (trainingClassUserBean != null) {
					List<Integer> classInfoList = new ArrayList<Integer>();
					classInfoList.add(trainingClassUserBean.getClassId());
					declareReportBean.setTrainingClassInfoList(classInfoList);
					business.updateDeclareReport(declareReportBean);
					result = Constants.STATUS_VALID;
				}
			}

		} catch (Exception e) {
			log.error("总表记录创建失败", e);
		} finally {
		}
		if (result > 0) {
			HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/DeclareReportList.do");
		} else {
			HttpResponseKit.alertMessage(response, "已全部更新", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}
}
