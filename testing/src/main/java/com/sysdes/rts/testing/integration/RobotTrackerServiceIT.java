package com.sysdes.rts.testing.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={com.sysdes.rts.infra.App.class})
@AutoConfigureMockMvc
public class RobotTrackerServiceIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_CreateAndMoveRobot_Success() throws Exception {
        CreateRobotRequest cr = new CreateRobotRequest("sofia", new Location(0, 0));


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
                "{\"name\":\"sofia\",\"currentLocation\":{\"x\":0,\"y\":0},\"status\":\"ALIVE\",\"error\":null}"
        );

        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/robots/{name}", "sofia"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(getResult.getResponse().getContentAsString(),
                "{\"name\":\"sofia\",\"currentLocation\":{\"x\":0,\"y\":0},\"status\":\"ALIVE\",\"error\":null}"
        );

        MoveRobotRequest mr = new MoveRobotRequest("sofia", Movement.builder().east(1).build());
        MvcResult moveResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/api/robots/{name}/action/move", "sofia")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mr))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(moveResult.getResponse().getContentAsString(),
                "{\"name\":\"sofia\",\"oldLocation\":{\"x\":0,\"y\":0},\"currentLocation\":{\"x\":1,\"y\":0},\"movement\":{\"north\":null,\"east\":1,\"west\":null,\"south\":null},\"error\":null}"
        );
    }

    @Test
    public void test_MoveNonExistingRobot_Failure() throws Exception {
        MoveRobotRequest mr = new MoveRobotRequest("sofia", Movement.builder().east(1).build());
        MvcResult moveResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/api/robots/{name}/action/move", "sofia")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mr))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assertions.assertEquals(moveResult.getResponse().getStatus(), 422);
        Assertions.assertEquals(moveResult.getResponse().getContentAsString(),
                "{\"name\":null,\"oldLocation\":null,\"currentLocation\":null,\"movement\":null,\"error\":{\"message\":\"Robot doesn't exist with name sofia\"}}"
        );
    }
}
