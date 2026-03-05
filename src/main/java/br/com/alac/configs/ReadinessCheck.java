package br.com.alac.configs;

import br.com.alac.services.StarWarsService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
public class ReadinessCheck implements HealthCheck {

    @RestClient
    StarWarsService starWarsService;

    @Override
    public HealthCheckResponse call() {
        var containsErrorApi = starWarsService.getStarships().contains(StarWarsService.MSG_ERROR);

        if (containsErrorApi) {
            return HealthCheckResponse.down("ERROR");
        }
        return HealthCheckResponse.up("OK");
    }
}
