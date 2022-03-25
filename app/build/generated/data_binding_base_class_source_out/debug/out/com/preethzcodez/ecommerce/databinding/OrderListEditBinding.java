// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class OrderListEditBinding implements ViewBinding {
  @NonNull
  private final ListView rootView;

  @NonNull
  public final ListView listview;

  private OrderListEditBinding(@NonNull ListView rootView, @NonNull ListView listview) {
    this.rootView = rootView;
    this.listview = listview;
  }

  @Override
  @NonNull
  public ListView getRoot() {
    return rootView;
  }

  @NonNull
  public static OrderListEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OrderListEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.order_list_edit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OrderListEditBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    ListView listview = (ListView) rootView;

    return new OrderListEditBinding((ListView) rootView, listview);
  }
}