package it.polito.applied.ToMi.pojo;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4906939939316484105L;
	private String role;
    
    public Role(){}
    
    public Role(String role){
    	this.role=role;
    }

    public String getAuthority() {
        return role;
    }
    
    public void setRole(String role){
    	this.role=role;
    }
       
}