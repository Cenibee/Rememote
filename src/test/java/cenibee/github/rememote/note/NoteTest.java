package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.note.detail.NoteDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NoteTest {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    NoteDetailRepository noteDetailRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("NoteRepository 의존 주입 확인")
    void testNoteRepository() {
        assertThat(this.noteRepository).isNotNull();
        assertThat(this.entityManager).isNotNull();
    }

    @Test
    @DisplayName("NoteDetail 이 있는 Note 를 삽입하면 NoteDetail 이 함께 저장된다.")
    void syncNoteDetailWithNote() {
        // given: NoteDetail 이 있는 Note 와 초기 NoteDetail 개수가 주어졌을 때
        List<NoteDetail> details = IntStream.range(0, 5)
                .mapToObj(value ->
                        NoteDetail.builder()
                                .detail("this is detail - " + value)
                                .build()
                ).collect(Collectors.toList());

        Note note = Note.builder()
                .keyword("this is keyword")
                .details(details)
                .build();
        long detailsCount = this.noteDetailRepository.count();

        // when: Note 를 삽입하면
        note = this.noteRepository.save(note);

        // then: select 된 Note 가 삽입한 노트와 같고, NoteDetail 개수가 5개 증가한다.
        assertThat(this.noteRepository.findById(note.getId()).orElse(null)).isEqualTo(note);
        assertThat(this.noteDetailRepository.count()).isEqualTo(detailsCount + 5);
    }
}