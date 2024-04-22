package com.racemus.aircaft.racemus;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.racemus.aircaft.racemus.entity.TemperatureValues;
import com.racemus.aircaft.racemus.service.ITemperatureDeviationService;

import static org.mockito.Mockito.when;

@WebMvcTest
public class TemperatureDeviationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITemperatureDeviationService iTemperatureDeviationService;

    @Test
    public void testGetTemperatureDeviation() throws Exception {
        // Mock the service response
        String acModel = "A320";
        Double altitude = 10000.0;
        String aeroPhase = "CRUISE";
        TemperatureValues expectedTemperatureValues = new TemperatureValues();
        expectedTemperatureValues.setTmin(10.0);
        expectedTemperatureValues.setTmax(20.0);
        when(iTemperatureDeviationService.getTemperatureDeviation(acModel, altitude, aeroPhase))
                .thenReturn(expectedTemperatureValues);

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/aircraft/csv-data")
                .param("acModel", acModel)
                .param("altitude", altitude.toString())
                .param("aeroPhase", aeroPhase))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tmin").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tmax").value(20.0));
    }
}
