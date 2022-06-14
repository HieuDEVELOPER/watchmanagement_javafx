package com.example.watchmanagement.models;

public class Watch {
  public int id;
  public String name;
  public String image;
  public int price;
  public String description;

  public Watch(int id, String name, String image, int price, String description) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.price = price;
    this.description = description;
  }

  public Watch(String name, String image, int price, String Description ) {
    this.name = name;
    this.image = image;
    this.price = price;
    this.description = Description;
  }
}
