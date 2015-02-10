package com.jtech.persys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtils {
	public static String readFile(File file) throws Exception {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
