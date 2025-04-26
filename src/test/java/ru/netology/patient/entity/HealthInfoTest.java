package ru.netology.patient.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class HealthInfoTest
{
    @Test
    public void HealthinfoTst(){
        BloodPressure bloodPressure = Mockito.mock(BloodPressure.class);
        Mockito.when(bloodPressure.toString())
                .thenReturn("BloodPressure{" +
                        "high=" + 120 +
                        ", low=" + 80 +
                        '}');
        HealthInfo healthInfo = new HealthInfo(BigDecimal.valueOf(37.6), bloodPressure);
        Assertions.assertEquals("HealthInfo{" +
                "normalTemperature=" + "37.6" +
                ", bloodPressure=" + bloodPressure.toString() +
                '}', healthInfo.toString());
    }
}
