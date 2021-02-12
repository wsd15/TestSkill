<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
Route::get('article','App\Http\Controllers\apicontroller@get_all');

Route::post('article/add','App\Http\Controllers\apicontroller@insert_data');

Route::put('article/update/{idarticle}', 'App\Http\Controllers\apicontroller@update_data');

Route::delete('article/delete/{idarticle}','App\Http\Controllers\apicontroller@delete_data');



Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});
