package com.mallu;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mallu.config.AppConstatns;
import com.mallu.model.Role;
import com.mallu.repository.RoleRepository;

@SpringBootApplication
public class SpringBoot21BlogApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot21BlogApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println("Encoded pass: "+this.passwordEncoder.encode("1234"));
		
		try {
			Role role1 = new Role();
			role1.setId(AppConstatns.ADMIN_USER);
			role1.setName("ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstatns.NORMAL_USER);
			role2.setName("USER");
			
			List <Role> roles= List.of(role1,role2);
			
			List <Role> saveRoles = this.roleRepo.saveAll(roles);
			
			saveRoles.forEach(role -> {
				System.out.println(role.getName());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
