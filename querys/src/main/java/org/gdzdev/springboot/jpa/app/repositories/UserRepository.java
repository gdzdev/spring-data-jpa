package org.gdzdev.springboot.jpa.app.repositories;

import org.gdzdev.springboot.jpa.app.entities.User;
import org.gdzdev.springboot.jpa.app.entities.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.id in ?1")
    List<User> getUsernameByIds(List<Long> ids);

    List<User> findByIdIn(List<Long> ids);

    List<User> findByIdNotIn(List<Long> ids);

    @Query("select u.username, length(u.username) from User u where length(u.username)=(select min(length(u.username)) from User u)")
    List<Object[]> getMinSizeUsername();

    @Query("select u from User u where u.id=(select min(u.id) from User u)")
    Optional<User> getLastUser();

    @Query("select new org.gdzdev.springboot.jpa.app.entities.UserDto(u.username, u.email) from User u")
    List<UserDto> findAllUserDto();

    @Query("select new User(u.username, u.email) from User u")
    List<User> findAllUser();

    @Query("select distinct(u.username) from User u")
    List<String> findAllUsernameDistinct();

    List<User> findDistinctByUsername(String username);

    @Query("select count(*) from User u")
    Long findCountUsers();

    //@Query("select concat(u.username, ' ', u.email) from User u")
    @Query("select u.username || ' ' || u.email from User u")
    List<String> getUsernameAndEmail();

    @Query("select lower(u.username) from User u")
    List<String> getLowerUsername();

    @Query("select upper(u.username) from User u")
    List<String> getUpperUsername();

    @Query("select u from User u where u.id between 2 and 4")
    List<User> getBetweenUserById();

    @Query("select u from User u where u.username between 'C' and 'K'")
    List<User> getBetweenUserByUsername();

    //@Query("select u from User u where u.username between ?1 and ?2 order by u.username asc")
    @Query("select u from User u where u.username between ?1 and ?2 order by u.username, u.email desc")
    List<User> getBetweenUserByUsername(String c1, String c2);

    List<User> findByUsernameBetween(String c1, String c2);

    List<User> findByOrderByUsernameDesc();

    List<User> findAllByOrderByUsernameDesc();

    List<User> findByIdBetweenOrderByIdDesc(Long id1, Long id2);

    List<User> findByUsernameBetweenOrderByUsernameDescEmailAsc(String c1, String c2);

    @Query("select min(u.id) from User u")
    Long getMinId();

    @Query("select max(u.id) from User u")
    Long getMaxId();

    @Query("select min(length(u.username)) from User u")
    Integer getMinLengthUsername();

    @Query("select max(length(u.username)) from User u")
    Integer getMaxLengthUsername();
}
