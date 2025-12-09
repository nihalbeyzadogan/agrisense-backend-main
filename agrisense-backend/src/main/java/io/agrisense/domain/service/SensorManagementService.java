package io.agrisense.domain.service;

import io.agrisense.domain.model.Sensor;
import io.agrisense.ports.in.IManageSensorUseCase;
import io.agrisense.ports.out.ISensorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SensorManagementService implements IManageSensorUseCase {

    private final ISensorRepository sensorRepository;

    public SensorManagementService(ISensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    @Transactional
    public Sensor createSensor(Sensor sensor) {
        // İleride buraya "Aynı isimde sensör var mı?" kontrolü eklenebilir.
        return sensorRepository.save(sensor);
    }

    @Override
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @Override
    public Sensor getSensorById(Long id) {
        Sensor sensor = sensorRepository.findById(id);
        if (sensor == null) {
            // İsteğe bağlı: Burada özel bir Domain Exception fırlatılabilir
             throw new IllegalArgumentException("Sensor with ID " + id + " not found.");
        }
        return sensor;
    }
}