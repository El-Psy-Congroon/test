/**
 * 房间类，一个Room类代表游戏中的一个位置。
 * 房间可以由出口通往另一个房间。
 */
package com.example.sept2mars.entity;

import java.util.HashMap;
import java.util.Set;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    //添加功能，使得一个房间里可以存放任意数量的物件
    //每个物件可以有一个描述和一个重量值，玩家进入一个房间后，可以通过“look”命令查看当前房间的信息以及房间内的所有物品信息
    private HashMap<String,String> objects;


    /**
     * Room类的构造器
     * @param description 当前房间的描述信息
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        objects=new HashMap<>();
    }

    /**
     * 扩展功能
     * @return 返回当前房间内物品信息
     */
    public HashMap<String, String> getObjects() {
        return objects;
    }

    /**
     * 扩展功能
     * @param description 物品描述
     * @param weight 物品重量
     */
    public void setObjects(String description,String weight) {
        objects.put(description,weight);
    }

    /**
     * 设置房间出口
     * @param direction 出口的方向
     * @param neighbor 出口方向对应的房间
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return 返回当前房间的简略描述信息
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * @return 返回当前房间的详细描述信息
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * @return 返回出口方向
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * 获取指定方向的房间信息
     * @param direction 出口方向
     * @return 该方向的房间信息
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

}


