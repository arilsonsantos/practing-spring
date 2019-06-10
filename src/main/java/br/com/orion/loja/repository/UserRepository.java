package br.com.orion.loja.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.orion.loja.entity.User;


/**
 * UserRepository
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u join fetch u.roles where u.username = :username")
    User findByUsernameFetchRole(String username);
    
}