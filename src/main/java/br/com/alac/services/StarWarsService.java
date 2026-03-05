package br.com.alac.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.temporal.ChronoUnit;

@RegisterRestClient(baseUri = "https://swapi.info/api/")
public interface StarWarsService {

    public static final String MSG_ERROR = "Fallback ";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("starships")
    @Timeout(
            value = 3,
            unit = ChronoUnit.SECONDS
    )
    @Fallback(
            fallbackMethod = "getStarshipsFallback"
    )
    @CircuitBreaker(
            requestVolumeThreshold = 2,  //a cada 2 requests o circuit breaker vai analisar se precisa ativar o 'disjuntor'
            failureRatio = .5, //50% , se de 2 requests 1 der erro, eu ativo o circuit
            delay = 3000L, //3 seg
            successThreshold = 2 //se der 2 requests de sucesso, desliga o disjuntor
    )
    public String getStarships(); //O metodo do fallback precisa ter a mesma assinatura do metodo que ta sendo aplicado


    default String getStarshipsFallback() {
        return MSG_ERROR;
    }
}
