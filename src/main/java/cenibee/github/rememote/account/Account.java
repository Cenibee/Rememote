package cenibee.github.rememote.account;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"ssoKey", "email"})
@ToString(of = "email")
public class Account {

    @Id
    private String ssoKey;

    @Enumerated
    private SsoProvider ssoProvider;

    @Email
    private String email;

    private LocalDateTime createdAt;

}
