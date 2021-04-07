package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.note.dto.NoteDto;
import cenibee.github.rememote.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "keyword"})
@ToString(exclude = "id")
public class Note implements NoteDto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String keyword;

    @OneToMany(mappedBy = "note", cascade = {CascadeType.PERSIST})
    private List<NoteDetail> details;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Tag> tags;

    public void addDetail(NoteDetail newDetail) {
        this.checkDetails();
        this.details.add(newDetail);
    }

    public void addDetails(Collection<NoteDetail> details) {
        this.checkDetails();
        this.details.addAll(details);
    }

    public void addTag(Tag tag) {
        this.checkTags();
        this.tags.add(tag);
    }

    public void addTagAll(Collection<Tag> tags) {
        this.checkTags();
        this.tags.addAll(tags);
    }

    private void checkDetails() {
        if (this.details == null) this.details = new LinkedList<>();
    }

    private void checkTags() {
        if (this.tags == null) this.tags = new HashSet<>();
    }

}
