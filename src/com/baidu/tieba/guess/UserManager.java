package com.baidu.tieba.guess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

	List<String> allSignedUser = new ArrayList<String>();

	@SuppressWarnings("resource")
	public void loadAllUserForm(String userListFilePath) throws IOException{
		if(userListFilePath == null || userListFilePath.trim().length() == 0){
			userListFilePath = "userList.txt";
		}
		File file = new File(userListFilePath);
		BufferedReader  fr = new BufferedReader (new InputStreamReader(new FileInputStream(file)));
		for(String line = fr.readLine(); line != null; line = fr.readLine() ){
			if(line.trim().length() > 0 )
				allSignedUser.add(line);
		}
	}
	
	
}
