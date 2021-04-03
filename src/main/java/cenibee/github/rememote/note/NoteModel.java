package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetailModel;
import cenibee.github.rememote.tag.Tag;
import lombok.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteModel extends RepresentationModel<NoteModel> {

    private String keyword;
    private List<NoteDetailModel> details;
    private Set<Tag> tags;

}
