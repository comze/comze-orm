# Quick Start

### Adding dependency
```xml
<dependency>
    <groupId>net.comze</groupId>
    <artifactId>comze-orm</artifactId>
    <version>3.3.1</version>
</dependency>
```

### Sample Code

```java
import net.comze.framework.orm.support.SqlMapDaoSupport;

public class SampleDao extends SqlMapDaoSupport {
	
	private static final String SAMPLE_SQL_SELECT = "select `id`, `username` from `sample` where `id` > ? limit 1;";
	
	public SampleEntity select(long id) {
		return getSqlMapTemplate().queryForObject(SAMPLE_SQL_SELECT, SampleEntity.class, id);
	}
	
}
```

```java
import net.comze.framework.annotation.Attribute;

public class SampleEntity {

	private Long id;

	@Attribute(name = "username")
	private String name;
	
	// ... Aetter and setter

}
```

```java
import javax.sql.DataSource;

import net.comze.framework.orm.datasource.DbcpDataSourceFactory;

public class Sample {

	public void sample(long id) {
		DataSource dataSource = new DbcpDataSourceFactory().getDataSource();  // load jdbc.properties
		SampleDao sampleDao = new SampleDao();
		sampleDao.setDataSource(dataSource);

		SampleEntity sampleEntity = sampleDao.select(id);
		// ...
	}

}
```

Use `@PropertiesEditor` annotation

```java
import net.comze.framework.annotation.Attribute;
import net.comze.framework.annotation.PropertyEditor;

public class SampleEntity {

	private Long id;

	@Attribute(name = "username")
	private String name;
	
	@PropertyEditor(className = "sample.JsonAddressEditor")
	private Address address;
	
	// ... Aetter and setter

}
```

```java
public class Address {

	private String state;

	private String city;

	private String street;

	// ... Getter and setter

}
```

```java
import java.beans.PropertyEditorSupport;

import com.google.gson.Gson;

public class JsonAddressEditor extends PropertyEditorSupport {

	@Override
	public Object getValue() {
		return new Gson().fromJson(getAsText(), Address.class);
	}

}
```