// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity.doc;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SignActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.doc.SignActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361817, "field 'etText'");
    target.etText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361816, "field 'tvText'");
    target.tvText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361815, "field 'llText'");
    target.llText = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361841, "field 'tvSel3'");
    target.tvSel3 = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361820, "field 'llSel1'");
    target.llSel1 = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361821, "field 'tvSel1'");
    target.tvSel1 = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361837, "field 'etDep'");
    target.etDep = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361839, "field 'iv_line_dep'");
    target.iv_line_dep = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361842, "field 'etMode'");
    target.etMode = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361840, "field 'llSel3'");
    target.llSel3 = (android.widget.LinearLayout) view;
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
    view = finder.findRequiredView(source, 2131361838, "field 'ivDep' and method 'onSelLookMan'");
    target.ivDep = (android.widget.ImageView) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSelLookMan();
        }
      });
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
    view = finder.findRequiredView(source, 2131361819, "field 'tvNotify'");
    target.tvNotify = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361844, "field 'iv_line_mode'");
    target.iv_line_mode = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361843, "field 'ivMode' and method 'onSelMode'");
    target.ivMode = (android.widget.ImageView) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSelMode();
        }
      });
    view = finder.findRequiredView(source, 2131361825, "field 'llSel2'");
    target.llSel2 = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361826, "field 'tvSel2'");
    target.tvSel2 = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361822, "field 'etClass'");
    target.etClass = (android.widget.EditText) view;
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
    view = finder.findRequiredView(source, 2131361813, "field 'etNext'");
    target.etNext = (android.widget.EditText) view;
  }

  public static void reset(com.publicstech.documentsystem.activity.doc.SignActivity target) {
    target.etText = null;
    target.tvText = null;
    target.llText = null;
    target.tvSel3 = null;
    target.llSel1 = null;
    target.tvSel1 = null;
    target.etDep = null;
    target.iv_line_dep = null;
    target.etMode = null;
    target.llSel3 = null;
    target.ivNext = null;
    target.ivDep = null;
    target.btnSave = null;
    target.tvNotify = null;
    target.iv_line_mode = null;
    target.ivMode = null;
    target.llSel2 = null;
    target.tvSel2 = null;
    target.etClass = null;
    target.ivClass = null;
    target.etNext = null;
  }
}
