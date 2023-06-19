package cn.edu.whut.sept.zuul;

import java.util.Set;
import java.util.HashMap;
/**
 * 房间类
 */
public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    /**
     * 创建房间，创建description
     * @param description
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
    }
    /**
     * 用于定义房间出口
     * @param direction
     * @param neighbor
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    /**
     * 返回房间描述
     * @return 房间描述
     */
    public String getShortDescription()
    {
        return description;
    }
    /**
     * 返回当前房间的详细描述："你位于__，出口:__"
     * @return  房间描述，房间出口
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }
    /**
     * 获取出口信息
     * @return 出口方向
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
     * 要走的方向
     * @param direction
     * @return 走入下一个房间
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}


