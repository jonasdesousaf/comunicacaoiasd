package br.ufc.quixada.dsdm.comunicacaoiasd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.dsdm.comunicacaoiasd.R;
import br.ufc.quixada.dsdm.comunicacaoiasd.adapter.AdapterContato;
import br.ufc.quixada.dsdm.comunicacaoiasd.adapter.RecyclerItemClickListener;
import br.ufc.quixada.dsdm.comunicacaoiasd.model.Contato;


public class ContatosFragment extends Fragment {

    private Context context;

    public ContatosFragment(Context context){
        this.context = context;
    }

    private static String TAG = ContatosFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterNotification;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterContato adapter;

    private List<Contato> contatos;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        contatos = getContatos();

        adapter = new AdapterContato(contatos);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(context, "" + contatos.get(position).toString(), Toast.LENGTH_LONG).show();
                    }
                })
        );

        return view;
    }

    public List<Contato> getContatos(){
        List<Contato> contatos = new ArrayList<Contato>();
        Contato contato = new Contato("Comunicação", "Jonas de Sousa", "José", "jonas@jonas", "jose@jose", "999999", "999999", "Messejana");
        Contato contato2 = new Contato("Comunicação", "Jonas de Sousa", "José", "jonas@jonas", "jose@jose", "999999", "999999", "Messejana");

        contatos.add(contato);
        contatos.add(contato2);
        return contatos;
    }

}
