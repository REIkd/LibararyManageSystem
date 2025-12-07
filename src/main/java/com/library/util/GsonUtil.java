package com.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gson utility with Java 8 Time API support
 */
public class GsonUtil {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private static final Gson GSON = new GsonBuilder()
        // LocalDateTime serializer
        .registerTypeAdapter(LocalDateTime.class, 
            (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> 
                context.serialize(src.format(DATE_TIME_FORMATTER)))
        // LocalDateTime deserializer
        .registerTypeAdapter(LocalDateTime.class, 
            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> 
                LocalDateTime.parse(json.getAsString(), DATE_TIME_FORMATTER))
        // LocalDate serializer
        .registerTypeAdapter(LocalDate.class, 
            (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                context.serialize(src.format(DATE_FORMATTER)))
        // LocalDate deserializer
        .registerTypeAdapter(LocalDate.class, 
            (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> 
                LocalDate.parse(json.getAsString(), DATE_FORMATTER))
        .serializeNulls()
        .create();
    
    /**
     * Get configured Gson instance
     */
    public static Gson getGson() {
        return GSON;
    }
    
    /**
     * Convert object to JSON string
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
    
    /**
     * Convert JSON string to object
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
}

