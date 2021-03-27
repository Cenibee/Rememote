package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
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
    private Long id;

    @Column(unique = true)
    private String keyword;

    @OneToMany(mappedBy = "note", cascade = {CascadeType.PERSIST})
    private List<NoteDetail> details;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Tag> tags;

}
