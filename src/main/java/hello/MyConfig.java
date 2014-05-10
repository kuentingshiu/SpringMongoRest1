package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MyConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "mydb";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1");
	}
	
	
}
