package com.example.ianshinbro.trackerbat.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

import com.example.ianshinbro.trackerbat.Implentation.Game;
import com.example.ianshinbro.trackerbat.Implentation.Player;
import com.example.ianshinbro.trackerbat.R;
import com.example.ianshinbro.trackerbat.UI.Adapters.adapterHelpers.DividerItemDecoration;
import com.example.ianshinbro.trackerbat.UI.Adapters.GameAdapter;
import com.example.ianshinbro.trackerbat.UI.Adapters.adapterHelpers.ItemTouchHelperCallBack;
import com.example.ianshinbro.trackerbat.UI.Adapters.adapterHelpers.OnStartDragListener;
import com.example.ianshinbro.trackerbat.UI.popupScreens.AddGame;


/**
 * Created by ianshinbrot on 4/30/15.
 */
public class gameOverviewScreen extends AppCompatActivity {
    RecyclerView gameList;
    FloatingActionButton addGameButton;
    Game game;
    Player player;
    private ItemTouchHelper mItemTouchHelper;
    private LinearLayoutManager linearLayoutManager;
    int totalinList = -1;
    private String tag = "gameOverviewScreen";
    private int selectedPosition = -1;
    private Context context;
    private GameAdapter gameAdapter;
    private Toolbar toolbar;
    private boolean firstPlayer = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        Intent intent = getIntent();
        setSupportActionBar(toolbar);
        player = (Player) intent.getExtras().getSerializable("player");
        player.updateGames((player.getGames()));
        Log.d(this.tag, "entering gameListScreen with game size" + player.getGames().size());
        this.loadFields();
        this.setOnClickListeners();
        this.setUpToolbar();
        this.loadList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {        // return 1 is adding
            Log.d(this.tag, "Adding game");
            game = (Game) data.getExtras().getSerializable("game");
            player.addGame(game);
            totalinList++;
            selectedPosition = totalinList;
            editGame();
        }
        if (resultCode == 2) {        // new at bat
            Log.d(this.tag, "Updating game");
            game = (Game) data.getExtras().getSerializable("game");
            player.updateGame(selectedPosition, game);
            gameAdapter.updateGame(game, selectedPosition);

            selectedPosition = -1;        // deselect current position
        }
        if (resultCode == 3) {
            Log.d(this.tag, "Ending Player Edit");
            game = (Game) data.getExtras().getSerializable("game");
            player.updateGame(selectedPosition, game);
            selectedPosition = -1;

        }
    }

    private void editGame() {
        Intent intent = new Intent(gameOverviewScreen.this, AtBatListScreen.class);
        intent.putExtra("game", game);
        startActivityForResult(intent, 0);


    }

    private OnClickListener addGameListener = new OnClickListener() {
        public void onClick(View v) {
            // register selection

            Intent addGame = new Intent(gameOverviewScreen.this, AddGame.class);

            startActivityForResult(addGame, 0);

        }
    };
    private OnClickListener savePlayerListener = new OnClickListener() {
        public void onClick(View v) {
            // register selection
            savePlayerFunction();

        }
    };

    private void savePlayerFunction() {
        Intent savePlayer = new Intent();
        savePlayer.putExtra("player", player);
        setResult(3, savePlayer);
        finish();
    }

    private void loadList() {

        gameAdapter = new GameAdapter(player.getGames(), new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }

            @Override
            public void onUpdate(RecyclerView.ViewHolder viewHolder) {

                int position = viewHolder.getAdapterPosition();
                selectedPosition = position;
                updateGame(position);
            }

            @Override
            public void onSelect(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                selectedPosition = position;
                selectGame(position);
            }
        });
        gameList.setAdapter(gameAdapter);
        gameList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gameList.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        gameList.addItemDecoration(itemDecoration);

        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallBack(gameAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(gameList);
    }

    private void updateGame(int position) {
        Game game = player.getGame(position);
        Intent selectedGame = new Intent(gameOverviewScreen.this, UpdateGame.class);

        selectedGame.putExtra("game", game);

        startActivityForResult(selectedGame, 3);
    }

    private void selectGame(int position) {
        Game game = player.getGame(position);
        selectedPosition = position;

        Intent selectedGame = new Intent(gameOverviewScreen.this, AtBatListScreen.class);

        selectedGame.putExtra("game", game);

        startActivityForResult(selectedGame, 3);
    }

    private void setOnClickListeners() {
        addGameButton.setOnClickListener(addGameListener);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.savePlayerButton) {
            savePlayerFunction();
            return true;
        }
        if (id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gamelist_menu, menu);
        return true;
    }
    private void setUpToolbar() {
        toolbar.setTitle(R.string.gameListScreenTitleText);
        setSupportActionBar(toolbar);
    }

    private void loadFields() {
        gameList = (RecyclerView) findViewById(R.id.listView_listScreen);
        addGameButton = (FloatingActionButton) findViewById(R.id.addBTN_listScreen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}