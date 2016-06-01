package com.example.sms1_0.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sms1_0.base.BaseFragment;

public class SearchFragment extends BaseFragment {

	public static int XPoint = 10;
	public static int YPoint = 0; 
	public static int XScale = 24; // ¿Ì¶È³¤¶È 
	public static int YScale = 12; 
	public static int XLength = 0; 
	public static int YLength = 0; 
	public static int proportion = 0;
	public static int MaxDataSize = 0; 
	
	private BluetoothAdapter bluetoothAdapter;
	private MyView myView;
 
	EegPowerList eegPowerList = new EegPowerList();
    
	public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
		this.bluetoothAdapter = bluetoothAdapter;
	}
	
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myView = new MyView(getActivity(), null, eegPowerList);
		return myView;
	
	}

	@Override
	public void initData() {	
		XLength = g_Window_Width; 
		YLength = g_Window_Width * 2 / 3; 
		YPoint = XLength - 100;
		proportion = 16777216 * YScale / YLength;
		MaxDataSize = XLength / XScale; 
		
		//Log.e("info", "" + XLength +":" +proportion + ":" + MaxDataSize);
		
		g_Control_Handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				 if(eegPowerList.deltaList.size() >= MaxDataSize) {  
					 eegPowerList.deltaList.remove(0);  
					 eegPowerList.highAlaphList.remove(0);
					 eegPowerList.highBetaList.remove(0);
					 eegPowerList.lowAlaphList.remove(0);
					 eegPowerList.lowBetaList.remove(0);
					 eegPowerList.midGammaList.remove(0);
					 eegPowerList.lowGammaList.remove(0);
					 eegPowerList.thetaList.remove(0);
                 }  

            	 eegPowerList.deltaList.add(g_EegPower.delta / proportion);  
				 eegPowerList.highAlaphList.add(g_EegPower.highAlpha / proportion);
				 eegPowerList.highBetaList.add(g_EegPower.highBeta / proportion);
				 eegPowerList.lowAlaphList.add(g_EegPower.lowAlpha / proportion);
				 eegPowerList.lowBetaList.add(g_EegPower.lowBeta / proportion);
				 eegPowerList.midGammaList.add(g_EegPower.midGamma / proportion);
				 eegPowerList.lowGammaList.add(g_EegPower.lowGamma / proportion);
				 eegPowerList.thetaList.add(g_EegPower.theta / proportion);
                 
                 // Ë¢ÐÂMyView
                 myView.invalidate();
			}
		};
		g_Is_Control_Handler_Ready = true;
	}
	
	
	/**
	 *ÓÃÀ´´æ·ÅEegPower
	 */
	class EegPowerList {
		 List<Integer> deltaList = new ArrayList<Integer>(); 
	     List<Integer> highAlaphList = new ArrayList<Integer>();
	     List<Integer> highBetaList = new ArrayList<Integer>();
	     List<Integer> lowAlaphList = new ArrayList<Integer>();
	     List<Integer> lowBetaList = new ArrayList<Integer>();
	     List<Integer> lowGammaList = new ArrayList<Integer>();
	     List<Integer> midGammaList = new ArrayList<Integer>();
	     List<Integer> thetaList = new ArrayList<Integer>();
	}
	
	
	/**
	 *»æÍ¼µÄview 
	 */
	class MyView extends View { 

	    private EegPowerList eegPowerList;
	    
	    public MyView(Context context, AttributeSet attrs, EegPowerList list) {  
	        super(context, attrs);  
	        eegPowerList = list;
	    }  
	      
	    @Override  
	    protected void onDraw(Canvas canvas) {  
	        super.onDraw(canvas);  
	     //   Log.e("eegpower",  "" + g_EegPower.delta);
	        // 1.»­×ø±ê
	        Paint paint_coord= new Paint();  
	        paint_coord.setStyle(Paint.Style.FILL);  
	        paint_coord.setAntiAlias(true); //È¥¾â³Ý  
	        paint_coord.setColor(Color.BLACK);  
	        paint_coord.setTextSize(40);
	        paint_coord.setStrokeWidth(6);
	        // 2.»­delta
	        Paint paint_delta= new Paint();  
	        paint_delta.setStyle(Paint.Style.FILL);  
	        paint_delta.setAntiAlias(true); //È¥¾â³Ý  
	        paint_delta.setColor(Color.RED);
	        paint_delta.setTextSize(40);
	        paint_delta.setStrokeWidth(4);
	        // 3.»­ highAlpha
	        Paint paint_highAlpha = new Paint();  
	        paint_highAlpha.setStyle(Paint.Style.FILL);  
	        paint_highAlpha.setAntiAlias(true); //È¥¾â³Ý  
	        paint_highAlpha.setColor(0xFF007DFF);
	        paint_highAlpha.setTextSize(40);
	        paint_highAlpha.setStrokeWidth(4);
	        // 4.»­highBeta
	        Paint paint_highBeta = new Paint();  
	        paint_highBeta.setStyle(Paint.Style.FILL);  
	        paint_highBeta.setAntiAlias(true); //È¥¾â³Ý  
	        paint_highBeta.setColor(Color.YELLOW);
	        paint_highBeta.setTextSize(40);
	        paint_highBeta.setStrokeWidth(4);
	        // 5.»­lowAlpha
	        Paint paint_lowAlpha = new Paint();  
	        paint_lowAlpha.setStyle(Paint.Style.FILL);  
	        paint_lowAlpha.setAntiAlias(true); //È¥¾â³Ý  
	        paint_lowAlpha.setColor(Color.GREEN);
	        paint_lowAlpha.setTextSize(40);
	        paint_lowAlpha.setStrokeWidth(4);
	        // 6.»­lowBeta
	        Paint paint_lowBeta = new Paint();  
	        paint_lowBeta.setStyle(Paint.Style.FILL);  
	        paint_lowBeta.setAntiAlias(true); //È¥¾â³Ý  
	        paint_lowBeta.setColor(Color.BLUE);
	        paint_lowBeta.setTextSize(40);
	        paint_lowBeta.setStrokeWidth(4);
	        // 7.»­lowGamma
	        Paint paint_lowGamma = new Paint();  
	        paint_lowGamma.setStyle(Paint.Style.FILL);  
	        paint_lowGamma.setAntiAlias(true); //È¥¾â³Ý  
	        paint_lowGamma.setColor(0xFFFF00AC);
	        paint_lowGamma.setTextSize(40);
	        paint_lowGamma.setStrokeWidth(4);
	        // 8.»­midGamma
	        Paint paint_midGamma = new Paint();  
	        paint_midGamma.setStyle(Paint.Style.FILL);  
	        paint_midGamma.setAntiAlias(true); //È¥¾â³Ý  
	        paint_midGamma.setColor(0xFFAB49EF);
	        paint_midGamma.setTextSize(40);
	        paint_midGamma.setStrokeWidth(4);
	        // 9.»­theta
	        Paint paint_theta = new Paint();  
	        paint_theta.setStyle(Paint.Style.FILL);  
	        paint_theta.setAntiAlias(true); //È¥¾â³Ý  
	        paint_theta.setColor(0xFF7D00FF);
	        paint_theta.setTextSize(40);
	        paint_theta.setStrokeWidth(4);
	        
	        //»­YÖá  
	        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint_coord);  
	        Log.e("XY", "" +XPoint+":" +(YPoint - YLength)+":"+ XPoint+":" +YPoint);
	        //YÖá¼ýÍ·  
	        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 10, YPoint-YLength + 20, paint_coord);  //¼ýÍ·  
	        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 10, YPoint-YLength + 20 ,paint_coord);  
	          
	        //»­XÖá  
	        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint_coord);  
	        
	        //»­±êÌâ¼°±êÇ©
	        paint_coord.setStrokeWidth(2);
	        canvas.drawText("EEGPOWER", g_Window_Width/3, 110, paint_coord);
	        canvas.drawText("ÐÅºÅÑÕÉ«£º", 10, YPoint + (g_Window_Height-YPoint)/8, paint_coord);
	        paint_highAlpha.setStrokeWidth(2);
	        canvas.drawText("highAlpha", 10, YPoint + (g_Window_Height-YPoint)*2/8, paint_highAlpha);
	        paint_highBeta.setStrokeWidth(2);
	        canvas.drawText("highBeta", 10, YPoint + (g_Window_Height-YPoint)*3/8, paint_highBeta);
	        paint_lowBeta.setStrokeWidth(2);
	        canvas.drawText("lowBeta", 10, YPoint + (g_Window_Height-YPoint)*4/8, paint_lowBeta);
	        paint_lowAlpha.setStrokeWidth(2);
	        canvas.drawText("lowAlapha", 10, YPoint + (g_Window_Height-YPoint)*5/8, paint_lowAlpha);
	        
	        paint_lowGamma.setStrokeWidth(2);
	        canvas.drawText("lowGamma", 10 + g_Window_Width/2, YPoint + (g_Window_Height-YPoint)*2/8, paint_lowGamma);
	        paint_midGamma.setStrokeWidth(2);
	        canvas.drawText("highBeta", 10 + g_Window_Width/2, YPoint + (g_Window_Height-YPoint)*3/8, paint_midGamma);
	        paint_delta.setStrokeWidth(2);
	        canvas.drawText("highBeta", 10+ g_Window_Width/2, YPoint + (g_Window_Height-YPoint)*4/8, paint_delta);
	        paint_theta.setStrokeWidth(2);
	        canvas.drawText("highBeta", 10+ g_Window_Width/2, YPoint + (g_Window_Height-YPoint)*5/8, paint_theta);
	        
	        
	        if(eegPowerList.deltaList.size() > 1){  
	            for(int i=1; i<eegPowerList.deltaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.deltaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.deltaList.get(i) * YScale, paint_delta);  
	                Log.e("eeglist", ""+eegPowerList.deltaList.get(i));
	            }  
	        }  
	        if(eegPowerList.highAlaphList.size() > 1){  
	            for(int i=1; i<eegPowerList.highAlaphList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.highAlaphList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.highAlaphList.get(i) * YScale, paint_highAlpha);  
	            }  
	        } 
	        if(eegPowerList.highBetaList.size() > 1){  
	            for(int i=1; i<eegPowerList.highBetaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.highBetaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.highBetaList.get(i) * YScale, paint_highBeta);  
	            }  
	        } 
	        if(eegPowerList.lowAlaphList.size() > 1){  
	            for(int i=1; i<eegPowerList.lowAlaphList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.lowAlaphList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.lowAlaphList.get(i) * YScale, paint_lowAlpha);  
	            }  
	        } 
	        if(eegPowerList.lowBetaList.size() > 1){  
	            for(int i=1; i<eegPowerList.lowBetaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.lowBetaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.lowBetaList.get(i) * YScale, paint_lowBeta);  
	            }  
	        } 
	        if(eegPowerList.lowGammaList.size() > 1){  
	            for(int i=1; i<eegPowerList.lowGammaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.lowGammaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.lowGammaList.get(i) * YScale, paint_delta);  
	            }  
	        } 
	        if(eegPowerList.midGammaList.size() > 1){  
	            for(int i=1; i<eegPowerList.midGammaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.midGammaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.midGammaList.get(i) * YScale, paint_midGamma);  
	            }  
	        } 
	        if(eegPowerList.thetaList.size() > 1){  
	            for(int i=1; i<eegPowerList.thetaList.size(); i++){  
	                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - eegPowerList.thetaList.get(i-1) * YScale,   
	                        XPoint + i * XScale, YPoint - eegPowerList.thetaList.get(i) * YScale, paint_theta);  
	            }  
	        } 
	    }  
	}  
}
