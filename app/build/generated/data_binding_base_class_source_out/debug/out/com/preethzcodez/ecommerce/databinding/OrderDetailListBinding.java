// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.tabs.TabLayout;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class OrderDetailListBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TableLayout bottomLay;

  @NonNull
  public final Button editOrder;

  @NonNull
  public final ListView orderProductList;

  @NonNull
  public final Button send1CButton;

  @NonNull
  public final TabLayout tabs;

  @NonNull
  public final ToolbarBinding toolbar;

  @NonNull
  public final TextView totalOrder;

  private OrderDetailListBinding(@NonNull RelativeLayout rootView, @NonNull TableLayout bottomLay,
      @NonNull Button editOrder, @NonNull ListView orderProductList, @NonNull Button send1CButton,
      @NonNull TabLayout tabs, @NonNull ToolbarBinding toolbar, @NonNull TextView totalOrder) {
    this.rootView = rootView;
    this.bottomLay = bottomLay;
    this.editOrder = editOrder;
    this.orderProductList = orderProductList;
    this.send1CButton = send1CButton;
    this.tabs = tabs;
    this.toolbar = toolbar;
    this.totalOrder = totalOrder;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OrderDetailListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OrderDetailListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.order_detail_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OrderDetailListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomLay;
      TableLayout bottomLay = rootView.findViewById(id);
      if (bottomLay == null) {
        break missingId;
      }

      id = R.id.editOrder;
      Button editOrder = rootView.findViewById(id);
      if (editOrder == null) {
        break missingId;
      }

      id = R.id.order_product_list;
      ListView orderProductList = rootView.findViewById(id);
      if (orderProductList == null) {
        break missingId;
      }

      id = R.id.send1CButton;
      Button send1CButton = rootView.findViewById(id);
      if (send1CButton == null) {
        break missingId;
      }

      id = R.id.tabs;
      TabLayout tabs = rootView.findViewById(id);
      if (tabs == null) {
        break missingId;
      }

      id = R.id.toolbar;
      View toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }
      ToolbarBinding binding_toolbar = ToolbarBinding.bind(toolbar);

      id = R.id.totalOrder;
      TextView totalOrder = rootView.findViewById(id);
      if (totalOrder == null) {
        break missingId;
      }

      return new OrderDetailListBinding((RelativeLayout) rootView, bottomLay, editOrder,
          orderProductList, send1CButton, tabs, binding_toolbar, totalOrder);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
