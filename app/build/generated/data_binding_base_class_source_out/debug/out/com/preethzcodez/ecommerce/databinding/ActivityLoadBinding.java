// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoadBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ProgressBar progBar;

  @NonNull
  public final TextView progressBarTitle;

  @NonNull
  public final TextView progressCircle;

  private ActivityLoadBinding(@NonNull RelativeLayout rootView, @NonNull ProgressBar progBar,
      @NonNull TextView progressBarTitle, @NonNull TextView progressCircle) {
    this.rootView = rootView;
    this.progBar = progBar;
    this.progressBarTitle = progressBarTitle;
    this.progressCircle = progressCircle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoadBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoadBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_load, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoadBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.progBar;
      ProgressBar progBar = rootView.findViewById(id);
      if (progBar == null) {
        break missingId;
      }

      id = R.id.progress_bar_title;
      TextView progressBarTitle = rootView.findViewById(id);
      if (progressBarTitle == null) {
        break missingId;
      }

      id = R.id.progress_circle;
      TextView progressCircle = rootView.findViewById(id);
      if (progressCircle == null) {
        break missingId;
      }

      return new ActivityLoadBinding((RelativeLayout) rootView, progBar, progressBarTitle,
          progressCircle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
