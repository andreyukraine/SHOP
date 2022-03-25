package com.preethzcodez.ecommerce.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.ClientListAdapter;
import com.preethzcodez.ecommerce.comparators.SortByNameClient;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.hideProgress;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.isNetworkAvaliable;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class Clients extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private DB_Handler db_handler;
    private SessionManager sessionManager;
    private GridView clientsGrid;
    private ToolbarTitle toolbarTitleCallback;
    int id = 0;
    private Context thisContext;
    private TextView notClients;
    private User user;
    private List<Client> clientList;
    private ClientListAdapter clientListAdapter;
    private SwipeRefreshLayout swipeLayout;
    private RetrofitBuilder retrofitBuilder;
    private OkHttpClient httpClient;
    private String basicAuth;
    private ProgressDialog dialogProgress;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
        toolbarTitleCallback = (ToolbarTitle) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.clients_list, container, false);

        sessionManager = new SessionManager(getActivity());
        db_handler = new DB_Handler(getActivity());
        retrofitBuilder = new RetrofitBuilder(getContext());
        httpClient = retrofitBuilder.setClient();
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        setIds(view);

        swipeLayout.setOnRefreshListener(this);
        //swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        //add top menu fragment
        setHasOptionsMenu(true);

        // Get Data and Fill Grid
        clientList = db_handler.getClients();

        fillGridView();

        return view;
    }

    public void setClient() {
        //String session_client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        String session_client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getXml_id().equals(session_client_code)) {
                sessionManager.saveSession(Constants.SESSION_CLIENT_CODE, clientList.get(i).getXml_id());
                sessionManager.saveSession(Constants.SESSION_CLIENT_POSITION, Integer.toString(i));
            }
        }
    }

    // Set Ids
    private void setIds(View view) {
        clientsGrid = view.findViewById(R.id.clientsGrid);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        notClients = view.findViewById(R.id.notClients);
    }

    // Fill GridView With Data
    private void fillGridView() {
        //sort
        if (clientList.size() > 0) {
            swipeLayout.setVisibility(View.VISIBLE);
            notClients.setVisibility(View.GONE);
            Collections.sort(clientList, new SortByNameClient());
            setClient();
            clientListAdapter = new ClientListAdapter(getActivity(), clientList);
            clientsGrid.setNumColumns(1);
            clientsGrid.setAdapter(clientListAdapter);
        }else{
            swipeLayout.setVisibility(View.GONE);
            notClients.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }

        dialogProgress = ProgressDialog.show(getActivity(),"","Завантаження...",true);
        dialogProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogProgress.setContentView(R.layout.custom_progressdialog);
        dialogProgress.setCancelable(false);

        //проверяем есть интернет или нет
        if (isNetworkAvaliable(getContext())) {
            getClients();
        }else{
            swipeLayout.setRefreshing (false);
            hideProgress(dialogProgress);
            infoMsg(getContext(), "Немає інтернету");
        }
    }

    // Получаем клиентов
    private void getClients() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.CLIENTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ArrayList<Client>> call = retrofitInterface.getClients(basicAuth, user.getName());
        call.request();
        call.enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                try {
                    if (response.body() != null) {
                        clientData(response.body());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
               Util.getErrorMessage(t, getContext());
            }
        });

    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void clientData(ArrayList<Client> responseJSON) {
        for (int i = 0; i < responseJSON.size(); i++) {
            String name = responseJSON.get(i).getName();
            String xml_id = responseJSON.get(i).getXml_id();
            double dept = responseJSON.get(i).getDebt();
            String price_id = responseJSON.get(i).getPrice_id();
            String type = responseJSON.get(i).getPrice_id();
            String address = responseJSON.get(i).getAddress();
            String phone = responseJSON.get(i).getPhone();

            db_handler.insertClient(xml_id, name, dept, price_id, type, address, phone);
        }
        clientList = db_handler.getClients();
        dialogProgress.hide();
        fillGridView();
        swipeLayout.setRefreshing (false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                args.putString(Constants.SEARCH_TEXT, query);
                args.putString(Constants.TITLE, getResources().getString(R.string.TitleSearch));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clientList = db_handler.findClientsByName(newText);
                fillGridView();
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }
}
