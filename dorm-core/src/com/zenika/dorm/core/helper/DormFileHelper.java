package com.zenika.dorm.core.helper;

import java.io.File;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

public class DormFileHelper {

	public static String getExtensionFromFilename(String filename) {

		try {
			return filename.trim().substring(filename.lastIndexOf(".")).toLowerCase().substring(1);
		} catch (Exception e) {
			throw new CoreException("Cannot create DormFile, maybe extension is missing");
		}
	}

	public static <T extends MetadataExtension> DormFile createDormFile(DormMetadata<T> metadata, File file, String extension) {

		DormFile dormFile = new DormFile();
		dormFile.setExtension(extension);
		dormFile.setFile(file);
		dormFile.setName(metadata.getName() + ":" + metadata.getVersion() + ":" + metadata.getOrigin());

		return dormFile;
	}
}
