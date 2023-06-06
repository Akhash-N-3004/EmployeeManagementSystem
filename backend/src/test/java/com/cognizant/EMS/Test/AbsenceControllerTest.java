//package com.cognizant.EMS.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.cognizant.EMS.Exception.ResourceNotFoundException;
//import com.cognizant.EMS.controller.AbsencesController;
//import com.cognizant.EMS.entity.Absences;
//import com.cognizant.EMS.entity.Employee;
//import com.cognizant.EMS.service.AbsencesService;
//import com.cognizant.EMS.service.EmployeeService;
//
//public class AbsenceControllerTest {
//	
//	@Mock
//    private AbsencesService absencesService;
//
//    @Mock
//    private EmployeeService employeeService;
//
//    @InjectMocks
//    private AbsencesController absencesController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllAbsences() {
//        // Arrange
//        List<Absences> absencesList = new ArrayList<>();
//        when(absencesService.getAllAbsences()).thenReturn(absencesList);
//
//        // Act
//        List<Absences> result = absencesController.getAllAbsences(java.util.Optional.empty());
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(absencesList, result);
//        verify(absencesService, times(1)).getAllAbsences();
//    }
//
//    @Test
//    void testGetAbsencesById_WhenAbsencesExists() throws ResourceNotFoundException {
//        // Arrange
//        long id = 1L;
//        Absences absences = new Absences(new Date(), new Date(), 0, "Reason", null);
//        Optional<Absences> optionalAbsences = Optional.of(absences);
//        when(absencesService.getAbsencesById(id)).thenReturn(optionalAbsences);
//
//        // Act
//        Optional<Absences> result = absencesController.getAbsencesById(id);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(optionalAbsences, result);
//        verify(absencesService, times(1)).getAbsencesById(id);
//    }
//
//    @Test
//    void testGetAbsencesById_WhenAbsencesDoesNotExist() throws ResourceNotFoundException {
//        // Arrange
//        long id = 1L;
//        when(absencesService.getAbsencesById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
//                ResourceNotFoundException.class,
//                () -> absencesController.getAbsencesById(id)
//        );
//        assertEquals("Not Found", exception.getMessage());
//        verify(absencesService, times(1)).getAbsencesById(id);
//    }
//
//    @Test
//    void testApplyLeave_WhenValidEmpId() {
//        // Arrange
//        long id = 1L;
//        Absences absences = new Absences(new Date(), new Date(), 0, "Reason", null);
//        Employee employee = new Employee();
//        when(employeeService.getEmployee(id)).thenReturn(employee);
//        when(absencesService.createAbsences(absences)).thenReturn(absences);
//
//        // Act
//        ResponseEntity<Object> responseEntity = absencesController.applyLeave(id, absences);
//
//        // Assert
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(absences, responseEntity.getBody());
//        verify(employeeService, times(1)).getEmployee(id);
//        verify(absencesService, times(1)).createAbsences(absences);
//    }
//
//    @Test
//    void testApplyLeave_WhenInvalidEmpId() {
//        // Arrange
//        long id = 1L;
//        Absences absences = new Absences(new Date(), new Date(), 0, "Reason", null);
//        when(employeeService.getEmployee(id)).thenReturn(null);
//
//        // Act
//        ResponseEntity<Object> responseEntity = absencesController.applyLeave(id, absences);
//
//        // Assert
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertEquals("Invalid Emp Id", responseEntity.getBody());
//        verify(employeeService, times(1)).getEmployee(id);
//        verify(absencesService, never()).createAbsences(any(Absences.class));
//    }
//
//    @Test
//    void testUpdateAbsences_WhenAbsencesExists() throws ResourceNotFoundException {
//        // Arrange
//        long id = 1L;
//        Optional<Absences> absences = new Absences(new Date(), new Date(), 0, "Reason", null);
//        when(absencesService.getAbsencesById(id)).thenReturn(absences);
//        when(absencesService.updateAbsences(id, absences)).thenReturn(absences);
//
//        // Act
//        ResponseEntity<Absences> result = absencesController.updateAbsences(id, absences);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(absences, result);
//        verify(absencesService, times(1)).getAbsencesById(id);
//        verify(absencesService, times(1)).updateAbsences(id, absences);
//    }
//
//    @Test
//    void testUpdateAbsences_WhenAbsencesDoesNotExist() throws ResourceNotFoundException {
//        // Arrange
//        long id = 1L;
//        Absences absences = new Absences(new Date(), new Date(), 0, "Reason", null);
//        when(absencesService.getAbsencesById(id)).thenReturn(null);
//
//        // Act & Assert
//        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
//                ResourceNotFoundException.class,
//                () -> absencesController.updateAbsences(id, absences)
//        );
//        assertEquals("Not Found", exception.getMessage());
//        verify(absencesService, times(1)).getAbsencesById(id);
//        verify(absencesService, never()).updateAbsences(anyLong(), any(Absences.class));
//    }
//
//    @Test
//    void testDeleteAbsencesById() {
//        // Arrange
//        long id = 1L;
//        when(absencesService.deleteAbsences(id)).thenReturn(true);
//
//        // Act
//        boolean result = absencesController.deleteAbsencesById(id);
//
//        // Assert
//        assertEquals(true, result);
//        verify(absencesService, times(1)).deleteAbsences(id);
//    }
//}