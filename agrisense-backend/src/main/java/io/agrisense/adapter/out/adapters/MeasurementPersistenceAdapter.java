package io.agrisense.adapter.out.adapters;

import io.agrisense.adapter.out.Mapper.AgriSenseMapper;
import io.agrisense.adapter.out.persistence.entity.MeasurementEntity;
import io.agrisense.adapter.out.persistence.entity.SensorEntity;
import io.agrisense.domain.model.Measurement;
import io.agrisense.ports.out.IMeasurementRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MeasurementPersistenceAdapter implements IMeasurementRepository {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public Measurement save(Measurement measurement) {
        MeasurementEntity entity = AgriSenseMapper.toEntity(measurement);
        if (measurement.getSensorId() != null) {
            entity.setSensor(entityManager.getReference(SensorEntity.class, measurement.getSensorId()));
        }
        entity.setCreatedAt(java.time.Instant.now());
        entityManager.persist(entity);
        measurement.setId(entity.getId());
        return measurement;
    }
}
