package cenibee.github.rememote.account;

import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return null;
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .build();
    }

}
