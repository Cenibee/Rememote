package cenibee.github.rememote.note;

import cenibee.github.rememote.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "keyword"})
@ToString(exclude = "id")
public class Note {

    @Id
    @GeneratedValue
    Long id;

    @Column(unique = true)
    String keyword;

    @OneToMany(mappedBy = "note")
    List<NoteDetail> details;

    @ManyToMany
    Set<Tag> tags;

}
