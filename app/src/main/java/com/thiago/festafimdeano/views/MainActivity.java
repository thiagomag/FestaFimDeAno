package com.thiago.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thiago.festafimdeano.R;
import com.thiago.festafimdeano.constant.FimDeAnoConstants;
import com.thiago.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday = findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = findViewById(R.id.txt_days_left);
        this.mViewHolder.btnConfirm = findViewById(R.id.btn_confirm);

        this.mViewHolder.btnConfirm.setOnClickListener(this);

        //Datas
        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime())); //dia de hoje

        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeft()), getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {

            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY, presence);
            startActivity(intent);
        }
    }

    private void verifyPresence() {
        String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
        if (presence.equals("")) {
            this.mViewHolder.btnConfirm.setText(getString(R.string.nao_confirmado));
        } else if (presence.equals(FimDeAnoConstants.CONFIMATION_YES)) {
            this.mViewHolder.btnConfirm.setText(getString(R.string.sim));
        } else {
            this.mViewHolder.btnConfirm.setText(getString(R.string.nao));
        }
    }

    private int getDaysLeft() {
        //Data de hoje
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        //Data máximo do ano
        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum((Calendar.DAY_OF_YEAR));

        return dayMax - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button btnConfirm;
    }
}