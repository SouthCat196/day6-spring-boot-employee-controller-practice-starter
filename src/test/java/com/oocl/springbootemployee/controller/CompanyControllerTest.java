package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.CompanyVO;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CompanyControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private JacksonTester<List<CompanyVO>> jacksonTester;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        companyRepository.clearAll();
        companyRepository.createCompany(new Company(0, "OOCL"));
        companyRepository.createCompany(new Company(0, "COSCO"));

    }

    @AfterEach
    void clean() {
        companyRepository.resetSequence();
    }

    @Test
    void should_return_employees_when_get_all_given_emplpyees() throws Exception {
        // Given
        final List<CompanyVO> companyVOs = companyRepository.getAll();
        String companyVOsJSON = jacksonTester.write(companyVOs).getJson();

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/company"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(companyVOs.size())))
                .andExpect(MockMvcResultMatchers.content().json(companyVOsJSON));

    }


}

