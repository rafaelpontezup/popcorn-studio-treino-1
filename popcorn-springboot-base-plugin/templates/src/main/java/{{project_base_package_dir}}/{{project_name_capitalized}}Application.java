package {{project_base_package}};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class {{project_name_capitalized}}Application {

	public static void main(String[] args) {
		SpringApplication.run({{project_name_capitalized}}Application.class, args);
	}

}