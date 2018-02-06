package com.example.mostafa.aman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafa.aman.connection.HttpManager;
import com.example.mostafa.aman.connection.RequestPackage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.X;
import static android.view.View.Z;
import static com.example.mostafa.aman.R.id.menu_item_info;


public class MainActivity extends AppCompatActivity {

    Spinner government, specially;
    String gov_text, spec_txt;
    Button search ;
    JSONArray jsonArray = null;
    String uri = null ;
    ArrayList<Model> data = new ArrayList<Model>() ;
    ArrayList<Model> test;
    Model model;
    Intent intent ;
    ListView list;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (Button) findViewById(R.id.search_btn);
        government = (Spinner) findViewById(R.id.spinner_govrnment);
        intent = new Intent(MainActivity.this,Doctors.class);
         b = new Bundle();
        test = new ArrayList<Model>();


//         list = (ListView) findViewById(R.id.mylist);

        ArrayAdapter gov_adapter = ArrayAdapter.createFromResource(this, R.array.government, android.R.layout.simple_spinner_item);
        government.setAdapter(gov_adapter);

        government.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    TextView txt_gov = (TextView) view;
                    gov_text = (String) txt_gov.getText();
                    Log.e("test", "gov is " + gov_text);
                }catch (Exception e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        specially = (Spinner) findViewById(R.id.spinner_specially);
        ArrayAdapter spec_adapter = ArrayAdapter.createFromResource(this, R.array.specially, android.R.layout.simple_spinner_item);
        specially.setAdapter(spec_adapter);
        specially.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    TextView txt_spec = (TextView) view;
                    spec_txt = (String) txt_spec.getText();
                    Log.e("test", "spec is " + spec_txt);
                } catch (Exception e) {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                RequestPackage request = new RequestPackage();
                request.setMethod("POST");
                request.setUri("search");
                if (validateInput()) {
                    request.setParam("govrnment", gov_text);
                    request.setParam("specialty", spec_txt);

                    if (isOnline() == false) {
                        Toast.makeText(MainActivity.this, "من فضلك تأكد من تحقيق اتصالك بالانترنت..!", Toast.LENGTH_LONG).show();

                    } else {

                        Log.e("CarTest", gov_text + "and" + spec_txt);

                        try {
                            new searchTask(getApplicationContext()).execute(request);

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "من فضلك اختر المحافظه والتخصص..!", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });


        }

    private boolean validateInput() {

        if (gov_text != null && !gov_text.isEmpty()) {
        } else {
            Toast.makeText(getBaseContext(), "من فضلك اختر المحافظه ..!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gov_text.equalsIgnoreCase("ختر محافظتك")) {

            Toast.makeText(getBaseContext(), "من فضلك اختر المحافظه ..!",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {}

        if (spec_txt != null &&!spec_txt.isEmpty()) {
        } else {
            Toast.makeText(getBaseContext(), "من فضلك اختر اسم التخصص ..!",
                    Toast.LENGTH_SHORT).show();
            return false;

        }

        if (spec_txt.equalsIgnoreCase("اختر اسم التخصص")) {
            Toast.makeText(getBaseContext(), "من فضلك اختر اسم التخصص ..!",
                    Toast.LENGTH_SHORT).show();
            return false;

        } else {}

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.menu_item_info:

                Intent i = new Intent(MainActivity.this,about.class);
                startActivity(i);
                break;

            case R.id.menu_item_exit:
                finish();
                System.exit(0);
                break;

        }
                return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//
    private class searchTask extends AsyncTask<RequestPackage, String, ArrayList<Model>> {
    Context context ;
    public searchTask(Context context) {
        this.context = context;
    }
        String degree = null;
        String PhoneNumber = null;
        String address = null;
        String doc_name = null;
        private ProgressDialog progressDialog;

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // prgress dialouge
            progressDialog = ProgressDialog.show(MainActivity.this, "جاري التحميل  ...", "من فضلك انتظر .. ", true);
        }

        @Override
        protected ArrayList<Model> doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
//            Log.d("CatTest", "content is" + content);
            try {
                JSONArray resArray = new JSONArray(content);
                for (int j = 0; j < resArray.length(); j++) {
                    JSONObject jsonObject = resArray.getJSONObject(j);
                    degree = jsonObject.getString("الدرجة العلميه للطبيب");
                    PhoneNumber = jsonObject.getString("التليفون");
                    address = jsonObject.getString("العنوان");
                    doc_name = jsonObject.getString("اسم الدكتور");
                    model = new Model(degree,PhoneNumber,address,doc_name,gov_text,spec_txt);
                    data.add(model);


                }
//
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }


            @Override
        protected void onPostExecute(ArrayList<Model> models) {
                super.onPostExecute(models);
                progressDialog.dismiss();

                Log.i("test", models == null ? "yes" : "no");
                test = models;
                Log.d("CarTest", "Test arry is "+test.size());
                Log.d("CarTest", "main data arry is "+data.size());

                b.putSerializable("data", test);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
    }
        }