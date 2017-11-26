package accservice.config;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

@Configuration
public class LevelDBConfig {

	@Value("${leveldb.filename}")
	private String filename;

	@Value("${leveldb.cache.size.mb}")
	private String size;

	@Bean
	public Options options(){
		Options options = new Options();
		options.createIfMissing(true);
		options.cacheSize(Integer.parseInt(size) * 1048576);
		return options;
	}

	@Bean
	public DB db(@Autowired Options options) throws IOException {
		return factory.open(new File(filename), options);
	}

}
