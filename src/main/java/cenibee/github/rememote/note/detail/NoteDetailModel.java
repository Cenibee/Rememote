package cenibee.github.rememote.note.detail;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDetailModel extends RepresentationModel<NoteDetailModel> {

    private String category;
    private String detail;

}
