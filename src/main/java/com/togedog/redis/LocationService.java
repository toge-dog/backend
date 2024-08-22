package com.togedog.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class LocationService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper를 사용하여 JSON 직렬화

    private static final long TTL_SECONDS = 10000; // 3일 (259,200초)

    public void saveLocation(String locationKey, double latitude, double longitude, String userEmail ) {
        try {


            String locationValue = objectMapper.writeValueAsString(new Location(latitude, longitude));
            String finalLocationKey = locationKey + ":" + userEmail;
            redisTemplate.opsForValue().set(locationKey, locationValue);
            redisTemplate.expire(locationKey, TTL_SECONDS, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public Location getLocation(String locationKey) {
        try {
            // Redis에서 JSON 값 읽어오기
            String locationValue = (String) redisTemplate.opsForValue().get(locationKey);
            // JSON을 Location 객체로 역직렬화
            return objectMapper.readValue(locationValue, Location.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 패턴에 맞는 모든 키를 가져오는 메서드
    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }


    @Getter
    @Setter
    public static class Location {
        private double latitude;
        private double longitude;

        public Location() {
        }

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}