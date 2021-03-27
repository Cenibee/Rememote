package cenibee.github.rememote.note;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> tempGet(@PathVariable Long id) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        note = this.noteRepository.save(note);
        return ResponseEntity
                .created(linkTo(NoteController.class).slash(note.getId()).toUri())
                .body(EntityModel.of(note,
                        linkTo(methodOn(NoteController.class).tempGet(note.getId())).withSelfRel()));
    }

}
