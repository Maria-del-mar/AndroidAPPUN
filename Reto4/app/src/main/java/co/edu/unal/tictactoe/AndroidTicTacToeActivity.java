package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;
import android.content.Context;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private boolean mGameOver = false;
    private TextView mHumanScore;
    private TextView mComputerScore;
    private TextView mTieScore;
    private int mHumanCount,mComputerCount,mTieCount;
    private boolean mHumanFirst;

    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    static final int DIALOG_ABOUT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanScore = (TextView) findViewById(R.id.human_score);
        mComputerScore = (TextView) findViewById(R.id.computer_score);
        mTieScore = (TextView) findViewById(R.id.tie_score);
        mHumanCount=0;
        mComputerCount=0;
        mTieCount=0;
        mHumanFirst = true;
        mGame = new TicTacToeGame();
        startNewGame();
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()&&mGameOver==false) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    mTieCount++;
                    updateScores();
                }else if (winner == 2){
                    mInfoTextView.setText(R.string.result_human_wins);
                    mGameOver = true;
                    mHumanCount++;
                    updateScores();
                }
                else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mGameOver = true;
                    mComputerCount++;
                    updateScores();
                }
            }
        }
    }

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;
        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if(mHumanFirst){
            mHumanFirst = false;
            mInfoTextView.setText(R.string.first_human);
        }
        else{
            mHumanFirst = true;
            mInfoTextView.setText(R.string.turn_computer);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setText(R.string.turn_human);
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER) {
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        } else {
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }

    public void updateScores(){
        mHumanScore.setText(Integer.toString(mHumanCount));
        mComputerScore.setText(Integer.toString(mComputerCount));
        mTieScore.setText(Integer.toString(mTieCount));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
            case R.id.about:
                showDialog(DIALOG_ABOUT_ID);
                return true;
        }
        return false;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)};
                int selected = 2;
                builder.setSingleChoiceItems(levels, selected,
                        (dialog1, item) -> {
                            dialog1.dismiss(); // Close dialog

                            switch (item){
                                case 0:
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                                    break;
                                case 1:
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                                    break;
                                case 2:
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                    break;
                            }

                            Toast.makeText(getApplicationContext(), levels[item], Toast.LENGTH_SHORT).show();
                        });
                dialog = builder.create();

                break;

            case DIALOG_QUIT_ID:
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {AndroidTicTacToeActivity.this.finish(); }
                        })
                        .setNegativeButton("no", null);
                dialog = builder.create();
                break;

            case DIALOG_ABOUT_ID:
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();
                break;
        }

        return dialog;
    }
}