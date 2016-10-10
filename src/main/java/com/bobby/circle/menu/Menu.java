package com.bobby.circle.menu;


import com.bobby.circle.SuperCircle;
import lombok.Getter;
import processing.core.PConstants;

@Getter
public class Menu {
    private static final int BUTTON_BORDER = 10;
    private static final int BUTTON_WIDTH = 600;
    private static final int BUTTON_HEIGHT = 200;
    private static SuperCircle game;
    private String title;
    private Button[] buttons;
    public Menu(String title, String... buttons) {
        game = SuperCircle.getInstance();
        this.title = title;
        this.buttons = new Button[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            this.buttons[i] = new Button(buttons[i], ((int) SuperCircle.mid.x), 200 + BUTTON_BORDER + (i * (BUTTON_HEIGHT + BUTTON_BORDER)) + BUTTON_HEIGHT/2, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    public void draw(float mouseX, float mouseY) {
        game.textAlign(PConstants.CENTER, PConstants.CENTER);
        game.text(title, SuperCircle.mid.x, game.textAscent()/2 + 30);
        for (Button button : buttons) {
            button.draw(button.contains(mouseX, mouseY)?120:60);
        }
    }

    public Button[] getButtons() {
        return buttons;
    }
}
