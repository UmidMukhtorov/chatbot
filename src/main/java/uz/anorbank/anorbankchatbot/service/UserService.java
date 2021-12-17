package uz.anorbank.anorbankchatbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.anorbank.anorbankchatbot.domain.User;
import uz.anorbank.anorbankchatbot.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sahobiddin Abbosaliyev
 * 12/8/2021
 */
@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(Update update) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> users = userRepository.findByChatId(chatId);
        User user = new User();
        if (users.isEmpty()) {
            user.setChatId(chatId);
            user.setUsername(update.getMessage().getFrom().getUserName());
            user.setName(update.getMessage().getFrom().getFirstName());
            userRepository.save(user);
        }

    }

    public void saveUserLanguage(Update update, String lang) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> users = userRepository.findByChatId(chatId);

        if (users.isPresent()) {
            User user = users.get();
            user.setLanguage(lang);
            userRepository.save(user);
        }
    }

    public String editUserPhone(Update update, String phone){
        Long chatId = update.getMessage().getChatId();

        Optional<User> user = userRepository.findByChatId(chatId);

        if (user.isPresent()){
            user.get().setPhoneNumber(phone);
            userRepository.save(user.get());
            return "success";
        } else return "error";
    }

    public Optional<User> findUserByChatId(Update update){
        return userRepository.findByChatId(update.getMessage().getChatId());
    }


//    public static List<String> getuser(Update update){
//        Long chatId=update.getMessage().getChatId();
//        User user = new User();
//        user.setChatId(chatId);
//        user.setUsername(update.getMessage().getFrom().getUserName());
//        return (List<String>) user;
//    }
}
