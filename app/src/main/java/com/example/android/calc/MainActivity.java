package com.example.android.calc;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_current, tv_total;
    Button[] num = new Button[10];
    Button[] operator = new Button[4];
    Button[] manu = new Button[4];

    int[] id_num = new int[10];
    int[] id_oper = new int[4];
    int[] id_manu = new int[4];

    int result;
    int oper;   // 0: +  1: -  2: *  3: /

    boolean first;

    boolean isNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = 0;
        oper = 0;
        first = true;
        isNum = false;
        tv_current = (TextView) findViewById(R.id.tv_current_display);
        tv_total = (TextView) findViewById(R.id.tv_total_display);

        for(int i=0;i<10;i++){
            String tmp = "num_"+String.valueOf(i);
            int resID = getResources().getIdentifier(tmp, "id", this.getPackageName());
            id_num[i] = resID;
            num[i] = (Button)findViewById(id_num[i]);
            Log.i("check1", String.valueOf(R.id.num_0));
            Log.i("check2", String.valueOf(id_num[i]));
            num[i].setOnClickListener(mClickListener);
        }
        id_oper[0] = R.id.plus;
        id_oper[1] = R.id.minus;
        id_oper[2] = R.id.mul;
        id_oper[3] = R.id.divide;

        id_manu[0] = R.id.ce;
        id_manu[1] = R.id.c;
        id_manu[2] = R.id.backspace;
        id_manu[3] = R.id.result;

        for(int i=0;i<4;i++){
            operator[i] = (Button)findViewById(id_oper[i]);
            operator[i].setOnClickListener(mClickListener);
            manu[i] = (Button)findViewById(id_manu[i]);
            manu[i].setOnClickListener(mClickListener);
        }
    }

    protected void calc(int operator, int current){
        if(first){
            first = false;
            result = current;
        }
        else{
            if(operator==0){    // +
                result+=current;
            } else if(operator == 1){   // -
                result-=current;
            } else if(operator==2){     // *
                result*=current;
            }else{                      // /
                result/=current;
            }
        }
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            boolean flag = true;
            for(int i=0;i<10;i++){
                if(isNum==false){
                    isNum = true;
                    tv_current.setText("");
                }
                if(id==id_num[i]){
                    tv_current.append(String.valueOf(i));
                    flag = false;
                }
            }
            String tmp;
            if(flag) {
                isNum=false;
                switch (id) {
                    case R.id.c:
                        result=0;
                        tv_current.setText("");
                        tv_total.setText("");
                        break;
                    case R.id.ce:
                        tv_current.setText("");
                        break;
                    case R.id.plus:
                        tmp = String.valueOf(tv_current.getText().toString());
                        tv_total.append(tmp+" + ");
                        calc(oper,Integer.parseInt(tmp));
                        tv_current.setText(String.valueOf(result));
                        oper = 0;
                        break;
                    case R.id.minus:
                        tmp = String.valueOf(tv_current.getText().toString());
                        tv_total.append(tmp+" - ");
                        calc(oper,Integer.parseInt(tmp));
                        tv_current.setText(String.valueOf(result));
                        oper = 1;
                        break;
                    case R.id.mul:
                        tmp = String.valueOf(tv_current.getText().toString());
                        tv_total.append(tmp+" * ");
                        calc(oper,Integer.parseInt(tmp));
                        tv_current.setText("");
                        oper = 2;
                        break;
                    case R.id.divide:
                        tmp = String.valueOf(tv_current.getText().toString());
                        tv_total.append(tmp+" / ");
                        calc(oper,Integer.parseInt(tmp));
                        tv_current.setText(String.valueOf(result));
                        oper = 3;
                        break;
                    case R.id.result:
                        tmp = String.valueOf(tv_current.getText().toString());
                        calc(oper, Integer.parseInt(tmp));
                        tv_total.setText("");
                        tv_current.setText(String.valueOf(result));
                        result=0;
                        break;
                    case R.id.backspace:
                        tmp = tv_current.getText().toString();
                        if(tmp.length()!=0){
                            tmp.substring(0,tmp.length()-2);
                        }
                        tv_current.setText(tmp);
                }
            }
        }
    };
}
