package in.bushansirgur.springbootmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SpringbootmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootmongodbApplication.class, args);
	}

}
