package com.rai.mt.clientimpl;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rai.mt.protocol.IAppProtocolClient;
import com.rai.mt.protocol.IReceiver;

public class ClientImpl {

	private Shell shell;

	private Text responseBox;

	private Text requestBox;

	private Combo urlBox;

	private Button sendButton;

	private Button stopButton;

	private Button connect;

	private Combo timeCombo;

	private IAppProtocolClient protocol;

	private IReceiver receiver;

	public ClientImpl(Shell shell, IAppProtocolClient protocol) {
		this.shell = shell;
		this.protocol = protocol;
	}

	public void createView() {

		Composite mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setLayout(new FormLayout());

		urlBox = new Combo(mainComposite, SWT.BORDER | SWT.SCROLL_LINE);
		urlBox.setLayoutData(getFormdata(2, 2, 48, 7));
		urlBox.add("wss://localhost:8025");
		urlBox.add("ws://localhost:8025");
		urlBox.add("ssl://localhost:443");
		urlBox.add("tcp://localhost:8025");
		urlBox.add("coaps://localhost/coap");
		urlBox.add("coap://localhost/coap");
		urlBox.select(0);

		responseBox = new Text(mainComposite, SWT.BORDER | SWT.V_SCROLL);
		responseBox.setLayoutData(getFormdata(2, 15, 48, 70));

		requestBox = new Text(mainComposite, SWT.BORDER | SWT.V_SCROLL);
		requestBox.setLayoutData(getFormdata(52, 15, 98, 70));

		sendButton = new Button(mainComposite, SWT.NONE);
		sendButton.setText("SEND");
		sendButton.setLayoutData(getFormdata(17, 72, 27, 77));

		stopButton = new Button(mainComposite, SWT.NONE);
		stopButton.setText("STOP");
		stopButton.setLayoutData(getFormdata(29, 72, 39, 77));

		connect = new Button(mainComposite, SWT.NONE);
		connect.setText("CONNECT");
		connect.setLayoutData(getFormdata(52, 2, 75, 7));

		connect.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					if (receiver == null) {
						receiver = new ClientReceiver();
					}
					protocol.connect(new URI(urlBox.getText()), receiver);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		timeCombo = new Combo(mainComposite, SWT.BORDER);
		timeCombo.setItems("0ms", "1ms", "2ms", "3ms", "5ms", "10ms", " 50ms", "100ms");
		timeCombo.setLayoutData(getFormdata(5, 72, 15, 77));

		sendButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				protocol.send(" Hello From client !");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Returns data for layout.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	private FormData getFormdata(int left, int top, int right, int bottom) {
		FormData fd = new FormData();
		fd.left = new FormAttachment(left, 0);
		fd.top = new FormAttachment(top, 0);
		fd.right = new FormAttachment(right, 0);
		fd.bottom = new FormAttachment(bottom, 0);
		return fd;
	}

	class ClientReceiver implements IReceiver {

		@Override
		public void onDataReceived(final String data) {
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					responseBox.append(data + "\n");

				}
			});

		}

		@Override
		public void onError(String errorDetails) {
			// TODO Auto-generated method stub

		}

	}

}
