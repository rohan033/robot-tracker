package testing.inmemory;

import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.exception.IllegalStateException;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import com.sysdes.rts.application.service.HoleService;
import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.application.spi.events.dto.Event;
import com.sysdes.rts.application.spi.events.dto.PublishEventResponse;
import com.sysdes.rts.application.spi.events.ports.EventPublisher;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.dal.adapter.RobotTrackerRepositoryAdapter;
import com.sysdes.rts.dal.storage.InMemoryStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

public class RobotTrackerServiceTests {
    private RobotTrackerService rts;
    private HoleService hs;
    private EventPublisher eventPublisher;

    @BeforeEach
    public void beforeEach(){
        RobotTrackerRepository rtr = new RobotTrackerRepositoryAdapter(new InMemoryStore(new HashMap<>(), new HashMap<>()));
        hs = new HoleService(rtr);
        eventPublisher = new EventPublisher() {
            @Override
            public <T> Optional<PublishEventResponse<T>> publishEvent(Event<T> event) {
                return Optional.empty();
            }
        };
        rts = new RobotTrackerService(rtr, hs, eventPublisher);
    }


    @Test
    public void test_CreateAndMoveRobot_Success() throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException, ResourceAlreadyExistsException {
        CreateRobotResponse crRes = rts.createRobot(new CreateRobotRequest("robot", new Location(0,0)));
        MoveRobotResponse mrRes = rts.moveRobot(new MoveRobotRequest(crRes.getName(), Movement.builder().east(1).north(1).build()));

        assert mrRes != null;
        assert mrRes.getName().equals("robot");
        assert mrRes.getCurrentLocation().equals(new Location(1,1));
    }

    @Test
    public void test_CreateAndMoveRobot_Failure() {
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            rts.moveRobot(new MoveRobotRequest("doesNotExistRobot", Movement.builder().east(1).north(1).build()));
        });
    }


}
