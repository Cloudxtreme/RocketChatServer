package ru.chikalin.kirill.rocketchat.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "rocketchat";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		final String mongoAddress = getEnv("MONGO_PORT_27017_TCP_ADDR", "192.168.99.100");
		final String mongoPort = getEnv("MONGO_PORT_27017_TCP_PORT", "27017");
		System.out.println("MONGO = " + mongoAddress);
		return new MongoClient(mongoAddress, Integer.parseInt(mongoPort));
	}

	public static String getEnv(String name, String defaultValue) {
		String env = System.getenv(name);
		return env != null ? env : defaultValue;
	}
}