package com.preethzcodez.ecommerce.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.preethzcodez.ecommerce.activities.LoadActivity;
import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.FinishActivity;
import com.preethzcodez.ecommerce.pojo.Auth;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import java.io.File;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.hideProgress;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class SignIn extends Fragment {

    private Button signIn;
    private EditText name, password;
    private ImageView showpassword;
    private boolean isPasswordShown = false;
    private FinishActivity finishActivityCallback;
    private DB_Handler db_handler;
    private boolean IS_NEW_APP = false;
    private RetrofitBuilder retrofitBuilder;
    private OkHttpClient httpClient;
    private Intent loadIntent;
    private View view;
    private ProgressDialog dialogProgress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        finishActivityCallback = (FinishActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sign_in, container, false);
        retrofitBuilder = new RetrofitBuilder(getContext());

        setIds(view);

        //Если база данных не существует, скопируйте ее из активов
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            IS_NEW_APP = true;
        }

        //mAuth = FirebaseAuth.getInstance();
        db_handler = new DB_Handler(getContext());

        setClickListeners();

        setNameUser();

        return view;
    }

    private void setNameUser() {
        User user = db_handler.getFirstUser();
        name.setText(user.getName());
        password.setText(user.getPassword());
    }

    //проверяем есть файл базы данных
    private boolean checkDataBase() {
        // определить папку с данными приложения
        boolean isEmptyBase;

        String dbFolder = getContext().getApplicationInfo().dataDir + "/" + "databases/E-Commerce";
        File dbFile = new File(dbFolder);
        if (dbFile.exists()) {
            isEmptyBase = true;
        } else {
            isEmptyBase = false;
        }
        return isEmptyBase;
    }

    // Set Click Listeners
    private void setClickListeners() {
        // Sign In
        signIn.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {

                if (name.getText().toString().length() > 0) {
                    if (password.getText().toString().length() > 0) {

                        dialogProgress = showProgress(getActivity());

                        //делаем первую букву заглавную
                        String UpperNameUser = Util.firstUpperCase(name.getText().toString());

                        //нужно проверить есть пользователь в базе
                        httpClient = retrofitBuilder.setClient1C(UpperNameUser, password.getText().toString());
                        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.AUTH_URL);
                        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                        // Call Web Service
                        String basic = getBase64Auth(UpperNameUser, password.getText().toString());
                        Call<Auth> call = retrofitInterface.getAuth(basic);
                        call.request();
                        call.enqueue(new Callback<Auth>() {
                            @Override
                            public void onResponse(Call<Auth> call, Response<Auth> response) {
                                try {
                                    if (response.body() != null) {

                                        //записываем в базу
                                        db_handler.registerUser(UpperNameUser, "", "38 067 000 00 00", password.getText().toString());


                                        loadIntent = new Intent(getActivity(), MainActivity.class);

                                        if (IS_NEW_APP) {
                                            loadIntent = new Intent(getActivity(), LoadActivity.class);
                                        }

                                        startActivity(loadIntent);

                                        finishActivityCallback.finishActivity();

                                    } else {
                                        hideProgress(dialogProgress);
                                        infoMsg(getContext(), "Пользователь или пароль введено неправно");
                                    }
                                } catch (Exception e) {
                                    hideProgress(dialogProgress);
                                    infoMsg(getContext(), e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<Auth> call, Throwable t) {
                                //тут смотрим что за ошибка при парсинге json или интернетом
                                hideProgress(dialogProgress);
                                infoMsg(getContext(), getString(R.string.NoInternet));

                                //TODO
                                //сделать проверку в базе данных когда нет интернета
                                if (db_handler.getUser(UpperNameUser, password.getText().toString())){
                                    loadIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(loadIntent);
                                    finishActivityCallback.finishActivity();
                                }
                            }
                        });
                    }else{
                        infoMsg(getContext(), "Введите пароль");
                    }
                }else{
                    infoMsg(getContext(), "Введите имя пользователя");
                }
            }
        });

        // Show / Hide Password
        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordShown) {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    showpassword.setImageResource(R.drawable.ic_eye_off_grey600_24dp);
                    isPasswordShown = false;
                } else {
                    password.setTransformationMethod(null);
                    showpassword.setImageResource(R.drawable.ic_eye_white_24dp);
                    isPasswordShown = true;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress(dialogProgress);
    }

    // Set Ids
    private void setIds(View view) {
        name = view.findViewById(R.id.name);
        password = view.findViewById(R.id.password);
        signIn = view.findViewById(R.id.signIn);
        showpassword = view.findViewById(R.id.showpassword);
    }
}


