// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SelectActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.SelectActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361829, "field 'ivDocument'");
    target.ivDocument = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361830, "field 'llOfficdoc' and method 'onOfficdoc'");
    target.llOfficdoc = (android.widget.LinearLayout) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOfficdoc();
        }
      });
    view = finder.findRequiredView(source, 2131361832, "field 'llNotice' and method 'onNotice'");
    target.llNotice = (android.widget.LinearLayout) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onNotice();
        }
      });
    view = finder.findRequiredView(source, 2131361828, "field 'llDocument' and method 'onDocument'");
    target.llDocument = (android.widget.LinearLayout) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onDocument();
        }
      });
    view = finder.findRequiredView(source, 2131361831, "field 'ivOfficdoc'");
    target.ivOfficdoc = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361833, "field 'ivNotice'");
    target.ivNotice = (android.widget.ImageView) view;
  }

  public static void reset(com.publicstech.documentsystem.activity.SelectActivity target) {
    target.ivDocument = null;
    target.llOfficdoc = null;
    target.llNotice = null;
    target.llDocument = null;
    target.ivOfficdoc = null;
    target.ivNotice = null;
  }
}
