package co.edu.unal.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    private TicTacToeGame mGame;
    private TextView mInfoTextView;
    private boolean mGameOver;
    private TextView mHumanScore;
    private TextView mComputerScore;
    private TextView mTieScore;
    private int mHumanCount,mComputerCount,mTieCount;
    private boolean mHumanFirst;
    private BoardView mBoardView;
    private boolean androidTurn;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    static final int DIALOG_ABOUT_ID = 2;
    static final int DIALOG_RESET_ID = 3;
    static final int DIALOG_ONLINE_ID = 4;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanScore = (TextView) findViewById(R.id.human_score);
        mComputerScore = (TextView) findViewById(R.id.computer_score);
        mTieScore = (TextView) findViewById(R.id.tie_score);
        mHumanCount=0;
        mComputerCount=0;
        mTieCount=0;
        mHumanFirst = true;
        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);
        if (savedInstanceState == null) {
            startNewGame();
        }
        else {
            // Restore the game's state
            mGame.setBoardState(savedInstanceState.getCharArray("board"));
            mGameOver = savedInstanceState.getBoolean("winnerFlag");
            androidTurn = savedInstanceState.getBoolean("androidTurn");
            mHumanCount = savedInstanceState.getInt("counterPlayer");
            mComputerCount = savedInstanceState.getInt("counterComputer");
            mTieCount = savedInstanceState.getInt("counterTie");
            mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
            mHumanScore.setText(savedInstanceState.getCharSequence("mHumanScore"));
            mComputerScore.setText(savedInstanceState.getCharSequence("mComputerScore"));
            mTieScore.setText(savedInstanceState.getCharSequence("mTieScore"));
        }
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        mHumanCount = mPrefs.getInt("mHumanCount", 0);
        mComputerCount = mPrefs.getInt("mComputerCount", 0);
        mTieCount = mPrefs.getInt("mTieCount", 0);
        mHumanScore.setText(Integer.toString(mHumanCount));
        mComputerScore.setText(Integer.toString(mComputerCount));
        mTieScore.setText(Integer.toString(mTieCount));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save the current scores
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("mHumanCount", mHumanCount);
        ed.putInt("mComputerCount", mComputerCount);
        ed.putInt("mTieCount", mTieCount);
        ed.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
        mHumanCount = savedInstanceState.getInt("mHumanCount");
        mComputerCount = savedInstanceState.getInt("mComputerCount");
        mTieCount = savedInstanceState.getInt("mTieCount");
        androidTurn = savedInstanceState.getBoolean("androidTurn");
        mHumanScore.setText(savedInstanceState.getCharSequence("mHumanScore"));
        mComputerScore.setText(savedInstanceState.getCharSequence("mComputerScore"));
        mTieScore.setText(savedInstanceState.getCharSequence("mTieScore"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putInt("mHumanCount", Integer.valueOf(mHumanCount));
        outState.putInt("mComputerCount", Integer.valueOf(mComputerCount));
        outState.putInt("mTieCount", Integer.valueOf(mTieCount));
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putBoolean("androidTurn", androidTurn);
        outState.putCharSequence("mHumanScore", mHumanScore.getText());
        outState.putCharSequence("mComputerScore", mComputerScore.getText());
        outState.putCharSequence("mTieScore", mTieScore.getText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.human);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.computer);
    }

    // Listen for touches on the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            // Determine which cell was touched
            int col = (int) motionEvent.getX() / mBoardView.getBoardCellWidth();
            int row = (int) motionEvent.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mGameOver && !androidTurn&& setMove(TicTacToeGame.HUMAN_PLAYER, pos))	{
                Handler handler = new Handler();
                if (mGame.checkForWinner() == 0){
                    mInfoTextView.setText(R.string.turn_computer);
                    androidTurn = true;
                    handler.postDelayed(() -> {
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);

                        // If no winner yet, humman continue
                        if (mGame.checkForWinner() == 0)
                            mInfoTextView.setText(R.string.turn_human);
                        else
                            updateScores();

                        androidTurn = false;
                    }, 2000);
                }
                else{
                    updateScores();
                    return false;
                }
            }
            return false;
        }
    };

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;
        androidTurn = false;
        mBoardView.invalidate();
        mInfoTextView.setText(R.string.first_human);
    }

    private boolean setMove(char player, int location) {
        /*if(player == mGame.HUMAN_PLAYER){
            mHumanMediaPlayer.start();
        }
        if(player == mGame.COMPUTER_PLAYER){
            mComputerMediaPlayer.start();
        }*/

        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();
            return true;
        }
        return false;
    }

    public void updateScores(){
        int winner = mGame.checkForWinner();
        if (winner == 1){
            mInfoTextView.setText(R.string.result_tie);
            mTieCount++;
            mTieScore.setText(Integer.toString(mTieCount));
        }else if (winner == 2) {
            mInfoTextView.setText(R.string.result_human_wins);
            mGameOver = true;
            mHumanCount++;
            mHumanScore.setText(Integer.toString(mHumanCount));
        }
        else {
            mInfoTextView.setText(R.string.result_computer_wins);
            mGameOver = true;
            mComputerCount++;
            mComputerScore.setText(Integer.toString(mComputerCount));
        }
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
            case R.id.online:
                showDialog(DIALOG_ONLINE_ID);
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
            case R.id.reset_score:
                showDialog(DIALOG_RESET_ID);
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

            case DIALOG_RESET_ID:
                mComputerCount = 0;
                mHumanCount = 0;
                mTieCount = 0;
                mHumanScore.setText(Integer.toString(mHumanCount));
                mComputerScore.setText(Integer.toString(mComputerCount));
                mTieScore.setText(Integer.toString(mTieCount));
                break;

            case DIALOG_ONLINE_ID:
                Intent i = new Intent(this, CreateGame.class);
                startActivity(i);
                break;
        }

        return dialog;
    }
}