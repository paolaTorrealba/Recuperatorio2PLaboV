package com.example.recuperatorio2plabov;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Dialog.NameDialogListener , SearchView.OnQueryTextListener  {
    public List<PersonaModel> personas;
    private List<PersonaModel> personasCopy = new ArrayList<>();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.prefs= getSharedPreferences("personas", Context.MODE_PRIVATE);

        String personasStr = this.prefs.getString("nuevaPersona","[]");
        this.personas = new ArrayList<PersonaModel>();
        this.personas.addAll(PersonaModel.generarLista(personasStr));
        this.personasCopy.addAll(personas);
        this.prefs= getSharedPreferences("personas", Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recuperatorio");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem((R.id.opSearch));
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Nuevo contacto");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.opcion1) {
            openDialog();
        }else if(item.getItemId() == android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void actualizarListaPersonas(PersonaModel persona) {
        this.personas.add(persona);
        this.personasCopy.clear();
        this.personasCopy.addAll(personas);
        TextView tv = findViewById(R.id.json);
        tv.setText(getJsonPersonas(this.personas).toString());

        SharedPreferences.Editor edit = this.prefs.edit();
        edit.putString("nuevaPersona" , getJsonPersonas(this.personas).toString());
        edit.commit();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private JSONArray getJsonPersonas(List<PersonaModel> lista) {
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i < lista.size(); i++) {
            jsonArray.put(lista.get(i).getJSONObject());
        }
        return jsonArray;
    }

    public void filter(String text) {
        boolean personaEncontrada = false;
        if(text.isEmpty()){
            personas.clear();
            personas.addAll(personasCopy);
        } else{
            List<PersonaModel> result = new ArrayList<>();
            text = text.toLowerCase();
            for(PersonaModel item: personasCopy){
                if(item.getNombre().toLowerCase().contains(text)){
                    result.add(item);
                    showDialog("Persona encontrada","El teléfono es:" + item.getTelefono());
                    personaEncontrada = true;
                    break;
                }
            }

            if(!personaEncontrada){
                showDialog("No encontrada","La persona que busco no está dentro de la lista" );
            }
            personas.clear();
            personas.addAll(result);
        }
    }

    private void showDialog(String title, String message){
        GenericDialog dialog = new GenericDialog(title,message);
        dialog.show(getSupportFragmentManager(), "test");
    }
}