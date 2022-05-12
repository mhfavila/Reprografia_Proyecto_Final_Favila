package com.example.reprografia_v2.DescargarDocumento;

import com.example.reprografia_v2.DTO.DocumentoDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * desde aqui consumimos la api pasandole el parametro sn al archivo php
 */
public interface Api {
    @FormUrlEncoded
    @POST("downloadDocument.php")
    Call<DocumentoDTO> downloadDocs(
            @Field("SN") int sn
    );
}
