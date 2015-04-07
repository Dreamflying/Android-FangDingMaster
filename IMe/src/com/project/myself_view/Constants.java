/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.project.myself_view;

import java.util.List;

import com.project.iwant.R;
import com.project.iwant_or_ihave_model.GetPostImageBean;
import com.project.myself_model.MyselfInfoImage;
import com.project.myself_model.NearByInfoImage;
/**
 *  @description 静态类
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-3下午7:30:15
 */
public final class Constants {
	
	public	static final int[] face1 = { R.drawable.e_01, R.drawable.e_02, R.drawable.e_03,
		R.drawable.e_04, R.drawable.e_05, R.drawable.e_06, R.drawable.e_07,
		R.drawable.e_08, R.drawable.e_09, R.drawable.e_10, R.drawable.e_11,
		R.drawable.e_12, R.drawable.e_13, R.drawable.e_14, R.drawable.e_15,
		R.drawable.e_16, R.drawable.e_17,R.drawable.e_18,R.drawable.e_19,R.drawable.e_20,R.drawable.e_21 ,R.drawable.e_22};
	public static final int[] face2 = { R.drawable.e_23, R.drawable.e_24,
		R.drawable.e_25, R.drawable.e_26, R.drawable.e_27, R.drawable.e_28,
		R.drawable.e_29, R.drawable.e_30, R.drawable.e_31, R.drawable.e_32,
		R.drawable.e_33, R.drawable.e_34, R.drawable.e_35, R.drawable.e_36,
		R.drawable.e_37, R.drawable.e_38,R.drawable.e_39,R.drawable.e_40,R.drawable.e_41,R.drawable.e_42 , R.drawable.e_43};
	public static final int[] face3 = { R.drawable.e_44, R.drawable.e_45,
		R.drawable.e_46, R.drawable.e_47, R.drawable.e_48, R.drawable.e_49,
		R.drawable.e_50, R.drawable.e_51, R.drawable.e_52, R.drawable.e_53,
		R.drawable.e_54, R.drawable.e_55, R.drawable.e_56, R.drawable.e_57,
		R.drawable.e_58, R.drawable.e_59,R.drawable.e_60,R.drawable.e_61,R.drawable.e_62,R.drawable.e_63 ,R.drawable.e_64};
	
	
	
	public static final int[] NearByCommentImage = { R.drawable.gou1, R.drawable.gou2,
		R.drawable.gou3, R.drawable.gou4, R.drawable.gou5, R.drawable.gou6, R.drawable.gou2,
		R.drawable.gou3, R.drawable.gou4, R.drawable.gou5, R.drawable.gou6,R.drawable.gou2,
		R.drawable.gou3, R.drawable.gou4, R.drawable.gou5, R.drawable.gou6
		};
	
	

	public static  String[] IMAGES = new String[] {
			// Heavy images
			"http://bcs.duapp.com/iwantuplad/test?sign=MBO:22r3LWoc89CteM7YcjPo89jn:RGLTMoC%2B6C5ea/XeJSs9357ngXs%3D&response-cache-control=private",
			"http://img3.zhubajie.com/task/2010-05/08/293963/0lvojo1l.jpg",
			"http://a2.att.hudong.com/05/04/14300001216113132869041595263.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/562c11dfa9ec8a13e028c4c0f603918fa0ecc0e4.jpg",
			"http://img3.zhubajie.com/task/2010-05/08/293963/0lvojo1l.jpg",
			"http://a2.att.hudong.com/05/04/14300001216113132869041595263.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/562c11dfa9ec8a13e028c4c0f603918fa0ecc0e4.jpg",
			"http://img3.zhubajie.com/task/2010-05/08/293963/0lvojo1l.jpg",
			"http://a2.att.hudong.com/05/04/14300001216113132869041595263.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/562c11dfa9ec8a13e028c4c0f603918fa0ecc0e4.jpg",
			"http://img3.zhubajie.com/task/2010-05/08/293963/0lvojo1l.jpg",
	};
	public static  String[] IMAGES_LIST;
	public static String[] Images(List<GetPostImageBean> imageBeans ,List<MyselfInfoImage> list ,List<NearByInfoImage> nearByInfoImages,int flag){
		
		if (flag==0) {
			IMAGES_LIST=new String[list.size()];
			 for (int i = 0; i < list.size(); i++) {
				 IMAGES_LIST[i]=list.get(i).getHeadurl();
				}
		}
        if (flag==1) 
		{
			IMAGES_LIST=new String[nearByInfoImages.size()];
			 for (int i = 0; i < nearByInfoImages.size(); i++) {
				 IMAGES_LIST[i]=nearByInfoImages.get(i).getImg_url();
				}
		}
		
		if (flag==2) {
			IMAGES_LIST=new String[1];
		}
	
			
		if (flag==3) {
			IMAGES_LIST=new String[imageBeans.size()];
			 for (int i = 0; i < imageBeans.size(); i++) {
				 IMAGES_LIST[i]=imageBeans.get(i).getImg_url();
				}
		}
			
			
		return IMAGES_LIST;	
		
	}

	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
	
	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.nostra13.example.universalimageloader.FRAGMENT_INDEX";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}
}
