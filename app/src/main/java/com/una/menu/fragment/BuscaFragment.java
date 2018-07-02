package com.una.menu.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.una.menu.R;
import com.una.menu.adapter.ProdutoAdapter;
import com.una.menu.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscaFragment extends Fragment {

    private Context context;

    // Variaveis
    private SearchView searchProdutos;
    private RecyclerView recyclerProdutos;
    private List<Produto> listaProduto = new ArrayList<>();
    private ProgressBar iconeLoad;
    private String HOST = "https://menu-app.000webhostapp.com/webservice";

    // Configurar Adapter
    ProdutoAdapter adapter = new ProdutoAdapter(listaProduto);


    public BuscaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busca, container, false);

        // -------------------------- INICIO DO CÓDIGO ------------------------

        searchProdutos = view.findViewById(R.id.buscaProdutos);
        recyclerProdutos = view.findViewById(R.id.recyclerViewProdutos);
        iconeLoad = view.findViewById(R.id.progressBar2);

        iconeLoad.setVisibility(View.VISIBLE);


        // Configura SearchView
        searchProdutos.setQueryHint("Pesquisa Produtos");

        // Lista Produtos
        this.buscaAutomaticaProdutos();


        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerProdutos.setLayoutManager(layoutManager);
        recyclerProdutos.setHasFixedSize(true);
        recyclerProdutos.addItemDecoration(new DividerItemDecoration(context,LinearLayout.VERTICAL));
        recyclerProdutos.setAdapter(adapter);


        // Configura SearchView
        searchProdutos.setQueryHint("Pesquisar Produtos");
        searchProdutos.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                String produto = "%" + query + "%";
                pesquisaProdutos(produto);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //System.out.println("newText: "+ newText);
                //System.out.println(produto);
                return false;
            }
        });

        // -------------------------- FIM DO CÓDIGO ------------------------
        return  view;

    }

    private void buscaAutomaticaProdutos() {

//        fechaTeclado();
        iconeLoad.setVisibility(View.VISIBLE);

        String url = HOST + "/readprodutos/read.php";

        Ion.with(context)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {


                        try {

                            for(int i = 0; i < result.size(); i++) {

                                JsonObject obj = result.get(i).getAsJsonObject();

                                //String nome = obj.get("nome").getAsString();
                                //Produto p = new Produto(nome);

                                Produto p = new Produto();
                                p.setId_produto(obj.get("id_produto").getAsString());
                                p.setNome(obj.get("nome").getAsString());
                                p.setDescricao(obj.get("descricao").getAsString());
                                p.setPreco(obj.get("preco").getAsString());

                                listaProduto.add(p);
                            }
                            adapter.notifyDataSetChanged();


                        } catch (Exception erro) {
                            Toast.makeText(context, "Ops! Erro, " + erro, Toast.LENGTH_LONG).show();
                        }

                        iconeLoad.setVisibility(View.GONE);
                    }
                });
    }

    private void pesquisaProdutos(String produto) {

//        fechaTeclado();
        iconeLoad.setVisibility(View.VISIBLE);

        String url = HOST + "/readprodutos/readpesquisa.php";
        listaProduto.clear();

        if (produto.length() > 0) {

            Ion.with(context)
                    .load(url)
                    .setBodyParameter("produto", produto)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            try {

                                for (int i = 0; i < result.size(); i++) {

                                    JsonObject obj = result.get(i).getAsJsonObject();

                                    //String nome = obj.get("nome").getAsString();
                                    //Produto p = new Produto(nome);

                                    Produto p = new Produto();
                                    p.setId_produto(obj.get("id_produto").getAsString());
                                    p.setNome(obj.get("nome").getAsString());
                                    p.setDescricao(obj.get("descricao").getAsString());
                                    p.setPreco(obj.get("preco").getAsString());

                                    listaProduto.add(p);
                                }
                                adapter.notifyDataSetChanged();


                            } catch (Exception erro) {

                                Toast.makeText(context, "Ops! Erro, " + erro, Toast.LENGTH_LONG).show();
                            }

                            iconeLoad.setVisibility(View.GONE);

                        }
                    });
        }
    }

    //Declara um atributo para guardar o context.
    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

   /* private void fechaTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/
}