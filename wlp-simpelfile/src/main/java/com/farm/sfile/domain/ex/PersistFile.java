package com.farm.sfile.domain.ex;

import java.io.File;

 
public class PersistFile {
	private File file;
	private String name;
	private String secret;

	public PersistFile(File file, String name, String secret) {
		this.file = file;
		this.name = name;
		this.secret = secret;
	}

	public PersistFile() {
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
