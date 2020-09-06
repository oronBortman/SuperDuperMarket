package logic.metadata;

import commonUI.*;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import exceptions.TaskIsCanceledException;

public class CollectMetadataTask extends Task<Boolean> {

    private String fileName;
    private Consumer<Runnable> onCancel;

    private final int SLEEP_TIME = 100;

    public CollectMetadataTask(String fileName, Consumer<Runnable> onCancel) {
        this.fileName = fileName;
        this.onCancel = onCancel;

    }

    @Override
    protected Boolean call() throws Exception {

        try {

            updateMessage("Fetching file...");
            BufferedReader reader = Files.newBufferedReader(
                    Paths.get(fileName),
                    StandardCharsets.UTF_8);

            updateMessage("Counting numbers..");
            int totalNum=50;
            updateProgress(0, totalNum);

            for(int i=1; i<=totalNum; i++)
            {
                if (isCancelled()) {
                    throw new TaskIsCanceledException();
                }
                updateProgress(i, totalNum);
                SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
            }

            updateMessage("Done...");
        } catch (IOException e) {
            e.printStackTrace();
        }
     catch (TaskIsCanceledException e) {
        SuperDuperMarketUtils.log("Task was canceled !");
        onCancel.accept(null);
    }
    updateMessage("Done...");
        return Boolean.TRUE;

    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }

}