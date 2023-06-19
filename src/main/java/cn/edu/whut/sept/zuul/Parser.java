package cn.edu.whut.sept.zuul;

import java.util.Scanner;
/**
 * 解析器类
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    /**
     * 创建解析器，从终端读取用户输入
     */
    public Parser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }
    /**
     * 获取用户输入的指令
     * @return Command
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt
        //判断输入的前两个单词
        inputLine = reader.nextLine();
        //将输入信息传递至tokenizer
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
            }
        }
        //判断前一个词汇是否为已知指令词,如果是，用以创建指令
        Command command = commands.get(word1);
        if(command != null) {
            command.setSecondWord(word2);
        }
        return command;
    }
    /**
     * 显示所有有效指令
     */
    public void showCommands()
    {
        commands.showAll();
    }
}

