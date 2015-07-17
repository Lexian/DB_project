package DB;

/**
 *
 * @author Daniel Junker
 */


import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JDBC {
    private Boolean isConnected;

    // private variables to encapsulate database tier
    private Connection dbcon;
    private static Statement stmt;
    private PreparedStatement sql;

    // Open an Connection
    public JDBC(String db_url, String username, char[] pw){
        String password = new String(pw);
        isConnected = null;
        // create database connection and a statement object to sent queries
        try{
            // jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0544321__beleg_junker_yarin
            dbcon= DriverManager.getConnection("jdbc:postgresql://"+db_url+":5432/_s0544321__beleg_junker_yarin", username, password);
            stmt= dbcon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            isConnected = true;
        }
        catch (SQLException e){
            isConnected = false;
            System.out.println("Can't get a connection: "+e.toString());
            try{
                if (stmt!=null) stmt.close();
            } catch(SQLException f){}
            try{
                if (dbcon!=null) dbcon.close();
            } catch(SQLException f){}
//            System.exit(0);
        }
    }



    // Method to send queries to DB-Server,  Returns ResultSet if query successfull -null otherwise
   public  ResultSet executeQuery(String query){
        try{
            return stmt.executeQuery(query);
        }
        catch (SQLException e){
            System.out.println("Can't execute Query!" + e.toString());
        }
        return null;
    };

    public int executeUpdate(String query){
        try{
            return stmt.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("Can't execute Query!"+e.toString());
        }
        return 0;
    }



    // Method to close the database
    public void close_database()
    {
        try{
            if (stmt!=null)
                stmt.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if (dbcon!=null) dbcon.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("\nDatabase closed.");
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void close() {
        try {
            dbcon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


};

