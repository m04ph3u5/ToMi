package it.polito.applied.ToMi.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.polito.applied.ToMi.pojo.Role;
import it.polito.applied.ToMi.pojo.User;
import it.polito.applied.ToMi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private final String userAppPassword="userAppPassword";
	private String userAppEncodedPassword;
	
	@PostConstruct
	private void init(){
		userAppEncodedPassword = passwordEncoder.encode(userAppPassword);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepo.findByUsername(username);
		List<Role> roles = u.getRoles();
		if(roles!=null && roles.size()==1 && roles.get(0).equals("ROLE_APPUSER"))
			u.setPassword(userAppEncodedPassword);
		return u;
	}

}
