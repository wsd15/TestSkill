<?php

namespace App\Http\Controllers;
use App\Models\ArtModel;

use Illuminate\Http\Request;

class apicontroller extends Controller
{
    public function get_all(){
        return response()->json(ArtModel::all(),200);
    }

    public function insert_data(Request $request){
        $insert_data = new ArtModel();
        $insert_data->namaarticle =$request->namaArticle;
        $insert_data->isiarticle = $request->isiArticle;
        $insert_data->save();
        return response()->json(200);
    }

    public function update_data(Request $request,$id){
        $check = ArtModel::firstWhere('idarticle',$id);
        if ($check){
            $data = ArtModel::find($id);
            $data->namaarticle = $request->namaArticle;
            $data->isiarticle = $request->isiArticle;
            $data->save();
            return response()->json(200);
        }
        else
            {
            return response()->json(404);
        }

    }


    public function delete_data($id){
        $check = ArtModel::firstWhere('idarticle',$id);
        if ($check){
            ArtModel::destroy($id);
            return response(200);
        }else{
            return response(404);
        }


    }



}
