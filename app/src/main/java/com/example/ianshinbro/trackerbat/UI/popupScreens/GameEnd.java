package com.example.ianshinbro.trackerbat.UI.popupScreens;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ianshinbro.trackerbat.Implentation.AtBat;
import com.example.ianshinbro.trackerbat.Implentation.Base;
import com.example.ianshinbro.trackerbat.Implentation.Game;
import com.example.ianshinbro.trackerbat.R;

import org.w3c.dom.Text;

public class GameEnd extends Activity {

    Button endGame;
    EditText awayTeamScore;
    EditText homeTeamScore;
    Game game;
    private String tag="AtBatSetup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Log.d(this.tag, "entering At BatSet Up Screen");

        game = (Game) intent.getExtras().getSerializable("game");

        setContentView(R.layout.content_game_end);
        this.loadButtons();
        this.setOnClickListeners();
        this.setHints();
    }

    private View.OnClickListener endGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            game.setHomeScore(Integer.parseInt(homeTeamScore.getText().toString()));
            game.setAwayScore(Integer.parseInt(awayTeamScore.getText().toString()));
            intent.putExtra("game", game);
            setResult(3, intent);
            finish();

        }
    };
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){

        String s1 = homeTeamScore.getText().toString();
        String s2 = awayTeamScore.getText().toString();

        if(s1.equals("")|| s2.equals("")){
            endGame.setEnabled(false);
        } else {
            endGame.setEnabled(true);
        }
    }
    private void setHints() {
        homeTeamScore.setHint(game.getHomeTeam() + " Score");
        awayTeamScore.setHint(game.getAwayTeam()+ " Score");
    }
    private void setOnClickListeners() {
        endGame.setOnClickListener(endGameListener);
    }
    private void loadButtons() {
        endGame = (Button) findViewById(R.id.endGameButton_EndGamePopUp);
        homeTeamScore = (EditText) findViewById(R.id.HomeTeamScore);
        awayTeamScore = (EditText) findViewById(R.id.AwayTeamScore);
        homeTeamScore.addTextChangedListener(mTextWatcher);
        awayTeamScore.addTextChangedListener(mTextWatcher);
        endGame.setEnabled(false);
    }
}