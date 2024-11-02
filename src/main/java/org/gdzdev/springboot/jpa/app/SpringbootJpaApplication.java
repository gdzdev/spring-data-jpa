package org.gdzdev.springboot.jpa.app;

import org.gdzdev.springboot.jpa.app.entities.Person;
import org.gdzdev.springboot.jpa.app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		updateEntity();
	}

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
