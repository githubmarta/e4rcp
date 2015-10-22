 
package com.celad.rental.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AfficherMessageHandler {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Shell shell) {
		MessageDialog.openInformation(shell,  "Titre", "Message");
		
	}
		
}