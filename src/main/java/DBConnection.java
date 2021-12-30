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

    //    public static Connection getConnection(){
//        if(connection == null){
//            try{
//                connection = DriverManager.getConnection(url,userName,passwordDB);
//                connection.createStatement().execute("DROP TABLE IF EXISTS search_engine");
//                connection.createStatement().execute("CREATE TABLE search_engine(" +
//                        "id INT NOT NULL AUTO_INCREMENT, " +
//                        "path TEXT NOT NULL, " +
//                        "code INT NOT NULL, " +
//                        "content MEDIUMTEXT NOT NULL, " +
//                        "PRIMARY KEY(id)," +
//                        "UNIQUE KEY  path_id(path(50), id))");
//            }
//            catch (SQLException e){
//                e.printStackTrace();
//
//            }
//        }
//        return connection;
//    }
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
}
