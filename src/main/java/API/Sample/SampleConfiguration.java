package API.Sample;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;

public class SampleConfiguration extends Configuration{

	 @Valid
	 @NotNull
	 @JsonProperty("database")
	 private DataSourceFactory dataSource = new DataSourceFactory();

	public PooledDataSourceFactory getDataSource() {
		return dataSource;
	}
	 
	 
}
