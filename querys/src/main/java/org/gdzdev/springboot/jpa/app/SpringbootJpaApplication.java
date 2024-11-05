package org.gdzdev.springboot.jpa.app;

import org.gdzdev.springboot.jpa.app.entities.Person;
import org.gdzdev.springboot.jpa.app.repositories.PersonRepository;
import org.gdzdev.springboot.jpa.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//findAll();
		//findBy();
		//createEntity();
		//updateEntity();
		//deleteById();
		//deleteByEntity();
		//distinct();
		//count();
		//upperCaseAndLowerCase();
		//between();
		//orderBy();
		//minAndMax();
		//subQuery();
		whereIn();
	}

	@Transactional(readOnly = true)
	public void whereIn() {
		System.out.println("Consulta con WHERE IN");
		userRepository.getUsernameByIds(Arrays.asList(2L, 5L, 4L)).forEach(System.out::println);
		System.out.println("Consulta con IN");
		userRepository.findByIdIn(Arrays.asList(1L, 5L)).forEach(System.out::println);
		System.out.println("Consulta con NOT IN");
		userRepository.findByIdNotIn(Arrays.asList(1L, 5L)).forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void subQuery() {
		System.out.println("Consulta con el nombre mas corto y su largo");
		List<Object[]> register = userRepository.getMinSizeUsername();
		register.forEach(data -> {
			String username = (String) data[0];
			Integer length = (Integer) data[1];
			System.out.println("Username: " + username + ", Largo: " + length);
		});
	}

	@Transactional(readOnly = true)
	public void minAndMax() {
		System.out.println("userRepository.getMinId() = " + userRepository.getMinId());
		System.out.println("userRepository.getMaxId() = " + userRepository.getMaxId());
		System.out.println("userRepository.getMaxLengthUsername() = " + userRepository.getMaxLengthUsername());
		System.out.println("userRepository.getMinLengthUsername() = " + userRepository.getMinLengthUsername());
	}

	@Transactional(readOnly = true)
	public void orderBy() {
		System.out.println("\nOrdenar por");
		userRepository.findByOrderByUsernameDesc().forEach(System.out::println);
		userRepository.findAllByOrderByUsernameDesc().forEach(System.out::println);
		System.out.println("\nOrdenar pero con between");
		userRepository.findByIdBetweenOrderByIdDesc(2L, 5L).forEach(System.out::println);
		System.out.println("\nOrdenar por username (desc) y email (asc) por un rango (between)");
		userRepository.findByUsernameBetweenOrderByUsernameDescEmailAsc("C", "K").forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void between() {
		//El between es para un rango pero de cualquier dato
		System.out.println("\nRango entre ids 2 y 5");
		userRepository.getBetweenUserById().forEach(System.out::println);
		System.out.println("\nRango entre letras de los username");
		userRepository.getBetweenUserByUsername("A", "C").forEach(System.out::println);
		System.out.println("\nRango pero sin Query");
		userRepository.findByUsernameBetween("D", "K").forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void upperCaseAndLowerCase() {
		System.out.println("\nUPPERCASE");
		userRepository.getUpperUsername().forEach(System.out::println);
		System.out.println("\nlowercase");
		userRepository.getLowerUsername().forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void count() {
		System.out.println("Contar cantidad de usuarios");
		Long cantQuery = userRepository.findCountUsers();
		System.out.println("Cantidad: " + cantQuery);
		long cantSpring = userRepository.count();
		System.out.println("Cantidad: " + cantSpring);
	}

	@Transactional(readOnly = true)
	public void distinct() {
		System.out.println("\nTodos los Username");
		userRepository.findAllUser().forEach(System.out::println);

		System.out.println("\nUsernames Unicos");
		userRepository.findAllUsernameDistinct().forEach(System.out::println);
		userRepository.findDistinctByUsername("Deus").forEach(System.out::println);

	}

	@Transactional
	public void deleteByEntity() {
		Scanner sc = new Scanner(System.in);

		repository.findAll().forEach(System.out::println);
		System.out.print("\nId para eliminar: ");
		Long id = sc.nextLong();
		Optional<Person> personOptional = repository.findById(id);

		personOptional.ifPresentOrElse(repository::delete,
				() -> System.out.println("No existe!!"));

		System.out.println("\nLista actualizada");
		repository.findAll().forEach(System.out::println);
	}

	@Transactional
	public void deleteById() {
		Scanner sc = new Scanner(System.in);

		repository.findAll().forEach(System.out::println);
		System.out.print("\nId para eliminar: ");
		Long id = sc.nextLong();
		repository.deleteById(id);
		System.out.println("\nLista actualizada");
		repository.findAll().forEach(System.out::println);

	}

	@Transactional
	public void updateEntity() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Id: ");
		Long id = sc.nextLong();

		Optional<Person> personOptional = repository.findById(id);

		personOptional.ifPresent(person -> {
			System.out.println(person);
			System.out.print("Nuevo lenguaje?!: ");
			String programmingLanguage = sc.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person update = repository.save(person);
			System.out.println("Actualizado: " + update);
		});

		sc.close();

	}

	@Transactional
	public void createEntity() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Nombre: ");
		String firstName = sc.nextLine();
		System.out.print("Apellido: ");
		String lastName = sc.nextLine();
		System.out.print("Lenguaje Favorito: ");
		String programmingLanguage = sc.nextLine();
		sc.close();

		Person person = Person.builder()
				.lastName(lastName)
				.firstName(firstName)
				.programmingLanguage(programmingLanguage)
				.build();

		Person newPerson = repository.save(person);
		System.out.println(newPerson);
		repository.findById(newPerson.getId()).ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void findBy() {

		Optional<Person> person = repository.findById(4L);
		if (person.isPresent()) {
			System.out.println("\nEncontrado!!: " + person + "\n");
		} else {
			System.out.println("\nNo encontrado!!\n");
		}

		//Personalizado y con programacion funcional
		repository.buscarUnId(4L).ifPresent(System.out::println);
		System.out.println("========================================");
		repository.buscarLikeApellido("st").ifPresent(System.out::println);

		System.out.println("\nBuscar la persona por su lenguaje de programacion");
		repository.findByProgrammingLanguageContaining("y").ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void findAll() {

		//Buscar todos
		List<Person> persons = (List<Person>) repository.findAll();
		System.out.println("\nLista de personas");
		persons.forEach(System.out::println);

		// Buscar por un campo
		List<Person> people = repository.findByProgrammingLanguage("java");
		System.out.println("\nLista de personas con Java");
		people.forEach(System.out::println);


		// Buscar por dos campos
		//List<Person> nameAndProgramming = repository.buscarPorLenguajeDeProgramacion("C++", "black");
		List<Person> nameAndProgramming = repository.findByProgrammingLanguageAndFirstName("C++", "black");
		System.out.println("\nNombre y lenguaje de programacion");
		nameAndProgramming.forEach(System.out::println);


		// Buscar por campos
		List<Object[]> personData = repository.buscarDatosDePersona();
		System.out.println("\nBuscar todos");
		personData.forEach(data -> {
			System.out.println(data[0] + " le gusta " + data[1]);
		});
	}

}
