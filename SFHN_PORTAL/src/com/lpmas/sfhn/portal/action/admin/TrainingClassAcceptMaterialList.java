package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

@WebServlet("/sfhn/admin/TrainingClassAcceptMaterialList.do")
public class TrainingClassAcceptMaterialList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassAcceptMaterialList() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int classId = ParamKit.getIntParameter(request, "classId", 0);

		// 获取项目相关资料的附件信息
		Map<String, FileInfoBean> fileInfoMap = new HashMap<String, FileInfoBean>();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
		condMap.put("infoId1", String.valueOf(classId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		fileInfoMap = ListKit.list2Map(fileInfoList, "fileType");
		// 判断集中培训材料是否齐全
		String lackOfMaterial = "";
		int centralizedMaterialCount = 0;
		for (Integer key : FileInfoConfig.ALLOWED_FILE_TYPE_MAP.keySet()) {
			if (fileInfoMap.get(key) != null) {
				centralizedMaterialCount++;
			} else {
				lackOfMaterial += FileInfoConfig.FILE_TYPE_MAP.get(key) + "、";
			}
		}
		// 判断田间实训材料是否齐全
		int fieldMaterialCount = 0;
		/*for (int i = FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN; i <= FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PHOTO; i++) {
			if (fileInfoMap.get(i) != null) {
				fieldMaterialCount++;
			} else {
				lackOfMaterial += FileInfoConfig.FILE_TYPE_MAP.get(i) + "、";
			}
		}*/

		if (lackOfMaterial.length() > 1) {
			lackOfMaterial = lackOfMaterial.substring(0, lackOfMaterial.length() - 1);
		}

		request.setAttribute("FileInfoMap", fileInfoMap);
		request.setAttribute("CentralizedMaterialCount", centralizedMaterialCount);
		request.setAttribute("FieldMaterialCount", fieldMaterialCount);
		request.setAttribute("LackOfMaterial", lackOfMaterial);
		request.setAttribute("ClassId", classId);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassAcceptMaterialList.jsp");

	}

}
