// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.SettingActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361835, "field 'etServerPort'");
    target.etServerPort = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361834, "field 'etServerIP'");
    target.etServerIP = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361836, "method 'onSave'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSave();
        }
      });
  }

  public static void reset(com.publicstech.documentsystem.activity.SettingActivity target) {
    target.etServerPort = null;
    target.etServerIP = null;
  }
}
