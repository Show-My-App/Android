package com.idemobi.show_my_app;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin the transaction
        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        //ft.replace(R.id.fragment_container, new FragmentMain());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        //ft.commit();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentMain())
                .commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        Log.d("Activity", "onBackPressed: " + getFragmentManager().getBackStackEntryCount());
    }

    public void onButtonClicked(View view) {
        TextView _label = findViewById(R.id.textView);
        _label.setText("Click Me from 'XML'");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_share:
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new FragmentShare())
                        .addToBackStack("share")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            case R.id.action_test:
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new FragmentTest())
                        .addToBackStack("test")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
