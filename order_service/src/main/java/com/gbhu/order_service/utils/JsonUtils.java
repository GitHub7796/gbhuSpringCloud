package com.gbhu.order_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 工具类
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json 字符串 转 JsonNode
     * @param string
     * @return
     */
    public static JsonNode str2JsonNode(String string) {
        try {
            return objectMapper.readTree(string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
