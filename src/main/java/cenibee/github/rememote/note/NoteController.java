package cenibee.github.rememote.note;

import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository noteRepository;

    private final NoteModelAssembler assembler;

    public NoteController(NoteRepository noteRepository, NoteModelAssembler assembler) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        return this.noteRepository.findById(id)
                .map(note -> ResponseEntity.ok(assembler.toModel(note)))
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/list")
    public ResponseEntity<?> noteList() {
        return ResponseEntity.ok(assembler.toCollectionModel(this.noteRepository.findAllKeywords()));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        if (this.noteRepository.exists(Example.of(note))) {
            throw new EntityExistsException();
        }

        note = this.noteRepository.save(note);
        return ResponseEntity
                .created(linkTo(NoteController.class).slash(note.getId()).toUri())
                .body(assembler.toModel(note));
    }

}
