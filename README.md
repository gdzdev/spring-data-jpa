# 🐱‍💻 Spring Data JPA & Hibernate Practice Project

## 📋 Descripción
Este proyecto se centra en los conceptos básicos de Spring Data JPA y Hibernate para gestionar bases de datos relacionales en aplicaciones Java. Aquí podrás practicar el mapeo de entidades, la creación de repositorios y la ejecución de consultas.

## 🧰 Tecnologías utilizadas 
- `Java` 🟦
- `Spring Boot` 🌱
- `Spring Data JPA` 📜
- `Hibernate` 🐘
- `Maven` 📦

## ❓ ¿Qué es Spring Data JPA y Hibernate?
- Hibernate: Es un ORM (Object-Relational Mapping) que convierte objetos de Java en tablas de bases de datos y viceversa, facilitando el trabajo con datos.
- Spring Data JPA: Es una extensión de Spring que usa JPA para simplificar el acceso y manejo de datos, trabajando muy bien con Hibernate.

## 🌟 ¿Por qué se combinan tan bien?
- Automatización: Spring Data JPA usa Hibernate para que no necesites escribir tanto código SQL.
- Consultas personalizadas: Spring Data JPA permite hacer consultas usando `@Query`, mientras que Hibernate gestiona la conexión con la base de datos.
- Flexibilidad: Hibernate maneja el mapeo de objetos, y Spring Data JPA facilita el CRUD.

## 🔍 ¿Cómo diferenciar las anotaciones de cada uno?
### Anotaciones de JPA (y compatibles con Hibernate):

- `@Entity`, `@Table`, `@Id`, `@GeneratedValue`: Para definir entidades y su estructura.
- `@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany`: Para relaciones entre entidades.
- `@Embedded`, `@Embeddable`: Para tipos de datos compuestos.

### Anotaciones específicas de Spring Data JPA:

- `@Query`: Define consultas personalizadas.
- `@Transactional`: Gestiona las transacciones.

## 📚 Aprendizajes clave

- Anotaciones de mapeo de entidades (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, etc.).
- Anotaciones avanzadas para tipos compuestos y eventos de persistencia (`@Embeddable`, `@PrePersist`, `@PreUpdate`).
- Uso de repositorios de Spring Data JPA (JpaRepository y CrudRepository).
- Creación de métodos de consulta personalizados mediante anotaciones como `@Query`.

## 📚 Recursos y documentación adicional
Para profundizar en los conceptos de Spring Data JPA y Hibernate, aquí tienes algunos enlaces útiles:

* [Documentación oficial de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa.html)...
* [Documentación oficial de Hibernate](https://hibernate.org/)...
