package com.example.rasghar.weather;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void connectSql(View view){

        TextView temp =(TextView)findViewById(R.id.weather_current);
        TextView humidity = (TextView)findViewById(R.id.humidity_info);
        String connectionUrl = "jdbc:jtds:sqlserver://weatherdata1.database.windows.net:1433/DataHost;user=devicelogin@weatherdata1;password=Pokemo1!23456789;";

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection(connectionUrl);
            Log.w("Connection","Open");
            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT TOP 1 * FROM dbo.SensorData ORDER BY Pollingtime Desc";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.

            if (rs.next()) {
                temp.setText(rs.getString(1));
                humidity.setText(rs.getString(2));
            }


        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }


    }
}
