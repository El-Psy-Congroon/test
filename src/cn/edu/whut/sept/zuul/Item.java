package cn.edu.whut.sept.zuul;

public class Item {
    private String name;
    private String description;
    private int weight;
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "[名称：" + name +"，简介：" + description + "，重量："+weight+ "]";
    }

    public int getWeight() {
        return this.weight;
    }
}
