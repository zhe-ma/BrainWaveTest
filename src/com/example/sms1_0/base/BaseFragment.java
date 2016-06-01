package com.example.sms1_0.base;

import com.neurosky.thinkgear.TGEegPower;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public static TGEegPower g_EegPower = new TGEegPower();
	public static int g_Attention = 0;
	public static int g_Noise = 0;
	public static int g_Meditation = 0;
	public static int g_Meditation_Control = 0;
	public static int g_Attention_Control = 0;
	public static Handler g_Control_Handler = null;
	public static Handler g_Acquire_Handler = null;
	public static boolean g_Is_Control_Handler_Ready = false;
	public static boolean g_Is_Acquire_Handler_Ready = false;
	public static int g_Window_Width = 1;
	public static int g_Window_Height = 0;
	public static int g_count = 0; 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		DisplayMetrics  dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);   
		g_Window_Height = dm.heightPixels;
		g_Window_Width = dm.widthPixels;
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		return initView(inflater, container, savedInstanceState);
	}
	
	public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	public abstract void initData();

}



