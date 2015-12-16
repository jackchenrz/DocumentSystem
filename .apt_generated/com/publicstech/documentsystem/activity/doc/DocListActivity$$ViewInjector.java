// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.activity.doc;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DocListActivity$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.activity.doc.DocListActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361797, "field 'tv_desc_loading'");
    target.tv_desc_loading = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361793, "field 'pager'");
    target.pager = (android.support.v4.view.ViewPager) view;
    view = finder.findRequiredView(source, 2131361795, "field 'llLoading'");
    target.llLoading = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361794, "field 'tabStrip'");
    target.tabStrip = (android.support.v4.view.PagerTabStrip) view;
    view = finder.findRequiredView(source, 2131361796, "field 'progressBar'");
    target.progressBar = (android.widget.ProgressBar) view;
  }

  public static void reset(com.publicstech.documentsystem.activity.doc.DocListActivity target) {
    target.tv_desc_loading = null;
    target.pager = null;
    target.llLoading = null;
    target.tabStrip = null;
    target.progressBar = null;
  }
}
