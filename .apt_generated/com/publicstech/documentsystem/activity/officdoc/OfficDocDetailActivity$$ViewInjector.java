// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity.officdoc;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class OfficDocDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.officdoc.OfficDocDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361799, "field 'tvType'");
    target.tvType = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361802, "field 'ivGosign'");
    target.ivGosign = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361798, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361800, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361801, "field 'tabLayout'");
    target.tabLayout = (android.widget.TableLayout) view;
    view = finder.findRequiredView(source, 2131361803, "field 'btnGosave' and method 'OnGoSave'");
    target.btnGosave = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.OnGoSave();
        }
      });
  }

  public static void reset(com.publicstech.documentsystem.activity.officdoc.OfficDocDetailActivity target) {
    target.tvType = null;
    target.ivGosign = null;
    target.tvTitle = null;
    target.tvTime = null;
    target.tabLayout = null;
    target.btnGosave = null;
  }
}
