package DB;

/**
 * Created by danieljunker on 16.07.15.
 */
public class DBQueries {

    private String sql;

    public DBQueries() {
        this.sql = new String();
    }

    protected String InsertSong(String tblName , String bandName,int duration, int bandID, int albumID){
            return "INSERT INTO"+ tblName +"(title, duration, band_id, album_id)"+ "VALUES("+bandName+","+duration+","+bandID+","+albumID+")";

    }

    public String getTableList() {
        return "";
    }
}
