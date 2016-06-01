package com.example.sms1_0.ui.fragment;

 
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basv.gifmoviewview.widget.GifMovieView;
import com.example.brainwave_test.R;
import com.example.sms1_0.base.BaseFragment;
import com.example.sms1_0.utils.OtherUtils;

public class GroupFragment extends BaseFragment {

	private BluetoothAdapter bluetoothAdapter;
 
	private TextView tv_control_signal;
	private ProgressBar pb_attention;
	private ProgressBar pb_meditation;
	private GifMovieView gif_bulb; 
	
	static final int WHAT_BULB_SILENT = 1;
	static final int WHAT_BULB_BLINK = 2;
	static final int WHAT_BULB_BOMB = 3;
	
	private boolean bulb_blink = false;
	private boolean bulb_silent = false;
	private int bulb_bomb = 0;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_BULB_BLINK:
				gif_bulb.setMovieResource(R.drawable.bulb_blink);
				gif_bulb.setPaused(false);
				break;
			case WHAT_BULB_BOMB:
				gif_bulb.setMovieResource(R.drawable.bulb_bomb);
				gif_bulb.setPaused(false);
				break;
			case WHAT_BULB_SILENT:
				gif_bulb.setMovieResource(R.drawable.bulb_blink);
				gif_bulb.setPaused(true);
				Log.e("test", "bulb_silent");
				break;
			default:
				gif_bulb.setMovieResource(R.drawable.bulb_silent);
				break;
			}
		};
	};
	
	public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
		this.bluetoothAdapter = bluetoothAdapter;
	}
	
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_group, null);
		View view = inflater.inflate(R.layout.fragment_search, null);
		tv_control_signal = (TextView)view.findViewById(R.id.tv_control_signal);
		pb_attention = (ProgressBar)view.findViewById(R.id.pb_attention);
		pb_meditation = (ProgressBar)view.findViewById(R.id.pb_meditation);
		gif_bulb = (GifMovieView)view.findViewById(R.id.gif_bulb);
 
		return view;
	}

	@Override
	public void initData() {
		handler.sendEmptyMessage(WHAT_BULB_SILENT);
		initLister();
		g_Acquire_Handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
		
					tv_control_signal.setText("ÐÅºÅÇ¿¶È£º" + (100 - g_Noise) + "%");
					pb_attention.setProgress(g_Attention_Control);
					pb_meditation.setProgress(g_Meditation_Control);
					
					if (g_Meditation_Control<= 50 && !bulb_silent && bulb_bomb == 0) {
						handler.sendEmptyMessage(WHAT_BULB_SILENT);
						bulb_silent = true;
						bulb_blink = false;
					} 
					if (g_Meditation_Control > 50 && g_Meditation_Control < 80 && bulb_bomb == 0 && !bulb_blink) {
						handler.sendEmptyMessage(WHAT_BULB_BLINK);
						bulb_blink = true;
						bulb_silent = false;
						
					} 
					if (g_Meditation_Control >= 80 && bulb_bomb == 0) {
						handler.sendEmptyMessage(WHAT_BULB_BOMB);
						bulb_bomb++;
					}
					if (bulb_bomb != 0) {
						bulb_bomb++;
						if (bulb_bomb == 5) {
							handler.sendEmptyMessage(WHAT_BULB_SILENT);
						}
						if (bulb_bomb == 8) {
							bulb_bomb = 0;
							g_Meditation_Control = 20;
							bulb_blink = false;
							bulb_silent = false;
						}
					}
					
//					pb_attention.setProgress(g_Attention);
//					pb_meditation.setProgress(g_Meditation);
//					
//					if (g_Meditation<= 50 && !bulb_silent && bulb_bomb == 0) {
//						handler.sendEmptyMessage(WHAT_BULB_SILENT);
//						bulb_silent = true;
//						bulb_blink = false;
//					} 
//					if (g_Meditation > 50 && g_Meditation < 80 && bulb_bomb == 0 && !bulb_blink) {
//						handler.sendEmptyMessage(WHAT_BULB_BLINK);
//						bulb_blink = true;
//						bulb_silent = false;
//						
//					} 
//					if (g_Meditation >= 80 && bulb_bomb == 0) {
//						handler.sendEmptyMessage(WHAT_BULB_BOMB);
//						bulb_bomb++;
//					}
//					if (bulb_bomb != 0) {
//						bulb_bomb++;
//						if (bulb_bomb == 5) {
//							handler.sendEmptyMessage(WHAT_BULB_SILENT);
//						}
//						if (bulb_bomb == 8) {
//							bulb_bomb = 0;
//							g_Meditation = 20;
//							bulb_blink = false;
//							bulb_silent = false;
//						}
//					}
				}
		};
		g_Is_Acquire_Handler_Ready = true;
	}
	
	private void initLister() {
		((Button)getView().findViewById(R.id.bt1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (g_Is_Acquire_Handler_Ready) {
					g_Meditation_Control = 20;
	        		g_Acquire_Handler.sendEmptyMessage(1);
				}
			}
		});
		((Button)getView().findViewById(R.id.bt2)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (g_Is_Acquire_Handler_Ready) {
						g_Meditation_Control = 60;
						g_Acquire_Handler.sendEmptyMessage(1);
					}
				}
			});	
		((Button)getView().findViewById(R.id.bt3)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (g_Is_Acquire_Handler_Ready) {
						g_Meditation_Control = 90;
						g_Acquire_Handler.sendEmptyMessage(1);
					}
				}
			});
	}
}
