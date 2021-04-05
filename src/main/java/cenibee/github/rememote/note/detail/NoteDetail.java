package cenibee.github.rememote.note.detail;

import cenibee.github.rememote.note.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "category"})
@ToString(of = {"category", "detail"})
public class NoteDetail {

    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private String detail;

    @JsonIgnore
    @ManyToOne
    private Note note;

}
