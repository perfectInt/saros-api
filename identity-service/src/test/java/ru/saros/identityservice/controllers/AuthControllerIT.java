package ru.saros.identityservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.saros.identityservice.dto.AuthRequestDto;
import ru.saros.identityservice.models.UserCredential;
import ru.saros.identityservice.services.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({AuthService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithAnonymousUser
public class AuthControllerIT {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    UserCredential credential;

    AuthRequestDto authRequestDto;

    String token;

    @BeforeAll
    public void init() {
        credential = new UserCredential();
        credential.setUsername("rulik");
        credential.setPassword("12345678");
        credential.setEmail("test@gmail.com");
    }

    @Test
    @Order(1)
    public void registerTest() throws Exception {
        RequestBuilder requestBuilderPost = MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(credential));

        MvcResult mvcResult = mockMvc.perform(requestBuilderPost).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("user was added to system", response);
    }

    @Test
    @Order(2)
    public void loginTest() throws Exception {
        authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername(credential.getUsername());
        authRequestDto.setPassword(credential.getPassword());

        RequestBuilder requestBuilderPost = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(authRequestDto));

        MvcResult mvcResult = mockMvc.perform(requestBuilderPost).andReturn();
        token = mvcResult.getResponse().getContentAsString();

        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/auth/validate?token=" + token);

        mvcResult = mockMvc.perform(requestBuilderGet).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("token is valid", response);
    }

    @Test
    @Order(3)
    public void incorrectDataLoginTest() throws Exception {
        authRequestDto.setPassword("kefteme");

        RequestBuilder requestBuilderPost = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(authRequestDto));

        mockMvc.perform(requestBuilderPost).andExpect(status().isForbidden());
    }
}
