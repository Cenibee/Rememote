package cenibee.github.rememote.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    NoteRepository noteRepository;

    @Test
    void nullTest() {
        assertThat(mvc).isNotNull();
        assertThat(noteRepository).isNotNull();
    }

    @Test
    void getOneNote() throws Exception {
        Note note = Note.builder()
                .keyword("keyword")
                .build();
        note = this.noteRepository.save(note);

        mvc.perform(get("/api/notes/{id}", note.getId())
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href").exists());
    }

    @Test
    void createNote() throws Exception {
        Note note = Note.builder()
                .keyword("this is a keyword")
                .build();

        mvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("_links.self.href").exists());
    }

}