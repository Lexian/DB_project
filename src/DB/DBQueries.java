package DB;

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

    public String InsertSong(  String bandName,int duration, int bandID, int albumID){
            return "INSERT INTO song (title, duration, band_id, album_id)"+ "VALUES("+bandName+","+duration+","+bandID+","+albumID+")";

    }
    protected String InsertBand(String bandName){
            return "INSERT INTO band (title)"+ "VALUES("+bandName+")";

    }
    protected String InsertAlbum(String AlbumName, Date releaseDate, int totDuration){
            return "INSERT INTO album (name, release_date, total_duration)"+ "VALUES("+AlbumName+","+releaseDate+","+totDuration+")";

    }
    protected String InsertTour(String AlbumName, Date releaseDate, int totDuration){
            return "INSERT INTO album (name, release_date, total_duration)"+ "VALUES("+AlbumName+","+releaseDate+","+totDuration+")";

    }


    // update Schmarrn und View Schmarrn
}
