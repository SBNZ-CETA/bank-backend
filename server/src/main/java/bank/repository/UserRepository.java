package bank.repository;

import org.springframework.stereotype.Repository;

import facts.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findUserByUsername (String username);

    Optional<User> findUserByEmail (String email);
}
