package com.wyw.utils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class qqSend {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            robot.delay(8000);
            for (int i = 0; i < 5; i++) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("围攻光明顶Day2——现在排名：\n" +
                        "【华山派】总计5647.0：\n" +
                        "    孙晓艳:5140.0\n" +
                        "    张宸祎:507.0\n" +
                        "【崆峒派】总计5554.87：\n" +
                        "    王若诗蓝:3059.17\n" +
                        "    张梦慧:2495.7\n" +
                        "【峨眉派】总计2670.0：\n" +
                        "    陈蓁蓁:2570.0\n" +
                        "    韩  雪:100.0\n" +
                        "【武当派】总计2595.96：\n" +
                        "    黄宣绮:2567.0\n" +
                        "    李思奥:28.96\n" +
                        "【少林寺】总计2257.46：\n" +
                        "    周  湘:2057.46\n" +
                        "    唐晨葳:200.0\n" +
                        "【明教】总计962.5：\n" +
                        "    朱虹蓉:730.0\n" +
                        "    郭晓盈:232.5\n" +
                        "【华山派】目前为第1名，领先92.13\n"), null);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyPress(KeyEvent.VK_CONTROL);//qq设置的是ctrl+enter发送消息
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(3000);
            }
            //无限炸，这个快
/*            while (true) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("你好，帅小伙"), null);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(100);
            }*/
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
