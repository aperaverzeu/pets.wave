package wave.pets.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import wave.pets.query.repository.CollarRepository;
import wave.pets.utilities.entity.CollarEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class CollarHandler {
    private final CollarRepository collarRepository;
    private final Random rand = new Random();

    private final List<HealthData> healthData = Arrays.asList(
            new HealthData("90", "97", "Низкий"),
            new HealthData("93", "97", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("94", "98", "Низкий"),
            new HealthData("105", "96", "Низкий"),
            new HealthData("90", "97", "Низкий"),
            new HealthData("90", "97", "Низкий"),
            new HealthData("90", "97", "Низкий"),
            new HealthData("90", "97", "Низкий"),
            new HealthData("90", "97", "Низкий"),
            new HealthData("110", "94", "Средний"),
            new HealthData("114", "97", "Средний"),
            new HealthData("117", "98", "Средний"),
            new HealthData("113", "96", "Средний"),
            new HealthData("97", "95", "Средний"),
            new HealthData("130", "95", "Высокий"),
            new HealthData("140", "98", "Высокий"),
            new HealthData("165", "99", "Высокий")
    );

    private final List<GeoData> geoData = Arrays.asList(
            new GeoData(27.59502, 53.91176),
            new GeoData(27.59506, 53.91170),
            new GeoData(27.59512, 53.91166),
            new GeoData(27.59516, 53.91160)
    );

    public Mono<ServerResponse> getAll(@SuppressWarnings("unused parameter") ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(collarRepository.findAll(), CollarEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getCollarById(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(collarRepository.findById(UUID.fromString(request.pathVariable("id"))), CollarEntity.class)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> produceHealthData(@SuppressWarnings("unused parameter") ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(getRandomHealthData(), HealthData.class)
                .switchIfEmpty(badRequest().build());
    }

    public Mono<ServerResponse> produceGeoData(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(getRandomGeoData(), GeoData.class)
                .switchIfEmpty(badRequest().build());
    }

    private Mono<HealthData> getRandomHealthData() {
        return Mono.just(healthData.get(rand.nextInt(healthData.size())));
    }

    private Mono<GeoData> getRandomGeoData() {
        return Mono.just(geoData.get(rand.nextInt(geoData.size())));
    }

    record HealthData(String bpm, String spo2, String stressLevel) {}

    record GeoData(double lng, double lat) {}
}
