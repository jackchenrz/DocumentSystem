// Generated code from Butter Knife. Do not modify!
package com.publicstech.documentsystem.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DocListFragment$$ViewInjector {
  public static void inject(Finder finder, final com.publicstech.documentsystem.fragment.DocListFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361804, "field 'lvAutodoclist'");
    target.lvAutodoclist = (com.publicstech.documentsystem.view.AutoListView) view;
  }

  public static void reset(com.publicstech.documentsystem.fragment.DocListFragment target) {
    target.lvAutodoclist = null;
  }
}
