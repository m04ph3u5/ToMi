package it.polito.applied.ToMi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.polito.applied.ToMi.pojo.User;
import it.polito.applied.ToMi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepo.findByUsername(username);
		return u;
	}

}
