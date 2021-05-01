package com.morlock.caliban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.morlock.caliban.repositories.MutantRepository;


@SpringBootApplication(scanBasePackages = "com.morlock.caliban")
public class CalibanApplication implements CommandLineRunner{
	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(CalibanApplication.class, args);
	}
	   @Override
	    public void run(String...args) throws Exception {
		   MutantRepository class2 = context.getBean(MutantRepository.class);
	     //   System.out.println("Class1:" + class2.getClass1());
	     //   System.exit(1);
	    }

}
