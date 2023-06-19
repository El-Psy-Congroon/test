package cn.edu.whut.sept.zuul;

import java.util.ArrayList;

public class Player {
    private ArrayList<Item> itemList;
    int maxWeight;
    public Player() {
        itemList = new ArrayList<Item>();
        maxWeight = 10;
    }

    /**
     * 执行“items”命令，打印出当前携带的所有物品及其重量和总重量。
     */
    public void getAllItems() {
        String result = "你背包里的物品有：\n";
        boolean itemsInList = false;
        for(Item actualItem : itemList) {
            result += "名称：" + actualItem.getName() + "\n";
            result += "   " + "简介：" + actualItem.getDescription() + "\n";
            result += "   " + "重量：" + actualItem.getWeight() + "\n";
            itemsInList = true;
        }

        if(!itemsInList)
            result += "你的背包里没有物品\n";
        System.out.println(result);

    }

    public void addItemToInventory(Item item) {
        if (item.getWeight() <= inventoryCapacity()){
            itemList.add(item);
            System.out.println("你捡起了 " + item.getName());
        }
        else
            System.out.println("你的负重无法捡起该物品");
    }

    public int inventoryCapacity() {
        int totalWeight = 0;
        for(Item actualItem : itemList) {
            totalWeight += actualItem.getWeight();
        }
        return maxWeight - totalWeight;
    }

    public Item dropItemFromInventory(String itemName) {
        for(Item actualItem : itemList) {
            if (actualItem.getName().equals(itemName)) {
                itemList.remove(actualItem);
                return actualItem;
            }
        }
        return null;
    }

    public void dropAllItemsFromInvetroy(Room currentRoom) {
        for(Item actualItem : itemList) {
            currentRoom.addItemToRoom(actualItem);
        }
        itemList.clear();
    }
}
