package com.kitesoft.matchpic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView start,howto;
    int no[] = new int[5];
    int drawimg[] = {R.drawable.a_ele,R.drawable.a_frog,R.drawable.a_lion,R.drawable.a_monkey,R.drawable.a_pig};
    ImageView btn[] = new ImageView[5];
    int drawresult[] = {R.drawable.b_ele,R.drawable.b_frog,R.drawable.b_lion,R.drawable.b_monkey,R.drawable.b_pig};
    ImageView result;
    int pannum;

    SoundPool sp;
    int sd_again, sd_good, sd_select, sd_sijak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.startbtn);
        howto = findViewById(R.id.howtogame);
        result = findViewById(R.id.result);

        start.setOnClickListener(gamestart);
        howto.setOnClickListener(howtogame);

        for(int i=0; i<btn.length; i++){
            btn[i]= findViewById(R.id.image1+i);
            btn[i].setOnClickListener(kok);
        }

        //효과음 관리자
        sp= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        //사운드풀에 사운드 등록(load)하고 사운드아이디 받아오기...
        sd_again= sp.load(this, R.raw.s_again, 1);
        sd_good= sp.load(this, R.raw.s_goodjob, 1);
        sd_select= sp.load(this, R.raw.s_select, 1);
        sd_sijak= sp.load(this, R.raw.s_sijak, 1);

    }

    View.OnClickListener gamestart = new View.OnClickListener(){
        public void onClick(android.view.View v) {
            Toast.makeText(MainActivity.this, "게임을 시작합니다!",Toast.LENGTH_SHORT).show();
            sp.play(sd_sijak, 1, 1, 0, 0, 1);
            aa();
        };
    };

    //Random하게 이미지들 위치 변경하기
    public void aa(){

        Random random = new Random();

        //중복되지 않는 5개의 랜덤한 숫자(0~4)를 배열에 차례로 저장
        for(int i = 0; i<no.length; i++){
            no[i] = random.nextInt(5);
            for(int j = 0; j<i; j++){
                if(no[i]==no[j]){
                    i--;
                    break;
                }
            }
        }

        //아래 판넬에 표시될 정답번호(0~4) 랜덤하게 생성
        pannum = random.nextInt(5);

        //랜덤한 이미지를 이미지뷰에 설정하고 tag값 부여(정답값 확인위해)
        for(int j =0; j<5; j++){
            btn[j].setImageResource(drawimg[no[j]]);
            btn[j].setTag(no[j]);
        }

        //판넬에 정답 영어 글씨 표시
        result.setImageResource(drawresult[pannum]);
    }

    ImageView.OnClickListener howtogame = new ImageView.OnClickListener(){
        public void onClick(android.view.View v) {
            sp.play(sd_select, 1, 1, 0, 0, 1);

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("게임설명");
            alert.setMessage("matchPic이란 게임은 동물이 나올거야. 하단에 있는 단어와 터치한  동물이 일치하면 이기는거지!!");
            alert.setPositiveButton("확인",null );
            alert.show();
        };
    };

    ImageView.OnClickListener kok = new ImageView.OnClickListener(){
        public void onClick(android.view.View v) {
            //클릭한 그림의 tag값을 얻어오기
            int n= Integer.parseInt(v.getTag().toString());

            //정답그림을 선택하였는가?
            if(n==pannum){
                sp.play(sd_good, 1, 1, 0, 0, 1);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Good Job!");
                alert.setMessage("아주 잘했어! 한번 더 해볼까? 확인버튼을 눌러.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //다시 랜덤하게 그림들 바꾸기!!!
                        aa();
                    }

                });
                alert.show();
            }
            else{
                sp.play(sd_again, 1, 1, 0, 0, 1);
                Toast.makeText(MainActivity.this, "다시 잘 봐!",Toast.LENGTH_SHORT).show();
            }
        };
    };


}
