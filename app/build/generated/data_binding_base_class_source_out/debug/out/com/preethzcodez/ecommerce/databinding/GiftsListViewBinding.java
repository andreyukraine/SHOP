// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GiftsListViewBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button buttonCancel;

  @NonNull
  public final Button buttonOk;

  @NonNull
  public final TextView countAll;

  @NonNull
  public final TextView countAllText;

  @NonNull
  public final ListView giftsListView;

  @NonNull
  public final LinearLayout popupBottom;

  @NonNull
  public final TextView popupTitle;

  private GiftsListViewBinding(@NonNull RelativeLayout rootView, @NonNull Button buttonCancel,
      @NonNull Button buttonOk, @NonNull TextView countAll, @NonNull TextView countAllText,
      @NonNull ListView giftsListView, @NonNull LinearLayout popupBottom,
      @NonNull TextView popupTitle) {
    this.rootView = rootView;
    this.buttonCancel = buttonCancel;
    this.buttonOk = buttonOk;
    this.countAll = countAll;
    this.countAllText = countAllText;
    this.giftsListView = giftsListView;
    this.popupBottom = popupBottom;
    this.popupTitle = popupTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static GiftsListViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GiftsListViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.gifts_list_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GiftsListViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonCancel;
      Button buttonCancel = rootView.findViewById(id);
      if (buttonCancel == null) {
        break missingId;
      }

      id = R.id.buttonOk;
      Button buttonOk = rootView.findViewById(id);
      if (buttonOk == null) {
        break missingId;
      }

      id = R.id.countAll;
      TextView countAll = rootView.findViewById(id);
      if (countAll == null) {
        break missingId;
      }

      id = R.id.countAllText;
      TextView countAllText = rootView.findViewById(id);
      if (countAllText == null) {
        break missingId;
      }

      id = R.id.gifts_list_view;
      ListView giftsListView = rootView.findViewById(id);
      if (giftsListView == null) {
        break missingId;
      }

      id = R.id.popup_bottom;
      LinearLayout popupBottom = rootView.findViewById(id);
      if (popupBottom == null) {
        break missingId;
      }

      id = R.id.popup_title;
      TextView popupTitle = rootView.findViewById(id);
      if (popupTitle == null) {
        break missingId;
      }

      return new GiftsListViewBinding((RelativeLayout) rootView, buttonCancel, buttonOk, countAll,
          countAllText, giftsListView, popupBottom, popupTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
