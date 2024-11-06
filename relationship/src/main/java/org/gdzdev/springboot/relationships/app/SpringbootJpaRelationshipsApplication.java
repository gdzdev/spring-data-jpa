package org.gdzdev.springboot.relationships.app;

import org.gdzdev.springboot.relationships.app.entities.*;
import org.gdzdev.springboot.relationships.app.repositories.CustomerDetailsRepository;
import org.gdzdev.springboot.relationships.app.repositories.CustomerRepository;
import org.gdzdev.springboot.relationships.app.repositories.InvoiceRepository;
import org.gdzdev.springboot.relationships.app.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpringbootJpaRelationshipsApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//manyToOne();
		//manyToOneFindBy();
		//oneToMany();
		//oneToManyFindBy();
		//removeAddress();
		//removeAddressFindBy();
		//oneToManyBidireccional();
		//oneToManyBidireccionalFindById();
		//oneToOne();
		//oneToOneFindById();
		//oneToOneBidireccional();
		//manyToMany();
		manyToManyFindById();
	}

	@Transactional
	public void manyToManyBidireccional() {
		Student student1 = Student.builder().name("Daniela").country("Brasil").build();
		Student student2 = Student.builder().name("Danilo").country("EEUU").build();

		Course course1 = Course.builder().tittle("Java master").teacher("Guzman").build();
		Course course2 = Course.builder().tittle("Spring Framework").teacher("Guzman").build();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		System.out.println("studentRepository.saveAll(List.of(student1, student2)) = " + studentRepository.saveAll(List.of(student1, student2)));
	}

	@Transactional
	public void manyToManyFindById() {
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.orElseThrow();
		Student student2 = studentOptional2.orElseThrow();

		Course course1 = Course.builder().tittle("Java master").teacher("Guzman").build();
		Course course2 = Course.builder().tittle("Spring Framework").teacher("Guzman").build();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void manyToMany() {
		Student student1 = Student.builder().name("Daniela").country("Brasil").build();
		Student student2 = Student.builder().name("Danilo").country("EEUU").build();

		Course course1 = Course.builder().tittle("Java master").teacher("Guzman").build();
		Course course2 = Course.builder().tittle("Spring Framework").teacher("Guzman").build();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		System.out.println("studentRepository.saveAll(List.of(student1, student2)) = " + studentRepository.saveAll(List.of(student1, student2)));
	}

	@Transactional
	public void oneToOneBidireccional() {
		Customer customer = Customer.builder()
				.firstName("Clark")
				.lastName("Pura").build();

		CustomerDetails customerDetails = CustomerDetails.builder()
				.premium(true)
				.points(100).build();

		customerDetails.setCustomer(customer);
		customer.setCustomerDetails(customerDetails);

		System.out.println("customerRepository.save(customer) = " + customerRepository.save(customer));
	}

	@Transactional
	public void oneToOneFindById() {
		Optional<Customer> customerOptional = customerRepository.findById(1L);

		customerOptional.ifPresent(customer -> {
			CustomerDetails customerDetails = CustomerDetails.builder()
					.premium(true)
					.points(100).build();

			customerDetails.setCustomer(customer);

			System.out.println("customerDetailsRepository.save(customerDetails) = " + customerDetailsRepository.save(customerDetails));
			System.out.println(customerDetails.getCustomer().getFirstName());
		});
	}

	@Transactional
	public void oneToOne() {
		Customer customer = Customer.builder()
				.firstName("Clark")
				.lastName("Pura").build();
		customerRepository.save(customer);

		CustomerDetails customerDetails = CustomerDetails.builder()
				.premium(true)
				.points(100).build();
		customerDetails.setCustomer(customer);

		System.out.println("customerDetailsRepository.save(customerDetails) = " + customerDetailsRepository.save(customerDetails));
	}

	@Transactional
	public void oneToManyBidireccionalFindById() {
		Optional<Customer> customerOptional = customerRepository.findByIdCustomerInvoices(1L);

		customerOptional.ifPresent(customer -> {
			Invoice invoice1 = Invoice.builder()
					.description("Compras de laptops")
					.total(new BigDecimal("5000.47")).build();

			Invoice invoice2 = Invoice.builder()
					.description("Compras de Aguas")
					.total(new BigDecimal("999.00")).build();

			//oneToMany
			customer.setInvoices(Arrays.asList(invoice1, invoice2));
			//ManyToOne
			invoice1.setCustomer(customer);
			invoice2.setCustomer(customer);

			customerRepository.save(customer);

			System.out.println("\nId :" + customer.getId() + ", Nombre: " + customer.getFirstName() + ", Apellido: " + customer.getLastName());
			customer.getInvoices().forEach(invoice ->
					System.out.println("Description: " + invoice.getDescription() +
							", Total: " + invoice.getTotal() +
							", Nombre del Cliente: " + invoice.getCustomer().getFirstName())
			);
		});
	}

	@Transactional
	public void oneToManyBidireccional() {
		Customer customer = Customer.builder()
				.firstName("Frank")
				.lastName("Castle").build();

		Invoice invoice1 = Invoice.builder()
				.description("Compras de laptops")
				.total(new BigDecimal("5000.47")).build();

		Invoice invoice2 = Invoice.builder()
				.description("Compras de Aguas")
				.total(new BigDecimal("999.00")).build();

		//oneToMany
		customer.setInvoices(Arrays.asList(invoice1, invoice2));
		//ManyToOne
		invoice1.setCustomer(customer);
		invoice2.setCustomer(customer);

		customerRepository.save(customer);

		System.out.println("\nId :" + customer.getId() + ", Nombre: " + customer.getFirstName() + ", Apellido: " + customer.getLastName());

		customer.getInvoices().forEach(invoice ->
			System.out.println("Description: " + invoice.getDescription() +
					", Total: " + invoice.getTotal() +
					", Nombre del Cliente: " + invoice.getCustomer().getFirstName())
		);
	}
	@Transactional
	public void removeAddressFindBy() {
		Optional<Customer> customerOptional = customerRepository.findById(2L);

		customerOptional.ifPresent(customer -> {
			Address address1 = Address.builder()
					.street("Rue des Brooklyn")
					.number(3368).build();
			Address address2 = Address.builder()
					.street("Manhattan")
					.number(4465).build();

			customer.setAddresses(Arrays.asList(address1, address2));
			System.out.println("\ncustomerRepository.save(customer) = " + customerRepository.save(customer) + "\n");

		});
		Address address1 = Address.builder()
				.street("Rue des Brooklyn")
				.number(3368).build();

		Optional<Customer> customerOp = customerRepository.findByIdCustomer(2L);
		customerOp.ifPresent(custom -> {
			custom.getAddresses().remove(address1);
			System.out.println("\ncustomerRepository.save(custom) = " + customerRepository.save(custom) + "\n");
		});
	}

	@Transactional
	public void removeAddress() {
		Customer customer = Customer.builder()
				.firstName("Frank")
				.lastName("Castle").build();

		Address address1 = Address.builder()
				.street("Rue des Brooklyn")
				.number(3368).build();
		Address address2 = Address.builder()
				.street("Manhattan")
				.number(4465).build();

		customer.setAddresses(Arrays.asList(address1, address2));
		System.out.println("\ncustomerRepository.save(customer) = " + customerRepository.save(customer) + "\n");

		Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

		customerOptional.ifPresent(custom -> {
			custom.getAddresses().remove(address1);
			System.out.println("\ncustomerRepository.save(custom) = " + customerRepository.save(custom) + "\n");

		});
	}

	@Transactional
	public void oneToManyFindBy() {
		Optional<Customer> customerOptional = customerRepository.findById(2L);

		customerOptional.ifPresent(customer -> {
			Address address1 = Address.builder()
					.street("Rue des Brooklyn")
					.number(3368).build();
			Address address2 = Address.builder()
					.street("Manhattan")
					.number(4465).build();

			customer.setAddresses(Arrays.asList(address1, address2));
			System.out.println("\ncustomerRepository.save(customer) = " + customerRepository.save(customer) + "\n");

		});

	}

	@Transactional
	public void oneToMany() {
		Customer customer = Customer.builder()
				.firstName("Frank")
				.lastName("Castle").build();

		Address address1 = Address.builder()
				.street("Rue des Brooklyn")
				.number(3368).build();
		Address address2 = Address.builder()
				.street("Manhattan")
				.number(4465).build();

		customer.setAddresses(Arrays.asList(address1, address2));
		System.out.println("\ncustomerRepository.save(customer) = " + customerRepository.save(customer) + "\n");

	}

	@Transactional
	public void manyToOneFindBy() {
		Optional<Customer> customerOptional = customerRepository.findById(1L);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();

			Invoice invoice = Invoice.builder()
					.description("Compras de oficina")
					.total(new BigDecimal("643.99"))
					.customer(customer).build();
			System.out.println("\nFactura : save(invoice) = " + invoiceRepository.save(invoice) + "\n");
		}

	}

	@Transactional
	public void manyToOne() {
		Customer customer = Customer.builder()
				.firstName("John")
				.lastName("Doe").build();
		customerRepository.save(customer);

		Invoice invoice = Invoice.builder()
				.description("Compras de oficina")
				.total(new BigDecimal("643.99"))
				.customer(customer).build();

		System.out.println("\nFactura : save(invoice) = " + invoiceRepository.save(invoice) + "\n");
	}

}
