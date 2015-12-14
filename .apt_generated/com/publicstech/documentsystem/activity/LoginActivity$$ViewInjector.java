// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.LoginActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361806, "field 'etLoginName'");
    target.etLoginName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361808, "field 'cbRember'");
    target.cbRember = (android.widget.CheckBox) view;
    view = finder.findRequiredView(source, 2131361807, "field 'etPassword'");
    target.etPassword = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361809, "method 'onLogin'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onLogin();
        }
      });
  }

  public static void reset(com.publicstech.documentsystem.activity.LoginActivity target) {
    target.etLoginName = null;
    target.cbRember = null;
    target.etPassword = null;
  }
}
