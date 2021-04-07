package cenibee.github.rememote.note;

import cenibee.github.rememote.note.dto.NoteKeywordsOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n")
    Collection<NoteKeywordsOnly> findAllKeywords();

}
