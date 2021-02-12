package com.example.testskill;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testskill.Response.Data_Response;
import com.example.testskill.model.Data_Model;
import com.example.testskill.retrofit.ApiEndPoint;
import com.example.testskill.retrofit.ApiService;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDataArticleFragement extends Fragment {

    TextInputEditText updateNamaArticle, updateJumlahArticle;
    TextInputLayout layoutUpdateNamaArticle, layoutUpdateJumlahArticle;
    Button btnUpdateArticle;
    String kodeArticle, namaArticle, jumlahArticle;
    TextView hiddenKodeArticle;
    ApiEndPoint interfaceConnection;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        interfaceConnection = ApiService.getClient().create(ApiEndPoint.class);
        updateNamaArticle = (TextInputEditText)view.findViewById(R.id.updateNamaArticle);
        updateJumlahArticle = (TextInputEditText)view.findViewById(R.id.updateIsiArticle);
        layoutUpdateNamaArticle = (TextInputLayout)view.findViewById(R.id.layoutUpdateNamaArticle);
        layoutUpdateJumlahArticle = (TextInputLayout)view.findViewById(R.id.layoutUpdateIsiArticle);
        hiddenKodeArticle = (TextView)view.findViewById(R.id.hiddenkodeArticle);
        btnUpdateArticle = (Button)view.findViewById(R.id.btnUpdateArticle);

        try {
            final Bundle bundle = getArguments();
            kodeArticle = bundle.getString("kdArticle");
            namaArticle = bundle.getString("nmArticle");
            jumlahArticle = bundle.getString("isiArticle");
        }
        catch(final Exception e){
            // Do nothing
        }

        updateNamaArticle.setText(namaArticle);
        updateJumlahArticle.setText(jumlahArticle);

        btnUpdateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArticle();
            }
        });
    }

    private void updateArticle() {

        String getNewNamaArticle = updateNamaArticle.getText().toString();
        String getNewJumlahaArticle = updateJumlahArticle.getText().toString();
        Data_Model dataModel = new Data_Model();
        dataModel.setNama_article(getNewJumlahaArticle);
        dataModel.setIsi_article(getNewNamaArticle);
        Call<Data_Response> update_data_article = interfaceConnection.update_article(kodeArticle, getNewNamaArticle, getNewJumlahaArticle);
        Log.d("ID Article", kodeArticle);
        Log.d("Nama Article", namaArticle);
        Log.d("Jumlah Article", jumlahArticle);
        update_data_article.enqueue(new Callback<Data_Response>() {
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
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_data_article, container, false);
    }


}
