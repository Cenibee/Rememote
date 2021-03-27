package cenibee.github.rememote.note;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        Optional<Note> noteOptional = this.noteRepository.findById(id);

        if (noteOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok(getModel(noteOptional.get()));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        note = this.noteRepository.save(note);
        return ResponseEntity
                .created(linkTo(NoteController.class).slash(note.getId()).toUri())
                .body(getModel(note));
    }

    private EntityModel<Note> getModel(Note note) {
        return EntityModel.of(note,
                linkTo(methodOn(NoteController.class).getNote(note.getId())).withSelfRel());
    }
}
