package nine.teta.colormixer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class someActivity extends AppCompatActivity implements Callable<String> {


    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        CompletableFuture
                .supplyAsync(()->"hello")
                .completeAsync(() -> "hello")
                .thenAccept(s -> System.out.println());
    }


    @Override
    public String call() throws Exception {
        return "Hello";
    }

}