package com.webservices.rest.textfile.folder.UploadAndRetrive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.webservices.rest.textfile.folder.UploadAndRetrive.FileDao;

@Path("/Retrieve")
public class FileRetrieveService {
	String output=null;
	FileDao userDao = new FileDao();
	
	@POST
	@Path("/TextFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public List<FileUser> Retrieve(/*@Context UriInfo uriInfo,*/
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		String uploadedFileLocation = "h://Eclipse workspace/UploadAndRetrieve/"
				+ fileDetail.getFileName();
		
		//uriInfo.getAbsolutePath();
		// save it
		List<FileUser> result = userDao.saveToFolder(uploadedInputStream, uploadedFileLocation, fileDetail);
		
if(result!=null){
		 output = "File uploaded to : " + uploadedFileLocation +"\n\n"+"Data is"+result;
}
		//return Response.status(200).entity(output).build();
return result;

		
	}



}