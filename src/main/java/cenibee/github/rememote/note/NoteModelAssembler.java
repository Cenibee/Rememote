package cenibee.github.rememote.note;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, NoteModel> {

    @NonNull
    @Override
    public NoteModel toModel(@NonNull Note note) {
        return assemble(note)
                .add(links(note));

    }

    private NoteModel assemble(Note note) {
        return NoteModel.builder()
                .keyword(note.getKeyword())
                .details(note.getDetails())
                .tags(note.getTags())
                .build();
    }

    private Collection<Link> links(Note note) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteController.class).slash(note.getId()).withSelfRel());

        return links;
    }

}
