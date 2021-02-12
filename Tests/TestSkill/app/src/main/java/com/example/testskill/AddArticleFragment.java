package com.example.testskill;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testskill.Response.Data_Response;
import com.example.testskill.retrofit.ApiService;
import com.example.testskill.retrofit.ApiEndPoint;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticleFragment extends Fragment {

    TextInputLayout layoutNamaArticle, layoutJumlahArticle;
    TextInputEditText inputNamaArticle, inputIsiArticle;
    Button btnTambahArticle;

    ApiEndPoint interfaceConnection;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutNamaArticle = (TextInputLayout)view.findViewById(R.id.layoutNamaArticle);
        layoutJumlahArticle = (TextInputLayout)view.findViewById(R.id.layoutJumlahArticle);
        inputNamaArticle = (TextInputEditText)view.findViewById(R.id.inputNamaArticle);
        inputIsiArticle = (TextInputEditText)view.findViewById(R.id.inputIsiArticle);
        btnTambahArticle = (Button)view.findViewById(R.id.btnTambahArticle);

        interfaceConnection = ApiService.getClient().create(ApiEndPoint.class);
        btnTambahArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Article();
            }
        });
    }

    private void add_Article() {
        String namaArticle = inputNamaArticle.getText().toString();
        String jmlArticle = inputIsiArticle.getText().toString();
        if (namaArticle.isEmpty() || namaArticle.length() == 0){
            layoutNamaArticle.setError("Nama Article Tidak Boleh Kosong");
        } else if (jmlArticle.isEmpty() || jmlArticle.length() == 0){
            layoutJumlahArticle.setError("Isi Article Tidak Boleh Kosong");
        } else if (((!namaArticle.isEmpty() && namaArticle.length() ==0) && (!jmlArticle.isEmpty() && jmlArticle.length()!=0))){
            Call<Data_Response> add_article = interfaceConnection.tambah_article(namaArticle, jmlArticle);
            add_article.enqueue(new Callback<Data_Response>() {
                @Override
                public void onResponse(Call<Data_Response> call, Response<Data_Response> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Data_Response> call, Throwable t) {
                    Log.d("Error here", "Error here", t);
                    t.printStackTrace();
                    Log.d("Error here", "Error here2", t);
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_article, container, false);
    }

}
