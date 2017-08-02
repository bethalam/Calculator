package com.example.dineshvarma.calculator;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    TextView ansText, equText,equl;
    private boolean lastNumeric, lastDot;
    private boolean stateError, is;
    Vibrator v ;
    int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equText = (TextView) findViewById(R.id.equ_text);
        equl = (TextView) findViewById(R.id.equlText);
        ansText = (TextView) findViewById(R.id.ans_text);
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void acpressed(View view) {
        animation();
        v.vibrate(50);
        equText.setText("");
        ansText.setText("");
        stateError = true;
        lastDot = false;
        lastNumeric = false;
        a=1;
    }

    public void delPressed(View view) {
        animation();
        v.vibrate(50);
        String edit = equText.getText().toString();
        if (edit != null && edit.length() > 0 ) {
            edit = edit.substring(0, edit.length() - 1);
            equText.setText(edit);
            if (edit.length() != 0) {
                char check = edit.charAt(edit.length() - 1);
                if (check == '/' || check == '*' || check == '+' || check == '-' || check == '%') {
                    edit = edit.substring(0, edit.length() - 1);
                    Expression expression = new ExpressionBuilder(edit).build();
                    try {
                        double result = expression.evaluate();
                        ansText.setText(Double.toString(result));
                    } catch (ArithmeticException ex) {
                        ansText.setText("Error");
                        stateError = true;

                    }
                    lastNumeric = false;
                    lastDot = false;
                }else  if (check=='.'){
                    edit = edit.substring(0, edit.length() - 1);
                    Expression expression = new ExpressionBuilder(edit).build();
                    try {
                        double result = expression.evaluate();
                        ansText.setText(Double.toString(result));
                    } catch (ArithmeticException ex) {
                        ansText.setText("Error");
                        stateError = true;

                    }
                    lastDot = true;
                    lastNumeric = false;
                    a--;
                }
                else {
                    lastNumeric = true;
                    lastDot = false;
                    equal();
                }
            } else {
                ansText.setText("");
                lastNumeric = false;
                lastDot = false;
                stateError = true;
            }

        }
    }

    public void symblePressed(View view) {
        a = 1;
        animation();
        v.vibrate(50);
        if (lastNumeric && !stateError) {
            String text = view.getTag().toString();
            equText.append(text);
            lastNumeric = false;
            lastDot = false;
        }
    }

    public void numberPressed(View view) {
        animation();
        v.vibrate(50);
        if (a < 16) {
            String text = view.getTag().toString();

            if (stateError) {
                equText.setText(text);
                stateError = false;
            } else {
                equText.append(text);
            }
            lastNumeric = true;
            equal();
        }
        a++;
    }

    public void dotPressed(View view) {
        a=1;
        animation();
        v.vibrate(50);
        if (lastNumeric && !stateError && !lastDot) {
            equText.append(".");
            lastNumeric = false;
            lastDot = true;
        }
    }
   @TargetApi(Build.VERSION_CODES.N)
   public void equal(){
       if (lastNumeric && !stateError) {
           String txt = equText.getText().toString();
           txt = txt.replace("%","/100*");
           Expression expression = new ExpressionBuilder(txt).build();
           try {
               double result = expression.evaluate();
                   DecimalFormat df = new DecimalFormat("#.###########");
                   ansText.setText(df.format(result));


           } catch (ArithmeticException ex) {
               ansText.setText("Error");
               stateError = true;

           }
       }
   }
    public void onEqual(View view) {
        v.vibrate(50);
        equText.setVisibility(View.INVISIBLE);
      ansText.setTextSize(46);
        ansText.animate().translationY(-180).setDuration(500);
        is = true;
    }
    public void animation(){
        if (is) {

            equText.setText("");
            ansText.setText("");
            ansText.setTextSize(24);
            lastNumeric = false;
            lastDot = false;
            stateError = true;
            equText.setVisibility(View.VISIBLE);
            ansText.animate().translationY(0).setDuration(1);
            is = false;
            a =1;
        }
    }


}
