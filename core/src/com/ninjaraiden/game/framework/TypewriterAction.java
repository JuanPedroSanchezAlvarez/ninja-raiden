package com.ninjaraiden.game.framework;

public class TypewriterAction extends SetTextAction {

    private static final float CHARACTERS_PER_SECOND = 30;
    private float elapsedTime;
    private int numberOfCharacters;
    private String partialText;

    public TypewriterAction(final String t) {
        super(t);
        elapsedTime = 0;
        numberOfCharacters = 0;
        partialText = "";
    }

    @Override
    public boolean act(final float delta) {
        elapsedTime += delta;
        numberOfCharacters = (int)(elapsedTime * CHARACTERS_PER_SECOND);
        if (numberOfCharacters > textToDisplay.length()) {
            numberOfCharacters = textToDisplay.length();
        }
        partialText = textToDisplay.substring(0, numberOfCharacters);
        dialogBox = (DialogBox)target;
        dialogBox.setText(partialText);
        return (numberOfCharacters >= textToDisplay.length());
    }

}
