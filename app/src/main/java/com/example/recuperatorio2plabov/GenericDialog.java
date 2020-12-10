package com.example.recuperatorio2plabov;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class GenericDialog extends DialogFragment {
    String mensaje;
    String titulo;

    public GenericDialog(String titulo, String textoMensaje) {
        this.mensaje = textoMensaje;
        this.titulo = titulo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);
        builder.setMessage(this.mensaje);
        builder.setPositiveButton("Ok", null);
        return builder.create();
    }
}