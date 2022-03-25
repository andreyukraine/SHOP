// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SignUpBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView back;

  @NonNull
  public final EditText email;

  @NonNull
  public final EditText mobile;

  @NonNull
  public final EditText name;

  @NonNull
  public final EditText password;

  @NonNull
  public final ImageView showpassword;

  @NonNull
  public final Button signup;

  private SignUpBinding(@NonNull LinearLayout rootView, @NonNull ImageView back,
      @NonNull EditText email, @NonNull EditText mobile, @NonNull EditText name,
      @NonNull EditText password, @NonNull ImageView showpassword, @NonNull Button signup) {
    this.rootView = rootView;
    this.back = back;
    this.email = email;
    this.mobile = mobile;
    this.name = name;
    this.password = password;
    this.showpassword = showpassword;
    this.signup = signup;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SignUpBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back;
      ImageView back = rootView.findViewById(id);
      if (back == null) {
        break missingId;
      }

      id = R.id.email;
      EditText email = rootView.findViewById(id);
      if (email == null) {
        break missingId;
      }

      id = R.id.mobile;
      EditText mobile = rootView.findViewById(id);
      if (mobile == null) {
        break missingId;
      }

      id = R.id.name;
      EditText name = rootView.findViewById(id);
      if (name == null) {
        break missingId;
      }

      id = R.id.password;
      EditText password = rootView.findViewById(id);
      if (password == null) {
        break missingId;
      }

      id = R.id.showpassword;
      ImageView showpassword = rootView.findViewById(id);
      if (showpassword == null) {
        break missingId;
      }

      id = R.id.signup;
      Button signup = rootView.findViewById(id);
      if (signup == null) {
        break missingId;
      }

      return new SignUpBinding((LinearLayout) rootView, back, email, mobile, name, password,
          showpassword, signup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}