package com.lpmas.sfhn.portal.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.FileKit;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.dao.FileInfoDao;

public class FileInfoBusiness {
	public int addFileInfo(FileInfoBean bean) {
		FileInfoDao dao = new FileInfoDao();
		return dao.insertFileInfo(bean);
	}

	public int updateFileInfo(FileInfoBean bean) {
		FileInfoDao dao = new FileInfoDao();
		return dao.updateFileInfo(bean);
	}

	public FileInfoBean getFileInfoByKey(int fileId) {
		FileInfoDao dao = new FileInfoDao();
		return dao.getFileInfoByKey(fileId);
	}

	public PageResultBean<FileInfoBean> getFileInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		FileInfoDao dao = new FileInfoDao();
		return dao.getFileInfoPageListByMap(condMap, pageBean);
	}

	public List<FileInfoBean> getFileInfoListByMap(HashMap<String, String> condMap) {
		FileInfoDao dao = new FileInfoDao();
		return dao.getFileInfoListByMap(condMap);
	}

	public void deleteFile(FileInfoBean fileInfoBean) throws IOException {
		// 获取需要删除文件的路径
		String existsFilePath = FileInfoConfig.ADMIN_FILE_PATH + fileInfoBean.getFilePath();
		// 生成备份文件路径文件夹
		String backupPath = DateKit
				.getCurrentDateTime("yyyy" + Constants.FILE_SEPARATOR + "MM" + Constants.FILE_SEPARATOR + "dd");
		String backupFloder = FileInfoConfig.ADMIN_BACKUP_FILE_PATH + backupPath;
		FileKit.createDir(backupFloder);
		// 备份及删除文件
		FileKit.copyFile(existsFilePath, backupFloder + Constants.FILE_SEPARATOR +  fileInfoBean.getFileName() + "."
				+ fileInfoBean.getFileFormat());
		FileKit.deleteFile(existsFilePath);
	}

}
