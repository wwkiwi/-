package com.cathaybk.csp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // 1. 建立商品表 (product)
            stmt.execute("CREATE TABLE IF NOT EXISTS product (" +
                    "product_id INT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "price INT NOT NULL, " +
                    "stock INT NOT NULL, " +
                    "description TEXT, " +
                    "update_time TEXT NOT NULL);");

            // 2. 建立訂單主檔 (orders)
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "order_id TEXT PRIMARY KEY, " +
                    "buyer_name TEXT NOT NULL, " +
                    "phone TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "address TEXT NOT NULL, " +
                    "total_amount INT NOT NULL, " +
                    "status TEXT NOT NULL, " +
                    "create_time TEXT NOT NULL, " +
                    "review TEXT);");

            // 3. 建立訂單明細檔 (order_item)
            stmt.execute("CREATE TABLE IF NOT EXISTS order_item (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "order_id TEXT NOT NULL, " +
                    "product_id INT NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "unit_price INT NOT NULL, " +
                    "line_amount INT NOT NULL);");

            // 4. 檢查商品表是否已有資料，若無則塞入 6 筆測試商品
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM product");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO product VALUES (1001, '藍芽鍵盤', 999, 5, '輕薄小巧, 設計時尚', '2025/09/13/14:15');");
                stmt.execute("INSERT INTO product VALUES (1002, '藍芽耳機', 700, 10, '降噪、長續航, 適合通勤使用', '2025/09/13/14:15');");
                stmt.execute("INSERT INTO product VALUES (1003, '多功能集線器', 200, 5, '含HDMI、USB3.0、有SD卡插槽', '2025/09/13/14:15');");
                stmt.execute("INSERT INTO product VALUES (1004, '人體工學滑鼠', 500, 3, '符合手型, 長時間使用不疲勞', '2025/09/13/14:15');");
                stmt.execute("INSERT INTO product VALUES (1005, 'HDMI Cable', 299, 2, '2m, 輕巧方便', '2025/08/08/13:30');");
                stmt.execute("INSERT INTO product VALUES (1006, '行動電源', 799, 5, '支援PD快充, 雙USB輸出', '2025/09/13/14:15');");
                System.out.println("=== 成功初始化 6 筆測試商品資料到 SQLite！ ===");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}