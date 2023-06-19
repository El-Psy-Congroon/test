/**
 * 该类是“World-of-Zuul”应用程序的主类。
 * 《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。
 * 你们可以通过扩展该游戏的功能使它更有趣!.
 *
 * 如果想开始执行这个游戏，用户需要创建Game类的一个实例并调用“play”方法。
 *
 * Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
 * 接收用户输入，并将用户输入转换成命令后开始运行游戏。
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 1.0
 */
package com.example.sept2mars.entity;

import java.util.HashMap;
import java.util.Random;


public class Game
{
    private Parser parser;
    private Room currentRoom;
    //扩展功能，存储上一条命令
    private Command lastCommand;

    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office,randomRoom;

        // create the rooms
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
        outside.setObjects("dog","10kg");

        randomRoom.setExit("south",outside);

        theater.setExit("west", outside);
        theater.setObjects("gold","1kg");

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            //保存上一条命令
            if(command.getCommandWord().equals("go")) {
                lastCommand = command;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * 执行用户输入的游戏指令.
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                wantToQuit = quit(command);
                break;
            case "look":
                look();
                break;
            case "back":
                back();
                break;
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * 执行help指令，在终端打印游戏帮助信息.
     * 此处会输出游戏中用户可以输入的命令列表
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * 扩展功能，返回到上一个房间
     */
    private void back(){
        if(lastCommand==null){
            System.out.println("Can not go back!");
            return;
        }
        String direction=lastCommand.getSecondWord();
        switch (direction){
            case "east":
                direction="west";
                break;
            case "west":
                direction="east";
                break;
            case "north":
                direction="south";
                break;
            case "south":
                direction="north";
                break;
        }
        Command command=new Command("go",direction);
        processCommand(command);
    }

    /**
     * 扩展功能
     * 显示当前房间内所有物品信息
     */
    private void look(){
        HashMap<String, String> objects=currentRoom.getObjects();
        if(objects.size()==0){
            System.out.println("There are no objects!");
            return;
        }
        for(String object:objects.keySet()){
            System.out.print(object+"  ");
            System.out.println(objects.get(object));
        }
    }

    /**
     * 执行go指令，向房间的指定方向出口移动，如果该出口连接了另一个房间，则会进入该房间，
     * 否则打印输出错误提示信息.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            //随机传送
            if(currentRoom.getShortDescription().equals("in the randomRoom")){
                Random random=new Random();
                int index=random.nextInt(5);
                switch (index) {
                    case 0:
                        currentRoom = currentRoom.getExit("south").getExit("east");
                        break;
                    case 1:
                        currentRoom = currentRoom.getExit("south").getExit("south");
                        break;
                    case 2:
                        currentRoom = currentRoom.getExit("south").getExit("west");
                        break;
                    case 3:
                        currentRoom = currentRoom.getExit("south").getExit("south").getExit("east");
                        break;
                    case 4:
                        currentRoom = currentRoom.getExit("south");
                        break;
                }
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }

    /**
     * 执行Quit指令，用户退出游戏。如果用户在命令中输入了其他参数，则进一步询问用户是否真的退出.
     * @return 如果游戏需要退出则返回true，否则返回false.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}