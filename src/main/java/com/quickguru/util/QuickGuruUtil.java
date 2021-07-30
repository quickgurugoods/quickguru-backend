package com.quickguru.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.web.multipart.MultipartFile;

public class QuickGuruUtil {
	
	private QuickGuruUtil() { }

	public static String fileFormatSize(long v) {
	    if (v < 1024) return v + " B";
	    int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
	    return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
	}
	
	public static String fileDuration(MultipartFile multipart) throws IOException, UnsupportedAudioFileException {
		File file = multipartToFile(multipart);
		
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		AudioFormat format = audioInputStream.getFormat();
		long frames = audioInputStream.getFrameLength();
		double durationInSeconds = (frames+0.0) / format.getFrameRate(); 
		return String.format("%02d:%02d", (durationInSeconds % 3600) / 60, durationInSeconds % 60);
	}
	
	private static File multipartToFile(MultipartFile multipart) throws IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+ File.separator +multipart.getOriginalFilename());
	    multipart.transferTo(convFile);
	    return convFile;
	}
}
