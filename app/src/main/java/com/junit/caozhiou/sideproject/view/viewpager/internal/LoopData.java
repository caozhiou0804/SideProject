package com.junit.caozhiou.sideproject.view.viewpager.internal;

import java.util.List;

/**
 * 版权所有：XXX有限公司
 *
 * LoopData
 *
 * @author zhou.wenkai ,Created on 2015-1-14 19:30:18
 * Major Function：<b>自定义控件可以自动跳动的ViewPager数据实体</b>
 *
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class LoopData {

    /** 每个条目数据 */
    private List<ItemData> items;

   
	public List<ItemData> getItems() {
		return items;
	}


	public void setItems(List<ItemData> items) {
		this.items = items;
	}



}