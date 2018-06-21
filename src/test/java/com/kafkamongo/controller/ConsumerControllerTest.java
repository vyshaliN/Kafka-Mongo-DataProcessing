package com.kafkamongo.controller;

import com.kafkamongo.model.Employee;
import com.kafkamongo.repository.EmployeeRepository;
import com.kafkamongo.service.KafkaConsumer;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsumerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    KafkaConsumer consumerMock;

    @MockBean
    EmployeeRepository repositoryMock;

    private ConsumerController consumerController;

    @Before
    public void setUp() throws Exception {

        consumerController = new ConsumerController();
        consumerMock = mock(KafkaConsumer.class);
        repositoryMock = mock(EmployeeRepository.class);
        ReflectionTestUtils.setField(consumerController,"consumer",consumerMock);
        ReflectionTestUtils.setField(consumerController,"repository",repositoryMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testController() throws Exception {

        Employee employee = new Employee();
        employee.setEmpId("E001");
        employee.setEmpName("Vysh");
        employee.setDesignation("Engineer");
        employee.setAddress("Karur");

        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(employee);
        String employeeId = "E001";

        when(consumerMock.processMessage(employee)).thenReturn(employeeList);
        when(repositoryMock.findByEmpId(employeeId)).thenReturn(employee);

        MockHttpServletRequestBuilder builder = get("/kafkaMongo/"+employeeId).contentType(MediaType.APPLICATION_JSON);
        MvcResult result =  mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        assertTrue(response.getString("empId").equals("E001"));
        assertTrue(response.getString("empName").equals("Vysh"));
        assertTrue(response.getString("designation").equals("Engineer"));
        assertTrue(response.getString("address").equals("Karur"));


    }
 }
