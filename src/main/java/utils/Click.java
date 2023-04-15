package utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Instant;

public class Click {

    public static void printUsage() {
        System.out.println("Usage: \n     Click <interval-seconds | 60>");
        System.out.println("   Example:   Click 3600");
        System.out.println("   Example:   Click 3600000 \"\\n\\n\"");
    }

    public static void main(String[] args) {

        int interval = 60;

        try {

            if (args.length == 1) {
                interval = Integer.parseInt(args[0]);
            }

            Robot robot = new Robot();

            // Simulate a mouse click
            //robot.mousePress(InputEvent.BUTTON1_MASK);
            //arobot.mouseRelease(InputEvent.BUTTON1_MASK);

            while (true) {
                System.out.println(Instant.now() + " kkk");

                // Simulate a key press
                robot.keyPress(KeyEvent.VK_F24);
                robot.keyRelease(KeyEvent.VK_F24);

                Thread.sleep(interval * 1000L);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
