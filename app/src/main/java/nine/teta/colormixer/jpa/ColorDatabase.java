package nine.teta.colormixer.jpa;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nine.teta.colormixer.adapter.FavouriteColorItemModel;
import nine.teta.colormixer.setup.FavouriteColorsSetup;

@Database(entities = {FavouriteColorItemModel.class}, version = 1, exportSchema = false)
public abstract class ColorDatabase extends RoomDatabase {
    public abstract FavouriteColorItemDao favouriteColorItemDao();

    // - We've defined a singleton, WordRoomDatabase, to prevent having multiple instances
    // of the database opened at the same time.
    // - getDatabase returns the singleton. It'll create the database the first time it's accessed,
    // using Room's database builder to create a RoomDatabase object in the application context
    // from the WordRoomDatabase class and names it "word_database".
    // - We've created an ExecutorService with a fixed thread pool that you will use
    // to run database operations asynchronously on a background thread.
    private static volatile ColorDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 2;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback setupDatabase = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            System.out.println("DOING DB SETUP");
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                FavouriteColorItemDao dao = INSTANCE.favouriteColorItemDao();
                dao.deleteAll();

                List<FavouriteColorItemModel> defaultList = new FavouriteColorsSetup().getDefaultList();
                System.out.println("defaultList");
                System.out.println(defaultList);
                dao.insertAll(defaultList);
            });
        }
    };

    static ColorDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ColorDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    ColorDatabase.class,
                                    "color_database")
                            .addCallback(setupDatabase)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
