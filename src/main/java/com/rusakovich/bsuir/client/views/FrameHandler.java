package com.rusakovich.bsuir.client.views;

public class FrameHandler {
    public static void displayFrame(String frameName) {
        LifeJFrame frame = FrameHolder.getFrameByName(frameName);
        frame.setVisible(true);
        frame.onVisible();
    }
}
