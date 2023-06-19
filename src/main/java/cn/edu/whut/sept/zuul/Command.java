package cn.edu.whut.sept.zuul;

public abstract class Command
{
    private String secondWord;

    public Command()
    {
        secondWord = null;
    }
    /**
     * 获取第二个单词
     * @return secondWord
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    /**
     * 判断是否有第二个词
     */
    public boolean hasSecondWord()
    {
        return secondWord != null;
    }

    public void setSecondWord(String secondWord)
    {
        this.secondWord = secondWord;
    }

    public abstract boolean execute(Game game);
}
