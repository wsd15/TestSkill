package com.example.testskill;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testskill.Response.Data_Response;
import com.example.testskill.model.Data_Model;
import com.example.testskill.retrofit.ApiEndPoint;
import com.example.testskill.retrofit.ApiService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterDaftarArticle extends RecyclerView.Adapter<AdapterDaftarArticle.ViewHolder> {

    UpdateDataArticleFragement updateDataArticleFragement;
    ApiEndPoint interfaceConnection;
    ArrayList<Data_Model> daftarArticle;
    Context mContext;
    String id;

    public AdapterDaftarArticle(Context context){
        daftarArticle = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daftar_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kodeArticle.setText(daftarArticle.get(position).getIdArticle());
        holder.namaArticle.setText(daftarArticle.get(position).getNama_article());
        holder.IsiArticle.setText(daftarArticle.get(position).getIsi_Article());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = daftarArticle.get(position).getIdArticle();
                popupDelete();
            }
        });
        
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kode = daftarArticle.get(position).getIdArticle();
                String nama = daftarArticle.get(position).getNama_article();
                String isi = daftarArticle.get(position).getIsi_Article();
                Bundle bundle = new Bundle();
//                bundle.putString("key", "data");
                bundle.putString("kdArticle", kode);
                bundle.putString("nmArticle", nama);
                bundle.putString("isiArticle", isi);

                Fragment fragment = new UpdateDataArticleFragement();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                  fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarArticle.size();
    }

    public void updatedataarticle(ArrayList<Data_Model> updatedataarticle){
        daftarArticle.clear();
        daftarArticle.addAll(updatedataarticle);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layout_daftar_article;
        TextView kodeArticle, namaArticle, IsiArticle;
        ImageButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_daftar_article = (ConstraintLayout)itemView.findViewById(R.id.layout_daftar_article);
            kodeArticle = (TextView) itemView.findViewById(R.id.textViewIdArticle);
            namaArticle = (TextView)itemView.findViewById(R.id.textViewNamaArticle);
            IsiArticle = (TextView)itemView.findViewById(R.id.textViewIsiArticle);
            btnDelete = (ImageButton)itemView.findViewById(R.id.imgBtnDelete);
            btnEdit = (ImageButton)itemView.findViewById(R.id.imgBtnEditArticle);
        }
    }

    private void popupDelete() {
        Context context = new ContextThemeWrapper(mContext, R.style.AppTheme);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setTitle("Hapus Article")
                .setMessage("hapus article ini?")
                .setNegativeButton("batalkan", null)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteArticle();
                    }
                })
                .show();
    }

    private void deleteArticle(){
        interfaceConnection = ApiService.getClient().create(ApiEndPoint.class);
        Call<Data_Response> hapus_data_article = interfaceConnection.hapus_article(id);
        hapus_data_article.enqueue(new Callback<Data_Response>() {
            @Override
            public void onResponse(Call<Data_Response> call, Response<Data_Response> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext,  response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(mContext, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Data_Response> call, Throwable t) {

            }
        });
    }


}
