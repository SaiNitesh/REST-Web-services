package com.webservices.rest.FileUpload.UsingHTMLFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

//http://localhost:8080/RESTfulWS/api/UserService/users
//http://localhost:8080/RESTfulWS/HTML/UploadFile.html
@Path("/file")
public class UploadFileService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "h://Eclipse workspace/Upload/"
				+ fileDetail.getFileName()+ fileDetail.getType();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			// new output stream created, file will be saved in this locations
			// File newfile= new File(uploadedFileLocation);
			//OutputStream out = new FileOutputStream(newfile);-- newfile is object
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] buffer = new byte[1024];

		
			//out = new FileOutputStream(new File(uploadedFileLocation));
			
			// uploadedInputStream.read(buffer)  read stream data into buffer
			// and returns the total number of bytes read into the buffer
			while ((read = uploadedInputStream.read(buffer)) != -1) {
				
				//buffer- Array of characters
				//read- Number of characters to write
				// Writes a portion of an array of characters
				out.write(buffer, 0, read);
			}
			//Flushes the stream
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
