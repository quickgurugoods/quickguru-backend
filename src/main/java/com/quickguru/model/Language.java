package com.quickguru.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
public class Language {
	
	@Id
    @GeneratedValue
	private Long id;
	private String name;
}
