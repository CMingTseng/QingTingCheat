package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.view.groupselect.ChinaUnicomZoneView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class ChinaUnicomZoneController extends ViewController
  implements IEventHandler, INavigationBarListener
{
  private NavigationBarTopView barTopView;
  private ChinaUnicomZoneView mView;

  public ChinaUnicomZoneController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "webView";
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("联通免流量专区"));
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
    this.mView = new ChinaUnicomZoneView(paramContext);
    attachView(this.mView);
  }

  public void back()
  {
    this.mView.goBack();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.ChinaUnicomZoneController
 * JD-Core Version:    0.6.2
 */