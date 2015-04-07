package com.project.iwant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.project.login_view.LoginActivity;
import com.project.login_view.RegisterActivity;

/**
 * ��ӭ����ÿҳ����
 * 
 * @author Reek_Lee
 * @time 2014-4-17 ����10:29:48
 * @email��1522651962@qq.com
 */
@SuppressLint("ValidFragment")
public class WelcomePagerFragment extends Fragment implements 
		OnClickListener {

	private Button toLoginButton;
	private Button toRegistButton;
	private int pagerPosition;
	private LinearLayout linearLayout;

	public WelcomePagerFragment(int position) {

		pagerPosition = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_welcompager, null);
		initView(view);
		setListener();
		return view;
	}

	public void initView(View view) {
		linearLayout = (LinearLayout) view.findViewById(R.id.changeWel);
		switch (pagerPosition) {
		case 0:
			linearLayout.setBackgroundResource(R.drawable.new_welcome1);
			break;
		case 1:
			linearLayout.setBackgroundResource(R.drawable.new_welcome2);
			break;
		case 2:
			linearLayout.setBackgroundResource(R.drawable.new_welcome3);
			break;
		default:
			break;
		}
	}

	public void setListener() {
	}


	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		default:
			break;
		}
	}

	public void IntentActivity(Class Activity) {
		Intent intent = new Intent(getActivity(), Activity);
		startActivity(intent);
		getActivity().finish();

	}


}
