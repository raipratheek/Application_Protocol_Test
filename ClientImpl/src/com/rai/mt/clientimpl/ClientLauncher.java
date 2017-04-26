package com.rai.mt.clientimpl;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.rai.mt.protocol.IAppProtocolClient;

@Component
public class ClientLauncher {
	
	private IAppProtocolClient protocol;
	
	@Reference
	public void setApplicationClientProtocol(IAppProtocolClient prt) {
		protocol = prt;
	}
	
	public void unsetApplicationClientProtocol(IAppProtocolClient prt) {
		protocol = null;
	}
	
	@Activate
	public void activate() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("CLIENT");
		ClientImpl client = new ClientImpl(shell, protocol);
		client.createView();

		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
