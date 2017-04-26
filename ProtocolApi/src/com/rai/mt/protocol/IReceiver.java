package com.rai.mt.protocol;

public interface IReceiver {
	
	void onDataReceived (String data);
	
	void onError (String errorDetails);

}
