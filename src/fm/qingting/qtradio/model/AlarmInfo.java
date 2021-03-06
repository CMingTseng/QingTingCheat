package fm.qingting.qtradio.model;

import java.util.Calendar;

public class AlarmInfo
{
  private int MAX_DAY_OF_WEEK = 7;
  public long alarmTime;
  public int alarmType = 0;
  public int categoryId;
  public int channelId;
  public String channelName;
  public int dayOfWeek = 0;
  public boolean hasShouted = false;
  public boolean isAvailable = true;
  public int programId = 0;
  public boolean repeat = true;
  public String ringToneId = "0";

  private int getDayOfWeek()
  {
    return Calendar.getInstance().get(7);
  }

  private int getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return localCalendar.get(13) + (60 * (i * 60) + j * 60);
  }

  private boolean hitDay(int paramInt)
  {
    return ((paramInt & this.dayOfWeek) != 0) || ((this.dayOfWeek == 0) && (isWorkDay(paramInt)));
  }

  private boolean isWorkDay(int paramInt)
  {
    return ((paramInt & 0x80) == 0) && ((paramInt & 0x2) == 0);
  }

  public long getNextShoutTime()
  {
    if ((this.hasShouted) || (!this.isAvailable))
      return 9223372036854775807L;
    int i = (int)Math.pow(2.0D, getDayOfWeek());
    long l = getRelativeTime(System.currentTimeMillis());
    if ((hitDay(i)) && (l < this.alarmTime))
      return this.alarmTime - l;
    int j = i;
    for (int k = 1; k < 7; k++)
    {
      if (j == 128)
        j = 2;
      while (hitDay(j))
      {
        return 3600 * (k * 24) + this.alarmTime - l;
        j *= 2;
      }
    }
    return 9223372036854775807L;
  }

  public void update(AlarmInfo paramAlarmInfo)
  {
    if (paramAlarmInfo == null)
      return;
    this.channelName = paramAlarmInfo.channelName;
    this.channelId = paramAlarmInfo.channelId;
    this.categoryId = paramAlarmInfo.categoryId;
    this.alarmTime = paramAlarmInfo.alarmTime;
    this.repeat = paramAlarmInfo.repeat;
    this.dayOfWeek = paramAlarmInfo.dayOfWeek;
    this.isAvailable = paramAlarmInfo.isAvailable;
    this.hasShouted = paramAlarmInfo.hasShouted;
    this.alarmType = paramAlarmInfo.alarmType;
    this.ringToneId = paramAlarmInfo.ringToneId;
    this.programId = paramAlarmInfo.programId;
  }

  public void update(AlarmInfoLegacy paramAlarmInfoLegacy)
  {
    if (paramAlarmInfoLegacy == null)
      return;
    this.channelName = paramAlarmInfoLegacy.channelName;
    this.channelId = paramAlarmInfoLegacy.channelId;
    this.categoryId = paramAlarmInfoLegacy.categoryId;
    this.alarmTime = paramAlarmInfoLegacy.alarmTime;
    this.repeat = paramAlarmInfoLegacy.repeat;
    this.dayOfWeek = paramAlarmInfoLegacy.dayOfWeek;
    this.isAvailable = paramAlarmInfoLegacy.isAvailable;
    this.hasShouted = paramAlarmInfoLegacy.hasShouted;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AlarmInfo
 * JD-Core Version:    0.6.2
 */