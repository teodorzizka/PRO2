package cz.zemeplocha.mestskahlidka.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class StraznikControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithMockUser(username = "elanius", roles = {"ADMIN"})
    void getAll_returnsList() throws Exception {
        mockMvc.perform(get("/api/straznici"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    @WithMockUser(username = "elanius", roles = {"ADMIN"})
    void create_encodesPasswordWhenProvided() throws Exception {
        Straznik straznik = new Straznik();
        straznik.setJmeno("Alfréd Tračník");
        straznik.setRasa("člověk");
        straznik.setHodnost("seržant");
        straznik.setVek(62);
        straznik.setUsername("tracnik");
        mockMvc.perform(post("/api/straznici")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(straznik)))
                .andExpect(status().isOk());
    }
}
