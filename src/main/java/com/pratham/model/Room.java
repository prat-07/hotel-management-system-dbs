package com.pratham.model;

public class Room {
    private int roomNo;
    private RoomType type;
    private double price;
    private boolean isAvailable;

    public Room(int roomNo, RoomType type, double price) {
        this.roomNo = roomNo;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    //Getters
    public int getRoomNo() { return roomNo; }
    public RoomType getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    //Setter
    public void setAvailable(boolean value){this.isAvailable = value;}
}

