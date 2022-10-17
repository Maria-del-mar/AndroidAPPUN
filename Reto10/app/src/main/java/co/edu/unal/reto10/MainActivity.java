package co.edu.unal.reto10;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    co.edu.unal.reto10.ListViewAdapter adapter;
    SearchView editsearch;

    RequestQueue queue;
    String url_base;
    String url_params;

    ArrayList<JSONObject> arraylist = new ArrayList<JSONObject>();
    String filterField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listview);
        editsearch = (SearchView) findViewById(R.id.search);

        queue = Volley.newRequestQueue(this);
        url_base = "https://www.datos.gov.co/resource/n48w-gutb.json";

        updateData(url_base, this);

        filterField = "proveedor";
    }

    public void updateData(String url, Context context){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray proveedor = new JSONArray(response);
                            for(int x = 0; x < proveedor.length(); x++)
                                arraylist.add(proveedor.getJSONObject(x));

                            adapter = new co.edu.unal.reto10.ListViewAdapter(context, arraylist);
                            list.setAdapter(adapter);

                            editsearch = (SearchView) findViewById(R.id.search);
                            editsearch.setOnQueryTextListener((SearchView.OnQueryTextListener) context);

                        } catch (JSONException e) {}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }
        );

        queue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        editsearch = (SearchView) findViewById(R.id.search);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text, filterField);
        return false;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_proveedor:
                if (checked){
                    filterField = "proveedor";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?proveedor=";
                    break;
                }
            case R.id.radio_tecnologia:
                if (checked){
                    filterField = "tecnologia";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?tecnologia=";
                    break;
                }
            case R.id.radio_segmento:
                if (checked){
                    filterField = "segmento";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?segmento=";
                    break;
                }
            case R.id.radio_accesos:
                if (checked){
                    filterField = "no_de_accesos";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?no_de_accesos=";
                    break;
                }
            case R.id.radio_velsubida:
                if (checked){
                    filterField = "velocidad_subida";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?velocidad_subida=";
                    break;
                }
            case R.id.radio_velbajada:
                if (checked){
                    filterField = "velocidad_bajada";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?velocidad_bajada=";
                    break;
                }
            case R.id.radio_departamento:
                if (checked){
                    filterField = "departamento";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?departamento=";
                    break;
                }
            case R.id.radio_municipio:
                if (checked){
                    filterField = "municipio";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?municipio=";
                    break;
                }
            case R.id.radio_codept:
                if (checked){
                    filterField = "velocidad_subida";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?cod_departamento=";
                    break;
                }
            case R.id.radio_codmunicipio:
                if (checked){
                    filterField = "cod_municipio";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?cod_municipio=";
                    break;
                }
            case R.id.trimestre:
                if (checked){
                    filterField = "trimestre";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?trimestre=";
                    break;
                }
            case R.id.radio_anno:
                if (checked){
                    filterField = "anno";
                    url_params = "https://www.datos.gov.co/resource/n48w-gutb.json?anno=";
                    break;
                }
        }
    }




}