package org.java_websocket.osgi;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.rai.mt.protocol.IReceiver;

public class ClientWebsocketImpl extends WebSocketClient {

	IReceiver receiver;

	public ClientWebsocketImpl(URI serverURI) {
		super(serverURI);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println(" connection opened.");
		receiver.onDataReceived(" connection opened.");

	}

	@Override
	public void onMessage(String message) {
		receiver.onDataReceived(message);

	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		receiver.onError(ex.getMessage());

	}

	public void registerReceiver(IReceiver receiver) {
		this.receiver = receiver;
	}

}
