package example.kingja.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class FormActivity extends AppCompatActivity {

    private MorphAnimation morphAnimationLogin;
    private MorphAnimation morphAnimationRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        View loginContainer = findViewById(R.id.form_btn);
        View registeContainer = findViewById(R.id.form_register);

        Button buttonLogin = (Button) findViewById(R.id.button_inside_group);
        Button buttonRegister = (Button) findViewById(R.id.button_register);

        ViewGroup loginViews = (ViewGroup) findViewById(R.id.login_views);
        ViewGroup registeViews = (ViewGroup) findViewById(R.id.register_views);

        final FrameLayout rootView = (FrameLayout) findViewById(R.id.root_view);

        morphAnimationLogin = new MorphAnimation(loginContainer, rootView, loginViews);
        morphAnimationRegister = new MorphAnimation(registeContainer, rootView, registeViews);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!morphAnimationLogin.isPressed()) {
                    morphAnimationLogin.morphIntoForm();
                } else {
                    morphAnimationLogin.morphIntoButton();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!morphAnimationRegister.isPressed()) {
                    morphAnimationRegister.morphIntoForm();
                } else {
                    morphAnimationRegister.morphIntoButton();
                }
            }
        });
    }
}
