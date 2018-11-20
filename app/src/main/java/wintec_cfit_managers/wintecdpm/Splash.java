package wintec_cfit_managers.wintecdpm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        {
            int SPLASH_TIME_OUT = 3500;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent HomeScreen = new Intent(Splash.this, MainMenu.class);
                    startActivity(HomeScreen);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
