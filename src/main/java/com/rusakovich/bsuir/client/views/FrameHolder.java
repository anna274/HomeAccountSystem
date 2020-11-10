package com.rusakovich.bsuir.client.views;

public enum FrameHolder {
    LOGIN(new LoginFrame()),
    REGISTRATION(new RegistrationFrame()),
    HOME(new HomeFrame());

    private final LifeJFrame frame;

    FrameHolder(LifeJFrame frame) {
        this.frame = frame;
    }

    public static LifeJFrame getFrameByName(String name) {
        for (FrameHolder value : FrameHolder.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value.frame;
            }
        }
        return LOGIN.frame;
    }
}
