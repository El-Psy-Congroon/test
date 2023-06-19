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
 * @author  Michael K?lling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.Stack;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomsVisited = new Stack<>();
    private Player player;

    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
        player = new Player();
    }

    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     */
    private void createRooms()
    {
        Room outside, roomTwo, roomThree, roomFour, roomFive;

        //create items
        Item swordOne,swordTwo,swordThree,necklace;
        swordOne = new Item("奇特形状的深红色大剑","原初之火(Aestus Estus),剑上刻着Regnum Caelorum et Gehenna(天堂与地狱)",4);
        swordTwo = new Item("一对完好黑白短剑","从一地相同武器的碎片看得出来,它们是这里的主人的惯用武器",2);
        swordThree = new Item("石中剑","仅有被选中的人能拔出它",99);
        necklace = new Item("红宝石项链","原本蕴含大量魔力的项链,但不知为何魔力已经耗尽,现在只是一条普通的项链",1);
        // create the rooms
        outside = new Room("这里是外面");
        roomTwo = new Room("一个过于夸张的剧场,能感受到主人如烈焰一般的热情");
        roomTwo.addItemToRoom(swordOne);
        roomThree = new Room("一个插满剑的山丘，一把把剑看着就像一块块墓碑");
        roomThree.addItemToRoom(necklace);
        roomThree.addItemToRoom(swordTwo);
        roomFour = new Room("一个空房间");
        roomFive = new Room("房间的中央有一个插着剑的石台");
        roomFive.addItemToRoom(swordThree);

        // initialise room exits
        outside.setExit("east", roomTwo);
        outside.setExit("south", roomFour);
        outside.setExit("west", roomThree);

        roomTwo.setExit("west", outside);

        roomThree.setExit("east", outside);

        roomFour.setExit("north", outside);
        roomFour.setExit("east", roomFive);

        roomFive.setExit("west", roomFour);

        currentRoom = outside;  // start game outside
        roomsVisited.add(currentRoom);
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
            finished = processCommand(command, player);
        }
        System.out.println("感谢游玩,再见");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("欢迎来到我也不知道是哪里的地方!");
        System.out.println("你将作为测试员来帮忙测试这款游戏");
        System.out.println("输入“help”来获得帮助");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * 执行用户输入的游戏指令.
     *
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    private boolean processCommand(Command command, Player player)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("这只是个菜鸡程序员做的游戏,没那么多选项");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "help" -> printHelp();
            case "go" -> goRoom(command);
            case "look" -> look();
            case "back" -> back();
            case "take" -> takeItem(currentRoom, command, player);
            case "drop" -> dropItem(command, player, currentRoom);
            case "dropall" -> dropAllItems(player, currentRoom);
            case "items" -> listAllItemsOfPlayer(player);
            case "quit" -> wantToQuit = quit();
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
        System.out.println("看来你需要一点帮助,但我也不知道该怎么办...");
        System.out.println("开个玩笑");
        System.out.println();
        System.out.println("你有这些命令词:");
        parser.showCommands();
    }

    /**
     * 执行go指令，向房间的指定方向出口移动，如果该出口连接了另一个房间，则会进入该房间，
     * 否则打印输出错误提示信息.
     */
    private void goRoom(Command command)
    {
        if(!Command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("go！go！go！...所以说往哪个方向走？");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("这里可没路");
        }
        else {
            currentRoom = nextRoom;
            roomsVisited.add(currentRoom);
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * 执行Quit指令，用户退出游戏。如果用户在命令中输入了其他参数，则进一步询问用户是否真的退出.
     * @return 如果游戏需要退出则返回true，否则返回false.
     */
    private boolean quit()
    {
        if(Command.hasSecondWord()) {
            System.out.println("退出只需要“quit”就行了");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void back()
    {
        if(Command.hasSecondWord()){
            System.out.println("返回只需要”back“就行了");
            return;
        }
        if(roomsVisited.size() == 1){
            System.out.println("你已回到起点\n");
        }
        else{
            roomsVisited.pop();
            currentRoom = roomsVisited.lastElement();
            System.out.println("你往回走\n" + currentRoom.getLongDescription() + "\n");
        }

    }

    private void look()
    {
        String result = currentRoom.getLongDescription();
        result += currentRoom.getAllItemsInRoom();
        System.out.println(result);

    }
    private void takeItem(Room currentRoom, Command command, Player player) {
        Item item = currentRoom.takeItem(command.getSecondWord());
        if (item == null){
            System.out.println("该房间里没有此物品");
        }
        else
            player.addItemToInventory(item);
    }

    private void dropItem(Command command, Player player, Room currentRoom) {
        Item itemToDrop = player.dropItemFromInventory(command.getSecondWord());
        if (itemToDrop != null) {
            currentRoom.addItemToRoom(itemToDrop);
            System.out.println("物品已丢弃");
        }
        else
            System.out.println("无法丢弃该物品");
    }

    private void dropAllItems(Player player, Room currentRoom) {
        player.dropAllItemsFromInvetroy(currentRoom);
        System.out.println("你丢弃了背包中的所有物品");
    }

    private void listAllItemsOfPlayer(Player player) {
        player.getAllItems();
    }

}