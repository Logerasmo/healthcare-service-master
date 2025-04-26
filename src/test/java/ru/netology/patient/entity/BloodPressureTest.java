package ru.netology.patient.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BloodPressureTest {
    @Test
    public void BloodPressureTst(){
        BloodPressure bloodPressure = new BloodPressure(80, 120);
        Assertions.assertEquals(80, bloodPressure.getHigh());
        Assertions.assertEquals(120, bloodPressure.getLow());
    }
}
