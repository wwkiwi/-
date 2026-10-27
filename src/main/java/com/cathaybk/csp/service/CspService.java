package com.cathaybk.csp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CspService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 範例程式
     */
    public Map<String, Object> demoCode(Map<String, Object> demoMap) {
        String id = demoMap.get("id").toString();
        String keyword = demoMap.get("keyword").toString();
        System.err.println("id--->" + id);
        System.err.println("keyword--->" + keyword);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("success", true);
        rtnMap.put("returnMessage", "驗證成功");
        rtnMap.put("dataList", new ArrayList<>());
        return rtnMap;
    }

    /**
     * API-1: 查詢商品(模糊查詢) - 完美對齊前端網頁的 returnCode / list 格式
     */
    public Map<String, Object> queryProducts(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        
        try {
            String name = (map != null && map.get("name") != null) ? map.get("name").toString() : "";
            
            String sql;
            List<Map<String, Object>> products;

            if (name.trim().isEmpty()) {
                sql = "SELECT product_id AS productId, name, price, stock, description, update_time AS updateTime FROM product";
                products = jdbcTemplate.queryForList(sql);
            } else {
                sql = "SELECT product_id AS productId, name, price, stock, description, update_time AS updateTime FROM product WHERE name LIKE ?";
                products = jdbcTemplate.queryForList(sql, "%" + name.trim() + "%");
            }

            if (products == null || products.isEmpty()) {
                rtnMap.put("returnCode", "E001");
                rtnMap.put("returnMsg", "查無資料");
                rtnMap.put("list", new ArrayList<>());
            } else {
                rtnMap.put("returnCode", "0000");
                rtnMap.put("returnMsg", "查詢成功");
                rtnMap.put("list", products);
            }

        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("returnCode", "E999");
            rtnMap.put("returnMsg", "系統異常: " + e.getMessage());
            rtnMap.put("list", null);
        }

        return rtnMap;
    }

    /**
     * API-2: 建立訂單
     */
    public Map<String, Object> createOrder(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "訂單建立成功");
        return rtnMap;
    }

    /**
     * API-3: 查詢訂單(單筆)
     */
    public Map<String, Object> queryOrder(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "查詢成功");
        return rtnMap;
    }

    /**
     * API-4：修改訂單
     */
    public Map<String, Object> editOrder(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "修改成功");
        return rtnMap;
    }

    /**
     * API-5：完成訂單
     */
    public Map<String, Object> completeOrder(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "完成成功");
        return rtnMap;
    }

    /**
     * API-6：刪除訂單
     */
    public Map<String, Object> deleteOrder(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "刪除成功");
        return rtnMap;
    }

    /**
     * API-7：新增評論
     */
    public Map<String, Object> createReview(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");
        rtnMap.put("returnMsg", "評論新增成功");
        return rtnMap;
    }
}