package com.atguigu.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 菜品口味标准化工具类
 * 用于解决用户选择口味顺序不同导致的数据不一致问题
 */
@Slf4j
public class DishFlavorNormalizer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 标准化菜品口味JSON字符串
     * 
     * 解决问题：
     * 1. 用户选择相同口味但顺序不同时，JSON字符串不同
     * 2. 导致购物车、订单等系统误认为是不同商品
     * 
     * 标准化策略：
     * 1. 对口味列表中每个口味项的属性按字母顺序排序
     * 2. 对整个口味列表按name字段排序
     * 3. 重新序列化为标准JSON格式
     * 
     * @param dishFlavor 原始口味JSON字符串
     * @return 标准化后的口味JSON字符串
     */
    public static String normalize(String dishFlavor) {
        try {
            if (dishFlavor == null || dishFlavor.trim().isEmpty()) {
                return dishFlavor;
            }
            
            // 解析JSON字符串为Map列表
            List<Map<String, Object>> flavorList = objectMapper.readValue(
                dishFlavor, new TypeReference<List<Map<String, Object>>>() {});
            
            // 使用TreeMap对每个口味项的键进行排序，确保顺序一致
            for (Map<String, Object> flavor : flavorList) {
                // 将HashMap转换为TreeMap以保证键的排序
                Map<String, Object> sortedFlavor = new TreeMap<>(flavor);
                flavor.clear();
                flavor.putAll(sortedFlavor);
            }
            
            // 对整个口味列表按照name字段排序
            flavorList.sort((a, b) -> {
                String nameA = (String) a.get("name");
                String nameB = (String) b.get("name");
                if (nameA == null) nameA = "";
                if (nameB == null) nameB = "";
                return nameA.compareTo(nameB);
            });
            
            // 重新序列化为JSON字符串
            return objectMapper.writeValueAsString(flavorList);
            
        } catch (Exception e) {
            log.warn("口味JSON标准化失败，使用原始字符串: {}", dishFlavor, e);
            return dishFlavor;
        }
    }

    /**
     * 比较两个口味是否相同（忽略选择顺序）
     * 
     * @param flavor1 口味1
     * @param flavor2 口味2
     * @return 是否相同
     */
    public static boolean isSameFlavor(String flavor1, String flavor2) {
        // 处理null和空字符串情况
        if (flavor1 == null && flavor2 == null) {
            return true;
        }
        if (flavor1 == null || flavor2 == null) {
            return false;
        }
        
        String normalized1 = normalize(flavor1);
        String normalized2 = normalize(flavor2);
        
        return normalized1.equals(normalized2);
    }

    /**
     * 检查口味JSON字符串是否有效
     * 
     * @param dishFlavor 口味JSON字符串
     * @return 是否有效
     */
    public static boolean isValidFlavorJson(String dishFlavor) {
        if (dishFlavor == null || dishFlavor.trim().isEmpty()) {
            return true; // 空字符串视为有效（无口味选择）
        }
        
        try {
            objectMapper.readValue(dishFlavor, new TypeReference<List<Map<String, Object>>>() {});
            return true;
        } catch (Exception e) {
            log.warn("无效的口味JSON字符串: {}", dishFlavor, e);
            return false;
        }
    }
}