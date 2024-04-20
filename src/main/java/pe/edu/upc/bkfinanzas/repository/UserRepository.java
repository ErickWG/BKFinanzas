package pe.edu.upc.bkfinanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.bkfinanzas.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername (String username);
    @Query(value="SELECT * FROM userutopian u WHERE u.role='USER'", nativeQuery = true)
    List<User> findUSERS();
}
