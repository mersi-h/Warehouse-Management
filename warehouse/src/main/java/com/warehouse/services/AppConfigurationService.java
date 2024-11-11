package com.warehouse.services;

import com.warehouse.entities.AppConfiguration;
import com.warehouse.entities.ConfigKeys;
import com.warehouse.repository.AppConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConfigurationService implements IAppConfigurationService{

    private final AppConfigurationRepository configRepository;

    @Autowired
    public AppConfigurationService(AppConfigurationRepository configRepository) {
        this.configRepository = configRepository;
    }

    public int getMaxDeliveryPeriod() {
        return configRepository.findById(ConfigKeys.MAX_DELIVERY_PERIOD)
                .map(config -> Integer.parseInt(config.getValue()))
                .orElse(3);
    }

    public AppConfiguration updateMaxDeliveryPeriod(int period) {
        if (period > 30) {
            throw new IllegalArgumentException("The period cannot exceed 30 days.");
        }

        AppConfiguration config = configRepository.findById(ConfigKeys.MAX_DELIVERY_PERIOD)
                .orElse(new AppConfiguration());
        config.setKey(ConfigKeys.MAX_DELIVERY_PERIOD);
        config.setValue(String.valueOf(period));
        return configRepository.save(config);
    }
}
