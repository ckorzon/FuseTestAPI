package org.mycompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Component
public class RestRouter extends RouteBuilder {

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		}

	@Override
	public void configure() {
		restConfiguration()
			.host("")
			.scheme("http")
			.apiProperty("base.path", "camel")
			.component("servlet");
			//.bindingMode(RestBindingMode.json);
		rest("api/sample")
			.get().route().setBody(constant(this.getSampleEmployee()));

		rest("api/employees").get().outType(List.class).route().to("direct:select");

		from("direct:select")
			.setBody(constant("SELECT * FROM public.\"Employees\"")).to("jdbc:dataSource")
			.process(getSelectProcessor());
	}

	private Employee getSampleEmployee() {
		Employee johnSmith = new Employee();
		johnSmith.SetFirstName("John");
		johnSmith.SetLastName("Smith");
		johnSmith.SetDepartment("Information Technology");
		johnSmith.SetEmail("john.smith@sample.com");
		johnSmith.SetEmployeeId("e124232");
		return johnSmith;
	}

	private Processor getSelectProcessor() {
		Processor selectProcessor = new Processor() {
			public void process(Exchange xchg) throws Exception {
				doSelectProcess(xchg);
			}
		};
		return selectProcessor;
	}

	private void doSelectProcess(Exchange xchg) {
		//the camel jdbc select query has been executed. We get the list of employees.
		ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn()
		.getBody();
		List<Employee> employees = new ArrayList<Employee>();
		System.out.println(dataList);
		for (Map<String, String> data : dataList) {
			Employee employee = new Employee();

			employee.SetEmployeeId(data.get("EmployeeId"));
			employee.SetFirstName(data.get("FirstName"));
			employee.SetLastName(data.get("LastName"));
			employee.SetEmail(data.get("Email"));
			employee.SetDepartment(data.get("Department"));
			//employee.SetBirthday(newBirthday);

			employees.add(employee);
		}
		xchg.getIn().setBody(employees);
	}
}
