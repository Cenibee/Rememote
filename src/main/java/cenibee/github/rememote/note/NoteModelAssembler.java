package cenibee.github.rememote.note;

import cenibee.github.rememote.common.BaseModelAssembler;
import cenibee.github.rememote.note.detail.NoteDetailModelAssembler;
import cenibee.github.rememote.tag.TagModelAssembler;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class NoteModelAssembler implements BaseModelAssembler<Note, NoteModel> {

    private final NoteDetailModelAssembler detailAssembler;
    private final TagModelAssembler tagAssembler;

    public NoteModelAssembler(NoteDetailModelAssembler detailAssembler, TagModelAssembler tagAssembler) {
        this.detailAssembler = detailAssembler;
        this.tagAssembler = tagAssembler;
    }

    @Override
    public NoteModel assemble(Note note) {
        return NoteModel.builder()
                .keyword(note.getKeyword())
                .details(note.getDetails().stream()
                        .map(detailAssembler::toModel)
                        .collect(Collectors.toList()))
                .tags(note.getTags().stream()
                        .map(tagAssembler::toModel)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Collection<Link> links(Note note) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteController.class).slash(note.getId()).withSelfRel());

        return links;
    }

    @Override
    public Collection<Link> links(Iterable<? extends Note> entities) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteController.class).withSelfRel());

        return links;
    }
}
