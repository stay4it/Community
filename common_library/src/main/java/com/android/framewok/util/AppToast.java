package com.android.framewok.util;

import android.content.Context;
import android.widget.Toast;

public class AppToast {

	protected static final String TAG = "AppToast";
	public static Toast toast;

	public static void showShortText(Context context, int resId) {
		if(context==null)return;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.show();
	}
	public static void showShortText(Context context, CharSequence text) {
		if(context==null)return;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}




}
