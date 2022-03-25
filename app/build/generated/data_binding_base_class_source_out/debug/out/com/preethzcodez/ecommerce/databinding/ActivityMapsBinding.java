// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMapsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final EditText endDay;

  @NonNull
  public final LinearLayout filterMap;

  @NonNull
  public final EditText startDay;

  @NonNull
  public final Button submitMap;

  @NonNull
  public final ToolbarBinding toolbar;

  private ActivityMapsBinding(@NonNull RelativeLayout rootView, @NonNull EditText endDay,
      @NonNull LinearLayout filterMap, @NonNull EditText startDay, @NonNull Button submitMap,
      @NonNull ToolbarBinding toolbar) {
    this.rootView = rootView;
    this.endDay = endDay;
    this.filterMap = filterMap;
    this.startDay = startDay;
    this.submitMap = submitMap;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_maps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMapsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.endDay;
      EditText endDay = rootView.findViewById(id);
      if (endDay == null) {
        break missingId;
      }

      id = R.id.filter_map;
      LinearLayout filterMap = rootView.findViewById(id);
      if (filterMap == null) {
        break missingId;
      }

      id = R.id.startDay;
      EditText startDay = rootView.findViewById(id);
      if (startDay == null) {
        break missingId;
      }

      id = R.id.submitMap;
      Button submitMap = rootView.findViewById(id);
      if (submitMap == null) {
        break missingId;
      }

      id = R.id.toolbar;
      View toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }
      ToolbarBinding binding_toolbar = ToolbarBinding.bind(toolbar);

      return new ActivityMapsBinding((RelativeLayout) rootView, endDay, filterMap, startDay,
          submitMap, binding_toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}