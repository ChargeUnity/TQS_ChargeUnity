package tqs.ChargeUnity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.OperatorRepository;
import tqs.ChargeUnity.service.StationService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class OperatorControllerTest {

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private StationService stationService;

    @InjectMocks
    private OperatorController operatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOperator_ReturnsCreatedOperator() {
        Operator operator = new Operator();
        when(operatorRepository.save(operator)).thenReturn(operator);

        ResponseEntity<?> response = operatorController.createOperator(operator);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(operator);
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void getAllOperators_ReturnsListOfOperators() {
        Operator op1 = new Operator();
        Operator op2 = new Operator();
        List<Operator> operators = Arrays.asList(op1, op2);
        when(operatorRepository.findAll()).thenReturn(operators);

        List<Operator> result = operatorController.getAllOperators();

        assertThat(result).containsExactly(op1, op2);
        verify(operatorRepository, times(1)).findAll();
    }

    @Test
    void getOperatorById_Found_ReturnsOperator() {
        Operator operator = new Operator();
        when(operatorRepository.findById(1)).thenReturn(Optional.of(operator));

        ResponseEntity<?> response = operatorController.getOperatorById(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(operator);
        verify(operatorRepository, times(1)).findById(1);
    }

    @Test
    void getOperatorById_NotFound_ReturnsNotFound() {
        when(operatorRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = operatorController.getOperatorById(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Operator not found");
        verify(operatorRepository, times(1)).findById(1);
    }

    @Test
    void getOperatorStations_Found_ReturnsStations() {
        Operator operator = new Operator();
        List<Station> stations = Arrays.asList(new Station(), new Station());
        when(operatorRepository.findById(1)).thenReturn(Optional.of(operator));
        when(stationService.getStationsByOperator(1)).thenReturn(stations);

        ResponseEntity<?> response = operatorController.getOperatorStations(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(stations);
        verify(operatorRepository, times(1)).findById(1);
        verify(stationService, times(1)).getStationsByOperator(1);
    }

    @Test
    void getOperatorStations_NotFound_ReturnsNotFound() {
        when(operatorRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = operatorController.getOperatorStations(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Operator not found");
        verify(operatorRepository, times(1)).findById(1);
        verify(stationService, never()).getStationsByOperator(anyInt());
    }
}