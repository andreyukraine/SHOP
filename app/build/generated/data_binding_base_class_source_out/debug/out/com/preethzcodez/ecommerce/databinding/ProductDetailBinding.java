// Generated by view binder compiler. Do not edit!
package com.preethzcodez.ecommerce.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import com.preethzcodez.ecommerce.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.apmem.tools.layouts.FlowLayout;

public final class ProductDetailBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout Titleblock;

  @NonNull
  public final TextView barcode;

  @NonNull
  public final View borderBotom;

  @NonNull
  public final View borderTop;

  @NonNull
  public final TableLayout bottomLay;

  @NonNull
  public final TextView count;

  @NonNull
  public final FlowLayout countLay;

  @NonNull
  public final LinearLayout countParentLay;

  @NonNull
  public final LinearLayout detailProductLayout;

  @NonNull
  public final ImageView image;

  @NonNull
  public final ImageView minus;

  @NonNull
  public final FlowLayout optionLay;

  @NonNull
  public final TextView options;

  @NonNull
  public final LinearLayout optionsParentLay;

  @NonNull
  public final TextView percent;

  @NonNull
  public final ImageView plus;

  @NonNull
  public final TextView priceBase;

  @NonNull
  public final TextView priceClient;

  @NonNull
  public final EditText quantityValue;

  @NonNull
  public final LinearLayout quntityBlock;

  @NonNull
  public final SwipeRefreshLayout refresherDetailProduct;

  @NonNull
  public final TextView sku;

  @NonNull
  public final ScrollView srollData;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView titleBarcode;

  @NonNull
  public final TextView titleSku;

  @NonNull
  public final ToolbarBinding toolbar;

  @NonNull
  public final TextView totalText;

  private ProductDetailBinding(@NonNull RelativeLayout rootView, @NonNull LinearLayout Titleblock,
      @NonNull TextView barcode, @NonNull View borderBotom, @NonNull View borderTop,
      @NonNull TableLayout bottomLay, @NonNull TextView count, @NonNull FlowLayout countLay,
      @NonNull LinearLayout countParentLay, @NonNull LinearLayout detailProductLayout,
      @NonNull ImageView image, @NonNull ImageView minus, @NonNull FlowLayout optionLay,
      @NonNull TextView options, @NonNull LinearLayout optionsParentLay, @NonNull TextView percent,
      @NonNull ImageView plus, @NonNull TextView priceBase, @NonNull TextView priceClient,
      @NonNull EditText quantityValue, @NonNull LinearLayout quntityBlock,
      @NonNull SwipeRefreshLayout refresherDetailProduct, @NonNull TextView sku,
      @NonNull ScrollView srollData, @NonNull TextView title, @NonNull TextView titleBarcode,
      @NonNull TextView titleSku, @NonNull ToolbarBinding toolbar, @NonNull TextView totalText) {
    this.rootView = rootView;
    this.Titleblock = Titleblock;
    this.barcode = barcode;
    this.borderBotom = borderBotom;
    this.borderTop = borderTop;
    this.bottomLay = bottomLay;
    this.count = count;
    this.countLay = countLay;
    this.countParentLay = countParentLay;
    this.detailProductLayout = detailProductLayout;
    this.image = image;
    this.minus = minus;
    this.optionLay = optionLay;
    this.options = options;
    this.optionsParentLay = optionsParentLay;
    this.percent = percent;
    this.plus = plus;
    this.priceBase = priceBase;
    this.priceClient = priceClient;
    this.quantityValue = quantityValue;
    this.quntityBlock = quntityBlock;
    this.refresherDetailProduct = refresherDetailProduct;
    this.sku = sku;
    this.srollData = srollData;
    this.title = title;
    this.titleBarcode = titleBarcode;
    this.titleSku = titleSku;
    this.toolbar = toolbar;
    this.totalText = totalText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProductDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProductDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.product_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProductDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Titleblock;
      LinearLayout Titleblock = rootView.findViewById(id);
      if (Titleblock == null) {
        break missingId;
      }

      id = R.id.barcode;
      TextView barcode = rootView.findViewById(id);
      if (barcode == null) {
        break missingId;
      }

      id = R.id.border_botom;
      View borderBotom = rootView.findViewById(id);
      if (borderBotom == null) {
        break missingId;
      }

      id = R.id.border_top;
      View borderTop = rootView.findViewById(id);
      if (borderTop == null) {
        break missingId;
      }

      id = R.id.bottomLay;
      TableLayout bottomLay = rootView.findViewById(id);
      if (bottomLay == null) {
        break missingId;
      }

      id = R.id.count;
      TextView count = rootView.findViewById(id);
      if (count == null) {
        break missingId;
      }

      id = R.id.countLay;
      FlowLayout countLay = rootView.findViewById(id);
      if (countLay == null) {
        break missingId;
      }

      id = R.id.countParentLay;
      LinearLayout countParentLay = rootView.findViewById(id);
      if (countParentLay == null) {
        break missingId;
      }

      id = R.id.detailProductLayout;
      LinearLayout detailProductLayout = rootView.findViewById(id);
      if (detailProductLayout == null) {
        break missingId;
      }

      id = R.id.image;
      ImageView image = rootView.findViewById(id);
      if (image == null) {
        break missingId;
      }

      id = R.id.minus;
      ImageView minus = rootView.findViewById(id);
      if (minus == null) {
        break missingId;
      }

      id = R.id.optionLay;
      FlowLayout optionLay = rootView.findViewById(id);
      if (optionLay == null) {
        break missingId;
      }

      id = R.id.options;
      TextView options = rootView.findViewById(id);
      if (options == null) {
        break missingId;
      }

      id = R.id.optionsParentLay;
      LinearLayout optionsParentLay = rootView.findViewById(id);
      if (optionsParentLay == null) {
        break missingId;
      }

      id = R.id.percent;
      TextView percent = rootView.findViewById(id);
      if (percent == null) {
        break missingId;
      }

      id = R.id.plus;
      ImageView plus = rootView.findViewById(id);
      if (plus == null) {
        break missingId;
      }

      id = R.id.priceBase;
      TextView priceBase = rootView.findViewById(id);
      if (priceBase == null) {
        break missingId;
      }

      id = R.id.priceClient;
      TextView priceClient = rootView.findViewById(id);
      if (priceClient == null) {
        break missingId;
      }

      id = R.id.quantityValue;
      EditText quantityValue = rootView.findViewById(id);
      if (quantityValue == null) {
        break missingId;
      }

      id = R.id.quntityBlock;
      LinearLayout quntityBlock = rootView.findViewById(id);
      if (quntityBlock == null) {
        break missingId;
      }

      id = R.id.refresherDetailProduct;
      SwipeRefreshLayout refresherDetailProduct = rootView.findViewById(id);
      if (refresherDetailProduct == null) {
        break missingId;
      }

      id = R.id.sku;
      TextView sku = rootView.findViewById(id);
      if (sku == null) {
        break missingId;
      }

      id = R.id.srollData;
      ScrollView srollData = rootView.findViewById(id);
      if (srollData == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = rootView.findViewById(id);
      if (title == null) {
        break missingId;
      }

      id = R.id.title_barcode;
      TextView titleBarcode = rootView.findViewById(id);
      if (titleBarcode == null) {
        break missingId;
      }

      id = R.id.title_sku;
      TextView titleSku = rootView.findViewById(id);
      if (titleSku == null) {
        break missingId;
      }

      id = R.id.toolbar;
      View toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }
      ToolbarBinding binding_toolbar = ToolbarBinding.bind(toolbar);

      id = R.id.totalText;
      TextView totalText = rootView.findViewById(id);
      if (totalText == null) {
        break missingId;
      }

      return new ProductDetailBinding((RelativeLayout) rootView, Titleblock, barcode, borderBotom,
          borderTop, bottomLay, count, countLay, countParentLay, detailProductLayout, image, minus,
          optionLay, options, optionsParentLay, percent, plus, priceBase, priceClient,
          quantityValue, quntityBlock, refresherDetailProduct, sku, srollData, title, titleBarcode,
          titleSku, binding_toolbar, totalText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
