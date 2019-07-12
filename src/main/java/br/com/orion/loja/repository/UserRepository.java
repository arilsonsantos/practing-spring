package br.com.orion.loja.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.orion.loja.entity.User;


/**
 * UserRepository
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(@Param("username") String username);

    @Query("select u from User u join fetch u.roles where u.username = :username")
    User findByUsernameFetchRole(@Param("username") String username);
    
}