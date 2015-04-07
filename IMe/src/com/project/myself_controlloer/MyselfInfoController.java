package com.project.myself_controlloer;

import com.base.common.Common;
import com.base.controller.Controller;
import com.base.model.Configuration;

public class MyselfInfoController extends Controller {

	private String GET_MYSELF_DO = "GetUserActivity.do";
	private String ADD_MYSELF_DO = "DoingUpload.do";
	private String ActivityCommentUpload = "ActivityCommentUpload.do";
	private String HeadPhotoUpload = "HeadPhotoUpload.do";
	private String getActivityComment = "GetActivityComment.do";
	private String changeActivityCommentLayer = "ChangeActivityCommentLayer.do";
	private String getAllComment="GetAllComment.do";
	private String changeActivityCommentLayerList="ChangeActivityCommentLayerList.do";


	/** 获取单独的一条动态已经评论 */
	private String GetSingleActivityComment = "GetSingleActivityComment.do";

	/** 删除单独的一条动态已经评论 */
	private String DeleteDongtai = "ActivityDelete.do";

	/** 隔壁 */
	private String GET_NEARBY_DO = "GetNearby.do";
	private String NearUpload = "NearUpload.do";
	private String NearCommentUpload = "NearCommentUpload.do";

	/** 获取申请 */
	private String getOwnApply = "getOwnApply.do";
	private String dealApply = "dealApply.do";

	/** 这里的操作方式是问题的 */
	private String NickNameUpdate = "NickNameUpdate.do";
	private String SignUpdate = "SignUpdate.do";

	public MyselfInfoController(Configuration configuration) {
		super(configuration);
	}

	public void getMyselfInfo() {
		this.startRequest(GET_MYSELF_DO, Common.POST);
	}

	public void addHeadImg() {
		this.startRequest(HeadPhotoUpload, Common.POST);
	}

	public void addMyselfInfo() {
		this.startRequest(ADD_MYSELF_DO, Common.POST);
	}

	public void getNearByInfo() {
		this.startRequest(GET_NEARBY_DO, Common.POST);
	}

	public void getApplyInfo() {
		this.startRequest(getOwnApply, Common.POST);
	}

	public void dealApply() {
		this.startRequest(dealApply, Common.POST);
	}

	public void addNearByInfo() {
		this.startRequest(NearUpload, Common.POST);

	}

	public void addComment() {
		this.startRequest(ActivityCommentUpload, Common.POST);
	}

	public void addNearByComment() {
		this.startRequest(NearCommentUpload, Common.POST);
	}

	public void getCommentByDtid() {
		this.startRequest(GetSingleActivityComment, Common.POST);
	}

	public void updateNickName() {
		this.startRequest(NickNameUpdate, Common.POST);
	}

	public void getIdiograph() {
		this.startRequest(SignUpdate, Common.POST);
	}

	public void deleteDongtai() {
		this.startRequest(DeleteDongtai, Common.POST);
	}

	public void getActivityComment() {

		this.startRequest(getActivityComment, Common.POST);
	}

	public void changeActivityCommentLayer() {

		this.startRequest(changeActivityCommentLayer, Common.POST);
	}
	public void getAllComment() {

		this.startRequest(getAllComment, Common.POST);
	}
	public void changeActivityCommentLayerList() {

		this.startRequest(changeActivityCommentLayerList, Common.POST);
	}
	
	
}
