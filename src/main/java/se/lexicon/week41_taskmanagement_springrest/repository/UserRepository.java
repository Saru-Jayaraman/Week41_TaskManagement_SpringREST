package se.lexicon.week41_taskmanagement_springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    //Update Password by Email ID
    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.email = :email")
    int updatePasswordByEmail(@Param("email") String email,@Param("password") String password);

    //Update Expired by Email ID
    @Transactional
    @Modifying
    @Query("update User u set u.expired = :status where u.email = :email")
    int updateExpiredByEmail(@Param("email") String email,@Param("status") boolean status);
}
