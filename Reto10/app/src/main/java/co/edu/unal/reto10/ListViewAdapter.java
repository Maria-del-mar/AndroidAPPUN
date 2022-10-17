package co.edu.unal.reto10;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<JSONObject> proveedor;
    private ArrayList<JSONObject> arraylist;

    public ListViewAdapter(Context context, List<JSONObject> proveedor) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);

        this.proveedor = proveedor;
        this.arraylist = new ArrayList<JSONObject>();
        this.arraylist.addAll(proveedor);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return proveedor.size();
    }

    @Override
    public JSONObject getItem(int position) {
        return proveedor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_view_items, null);
            holder = new ViewHolder();

            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);

            Button viewButton = (Button) view.findViewById(R.id.view_info);
            viewButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                      ViewGroup viewGroup = v.findViewById(android.R.id.content);
                      View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.view_internet, viewGroup, false);
                      builder.setView(dialogView);

                      AlertDialog alertDialog = builder.create();
                      alertDialog.show();

                      try {
                        TextView proveedor_new = (TextView) dialogView.findViewById(R.id.proveedor);
                        proveedor_new.setText(proveedor.get(position).getString("proveedor"));

                        TextView tecnologia = (TextView) dialogView.findViewById(R.id.tecnologia);
                        tecnologia.setText(proveedor.get(position).getString("tecnologia"));

                        TextView segmento = (TextView) dialogView.findViewById(R.id.segmento);
                        segmento.setText(proveedor.get(position).getString("segmento"));

                        TextView velocidad_subida = (TextView) dialogView.findViewById(R.id.velocidad_subida);
                        velocidad_subida.setText(proveedor.get(position).getString("velocidad_subida"));

                          TextView velocidad_bajada = (TextView) dialogView.findViewById(R.id.velocidad_bajada);
                          velocidad_bajada.setText(proveedor.get(position).getString("velocidad_bajada"));

                          TextView departamento = (TextView) dialogView.findViewById(R.id.departamento);
                          departamento.setText(proveedor.get(position).getString("departamento"));

                          TextView cod_departamento = (TextView) dialogView.findViewById(R.id.cod_departamento);
                          cod_departamento.setText(proveedor.get(position).getString("cod_departamento"));

                          TextView municipio = (TextView) dialogView.findViewById(R.id.municipio);
                          municipio.setText(proveedor.get(position).getString("municipio"));

                          TextView cod_municipio = (TextView) dialogView.findViewById(R.id.cod_municipio);
                          cod_municipio.setText(proveedor.get(position).getString("cod_municipio"));

                          TextView anno = (TextView) dialogView.findViewById(R.id.anno);
                          anno.setText(proveedor.get(position).getString("anno"));

                          TextView trimestre = (TextView) dialogView.findViewById(R.id.trimestre);
                          trimestre.setText(proveedor.get(position).getString("trimestre"));

                          TextView no_de_accesos = (TextView) dialogView.findViewById(R.id.no_de_accesos);
                          no_de_accesos.setText(proveedor.get(position).getString("no_de_accesos"));

                        Button regresarButton = (Button) dialogView.findViewById(R.id.regresar);
                        regresarButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              alertDialog.dismiss();

                          }
                        });

                      } catch (JSONException e) {}


                  }

            });



        }
        else {
            holder = (ViewHolder) view.getTag();
        }


        // Set the results into TextViews
        try {
            String nombre = proveedor.get(position).getString("proveedor");
            holder.name.setText(nombre);
        } catch (JSONException e) {}

        return view;
    }


    // Filter Class
    public void filter(String charText, String campoName) {
        charText = charText.toLowerCase(Locale.getDefault());
        proveedor.clear();
        if (charText.length() == 0) {
            proveedor.addAll(arraylist);
        } else {
            for (JSONObject proveedor_new : arraylist) {
                try {
                    String campoValue = proveedor_new.getString(campoName);
                    if (campoValue.toLowerCase(Locale.getDefault()).contains(charText)) proveedor.add(proveedor_new);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        notifyDataSetChanged();
    }

}