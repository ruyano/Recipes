package br.com.udacity.ruyano.recipes.views.step.detail;

import androidx.appcompat.app.AppCompatActivity;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class StepDetailActivity extends AppCompatActivity {

    private static final String STEP_EXTRA = "STEP_EXTRA";

    public static Intent getIntent(Context context, Step step) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra(STEP_EXTRA, step);
        return intent;

    }

    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setActionBarTitle();

    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(STEP_EXTRA)) {
            step = getIntent().getExtras().getParcelable(STEP_EXTRA);
        }

    }

    private void setActionBarTitle() {
        if (step != null && !step.getShortDescription().isEmpty()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(step.getShortDescription());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
