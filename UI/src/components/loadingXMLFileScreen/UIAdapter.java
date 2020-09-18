package components.loadingXMLFileScreen;

import javafx.application.Platform;

import java.util.function.Consumer;

public class UIAdapter {

    private Consumer<Integer> updateTotalProcessedWords;


    public UIAdapter(Consumer<Integer> updateTotalProcessedWords) {
        this.updateTotalProcessedWords = updateTotalProcessedWords;
    }

    public void updateTotalProcessedWords(int delta) {
        Platform.runLater(
                () -> updateTotalProcessedWords.accept(delta)
        );
    }

}
