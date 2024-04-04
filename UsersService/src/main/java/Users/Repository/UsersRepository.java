package Users.Repository;


import Users.Dto.UserDto;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<UserDto, String> {
    Optional<UserDto> findByLogin(String login);
}
