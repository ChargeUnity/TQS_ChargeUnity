package tqs.ChargeUnity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Optional;

import tqs.ChargeUnity.model.Operator;

import tqs.ChargeUnity.repository.OperatorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import tqs.ChargeUnity.service.OperatorService;


public class OperatorServiceTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorService operatorService;

    private Operator sampleOperator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleOperator = new Operator();
        sampleOperator.setId(1);
        sampleOperator.setName("Galp");
    }

    @Test
    public void testFindAll() {
        when(operatorRepository.findAll()).thenReturn(List.of(sampleOperator));
        List<Operator> result = operatorService.findAll();
        assertEquals(1, result.size());
        assertEquals("Galp", result.get(0).getName());
    }

    @Test
    public void testFindById() {
        when(operatorRepository.findById(1)).thenReturn(Optional.of(sampleOperator));
        Optional<Operator> result = operatorService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Galp", result.get().getName());
    }

    @Test
    public void testFindByName() {
        when(operatorRepository.findByName("Galp")).thenReturn(Optional.of(sampleOperator));
        Optional<Operator> result = operatorService.findByName("Galp");
        assertTrue(result.isPresent());
    }

    @Test
    public void testSave() {
        when(operatorRepository.save(any(Operator.class))).thenReturn(sampleOperator);
        Optional<Operator> result = Optional.ofNullable(operatorService.save(sampleOperator));
        assertTrue(result.isPresent());
        assertEquals("Galp", result.get().getName());
    }

    @Test
    public void testUpdate() {
        Operator updated = new Operator();
        updated.setId(1);
        updated.setName("EDP");

        when(operatorRepository.findById(1)).thenReturn(Optional.of(sampleOperator));
        when(operatorRepository.save(any(Operator.class))).thenReturn(updated);

        Optional<Operator> result = operatorService.update(updated);
        assertTrue(result.isPresent());
        assertEquals("EDP", result.get().getName());
    }


    @Test
    public void testDeleteById() {
        doNothing().when(operatorRepository).deleteById(1);
        operatorService.deleteById(1);
        verify(operatorRepository, times(1)).deleteById(1);
    }


}