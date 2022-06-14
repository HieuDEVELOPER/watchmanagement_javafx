package com.example.watchmanagement;

import com.example.watchmanagement.models.Watch;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBconnection {
  public Connection connection;
  public DBconnection(){
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost/hieu_javafx","root", "");
      System.out.println("connected !!!");
    } catch (SQLException e) {
      System.out.println(e.getMessage());

    }
  }
  public ArrayList<Watch> getWatches(){
    ArrayList<Watch> List = new ArrayList<>();
    String sql = "SELECT * FROM watches";
    try {
      ResultSet results = connection.prepareStatement(sql).executeQuery();
      while (results.next()){

        Watch watch = new Watch(
                results.getInt("id"),
                results.getString("name"),
                results.getString("image"),
                results.getInt("price"),
                results.getString("description")
        );
        List.add(watch);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return List;
  }
  public void insertWatch(Watch watch){
    String sql="INSERT INTO watches (Name, Image, Price, Description) VALUES('" + watch.name+"', '"+ watch.image+"',"+ watch.price+", '"+watch.description+"')";
    System.out.println(sql);
    try {
      connection.prepareStatement(sql).executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public void updateWatch( Watch watch){
    String sql = "UPDATE watches SET name ='" + watch.name+"', image = '"+ watch.image+"', price="+watch.price+" WHERE id = "+ watch.id;
    System.out.println(sql);
    try {
      connection.prepareStatement(sql).executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteWatch(int id) {
    String sql= "DELETE FROM watches WHERE id="+id ;
    System.out.println(sql);
    try {
      connection.prepareStatement(sql).executeUpdate();
      System.out.println("Da xoa");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
