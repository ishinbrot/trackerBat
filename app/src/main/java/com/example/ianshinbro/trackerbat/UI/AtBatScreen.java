package com.example.ianshinbro.trackerbat.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.example.ianshinbro.trackerbat.Implentation.AtBat;
import com.example.ianshinbro.trackerbat.Implentation.Base;
import com.example.ianshinbro.trackerbat.Implentation.OutField;
import com.example.ianshinbro.trackerbat.R;
import com.example.ianshinbro.trackerbat.UI.popupScreens.HitPopUp;
import com.example.ianshinbro.trackerbat.UI.popupScreens.OutPopUp;


/**
 * Created by ianshinbrot on 4/30/15.
 */

/**
 * This class is for the AtBat Screen
 */
public class AtBatScreen extends AppCompatActivity {
    Button Hit;
    Button Walk;
    Button Out;
    Button AdvanceBase;
    Button Undo;
    TextView atBatStatus;
    AtBat atBat;
    Toolbar toolbar;
    private String tag="AtBatScreen";
    private Context context;

    /**
     * This method is called at the start of the activity
     * First the screen is selected then the atBat object is loaded from the previous activity
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_at_bat_update_screen);
        context = getApplicationContext();
        Intent intent = getIntent();

            atBat = (AtBat) intent.getExtras().getSerializable("atBat");

        Log.d(this.tag, "atbat load");
        this.loadFields();
        this.setUpToolbar();
        this.setOnClickListeners();
    }

    /**
     * This method is called when returning from a previous activity
     * @param requestCode - the request code sent from this activity
     * @param resultCode - the resulting code sent from an activity
     * @param data - the intent which contains the object sent
     */
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {        // return 1 is adding
            Log.d(this.tag, "Hit Updated");
            atBat = (AtBat) data.getExtras().getSerializable("atBat");
            Out.setEnabled(false);
            Walk.setEnabled(false);
            Undo.setEnabled(true);
            Hit.setEnabled(false);

            Base initialBase = atBat.getInitialBaseNum();
            String message = "";
            switch (initialBase) {
                case FIRST:
                    message = "HIT A SINGLE";
                    AdvanceBase.setEnabled(true);
                    break;
                case SECOND:
                    message = "HIT A DOUBLE";
                    AdvanceBase.setEnabled(true);
                    break;
                case THIRD:
                    message = "HIT A TRIPLE";
                    AdvanceBase.setEnabled(true);
                    break;
                case HOME:
                    message = "HOME RUN";
                    AdvanceBase.setEnabled(false);
            }
            if (message.equals("")) {

            } else {
                setStatus(message);
            }
        }
        if (resultCode == 2) {
            Log.d(this.tag, "Out Updated");
            atBat = (AtBat) data.getExtras().getSerializable("atBat");
            Out.setEnabled(false);
            Hit.setEnabled(false);
            Walk.setEnabled(false);
            Undo.setEnabled(true);

            OutField initialCatch = atBat.getInitialCatch();
            OutField finalCatch = atBat.getFinalCatch();
            String message = "";
            if (initialCatch!=finalCatch) {
                switch (initialCatch) {
                    case FIRST_BASE:
                        message = "Out via First to";
                        break;
                    case SECOND_BASE:
                        message = "Out via Second to";
                        break;
                    case THIRD_BASE:
                        message = "Out via THIRD to";
                        break;
                    case CATCHER:
                        message = "Out via Catcher to";
                        break;
                    case LEFT_FIELD:
                        message = "Out via Left Field to";
                        break;
                    case RIGHT_FIELD:
                        message = "Out via Right Field to";
                        break;
                    case CENTER_FIELD:
                        message = "Out via Center Field to";
                        break;
                    case PITCHER:
                        message = "Out via Pitcher to";
                        break;
                    case SHORT_STOP:
                        message = "Out via Short Stop to";
                        break;
                }
                switch (finalCatch) {
                    case FIRST_BASE:
                        message += " First";
                        break;
                    case SECOND_BASE:
                        message += " Second";
                        break;
                    case THIRD_BASE:
                        message += " Third";
                        break;
                    case CATCHER:
                        message += " Catcher";
                        break;
                    case LEFT_FIELD:
                        message += " Left Field";
                        break;
                    case RIGHT_FIELD:
                        message += " Right Field";
                        break;
                    case CENTER_FIELD:
                        message += " Center Field";
                        break;
                    case PITCHER:
                        message += " Pitcher";
                        break;
                    case SHORT_STOP:
                        message += " Short Stop";
                        break;
                }
            }
            else {
                switch (finalCatch) {
                    case FIRST_BASE:
                        message = "Out via First";
                        break;
                    case SECOND_BASE:
                        message = "Out via Second";
                        break;
                    case THIRD_BASE:
                        message = "Out via Third";
                        break;
                    case CATCHER:
                        message = "Out via Catcher";
                        break;
                    case LEFT_FIELD:
                        message = "Out via Left Field";
                        break;
                    case RIGHT_FIELD:
                        message = "Out via Right Field";
                        break;
                    case CENTER_FIELD:
                        message = "Out via Center Field";
                        break;
                    case PITCHER:
                        message = "Out via Pitcher";
                        break;
                    case SHORT_STOP:
                        message = "Out via Short Stop";
                        break;
                }
            }
            // if system forces back
            if (message.equals("")) {
                // do nothing
            } else {
                setStatus(message);
            }
        }
    }

    private void endAtBat() {
            Intent intent = new Intent();
            atBat.setFinalBase(atBat.getCurrentBase());
            intent.putExtra("atBat", atBat);
            setResult(2, intent);
            finish();
        }

    private OnClickListener HitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AtBatScreen.this, HitPopUp.class);

            intent.putExtra("atBat", atBat);
            startActivityForResult(intent, 0);


        }
    };
    private OnClickListener WalkListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            atBat.setInitialBase(Base.FIRST);
            atBat.setWalk();
            String message = "Walk to first";
            setStatus(message);
            Out.setEnabled(false);
            AdvanceBase.setEnabled(true);
            Walk.setEnabled(false);
            Undo.setEnabled(true);
        }
    };
    private OnClickListener OutListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new  Intent(AtBatScreen.this, OutPopUp.class);

            intent.putExtra("atBat",atBat);
            startActivityForResult(intent, 3);

        }
    };
    private OnClickListener AdvanceBaseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            atBat.nextBase();
            String message = "";
            switch (atBat.getCurrentBase()) {
                case FIRST:
                    message = "advanced to first";
                    break;
                case SECOND:
                    message = "Advanced to second";
                    break;
                case THIRD:
                    message = "Advanced to third";
                    break;
                case HOME:
                    message = "Made it Home";
                    AdvanceBase.setEnabled(false);
                    break;
            }
            setStatus(message);
        }
    };
    private OnClickListener UndoListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String message="";
            Base currentBase = atBat.getCurrentBase();
            Base initialBase = atBat.getInitialBaseEnum();

             if (atBat.getCurrentBase()==Base.ATBAT) {
                undoEnabler();
            }
            if (atBat.isOut()) {
                atBat.undoOut();
                undoEnabler();
                message = "Back at bat";
            }
             else if (currentBase==initialBase) {
                Log.d(tag, "Back at bat");
                message = "Back At bat";
                atBat.UndoInitialBase();
                 undoEnabler();
            }

            else {
                switch (currentBase) {
                    case FIRST:
                        atBat.revertBase();
                        message =  "Back at bat";
                        undoEnabler();
                        break;
                    case SECOND:
                        atBat.revertBase();
                        message = "Back at first";
                        break;
                    case THIRD:
                        message = "Back at second";
                        atBat.revertBase();
                        break;
                    case HOME:
                        message = "Back at third";
                        atBat.revertBase();
                        break;
                }
            }
            setStatus(message);
        }
    };
    private void setStatus(String message) {
        atBatStatus.setText("Status: " +message);

    }

    private void undoEnabler() {
        Undo.setEnabled(false);
        Hit.setEnabled(true);
        Out.setEnabled(true);
        Walk.setEnabled(true);
        AdvanceBase.setEnabled(false);
    }

    private void setOnClickListeners() {
        Walk.setOnClickListener(WalkListener);
        Hit.setOnClickListener(HitListener);
        AdvanceBase.setOnClickListener(AdvanceBaseListener);
        Undo.setOnClickListener(UndoListener);
        Out.setOnClickListener(OutListener);
        Undo.setEnabled(false);
        AdvanceBase.setEnabled(false);
        setStatus("At Bat");
    }
    private void setUpToolbar() {
        toolbar.setTitle(R.string.atbatTitleText);

        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.saveAtBatSummary:
                endAtBat();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.atbat_overview, menu);

        return true;
    }
    private void loadFields() {

        Hit = (Button) findViewById(R.id.HitButton_AtBatScreen);
        Walk = (Button) findViewById(R.id.WalkButton_AtBatScreen);
        Out = (Button) findViewById(R.id.OutButton_AtBatScreen);
        atBatStatus = (TextView) findViewById(R.id.AtBatStatsUpdateScreen);
        AdvanceBase = (Button) findViewById(R.id.AdvanceBaseButton_AtBatScreen);
        Undo = (Button) findViewById(R.id.UndoBat_AtBatScreen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}