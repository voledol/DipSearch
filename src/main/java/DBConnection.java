import model.Index;

import java.sql.*;

public class  DBConnection {
    private static Connection connection;
    private static final String userName = "valgall";
    private static final String passwordDB = "Gorila123";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/search_engine?useSSL=false&serverTimezone=UTC";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, userName, passwordDB);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void insertPage(sitePage page) {
        String insertQuery = "('" + page.getPath() + "', '" + page.getCode() + "', '" + page.getContent() + "')";
        String sql = "INSERT INTO search_engine(path, code, content) " +
                "VALUES" + insertQuery + ";";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIndex(Index index) {
        String insertQuery = "(" + index.getPage_id() + ", " + index.getLemma_id() + ", " + index.getRank() + ")";
        String sql = "INSERT INTO index(page_id, lemma_id, rank) " +
                "VALUES" + insertQuery + ";";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static float getRank(int page_id, int lemm_id) {
        String sql = "SELECT lemma_rank FROM search_engine.indexeslist where page_id = " + page_id + " and lemma_id = " + lemm_id + ";";
        float rank = 0;
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) {
                rank = rs.getFloat(1);
            } else {
                rank = 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rank;
    }

    public static ResultPage getPage(int id) {
        ResultPage page = new ResultPage();
        String sql = "SELECT path FROM search_engine.page where id = " + id + " limit 1;";
        String url;
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
            if(rs.next()){
                url = rs.getString(1);
                page.setUrl(url);
                page.setTitle(ColumCreator.getTitle(url));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        return page;
    }
}
