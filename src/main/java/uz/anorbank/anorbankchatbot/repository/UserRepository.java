package uz.anorbank.anorbankchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbankchatbot.domain.User;

import java.util.Optional;

/**
 * @author Sahobiddin Abbosaliyev
 * 12/8/2021
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByChatId(Long chatId);
}
