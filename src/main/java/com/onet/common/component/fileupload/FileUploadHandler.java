package com.onet.common.component.fileupload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String UPLOAD_DIRECTORY = "D:" + File.separator + "upload";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			try {
				// Create a factory for disk-based file items
				DiskFileItemFactory factory = new DiskFileItemFactory();
	
				// Configure a repository (to ensure a secure temp location is used)
				ServletContext servletContext = this.getServletConfig().getServletContext();
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);
	
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
	
				// Parse the request
				List<FileItem> items = upload.parseRequest(req);
				
				// Process the uploaded items
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
				    FileItem item = iter.next();
				    if (item.isFormField()) {
				    } else {
				        String fileName = dateFormat.format(new Date())+"_"+item.getName();
				        File uploadedFile = new File(UPLOAD_DIRECTORY + File.separator + fileName);
				        item.write(uploadedFile);
				    }
				}
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("message", "File upload exception occured!");
			}
		}
	
		
	}

}
