package com.rai.mt.mqtt.server;

import org.osgi.service.component.annotations.Component;

import com.rai.mt.protocol.IApprotocolServer;
import com.rai.mt.protocol.IReceiver;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.netty.buffer.ByteBuf;

@Component
public class MQTTServerService implements IApprotocolServer {

	private MQTTEmbeddedBroker mqttServer;

	private PublisherListener pubListener;

	private IReceiver receiver;

	private boolean isSecureServer = true;

	class PublisherListener extends AbstractInterceptHandler {

		@Override
		public String getID() {
			return "EmbeddedLauncherPublishListener";
		}

		@Override
		public void onPublish(InterceptPublishMessage msg) {

			ByteBuf byteBuffer = msg.getPayload();
			byte[] byteArray = new byte[byteBuffer.readableBytes()];
			byteBuffer.readBytes(byteArray);
			String request = new String(byteArray);

			receiver.onDataReceived(request);
			// System.err.println(" get messge from client is = " + request);
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// while (true) {
			// mqttServer.sendResponse(" " + System.currentTimeMillis());
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			// }
			// }).start();

			// System.out.println("Received on topic: " + msg.getTopicName() + "
			// content: " + new String(msg.getPayload().array()));
		}
	}

	@Override
	public void send(String response) {
		mqttServer.sendResponse(response);

	}

	@Override
	public void init(String address, int port, boolean secure) throws Exception {
		isSecureServer = secure;
		if (mqttServer == null) {
			mqttServer = new MQTTEmbeddedBroker();
			if ("localhost".equals(address)) {
				mqttServer.initServer("127.0.0.1", port, secure);
			} else {
				mqttServer.initServer(address, port, secure);
			}
		} else {
			System.out.println(" The MQTT server is already initialized. ");
		}

	}

	@Override
	public void registerReceiver(IReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void startServer() {
		
			pubListener = new PublisherListener();
			mqttServer.startMqttServer(pubListener, isSecureServer);
//		} else {
//			System.out.println(" The MQTT server is already running. ");
//		}
	}

}
