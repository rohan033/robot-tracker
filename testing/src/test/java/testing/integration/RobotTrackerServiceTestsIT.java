package testing.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import com.sysdes.rts.dal.repository.HoleRepository;
import com.sysdes.rts.dal.repository.RobotRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@SpringBootTest(classes={com.sysdes.rts.infra.App.class})
@AutoConfigureMockMvc
public class RobotTrackerServiceTestsIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private String robotName;

    @Autowired private RobotRepository robotRepository;
    @Autowired private HoleRepository holeRepository;

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        robotRepository.deleteAll();
        holeRepository.deleteAll();
        robotName = testInfo.getTestMethod().get().getName();
    }


    @Test
    void test_CreateAndMoveRobot_Success() throws Exception {
        CreateRobotRequest cr = new CreateRobotRequest(robotName, new Location(0, 0));

        MvcResult createResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/api/robots/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cr))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(createResult.getResponse().getContentAsString(),
                String.format("{\"data\":{\"name\":\"%s\",\"currentLocation\":{\"x\":0,\"y\":0},\"status\":\"ALIVE\"},\"error\":null}", robotName)
        );

        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/robots/{name}", robotName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(getResult.getResponse().getContentAsString(),
                String.format("{\"data\":{\"name\":\"%s\",\"currentLocation\":{\"x\":0,\"y\":0},\"status\":\"ALIVE\"},\"error\":null}", robotName)
        );

        MoveRobotRequest mr = new MoveRobotRequest(robotName, Movement.builder().east(1).build());
        MvcResult moveResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/api/robots/{name}/action/move", robotName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mr))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(moveResult.getResponse().getContentAsString(),
                String.format("{\"data\":{\"name\":\"%s\",\"oldLocation\":{\"x\":0,\"y\":0},\"currentLocation\":{\"x\":1,\"y\":0},\"movement\":{\"north\":null,\"east\":1,\"west\":null,\"south\":null},\"status\":\"ALIVE\"},\"error\":null}", robotName)
        );
    }

    @Test
    void test_MoveNonExistingRobot_Failure() throws Exception {
        MoveRobotRequest mr = new MoveRobotRequest(robotName, Movement.builder().east(1).build());
        MvcResult moveResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/api/robots/{name}/action/move", robotName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mr))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assertions.assertEquals(moveResult.getResponse().getStatus(), 422);
        Assertions.assertEquals(moveResult.getResponse().getContentAsString(),
                String.format("{\"data\":null,\"error\":{\"message\":\"Robot doesn't exist with name %s\"}}", robotName)
        );
    }
}
