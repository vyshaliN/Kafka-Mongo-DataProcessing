package com.kafkamongo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamongo.model.Employee;
import com.kafkamongo.repository.EmployeeRepository;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {

    @MockBean
    EmployeeRepository repositoryMock;

    MockMvc mockMvc;
    EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        employeeController = new EmployeeController();
        repositoryMock = mock(EmployeeRepository.class);
        ReflectionTestUtils.setField(employeeController,"repository",repositoryMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRead() throws Exception {
        Employee employee = new Employee();
        employee.setEmpId("E001");
        employee.setEmpName("Vysh");
        employee.setDesignation("Engineer");
        employee.setAddress("Karur");

        String responseExpected = new ObjectMapper().writeValueAsString(employee);

        String employeeId = "E001";
        when(repositoryMock.findByEmpId(employeeId)).thenReturn(employee);

        MockHttpServletRequestBuilder builder = get("/employee/"+employeeId).contentType(MediaType.APPLICATION_JSON);
        MvcResult result =  mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        assertTrue(responseExpected.equals(result.getResponse().getContentAsString()));
    }

    @Test
    public void testReadNull() throws Exception {

        String employeeId = "E002";
        String responseExpected = "Employee with "+employeeId+" is not available";

        when(repositoryMock.findByEmpId(employeeId)).thenReturn(null);

        MockHttpServletRequestBuilder builder = get("/employee/"+employeeId).contentType(MediaType.APPLICATION_JSON);
        MvcResult result =  mockMvc.perform(builder).andExpect(status().isNotFound()).andReturn();
        assert(responseExpected.equals(result.getResponse().getContentAsString()));
    }

    @Test
    public void testCreateOrUpdate() throws Exception{
        Employee employee = new Employee();
        employee.setEmpId("E001");
        employee.setEmpName("Vysh");
        employee.setDesignation("Engineer");
        employee.setAddress("Karur");

        when(repositoryMock.save(employee)).thenReturn(employee);
        String responseExpected = "Employee details are entered in MongoDB";
        String requestBody = new ObjectMapper().writeValueAsString(employee);

        MockHttpServletRequestBuilder builder = post("/employee").content(requestBody).contentType(MediaType.APPLICATION_JSON);
        MvcResult result =  mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        assertTrue(responseExpected.equals(result.getResponse().getContentAsString()));
        verify(repositoryMock,times(1)).save(any(Employee.class));

    }

    @Test
    public void testDelete() throws Exception{
        Employee employee = new Employee();
        employee.setEmpId("E001");
        employee.setEmpName("Vyshali");
        employee.setDesignation("Engineer");
        employee.setAddress("Karur");

        String employeeId = "E001";
        String responseExpected = "Employee details with "+employeeId+" are deleted";

        when(repositoryMock.findByEmpId(employeeId)).thenReturn(employee);
        MockHttpServletRequestBuilder builder = delete("/employee/"+employeeId).contentType(MediaType.APPLICATION_JSON);
        MvcResult result =  mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertTrue(responseExpected.equals(result.getResponse().getContentAsString()));

    }
}
