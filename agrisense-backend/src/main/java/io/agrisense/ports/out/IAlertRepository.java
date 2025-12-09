package io.agrisense.ports.out;

import io.agrisense.domain.model.Alert;

public interface IAlertRepository {
    Alert save(Alert alert);
    Alert findOpenAlert(Long sensorId, Long ruleId);
}