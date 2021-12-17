package uz.anorbank.anorbankchatbot.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Sahobiddin Abbosaliyev
 * 12/8/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private Long chatId;
    private String name;
    private String phoneNumber;
    private String username;
    private String language;

}
