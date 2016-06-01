package com.example.sms1_0.ui.fragment;

 
 
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brainwave_test.R;
import com.example.sms1_0.base.BaseFragment;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;

public class ConversationFragment extends BaseFragment {

	private BluetoothAdapter bluetoothAdapter;
	private TGDevice tgDevice;
	private Button bt_connect;
	private TextView tv_state;
	private ScrollView sv_state;
	private int subjectContactQuality_last;
	private int subjectContactQuality_cnt;
	private boolean connected = false;
	private boolean started = false;
	public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
		this.bluetoothAdapter = bluetoothAdapter;
	}
	
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conversation, null);
		bt_connect = (Button)view.findViewById(R.id.bt_connect);
		tv_state = (TextView)view.findViewById(R.id.tv_state);
		sv_state = (ScrollView)view.findViewById(R.id.sv_state);
		return view;
	}

	@Override
	public void initData() {
		subjectContactQuality_last = -1; /* start with impossible value */
	    subjectContactQuality_cnt = 200; /* start over the limit, so it gets reported the 1st time */

		//连接蓝牙
		bt_connect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!connected && tgDevice != null && tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
					tgDevice.connect(true);
					bt_connect.setText("Stop");
				} else {
					if (started) {
						tgDevice.stop();
						started = false;
						bt_connect.setText("Start");
					} else {
						tgDevice.start();
						started = true;
						bt_connect.setText("Stop");
					}
				}
			}
		});
		
		//检查蓝牙适配器是否正常
		if( bluetoothAdapter == null ) {            
		    // Alert user that Bluetooth is not available
			Toast.makeText( getActivity(), "Bluetooth not available", Toast.LENGTH_LONG ).show();
			return;
		} else {
			//如果蓝牙适配器正常，那么创建tgDevice对象。	
			tgDevice = new TGDevice(bluetoothAdapter, connectHandler);
		} 
	}
	
	 /**
     * 处理来自TGDevice对象的消息
     */
     final Handler connectHandler = new Handler() {
    	@Override
        public void handleMessage( Message msg ) {
            switch( msg.what ) {
            	case TGDevice.MSG_MODEL_IDENTIFIED:
                    tgDevice.setBlinkDetectionEnabled(true);
                    tgDevice.setTaskDifficultyRunContinuous(true);
                    tgDevice.setTaskDifficultyEnable(true);
                    tgDevice.setTaskFamiliarityRunContinuous(true);
                    tgDevice.setTaskFamiliarityEnable(true);
                    tgDevice.setRespirationRateEnable(true);  
                    tv_state.append("功能初始已经初始化成功\n");
            		break;
            	
            	// 蓝牙连接状态
                case TGDevice.MSG_STATE_CHANGE:
                    switch( msg.arg1 ) {
    	                case TGDevice.STATE_IDLE:
    	                    break;
    	                case TGDevice.STATE_CONNECTING:       	
    	                	tv_state.append( "正在连接...\n" );
    	                	break;	
                        case TGDevice.STATE_CONNECTED:
                            tv_state.append( "已经连接.\n" );
                            connected = true;
                            tgDevice.start();
                            started = true;
                            break;
    	                case TGDevice.STATE_NOT_FOUND:
    	                	tv_state.append( "搜索不到任何蓝牙设备，请重新连接.\n" );
    	                	break;
                        case TGDevice.STATE_ERR_NO_DEVICE:
                            tv_state.append( "蓝牙设备没有配对，请重新连接.\n" );
                            break;
    	                case TGDevice.STATE_ERR_BT_OFF:
    	                    tv_state.append( "蓝牙设备没有打开，请重新连接." );
    	                    break;
    	                case TGDevice.STATE_DISCONNECTED:
    	                	tv_state.append( "断开连接.\n" );
                    } 
                    break;
    
                 // 弱信号
                 // 这是来反应传感器芯片上生物信号强弱的,该值通常由ThinkGear硬件设备每秒输出一次.
                 // 值为0表示生物传感器接受到的信号很好没有任何明显问题。而1~199中得到越高的值表示生物传感器检测到信号问题就越多。
                 // 值200表示传感器可能甚至没有接触到导电体.
                case TGDevice.MSG_POOR_SIGNAL:
                	/* 每三十秒显示一次信号强度，或者喜信号强度改变时，显示信号强度*/
                	if (subjectContactQuality_cnt >= 30 || msg.arg1 != subjectContactQuality_last) {
                		if (msg.arg1 == 0) {
                			tv_state.append( "SignalQuality: is Good: " + msg.arg1 + "\n" );
                		} else {
                			tv_state.append( "SignalQuality: is POOR: " + msg.arg1 + "\n" );
                		}
                		subjectContactQuality_cnt = 0;
                		subjectContactQuality_last = msg.arg1;
                		
                	} else {
                		subjectContactQuality_cnt++;
                	}	
                	g_Noise = msg.arg1;
                    break;
                
                // 测出来的原始raw数据
                case TGDevice.MSG_RAW_DATA:	  
                	break;

                // 注意力信号
                case TGDevice.MSG_ATTENTION:
                    tv_state.append( "Attention: " + msg.arg1 + "\n" );
                	g_Attention = msg.arg1;
                	 if (g_Attention >= 50) {
 						g_Attention_Control += 5;
 					} else {
 						g_Attention_Control -= 5;
 					}
                     if (g_Attention_Control >= 100) {
                    	 g_Attention_Control = 100;
                     }
                     if (g_Attention_Control <= 0) {
                    	 g_Attention_Control = 0;
 					}
                    break;
                
                // 冥想信号
                case TGDevice.MSG_MEDITATION:
                    tv_state.append( "Meditation: " + msg.arg1 + "\n" );
                    g_Meditation = msg.arg1;
                    if (g_Meditation >= 50) {
						g_Meditation_Control += 5;
					} else {
						g_Meditation_Control -= 5;
					}
                    if (g_Meditation_Control >= 100) {
                    	g_Meditation_Control = 100;
                    }
                    if (g_Meditation_Control <= 0) {
						g_Meditation_Control = 0;
					}
                    
                	break;
                	
                // EEG八个信号值
	            case TGDevice.MSG_EEG_POWER:
	            	TGEegPower e = (TGEegPower)msg.obj;
	            //	tv_state.append("delta: " + e.delta + " theta: " + e.theta + " lowAlpha : " + e.lowAlpha + 
	            //			  " highAlpha : " + e.highAlpha + "\nlowBeta : " + e.lowBeta+ " highBeta : " +
	            	//		  e.highBeta + " lowGamma : " + e.lowGamma + " midGamma : " + e.midGamma + "\n");
	            	g_EegPower = (TGEegPower)msg.obj;
	            	if (g_Is_Control_Handler_Ready) {
	            		g_Control_Handler.sendEmptyMessage(1);
					}
	            	if (g_Is_Acquire_Handler_Ready) {
						g_Acquire_Handler.sendEmptyMessage(1);
					}
	            	break;
                
	            // 眨眼信号
                case TGDevice.MSG_BLINK:
                	tv_state.append( "Blink: " + msg.arg1 + "\n" );
                	break;
    
                default:
                	break;               	
        	}
        	sv_state.fullScroll( View.FOCUS_DOWN );
        } 
        
     };
}
