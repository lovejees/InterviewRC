package API.Sample;

import org.skife.jdbi.v2.DBI;

import com.homeshope.item.EmployeeResource;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

public class SampleApplication  extends Application<SampleConfiguration>{
	
	public static void main(String args[]) throws Exception{
		new SampleApplication().run(args);
	}

	
	@Override
	public void run(SampleConfiguration configuration, Environment environment) throws Exception {
		final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSource(), "postgresql");
		environment.jersey().register(new EmployeeResource(jdbi));
		System.out.println("Hello World");
		
	}
}


