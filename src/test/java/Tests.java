import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Tests {
    @Test
    public void BloodPressureTest(){
        BloodPressure bloodPressure = new BloodPressure(80, 120);
        Assertions.assertEquals(80, bloodPressure.getHigh());
        Assertions.assertEquals(120, bloodPressure.getLow());
    }

    @Test
    public void HealthinfoTest(){
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
    @Test
    public void MedicalServiceTest(){
        //PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        //System.setOut(mockPrintStream);

        PatientInfo patientInfo = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        PatientInfoRepository patientInfoFileRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoFileRepository.getById(Mockito.anyString())).thenReturn(patientInfo);


        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.doAnswer(inv -> {
            String s = inv.getArgument(0);
            System.out.println(s);
            return null;
        }).when(alertService).send(Mockito.anyString());

        MedicalService medicalService =
                new MedicalServiceImpl(patientInfoFileRepository, alertService);
        medicalService.checkBloodPressure("id_1", new BloodPressure(110, 80));
        medicalService.checkTemperature("id_1", new BigDecimal("30"));
        Mockito.verify(alertService, Mockito.times(2)).send(argumentCaptor.capture());
        List<String> allValues = argumentCaptor.getAllValues();
        Assertions.assertEquals("Warning, patient with id: null, need help", allValues.get(0));

        Assertions.assertEquals("Warning, patient with id: null, need help", allValues.get(1));

    }
}
