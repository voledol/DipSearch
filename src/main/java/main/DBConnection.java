package main;

import model.Index;
import model.Page;
import model.ResultPage;
import netscape.javascript.JSObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public  static void insertPage(Page page) {
        String insertQuery = "('" + page.getPath() + "', '" + page.getCode() + "', '" + page.getContent() + "', "+ page.getSite_id()+");";
        String sql = "INSERT INTO page(path, code, content, site_id) " +
                "VALUES" + insertQuery ;
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
    public static void insertSite(AvailableSites.Site site){
        String insertQuery = "('" + site.getUrl() + "', '" + site.getName() +"')";
        String sql = "INSERT INTO search_engine( url, name) " +
                "VALUES" + insertQuery + ";";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static int getSiteId(String url) throws SQLException {
        String sql = "SELECT id FROM site where url = '" + url + "';";
           ResultSet  rs = DBConnection.getConnection().createStatement().executeQuery(sql);
           int id = 0;
           if(rs.next()){
                id =Integer.parseInt(rs.getString(1));
           }
           return id;

    }
    public static void updateSiteINDEXING(String URL){

        String sql = "Update site set status = 'INDEXING' where url ='"+ URL + "';";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void updateStatus(String URL) {
        Date status_time = new Date();
        String sql = "Update site set status_time = "+ status_time.toString() +" where url ='" + URL + "/';";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateSiteINDEXED(String URL){

        String sql = "Update site set status = 'INDEXED' where url ='"+ URL + "';";
        try {
            DBConnection.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getSites(){
        String sql = "SELECT url FROM site ";
        List<String> urls = new ArrayList<>();
        try {
            ResultSet rs =
                    DBConnection.getConnection().createStatement().executeQuery(sql);
            while(rs.next()){
                int i = 1;
                urls.add(rs.getString(i));
                i++;
            }
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;

    }
    public static boolean isThisSite(String url){
        String[] url1 =url.split("/");
        String urlfin = url1[0] + "//"+ url1[1] + url1[2];
        String sql = "SELECT url FROM site where url = '" + urlfin + "';";
        List<String> urls = new ArrayList<>();
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
            if(rs != null){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//    public static String statistic(){
//        String sql = "SELECT count(*) FROM site ";
//        String sql1 = "SELECT count(*) FROM page ";
//        String sql2 = "SELECT count(*) FROM lemma ";
//        int sites;
//        int pages;
//        int lemmas;
//        try {
//            ResultSet rsSites =
//                    DBConnection.getConnection().createStatement().executeQuery(sql);
//            sites = Integer.parseInt(rsSites.getString(1));
//            ResultSet rsPages =
//                    DBConnection.getConnection().createStatement().executeQuery(sql);
//            pages = Integer.parseInt(rsPages.getString(1));
//            ResultSet rsLemmas =
//                    DBConnection.getConnection().createStatement().executeQuery(sql);
//            lemmas = Integer.parseInt(rsLemmas.getString(1));
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
