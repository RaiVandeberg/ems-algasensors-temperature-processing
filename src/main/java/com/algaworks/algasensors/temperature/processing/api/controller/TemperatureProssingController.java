package com.algaworks.algasensors.temperature.processing.api.controller;

import com.algaworks.algasensors.temperature.processing.api.model.TempetureLogOutput;
import com.algaworks.algasensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures/data")
@Slf4j
public class TemperatureProssingController {

    @PostMapping(consumes =MediaType.TEXT_PLAIN_VALUE)
    public void data(@PathVariable TSID sensorId, @RequestBody String input){
        if(input == null || input.isBlank()){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Double temperature;
        try {
            temperature = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TempetureLogOutput logOutput = TempetureLogOutput.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .sensorID(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.MAX)
                .build();

        log.info(logOutput.toString());


    }

}
