package DB;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.text.StringContent;
import java.util.Date;

/**
 * Created by danieljunker on 16.07.15.
 */
public class DBQueries {

    private String sql;

    public DBQueries() {
        this.sql = new String();
    }


    // insert Schmarren

    public String insertSong(String bandName,String duration, String bandID, String albumID){
            return "INSERT INTO song(title, duration, band_id, album_id)"+ " VALUES('"+bandName+"','"+duration+"','"+bandID+"','"+albumID+"')";

    }
    public String insertItemsToDB(String table, String leftColumn, String rightColumn, String leftItem, String rightItem) {
        return "INSERT INTO "+table+"("+leftColumn+","+rightColumn+") VALUES ('"+leftItem+"','"+rightItem+"')";
    }

    public String insertList(String table, String column_one,String column_two,String id_one, String id_two){

        return"INSERT INTO "+table+"("+column_one+", "+column_two+") VALUES ('"+id_one+"','"+id_two+"')";
    }
    public String insertBand(String bandName){
            return "INSERT INTO band(name)"+ " VALUES('"+bandName+"')";

    }
    public String insertAlbum(String albumName, java.sql.Date date,String band_id){
            return "INSERT INTO album(name, release_date, band_id)"+ " VALUES('"+albumName+"','"+date+"','"+band_id+"')";

    }
    public String insertTour(String tourName, Date startDate, Date endDate,String band_id){
            return "INSERT INTO tour(name, begin_date, end_date, band_id)"+ " VALUES('"+tourName+"','"+startDate+"','"+endDate+"','"+band_id+"')";

    }

    // delete


    // loeschen und einlesen  Schmarrn und View Schmarrn

    public String deleteDS(String table, String id){
        return "DELETE FROM " + table + " WHERE "+table+".band_id='" + id+"'";
    }
    public String deleteDS(String table, String column, String s) {
        return "DELETE FROM " + table + " WHERE "+table+"."+column+"='" + s+"'";
    }

    public String getElements(String table,String condition, String id){
        return "SELECT * FROM " + table +  " WHERE "+condition+"='"+id+"'";
    }
    //SELECT  title, duration, array_to_string(array(SELECT  name FROM genre g , belongs_to b, song s WHERE b.song_id=s.song_id and g.genre_id=b.genre_id ),',') FROM song s, band b WHERE s.band_id= 1 GROUP BY 1,2,3 Order BY 1
    // "song","title","duration","genre","genre_id","song_id",bandid)
    public String getElementsJoinSong(String id){
        return "SELECT  title, duration, array_to_string(array(SELECT  name FROM genre g , belongs_to b, song s WHERE b.song_id=s.song_id and g.genre_id=b.genre_id and s.band_id="+id+"),',') " +
                "FROM song s, band b WHERE s.band_id= "+id+" GROUP BY 1,2,3 Order BY 1";
    }
    //SELECT album.name, album.release_date, array_to_string(array(select duration from song where album_id=2 order by album_id),',' ) FROM album group by 1, 2 , 3 order by 1
    public String getElementsJoinAlbumTour(String table, String column, String column_two, String joinTable, String joinColumn, String condition, String id, String groupOrder){
        return "SELECT "+table+"."+column+", "+table+"."+column_two+", array_to_string(array(select "+joinColumn+" from "+joinTable+" where "+condition+"="+id+" order by "+condition+"),',' ) FROM "+table+" WHERE "+table+".band_id="+id+" group by "+groupOrder+" order by 1";
    }
    /*SELECT name FROM band*/
    public  String getElements(String table, String column){
        return "SELECT "+column+" FROM "+table;
    }
    /*SELECT cities.name,country.name FROM cities INNER JOIN country ON cities.country_id = country.country_id */
    public String getElements(String table, String name, String scndTable, String innerJoinOn) {
        return "SELECT "+table+"."+name+", "+scndTable+"."+name+" FROM "+table+" INNER JOIN "+scndTable+" ON "+table+"."+innerJoinOn+"="+scndTable+"."+innerJoinOn+"";
    }

    public String getElements(String table, String column,String operator ,String secTable ,String comparedValue) {
        String sql = "SELECT " +column+ " FROM " +table+" WHERE " +operator+"= (SELECT " +operator+" FROM "+secTable+" WHERE "+column+"='"+comparedValue+"' )";
        return  sql;
    }

    public String getID(String column, String table,String searchRequest,  String condition) {
        return "SELECT "+column+" FROM "+table+" WHERE "+searchRequest+"='"+condition+"'";
    }

    // SELECT duration FROM song WHERE album_id = (SELECT album_id FROM album WHERE name = 'iReise Reise')
    //"song","duration","album","album_id",rs.getString(""+column+"")
    public String countTime(String table, String column, String conditionTable, String condition, String conditionName) {
        return "SELECT "+column+" FROM "+table+" WHERE "+condition+"= (SELECT "+condition+" FROM "+conditionTable+" WHERE name='"+conditionName+"')  ";
    }




    //
//select a.name from album a, band b, song s where s.album_id=a.album_id and s.band_id = b.band_id



}
