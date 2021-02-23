package me.oreoezi.datamanagers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Database {
    private Connection con;
    private Statement st;
    private ResultSet rs;
	
	public Database(String host, String port, String user, String password, String database) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", user, password);
			st = con.createStatement();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public Database(String path) {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + path);
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    @SuppressWarnings("unchecked")
	public JSONArray GetData(String query) {
        JSONArray retarray= new JSONArray();
        try {
            rs = st.executeQuery(query);
            
            while(rs.next()) {
                JSONObject jsontemp = new JSONObject();
                for (int col=1; col <= rs.getMetaData().getColumnCount(); col++) {
                    jsontemp.put(rs.getMetaData().getColumnName(col), rs.getObject(col));
                }
                retarray.add(jsontemp);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return retarray;
    }
    public void SetData(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void CloseConnection() {
    	try {
			con.close();
		} catch (SQLException e) {
		}
    }
}
