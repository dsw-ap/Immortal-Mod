package com.tearhpi.immortal.screen._skill.line;

import net.minecraft.client.gui.components.AbstractWidget;

public class WidgetConnection {
    public final AbstractWidget from;
    public final AbstractWidget to;

    public WidgetConnection(AbstractWidget from, AbstractWidget to) {
        this.from = from;
        this.to = to;
    }

    public int getFromCenterX() {
        return from.getX() + from.getWidth() / 2;
    }

    public int getFromCenterY() {
        return from.getY() + from.getHeight() / 2;
    }

    public int getToCenterX() {
        return to.getX() + to.getWidth() / 2;
    }

    public int getToCenterY() {
        return to.getY() + to.getHeight() / 2;
    }
}
