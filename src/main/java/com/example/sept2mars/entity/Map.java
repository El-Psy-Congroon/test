package com.example.sept2mars.entity;


import java.io.Serializable;

public class Map implements Serializable {
    Room outside, theater, pub, lab, office,randomRoom;
    private static Map map=new Map();
    private Map(){
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        //增加具有传输功能的房间，每当玩家进入这个房间，就会被随机地传输到另一个房间；
        randomRoom=new Room("in the randomRoom");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", randomRoom);
        outside.setObjects("gold","1kg");
        outside.setObjects("coin","10kg");

        randomRoom.setExit("south",outside);

        theater.setExit("west", outside);
        theater.setObjects("gold","1kg");

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
    }

    public static Map getInstance(){
        return map;
    }

    @Override
    public String toString() {
        return "Map{" +
                "outside=" + outside +
                ", theater=" + theater +
                ", pub=" + pub +
                ", lab=" + lab +
                ", office=" + office +
                ", randomRoom=" + randomRoom +
                '}';
    }
}
