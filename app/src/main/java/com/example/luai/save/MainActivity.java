package com.example.luai.save;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    Button btn;
    EditText input;


    ArrayList<String> listluai;
    ArrayAdapter adapter;
    EditText erase;
    Button btnsave;
    Button btnrestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnsave = (Button) findViewById(R.id.btnsave);

        btnrestor = (Button) findViewById(R.id.btnrestore);
        btn = (Button) findViewById(R.id.btn);
        listview = (ListView) findViewById(R.id.listview);
        //  name = new String("name1");
        listluai = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listluai);
        input = (EditText) findViewById(R.id.input);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = input.getText().toString();
                listluai.add(names);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savedata();

                    }
                });



                btnrestor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loaddata();

                    }
                });


                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        Toast.makeText(MainActivity.this, listluai.remove(position), Toast.LENGTH_LONG).show();
                        // list.remove(position);
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });

                //  erase.setText("");


            }
        });


    }

    private void savedata() {

        SharedPreferences sharedPreferences = getSharedPreferences("task", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listluai);
        editor.putString("task", json);
        editor.apply();


    }

    private void loaddata() {

        SharedPreferences sharedPreferences = getSharedPreferences("task", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task", null);

        Type type=new TypeToken<ArrayList<listluai>>(){}.getType();
        listluai = gson.fromJson(json, type);


    }
}