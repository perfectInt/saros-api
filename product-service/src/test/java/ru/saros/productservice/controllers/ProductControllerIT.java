package ru.saros.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.saros.productservice.models.Product;
import ru.saros.productservice.services.ProductService;
import ru.saros.productservice.views.ProductView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({ProductService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIT {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    String title, category;
    MockMultipartFile[] files;

    Long id;
    Product product;

    @BeforeAll
    public void init() {
        title = "Test title";
        category = "Test category";
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        files = new MockMultipartFile[2];
        files[0] = new MockMultipartFile("images[]", "image1.jpg", "multipart/form-data", "test data".getBytes());
        files[1] = new MockMultipartFile("images[]", "image2.jpg", "multipart/form-data", "test data".getBytes());
    }

    @Test
    @Order(1)
    @WithMockUser
    public void createProductTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/api/v1/product/create")
                        .file(files[0])
                        .file(files[1])
                        .param("title", title)
                        .param("category", category)
        ).andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertNotNull(response);
        System.out.println(response);

        product = mapper.readValue(response, Product.class);
        assertNotNull(product);
        id = product.getId();
        assertEquals(title, product.getTitle());
    }

    @Test
    @Order(2)
    @WithAnonymousUser
    public void getNewProductTest() throws Exception {
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/product/" + id)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilderGet).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        ProductView productView = mapper.readValue(response, ProductView.class);

        assertNotNull(response);
        assertEquals("Test title", productView.getTitle());
        assertEquals(product.getImages().get(0).getId(), productView.getImagesIds().get(0));
        System.out.println(productView);
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    public void getAllProductsByPageTest() throws Exception {
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));

        requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/products?page=1")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    @Order(4)
    @WithAnonymousUser
    public void getAllProductsByPageAndCategoryTest() throws Exception {
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/products?category=" + category)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));

        requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/products?category=another+Category")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    @Order(5)
    @WithAnonymousUser
    public void checkAccessTest() throws Exception {
        RequestBuilder requestBuilderDelete = MockMvcRequestBuilders
                .delete("/api/v1/product/delete/" + id);

        mockMvc.perform(requestBuilderDelete).andExpect(status().isForbidden());
    }

    @Test
    @Order(6)
    @WithMockUser
    public void deleteProductTest() throws Exception {
        RequestBuilder requestBuilderDelete = MockMvcRequestBuilders
                .delete("/api/v1/product/delete/" + id);

        mockMvc.perform(requestBuilderDelete).andExpect(status().isOk());
    }
}
