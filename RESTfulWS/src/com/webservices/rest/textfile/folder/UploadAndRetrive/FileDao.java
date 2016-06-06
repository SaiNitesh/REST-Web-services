package com.webservices.rest.textfile.folder.UploadAndRetrive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class FileDao {

	private static final String Text_Type = ".txt";
	private static final String Doc_Type = ".doc";

	public List<FileUser> saveToFolder(InputStream uploadedInputStream,
			String uploadedFileLocation, FormDataContentDisposition fileDetail) {
		List<FileUser> returnValue = null;
		OutputStream out = null;
		try {
			String fileName = fileDetail.getFileName().toLowerCase();

			// new output stream created, file will be saved in this locations
			// File newfile= new File(uploadedFileLocation);
			// OutputStream out = new FileOutputStream(newfile);-- newfile is
			// object
			out = new FileOutputStream(new File(uploadedFileLocation));

			/*
			 * above statement just creates empty file in new
			 * location(uploadedFileLocation)
			 */

			int read = 0;
			byte[] buffer = new byte[1024];

			if (fileName.endsWith(Text_Type)) {

				/*
				 * here IF code, transfers content from file in source location
				 * to destination location
				 */

				// uploadedInputStream.read(buffer) read stream data into buffer
				// and returns the total number of bytes read into the buffer
				while ((read = uploadedInputStream.read(buffer)) != -1) {

					// buffer- Array of characters
					// read- Number of characters to write
					// Writes a portion of an array of characters
					out.write(buffer, 0, read);

				}

				List<FileUser> data = retrieveFromFile(uploadedFileLocation);

				returnValue = data;

			} else if (fileName.endsWith(Doc_Type)) {

			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				// Flushes the stream
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return returnValue;
	}

	private List<FileUser> retrieveFromFile(String uploadedFileLocation) {
		List<FileUser> data = null;
		List<FileUser> userData = null;

		try {
			InputStream uploadedInputStream = new FileInputStream(
					uploadedFileLocation);
			InputStreamReader inputReader = new InputStreamReader(
					uploadedInputStream);
			BufferedReader br = new BufferedReader(inputReader);

			/*
			 * for (String str : ar) {
			 * 
			 * System.out.println(str); //Example 1 }
			 */

			userData = retrieveAndAddToList(br);

			// List<String> list = new ArrayList<String>(Arrays.asList(ar));
			// List<FileUser> userData = new ArrayList<FileUser>();

			// System.out.println(list); // Example 2

			/*
			 * StringBuilder builder = new StringBuilder();
			 * 
			 * for (String str : list) {
			 * 
			 * System.out.println(str); //Example 3 builder.append(str);
			 * builder.append(" ");
			 * 
			 * 
			 * } // Remove last delimiter with setLength.
			 * 
			 * builder.setLength(builder.length() - 1);
			 */

			// System.out.println(builder.toString()); //Example 4

			// JSONObject jsonObject = new JSONObject(string);

			// System.out.println("hey"+string); //Example 5
			data = userData;
			br.close();// very very important to close this object else it lead to garbage collection error
		}

		catch (Exception e) {
			e.printStackTrace();

		}
		return data;
	}

	private List<FileUser> retrieveAndAddToList(BufferedReader br) {

		List<FileUser> userData = new ArrayList<FileUser>();
		String string = null;
		String line;
		try {
			while ((line = br.readLine()) != null) {

				String[] ar = line.split(",");

				String id = ar[0];
				String name = ar[1];
				String profession = ar[2];

				System.out.println(id+" "+name+" "+profession);
				FileUser user = new FileUser(Integer.parseInt(id), name,
						profession);
				
				userData.add(user);
				/*while (br.ready()) {

					FileUser user = new FileUser(Integer.parseInt(id), name,
							profession);
					userData.add(user);
				}*/

			}
			System.out.println(userData);
			string += line + "\n";
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return userData;
	}
}
