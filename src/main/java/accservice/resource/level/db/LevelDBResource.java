package accservice.resource.level.db;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;

@Component
public class LevelDBResource {

	@Autowired
	private DB db;

	public void putKeyValue(String key, String value) throws IOException {
		try (WriteBatch batch = db.createWriteBatch()) {
			batch.put(bytes(String.valueOf(key)), bytes(value));
			db.write(batch);
			batch.close();
		}
	}

	public void deleteByKey(String key) throws IOException {
		try (WriteBatch batch = db.createWriteBatch()) {
			batch.delete(bytes(String.valueOf(key)));
			db.write(batch);
			batch.close();
		}
	}

	public String getByKey(String key) throws IOException {
		return asString(db.get(bytes(String.valueOf(key))));
	}

	@PreDestroy
	private void closeDb() throws IOException {
		db.close();
	}

}
