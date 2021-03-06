// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.utils.PicsGridView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PicsDataItemBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView datePics;

  @NonNull
  public final PicsGridView pics;

  private PicsDataItemBinding(@NonNull RelativeLayout rootView, @NonNull TextView datePics,
      @NonNull PicsGridView pics) {
    this.rootView = rootView;
    this.datePics = datePics;
    this.pics = pics;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PicsDataItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PicsDataItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.pics_data_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PicsDataItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.date_pics;
      TextView datePics = rootView.findViewById(id);
      if (datePics == null) {
        break missingId;
      }

      id = R.id.pics;
      PicsGridView pics = rootView.findViewById(id);
      if (pics == null) {
        break missingId;
      }

      return new PicsDataItemBinding((RelativeLayout) rootView, datePics, pics);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
