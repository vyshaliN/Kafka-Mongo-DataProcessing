package com.kafkamongo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamongo.model.Employee;

import com.kafkamongo.service.KafkaProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProducerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    KafkaProducer producermock;

    ProducerController producerController;

    @Before
    public void setUp() throws Exception {
        producerController = new ProducerController();
        producermock = mock(KafkaProducer.class);
        ReflectionTestUtils.setField(producerController,"producer",producermock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(producerController).build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testController() throws Exception {

        doNothing().when(producermock).send(any(Employee.class));

        Employee employee = new Employee();
        employee.setEmpId("E001");
        employee.setEmpName("Vysh");
        employee.setDesignation("Engineer");

        String requestBody = new ObjectMapper().writeValueAsString(employee);
        final ResultActions resultActions = mockMvc.perform(post("/messages").content(requestBody).contentType("application/json"));
        resultActions.andExpect(status().isAccepted());

        verify(producermock,times(1)).send(any(Employee.class));
    }

}
