package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> itemList;

    /**
     * 创建一个描述为“description”的房间。
     * @param description 房间的描述。
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        itemList = new ArrayList<Item>();
    }

    public Room(String description, Item item) {
        this(description);
        itemList = new ArrayList<>();
        this.itemList.add(item);
    }
    /**
     * 定义此房间的出口。
     * @param direction 出口的方向。
     * @param neighbor  出口通向的房间。
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return 房间的简短描述
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * 返回表格中的房间描述:
     *     description
     *     出口: east north
     * @return 此房间的详细描述
     */
    public String getLongDescription()
    {
        return description + "\n" + getExitString();
    }

    /**
     * 返回描述房间出口的字符串,例如:
     * "出口: north west".
     * @return 房间出口的详细信息。
     */
    private String getExitString()
    {
        String returnString = "出口:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * 返回从该房间向某个方向走所到达的房间
     * "direction". 如果该方向没有房间，则返回为null
     * @param direction 出口方向
     * @return 给定方向的房间
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public void addItemToRoom(Item item) {
        itemList.add(item);
    }

    public String getAllItemsInRoom() {
        String result = "\n";
        boolean itemsInList = false;
        for(Item actualItem : itemList) {
             result += "你找到了: " + actualItem.getName() + "\n";
             result += "   " + "简介: " + actualItem.getDescription() + "\n";
             result += "   " + "重量: " + actualItem.getWeight() + "\n";
            itemsInList = true;
        }
        if(!itemsInList)
            result += "你什么也没找到\n";
        return result;
    }

    public Item takeItem(String name) {
        Item returnItem = null;
        for(Item actualItem : itemList) {
            if(actualItem.getName().equals(name)) {
                returnItem = actualItem;
            }
        }

        if(returnItem != null)
            itemList.remove(returnItem);

        return returnItem;

    }
}


