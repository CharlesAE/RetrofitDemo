package com.srstudios.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.srstudios.retrofitdemo.models.Course;
import com.srstudios.retrofitdemo.models.UCatalog;
import com.srstudios.retrofitdemo.util.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SR Studios";
    private ServiceManager rManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rManager = new ServiceManager();
        getCatalogs();
    }

    private void getCatalogs() {

        /**
         * Each Call from UService can make a synchronous or asynchronous HTTP request to the remote webserver.
         */

        Call<UCatalog> requestCatalog = rManager.getUService().listCatalog();
        final ArrayAdapter<Object> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        requestCatalog.enqueue(new Callback<UCatalog>() {
            @Override
            public void onResponse(Call<UCatalog> call, Response<UCatalog> response) {

                if (response.isSuccess()) {
                    UCatalog catalog = response.body();
                    List<String> cat = new ArrayList<>();
                    for (Course c : catalog.courses) {
                        cat.add(String.format("%s: %s.", c.title, c.subtitle));
                    }
                    listAdapter.addAll(cat);
                } else {
                    Log.i(TAG, "Error " + response.code());
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UCatalog> call, Throwable t) {
                Log.e(TAG, "Error " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String item = listView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected Course: \n"+item, Toast.LENGTH_LONG).show();

            }
        });
    }
}
