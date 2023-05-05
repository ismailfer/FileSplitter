package utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Click {

    public static void printUsage() {
        System.out.println("Usage: \n     Click <interval-seconds | 60> <key code>");
        System.out.println("   Example:   Click 3600");
        System.out.println("   Example:   Click 10 0xF00B");
        System.out.println("   Example:   Click 3600000 \"\\n\\n\"");
    }

    public static void main(String[] args) {

        int interval = 60;
        int keycode = KeyEvent.VK_F12;

        try {

            if (args.length == 1) {
                interval = Integer.parseInt(args[0]);
            }
            else if (args.length == 2) {
                interval = Integer.valueOf(args[0]);
                keycode = Integer.decode(args[1]);

            }

            Robot robot = new Robot();

            // Simulate a mouse click
            //robot.mousePress(InputEvent.BUTTON1_MASK);
            //arobot.mouseRelease(InputEvent.BUTTON1_MASK);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");

            while (true) {
                System.out.println(formatter.format(System.currentTimeMillis()) + " " + interval + " 0x" + Integer.toHexString(keycode));

                // Simulate a key press
                //robot.keyPress(keycode);
                robot.keyRelease(keycode);

                Thread.sleep(interval * 1000L);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
