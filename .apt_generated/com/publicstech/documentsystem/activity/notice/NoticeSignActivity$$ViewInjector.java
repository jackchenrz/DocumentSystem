// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity.notice;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NoticeSignActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.notice.NoticeSignActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361813, "field 'etNext'");
    target.etNext = (android.widget.EditText) view;
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
    view = finder.findRequiredView(source, 2131361816, "field 'tvText'");
    target.tvText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361817, "field 'etText'");
    target.etText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361819, "field 'tvNotify'");
    target.tvNotify = (android.widget.TextView) view;
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
  }

  public static void reset(com.publicstech.documentsystem.activity.notice.NoticeSignActivity target) {
    target.etNext = null;
    target.btnSave = null;
    target.llText = null;
    target.tvText = null;
    target.etText = null;
    target.tvNotify = null;
    target.ivNext = null;
  }
}
