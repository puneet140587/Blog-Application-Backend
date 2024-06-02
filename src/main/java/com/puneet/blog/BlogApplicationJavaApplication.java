package com.puneet.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.puneet.blog.config.AppConstant;
import com.puneet.blog.entites.Role;
import com.puneet.blog.repository.RoleRepository;

@SpringBootApplication
public class BlogApplicationJavaApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	@Autowired
	private RoleRepository roleRepository ;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationJavaApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("Eleei5#n"));
		
		Role role1 = new Role() ;
		role1.setId(AppConstant.ROLE_ADMIN);
		role1.setName("ROLE_ADMIN");
		
		Role role2 = new Role() ;
		role2.setId(AppConstant.ROLE_USER);
		role2.setName("ROLE_NORMAL");
		
		List<Role> roles = List.of(role1,role2) ;
		
		List<Role> result = roleRepository.saveAll(roles);
		
		try {
			result.forEach(r -> {System.out.println(r.getName());
				});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
