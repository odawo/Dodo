package com.vanessaodawo.rider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    CircleImageView ppic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ppic = findViewById(R.id.profile_image);
        ppic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPic();
            }
        });

    }

    private void setPic() {
        Toast.makeText(this, "will picK a photo", Toast.LENGTH_SHORT).show();
    }

}
