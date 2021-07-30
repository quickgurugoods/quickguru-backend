package com.quickguru.model;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

import com.quickguru.util.QuickGuruUtil;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
public class QuestionFile {
	
	public QuestionFile(MultipartFile file) throws IOException {
		this.name = file.getOriginalFilename();
		this.type = file.getContentType();
		this.size = QuickGuruUtil.fileFormatSize(file.getSize());
		//this.duration = QuickGuruUtil.fileDuration(file);
		this.data = file.getBytes();
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.updatedOn = new Timestamp(System.currentTimeMillis());
	}
	
	public QuestionFile() { }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	private String size;
	private String duration;
	
	@Lob
	private byte[] data;
	
	private Timestamp createdOn;
	private Timestamp updatedOn;
}

