package cenibee.github.rememote.note;

import cenibee.github.rememote.common.BaseModelAssembler;
import cenibee.github.rememote.note.detail.NoteDetailModelAssembler;
import cenibee.github.rememote.note.dto.NoteDto;
import cenibee.github.rememote.tag.TagModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class NoteModelAssembler implements BaseModelAssembler<NoteDto, NoteModel> {

    private final NoteDetailModelAssembler detailAssembler;
    private final TagModelAssembler tagAssembler;
    private final ModelMapper mapper;

    public NoteModelAssembler(NoteDetailModelAssembler detailAssembler, TagModelAssembler tagAssembler, ModelMapper mapper) {
        this.detailAssembler = detailAssembler;
        this.tagAssembler = tagAssembler;
        this.mapper = mapper;
    }

    @Override
    public NoteModel assemble(NoteDto noteDto) {
        Note note = new Note();
        this.mapper.map(noteDto, note);

        return NoteModel.builder()
                .keyword(note.getKeyword())
                .details(Optional.ofNullable(note.getDetails())
                        .map(noteDetails -> noteDetails.stream()
                                .map(detailAssembler::toModel)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .tags(Optional.ofNullable(note.getTags())
                        .map(tags -> tags.stream()
                                .map(tagAssembler::toModel)
                                .collect(Collectors.toSet()))
                        .orElse(null))

                .build();
    }

    @Override
    public Collection<Link> links(NoteDto noteDto) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteController.class).slash(noteDto.getId()).withSelfRel());

        return links;
    }

    @Override
    public Collection<Link> links(Iterable<? extends NoteDto> entities) {

        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteController.class).withSelfRel());

        return links;
    }
}
