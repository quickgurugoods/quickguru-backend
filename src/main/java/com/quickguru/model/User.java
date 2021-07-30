package com.quickguru.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class User {

	public enum Role {
		ADMIN("ADMIN"), EXPERT("EXPERT"), CUSTOMER("CUSTOMER");
	   
	    private final String code;

	    private Role(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String mobile;
	private String email;
	
	private String firstName;
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private Timestamp createdOn;
    private Timestamp lastLoggedOn;
    
    private String jobTitle;
    private String knowledge;
    private String verifier;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_tag", 
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="tag_id")
    )
    private Set<Tag> tags;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_language",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="language_id")
    )
    private Set<Language> languages;
	
}
