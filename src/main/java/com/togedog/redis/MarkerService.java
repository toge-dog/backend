package com.togedog.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MarkerService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final long TTL_SECONDS = 259200;

    public Marker getMarker(String markerKey) {
        try {
            // Redis에서 JSON 값 읽어오기
            String markerValue = (String) redisTemplate.opsForValue().get(markerKey);
            // JSON을 Marker 객체로 역직렬화
            return objectMapper.readValue(markerValue, Marker.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void saveMarker (String markerKey, double latitude, double longitude, String userEmail) {
        try {
            String markerValue =objectMapper.writeValueAsString(new LocationService.Location(latitude, longitude));
            String findMarkerKey = markerKey + ":" + userEmail;
            redisTemplate.opsForValue().set(markerKey,markerValue );
            redisTemplate.expire(markerKey, TTL_SECONDS, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
    }

    @Getter
    @Setter
    public static class Marker {
        private double latitude;
        private double longitude;

        public Marker() {
        }

        public Marker(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
