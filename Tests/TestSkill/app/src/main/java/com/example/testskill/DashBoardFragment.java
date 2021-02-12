package com.example.testskill;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testskill.Response.Data_Response;
import com.example.testskill.model.Data_Model;
import com.example.testskill.retrofit.ApiEndPoint;
import com.example.testskill.retrofit.ApiService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardFragment extends Fragment {
    ArrayList<Data_Model> daftarseluruhArticle = new ArrayList<>();
    RecyclerView tabel_article;
    ApiEndPoint interfaceConnection;
    AdapterDaftarArticle adapterDaftarArticle;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabel_article = (RecyclerView)view.findViewById(R.id.recyclerView_daftarArticle);
        interfaceConnection = ApiService.getClient().create(ApiEndPoint.class);
        loadDataArticle();
    }
    private void loadDataArticle() {
        adapterDaftarArticle = new AdapterDaftarArticle(getContext());
        Call<Data_Response> daftar_article = interfaceConnection.daftar_article();
        daftar_article.enqueue(new Callback<Data_Response>() {
            @Override
            public void onResponse(Call<Data_Response> call, Response<Data_Response> response) {
                if (response.isSuccessful()){
                    List<Data_Model> seluruh_article = response.body().getSeluruh_article();
                    daftarseluruhArticle.addAll(seluruh_article);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapterDaftarArticle.updatedataarticle(daftarseluruhArticle);
            }
            @Override
            public void onFailure(Call<Data_Response> call, Throwable t) {
                Log.d("Error Jaringan", "disini");
                t.printStackTrace();
                Log.d("here", "here", t);
                Toast.makeText(getActivity(), "Data Tidak dapat ", Toast.LENGTH_SHORT).show();
            }
        });
        startRecyclerView();
    }

    private void startRecyclerView() {
        tabel_article.setAdapter(adapterDaftarArticle);
        tabel_article.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
}
