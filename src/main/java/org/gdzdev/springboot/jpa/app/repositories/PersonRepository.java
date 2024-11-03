package org.gdzdev.springboot.jpa.app.repositories;

import org.gdzdev.springboot.jpa.app.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("select p.firstName from Person p where p.id=?1")
    String obtenerNombrePorId(Long id);

    @Query("select concat(p.firstName, ' ', p.lastName) from Person p where p.id=?1" )
    String obtenerTodoElNombre(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> buscarUnId(Long id);

    @Query("select p from Person p where p.firstName=?1")
    Optional<Person> buscarUnNombre(String firstName);

    @Query("select p from Person p where p.lastName like %?1%")
    Optional<Person> buscarLikeApellido(String lastName);

    Optional<Person> findByProgrammingLanguageContaining(String programmingLanguage);

    //Buscar todos
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.firstName=?2")
    List<Person> buscarPorLenguajeDeProgramacion(String programmingLanguage, String firstName);

    List<Person> findByProgrammingLanguageAndFirstName(String programmingLanguage, String firstName);

    @Query("select p.firstName, p.programmingLanguage from Person p")
    List<Object[]> buscarDatosDePersona();
}