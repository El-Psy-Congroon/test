package cn.edu.whut.sept.zuul;

public class Command
{
    private String commandWord;
    private static String secondWord;
    public static String back;

    /**
     * 创建命令对象. 必须提供第一个和第二个单词，但其中一个（或两个）可以为空。
     * @param firstWord 命令的第一个单词。如果命令未被识别，则为空。
     * @param secondWord 命令的第二个单词。
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * 返回此命令的命令字（第一个字）。如果未理解该命令，则结果为空。
     * @return 命令字。
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return 此命令的第二个单词。
     */
    public String getSecondWord()
    {
        back=secondWord;
        return secondWord;
    }

    /**
     * @return 判断信息是否与已知相匹配，匹配就返回1，不匹配就返回0
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return 判断用户输入的命令有没有第二个单词，有则返回1，否则返回0
     */
    public static boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}
