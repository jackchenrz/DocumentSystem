// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity.officdoc;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class OfficSignActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.officdoc.OfficSignActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361823, "field 'ivClass' and method 'onSelClass'");
    target.ivClass = (android.widget.ImageView) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSelClass();
        }
      });
    view = finder.findRequiredView(source, 2131361826, "field 'tvSel2'");
    target.tvSel2 = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361818, "field 'btnSave' and method 'onSave'");
    target.btnSave = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSave();
        }
      });
    view = finder.findRequiredView(source, 2131361815, "field 'llText'");
    target.llText = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361813, "field 'etNext'");
    target.etNext = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361827, "field 'ivRadio'");
    target.ivRadio = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361819, "field 'tvNotify'");
    target.tvNotify = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361825, "field 'llSel2'");
    target.llSel2 = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361824, "field 'ivSeluser'");
    target.ivSeluser = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361821, "field 'tvSel1'");
    target.tvSel1 = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361817, "field 'etText'");
    target.etText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361814, "field 'ivNext' and method 'onSelNext'");
    target.ivNext = (android.widget.ImageView) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSelNext();
        }
      });
    view = finder.findRequiredView(source, 2131361820, "field 'llSel1'");
    target.llSel1 = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361822, "field 'etClass'");
    target.etClass = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361816, "field 'tvText'");
    target.tvText = (android.widget.TextView) view;
  }

  public static void reset(com.publicstech.documentsystem.activity.officdoc.OfficSignActivity target) {
    target.ivClass = null;
    target.tvSel2 = null;
    target.btnSave = null;
    target.llText = null;
    target.etNext = null;
    target.ivRadio = null;
    target.tvNotify = null;
    target.llSel2 = null;
    target.ivSeluser = null;
    target.tvSel1 = null;
    target.etText = null;
    target.ivNext = null;
    target.llSel1 = null;
    target.etClass = null;
    target.tvText = null;
  }
}
