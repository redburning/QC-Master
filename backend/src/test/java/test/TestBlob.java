package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestBlob {

	public static void main(String[] args) {
        String url = "jdbc:sqlite:src/main/resources/db/data.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT task_id, blob_rt FROM base_peak_current WHERE task_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        byte[] inputStream = rs.getBytes("blob_rt");
                        // 处理输入流
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
