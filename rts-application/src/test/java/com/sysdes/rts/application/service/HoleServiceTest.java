package com.sysdes.rts.application.service;

import com.sysdes.rts.application.api.robot.dto.request.CreateHoleRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateHoleResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HoleServiceTest {
    private HoleService holeService;
    private RobotTrackerRepository robotTrackerRepository;

    @Captor private ArgumentCaptor<Integer> xCapture;
    @Captor private ArgumentCaptor<Integer> yCapture;

    @BeforeEach
    public void beforeEach(){
        robotTrackerRepository = Mockito.mock(RobotTrackerRepository.class);
        holeService = new HoleService(robotTrackerRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_createHole() throws InvalidArgumentException {
        CreateHoleRequest request = new CreateHoleRequest(0,0);
        when(robotTrackerRepository.saveHole(any(Location.class))).thenReturn(new Location(0,0));
        CreateHoleResponse response = holeService.createHole(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getX(), 0);
        Assertions.assertEquals(response.getY(), 0);
    }

    @Test
    public void test_createHole_nullRequest() throws InvalidArgumentException {
        Assertions.assertThrows(InvalidArgumentException.class,  ()->{
            holeService.createHole(null);
        });
    }

    @Test
    public void test_createHole_nullCoordinates() throws InvalidArgumentException {
        Assertions.assertThrows(InvalidArgumentException.class,  ()->{
            holeService.createHole(new CreateHoleRequest());
        });
    }

    @Test
    public void test_getHole() {
        when(robotTrackerRepository.findHole(anyInt(), anyInt())).thenReturn(Optional.of(new Location(0,0)));
        Optional<Location> response = holeService.getHole(new Location(0,0));
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(response.get().getX(), 0);
        Assertions.assertEquals(response.get().getY(), 0);

        verify(robotTrackerRepository).findHole(xCapture.capture(), yCapture.capture());
        Assertions.assertEquals(xCapture.getValue(), 0);
        Assertions.assertEquals(yCapture.getValue(), 0);
    }
}