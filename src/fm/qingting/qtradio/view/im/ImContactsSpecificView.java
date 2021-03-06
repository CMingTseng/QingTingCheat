package fm.qingting.qtradio.view.im;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.im.sortpinyin.CharacterParser;
import fm.qingting.qtradio.view.im.sortpinyin.QuickQueryView;
import fm.qingting.qtradio.view.im.sortpinyin.QuickQueryView.OnTouchingLetterChangeListener;
import fm.qingting.qtradio.view.im.sortpinyin.SortModel;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipsView;
import fm.qingting.qtradio.view.pinnedsection.CustomSectionView;
import fm.qingting.qtradio.view.pinnedsection.IPinnedAdapterIViewFactory;
import fm.qingting.qtradio.view.pinnedsection.PinnedSectionListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ImContactsSpecificView extends ViewGroupViewImpl
{
  private final ViewLayout dialogLayout = this.standardLayout.createChildLT(100, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private SpecialPinnedSectionAdapter mAdapter;
  private TextView mDialogView;
  private EmptyTipsView mEmptyView;
  private IPinnedAdapterIViewFactory mFactory;
  private PinnedSectionListView mListView;
  private QuickQueryView mQuickQueryView;
  private final ViewLayout sideLayout = this.standardLayout.createChildLT(50, 1200, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public ImContactsSpecificView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mFactory = new IPinnedAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return null;
        case 1:
          return new CustomSectionView(ImContactsSpecificView.this.getContext());
        case 0:
          return new ContactsItemView(ImContactsSpecificView.this.getContext(), this.val$hash);
        case 2:
        }
        return new SpecialContactsItemView(ImContactsSpecificView.this.getContext(), this.val$hash);
      }
    };
    this.mAdapter = new SpecialPinnedSectionAdapter(new ArrayList(), this.mFactory);
    this.mEmptyView = new EmptyTipsView(paramContext, 5);
    addView(this.mEmptyView);
    this.mListView = new PinnedSectionListView(paramContext);
    this.mListView.setEmptyView(this.mEmptyView);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setSelector(17170445);
    this.mListView.setShadowVisible(false);
    this.mListView.setDivider(null);
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mListView);
    this.mQuickQueryView = new QuickQueryView(paramContext);
    addView(this.mQuickQueryView);
    this.mDialogView = new TextView(paramContext);
    this.mDialogView.setBackgroundColor(SkinManager.getItemHighlightMaskColor());
    this.mDialogView.setTextColor(SkinManager.getTextColorHighlight());
    this.mDialogView.setGravity(17);
    addView(this.mDialogView);
    this.mDialogView.setVisibility(4);
    this.mQuickQueryView.setTextView(this.mDialogView);
    this.mQuickQueryView.setChangeListener(new QuickQueryView.OnTouchingLetterChangeListener()
    {
      public void onLetterChanged(String paramAnonymousString)
      {
        int i = ImContactsSpecificView.this.findIndex(paramAnonymousString);
        if (i < 0)
          return;
        ImContactsSpecificView.this.mListView.setSelection(i);
      }
    });
  }

  private int findIndex(String paramString)
  {
    List localList = this.mAdapter.getData();
    if ((localList != null) && (localList.size() > 0))
    {
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        SpecialPinnedItem localSpecialPinnedItem = (SpecialPinnedItem)localList.get(j);
        if ((localSpecialPinnedItem.type == 1) && (paramString.equalsIgnoreCase((String)localSpecialPinnedItem.data)))
          return j;
      }
    }
    return -1;
  }

  private void setData(List<UserInfo> paramList)
  {
    CharacterParser localCharacterParser = CharacterParser.getInstance();
    ArrayList localArrayList1 = new ArrayList();
    int i = paramList.size();
    ArrayList localArrayList2 = new ArrayList();
    int j = 0;
    if (j < i)
    {
      UserInfo localUserInfo = (UserInfo)paramList.get(j);
      String str2 = localCharacterParser.getSelling(localUserInfo.snsInfo.sns_name).toUpperCase(Locale.US);
      if (str2.substring(0, 1).matches("[A-Z]"))
        localArrayList2.add(new SortModel(localUserInfo, str2));
      while (true)
      {
        j++;
        break;
        localArrayList2.add(new SortModel(localUserInfo, "#"));
      }
    }
    Collections.sort(localArrayList2, new Comparator()
    {
      public int compare(SortModel paramAnonymousSortModel1, SortModel paramAnonymousSortModel2)
      {
        if (paramAnonymousSortModel2.sortLetters.equals("#"))
          return -1;
        if (paramAnonymousSortModel1.sortLetters.equals("#"))
          return 1;
        return paramAnonymousSortModel1.sortLetters.compareTo(paramAnonymousSortModel2.sortLetters);
      }
    });
    String str1 = "?";
    for (int k = 0; k < localArrayList2.size(); k++)
    {
      SortModel localSortModel = (SortModel)localArrayList2.get(k);
      if (!localSortModel.sortLetters.startsWith(str1))
      {
        str1 = localSortModel.sortLetters.substring(0, 1);
        localArrayList1.add(new SpecialPinnedItem(1, str1));
      }
      localArrayList1.add(new SpecialPinnedItem(0, localSortModel));
    }
    this.mAdapter.setData(localArrayList1);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    this.mEmptyView.close(paramBoolean);
    super.close(paramBoolean);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.layoutView(this.mListView);
    this.standardLayout.layoutView(this.mEmptyView);
    this.mQuickQueryView.layout(this.standardLayout.width - this.sideLayout.width, 0, this.standardLayout.width, this.standardLayout.height);
    this.mDialogView.layout((this.standardLayout.width - this.dialogLayout.width) / 2, (this.standardLayout.height - this.dialogLayout.height) / 2, (this.standardLayout.width + this.dialogLayout.width) / 2, (this.standardLayout.height + this.dialogLayout.height) / 2);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.sideLayout.scaleToBounds(this.standardLayout);
    this.dialogLayout.scaleToBounds(this.standardLayout);
    this.standardLayout.measureView(this.mListView);
    this.standardLayout.measureView(this.mEmptyView);
    this.mQuickQueryView.measure(this.sideLayout.getWidthMeasureSpec(), this.standardLayout.getHeightMeasureSpec());
    this.dialogLayout.measureView(this.mDialogView);
    this.mDialogView.setTextSize(0, 0.5F * this.dialogLayout.height);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      setData((List)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.ImContactsSpecificView
 * JD-Core Version:    0.6.2
 */