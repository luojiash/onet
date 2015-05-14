package com.onet.common.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onet.common.constant.CommConst;
import com.onet.common.constant.CookieKey;
import com.onet.common.dto.FileUploadResp;
import com.onet.common.util.CookieUtil;

@Controller
public class FileUploadController {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static Logger logger = Logger.getLogger(FileUploadController.class);
	
	@RequestMapping(value="/file/upload")
	@ResponseBody
	public FileUploadResp fileUpload(HttpServletRequest request, HttpServletResponse response) {
		FileUploadResp resp = new FileUploadResp();
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
				    FileItem item = iter.next();
				    if (!item.isFormField()) {
				    	String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
				        String fileName = dateFormat.format(new Date())+"_"+item.getName();
				        String file_path = CommConst.UPLOAD_DIRECTORY + "/" + userId + "/" + item.getContentType();
				        // 创建目录
				        File dir = new File(CommConst.FILESYSTEM_PATH + file_path);
				        if (!dir.exists()) {
				        	dir.mkdirs();
						}
				        file_path += "/" + fileName; 
				        File uploadedFile = new File(CommConst.FILESYSTEM_PATH + file_path);
				        item.write(uploadedFile);
				        resp.setSuccess(true);
				        resp.setFile_path(CommConst.STATIC_DOMAIN + file_path);
				        break;
				    }
				}
			}
		} catch (Exception e) {
			logger.error("fileUpload error", e);
			resp.setSuccess(false);
		}
		return resp;
	}
}
