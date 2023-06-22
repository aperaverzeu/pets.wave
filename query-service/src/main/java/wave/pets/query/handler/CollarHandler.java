package wave.pets.query.handler;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CollarHandler {
    private final CollarRepository collarRepository;
    private final Random rand = new Random();

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

    public Mono<ServerResponse> produceHealthData(ServerRequest request) {
        log.info(request.pathVariable("collar_id"));
        return ok()
                .contentType(APPLICATION_JSON)
                .body(getRandomHealthData(), HealthData.class)
                .switchIfEmpty(badRequest().build());
    }

    public Mono<ServerResponse> produceGeoData(ServerRequest request) {
        log.info(request.pathVariable("collar_id"));
        return ok()
                .contentType(APPLICATION_JSON)
                .body(getRandomGeoData(), GeoData.class)
                .switchIfEmpty(badRequest().build());
    }

    private Mono<HealthData> getRandomHealthData() {
        return Mono.just(HealthData.builder()
                .bpm(Integer.toString(65 + rand.nextInt(90)))
                .spo2(Integer.toString(95 + rand.nextInt(4)))
                .stressLevel(stressLevelData.get(rand.nextInt(stressLevelData.size())))
                .build());
    }

    private Mono<GeoData> getRandomGeoData() {
        return Mono.just(GeoData.builder()
                .lng(rand.doubles(27.5953, 27.5956).limit(1).findFirst().orElseThrow())
                .lat(rand.doubles(53.91171, 53.91176).limit(1).findFirst().orElseThrow())
                .build());
    }

    private static final List<String> stressLevelData = Arrays.asList(
            "Низкий", "Низкий", "Низкий", "Низкий", "Низкий",
            "Средний", "Средний", "Средний", "Средний", "Средний",
            "Высокий", "Высокий",
            "Низкий", "Низкий", "Низкий", "Низкий", "Низкий"
    );

    @Builder
    record HealthData(String bpm, String spo2, String stressLevel) {}

    @Builder
    record GeoData(double lng, double lat) {}
}
