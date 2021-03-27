package cenibee.github.rememote.tag;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = "name")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

}
