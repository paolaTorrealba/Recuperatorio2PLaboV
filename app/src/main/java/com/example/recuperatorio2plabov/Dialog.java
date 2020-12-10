package com.example.recuperatorio2plabov;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {

    private NameDialogListener listener;
    public PersonaModel persona;
    private TextView tvJSONArray;
    private EditText editNombre;
    private EditText editTelefono;


    public Dialog() { }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        editNombre= view.findViewById(R.id.edNombre);
        editTelefono= view.findViewById(R.id.edTelefono);
        persona = new PersonaModel();

        builder.setView(view)
                .setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = editNombre.getText().toString();
                        String telefono = editTelefono.getText().toString();

                        persona.setNombre(nombre);
                        persona.setTelefono(Integer.parseInt(telefono));
                        listener.actualizarListaPersonas(persona);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NameDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface  NameDialogListener{
        void actualizarListaPersonas(PersonaModel persona);
    }

}
