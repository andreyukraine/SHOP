// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout blockClient;

  @NonNull
  public final TextView checkClient;

  @NonNull
  public final FrameLayout contentHome;

  @NonNull
  public final TextView countOrder;

  @NonNull
  public final TextView dataUpdate;

  @NonNull
  public final TextView debtClient;

  @NonNull
  public final TableLayout homeMenu;

  @NonNull
  public final RelativeLayout idHome;

  @NonNull
  public final TextView myOrders;

  @NonNull
  public final TextView myRoads;

  @NonNull
  public final TextView textNameClient;

  private ActivityHomeBinding(@NonNull RelativeLayout rootView, @NonNull RelativeLayout blockClient,
      @NonNull TextView checkClient, @NonNull FrameLayout contentHome, @NonNull TextView countOrder,
      @NonNull TextView dataUpdate, @NonNull TextView debtClient, @NonNull TableLayout homeMenu,
      @NonNull RelativeLayout idHome, @NonNull TextView myOrders, @NonNull TextView myRoads,
      @NonNull TextView textNameClient) {
    this.rootView = rootView;
    this.blockClient = blockClient;
    this.checkClient = checkClient;
    this.contentHome = contentHome;
    this.countOrder = countOrder;
    this.dataUpdate = dataUpdate;
    this.debtClient = debtClient;
    this.homeMenu = homeMenu;
    this.idHome = idHome;
    this.myOrders = myOrders;
    this.myRoads = myRoads;
    this.textNameClient = textNameClient;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.block_client;
      RelativeLayout blockClient = rootView.findViewById(id);
      if (blockClient == null) {
        break missingId;
      }

      id = R.id.check_client;
      TextView checkClient = rootView.findViewById(id);
      if (checkClient == null) {
        break missingId;
      }

      id = R.id.content_home;
      FrameLayout contentHome = rootView.findViewById(id);
      if (contentHome == null) {
        break missingId;
      }

      id = R.id.countOrder;
      TextView countOrder = rootView.findViewById(id);
      if (countOrder == null) {
        break missingId;
      }

      id = R.id.dataUpdate;
      TextView dataUpdate = rootView.findViewById(id);
      if (dataUpdate == null) {
        break missingId;
      }

      id = R.id.debt_client;
      TextView debtClient = rootView.findViewById(id);
      if (debtClient == null) {
        break missingId;
      }

      id = R.id.homeMenu;
      TableLayout homeMenu = rootView.findViewById(id);
      if (homeMenu == null) {
        break missingId;
      }

      id = R.id.idHome;
      RelativeLayout idHome = rootView.findViewById(id);
      if (idHome == null) {
        break missingId;
      }

      id = R.id.myOrders;
      TextView myOrders = rootView.findViewById(id);
      if (myOrders == null) {
        break missingId;
      }

      id = R.id.myRoads;
      TextView myRoads = rootView.findViewById(id);
      if (myRoads == null) {
        break missingId;
      }

      id = R.id.textNameClient;
      TextView textNameClient = rootView.findViewById(id);
      if (textNameClient == null) {
        break missingId;
      }

      return new ActivityHomeBinding((RelativeLayout) rootView, blockClient, checkClient,
          contentHome, countOrder, dataUpdate, debtClient, homeMenu, idHome, myOrders, myRoads,
          textNameClient);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}