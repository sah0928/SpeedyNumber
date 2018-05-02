package modoorock.seol.speedynumber;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 타이머 : 140초동안 1초 간격으로 시간이 줄어듬
    private static final int MILLISINFUTURE = 140*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    Context context = this;
    Button buttons[] = new Button[30];
    Button startBtn;
    int[] randomArr = new int[30];
    static int numCount = 1;
    static int errorCount = 0;
    TextView timerText;
    static int timeCount = 140;
    CountDownTimer countDownTimer;
    TextView errorText;
    TextView scoreText;
    static double score = 7.0;

    ProgressBar progressBar;

    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;
    LinearLayout linearLayout5;
    LinearLayout linearLayout6;
    LinearLayout.LayoutParams parambtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.time);
        errorText = findViewById(R.id.errorText);
        scoreText = findViewById(R.id.scoreText);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);
        linearLayout4 = findViewById(R.id.linearLayout4);
        linearLayout5 = findViewById(R.id.linearLayout5);
        linearLayout6 = findViewById(R.id.linearLayout6);

        // 동적 레이아웃
        parambtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                 LinearLayout.LayoutParams.MATCH_PARENT);
        parambtn.weight = 1.0f;

        // 시작버튼
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                madeView();
                randomArr = generate();
                startBtn.setEnabled(false);

                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setEnabled(true);
                    buttons[i].setText("" + randomArr[i]);
                    buttons[i].setTag(buttons[i].getText());
                }

                countDownTimer();
                timerReset();
            }
        });

    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(timeCount / 60) + "분 " + String.valueOf(timeCount % 60) + "초");
                timeCount--;
                progressBar.setProgress(timeCount);
                if (timeCount % 10 == 9 && timeCount != 139) {
                    score -= 0.5;
                    scoreText.setText(String.format("%.1f", score));
                }
            }
            public void onFinish() {
                score -= 0.5;
                scoreText.setText(String.format("%.1f", score));
                timerText.setText(String.valueOf("Game Over"));
                Toast.makeText(context, "최종 점수는 " + (int) score + "점 입니다", Toast.LENGTH_LONG).show();

                //*** 남은 버튼 비활성화 ***//
                 for (int i = 0; i < linearLayout1.getChildCount(); i++) {
                    View child = linearLayout1.getChildAt(i);
                    child.setEnabled(false);
                }
                for (int i = 0; i < linearLayout2.getChildCount(); i++) {
                    View child = linearLayout2.getChildAt(i);
                    child.setEnabled(false);
                }
                for (int i = 0; i < linearLayout3.getChildCount(); i++) {
                    View child = linearLayout3.getChildAt(i);
                    child.setEnabled(false);
                }
                for (int i = 0; i < linearLayout4.getChildCount(); i++) {
                    View child = linearLayout4.getChildAt(i);
                    child.setEnabled(false);
                }
                for (int i = 0; i < linearLayout5.getChildCount(); i++) {
                    View child = linearLayout5.getChildAt(i);
                    child.setEnabled(false);
                }
                for (int i = 0; i < linearLayout6.getChildCount(); i++) {
                    View child = linearLayout6.getChildAt(i);
                    child.setEnabled(false);
                }
            }
        };
    }

    public void timerReset() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    public void madeView() {
        // 게임 초기화
        numCount = 1;
        errorCount = 0;
        timeCount = 140;
        score = 7.0;
        timerText.setText(String.valueOf(timeCount / 60) + "분 " + String.valueOf(timeCount % 60) + "초");
        errorText.setText(String.valueOf(errorCount));
        scoreText.setText(String.format("%.1f", score));
        progressBar.setProgress(timeCount);

        // 버튼 생성
        for(int i = 0; i < 30; i++) {
            buttons[i] = new Button(this);
            buttons[i].setId(i);
            buttons[i].setTextSize(20);
            buttons[i].setOnClickListener(this);
            buttons[i].setEnabled(false);

            if (i < 5)
                linearLayout1.addView(buttons[i], parambtn);
            else if (i < 10)
                linearLayout2.addView(buttons[i], parambtn);
            else if (i < 15)
                linearLayout3.addView(buttons[i], parambtn);
            else if (i < 20)
                linearLayout4.addView(buttons[i], parambtn);
            else if (i < 25)
                linearLayout5.addView(buttons[i], parambtn);
            else if (i < 30)
                linearLayout6.addView(buttons[i], parambtn);
        }
    }

    public void renewView() {
        Collections.shuffle(Arrays.asList(buttons));

        // 레이아웃 초기화
        for(int i = 0; i < 30; i++) {
            ((ViewGroup) buttons[i].getParent()).removeView(buttons[i]);
            if (i < 5) {
                linearLayout1.addView(buttons[i], parambtn);
            } else if (i < 10) {
                linearLayout2.addView(buttons[i], parambtn);
            } else if (i < 15) {
                linearLayout3.addView(buttons[i], parambtn);
            } else if (i < 20) {
                linearLayout4.addView(buttons[i], parambtn);
            } else if (i < 25) {
                linearLayout5.addView(buttons[i], parambtn);
            } else if (i < 30) {
                linearLayout6.addView(buttons[i], parambtn);
            }
        }
    }

    @Override
    public void onClick(View v) {
         Object tag = v.getTag();
         if (tag.equals(numCount + "")) {
             v.setEnabled(false);

             numCount++;
             score += 0.1;
             scoreText.setText(String.format("%.1f", score));
             if (numCount == 31) {
                 countDownTimer.cancel();
                 Toast.makeText(this, "최종 점수는 " + (int) score + "점 입니다", Toast.LENGTH_LONG).show();
             } else if (numCount == 16) {
                 renewView();
             }
         } else {
             errorCount++;
             score -= 0.1;
             errorText.setText(String.valueOf(errorCount));
             scoreText.setText(String.format("%.1f", score));
         }
     }

    public int[] generate() {
        int[] result = new int[30];

        HashSet<Integer> used = new HashSet<Integer>();
        for (int i = 0; i < 30; i++) {
            int add = (int)(Math.random() * 30 + 1); //this is the int we are adding
            while (used.contains(add)) { //while we have already used the number
                add = (int) (Math.random() * 30 + 1); //generate a new one because it's already used
            }
            //by this time, add will be unique
            used.add(add);
            result[i] = add;
        }

        return result;
    }

}
