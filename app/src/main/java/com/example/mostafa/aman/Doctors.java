package com.example.mostafa.aman;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.ShareActionProvider;
import java.util.ArrayList;
public class Doctors extends AppCompatActivity {
    ArrayList<Model> list;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle b = getIntent().getExtras();
        list = new ArrayList<Model>();
        list.clear();
        list = (ArrayList<Model>)b.getSerializable("data");
//        Toast.makeText(Doctors.this,"size result"+list.size(),Toast.LENGTH_LONG).show();
        ListView listView = (ListView) findViewById(R.id.listView);
        MainAdapter adapter = new MainAdapter(Doctors.this,list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);

//        MenuItem item = menu.findItem(R.id.menu_item_share);
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }


//    private void setShareIntent(Intent shareIntent) {
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.menu_item_info:

                Intent i = new Intent(Doctors.this,about.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
