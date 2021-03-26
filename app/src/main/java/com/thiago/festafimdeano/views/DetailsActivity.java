package com.thiago.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.thiago.festafimdeano.R;
import com.thiago.festafimdeano.constant.FimDeAnoConstants;
import com.thiago.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkboxParticipate = findViewById(R.id.check_participate);
        this.mViewHolder.checkboxParticipate.setOnClickListener(this);

        this.loadDataFromActivity();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.check_participate) {
            if (this.mViewHolder.checkboxParticipate.isChecked()) {
                //Salvar presença
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIMATION_YES);
            } else {
                //salvar ausencia
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIMATION_NO);
            }
        }
    }

    private void loadDataFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String presence = extras.getString(FimDeAnoConstants.PRESENCE_KEY);
            if (presence != null && presence.equals(FimDeAnoConstants.CONFIMATION_YES)) {
                this.mViewHolder.checkboxParticipate.setChecked(true);
            } else {
                this.mViewHolder.checkboxParticipate.setChecked(false);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkboxParticipate;
    }
}