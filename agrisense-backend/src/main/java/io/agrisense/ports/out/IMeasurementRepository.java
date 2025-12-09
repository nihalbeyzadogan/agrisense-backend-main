package io.agrisense.ports.out;

import io.agrisense.domain.model.Measurement;

public interface IMeasurementRepository {
    Measurement save(Measurement measurement);
}