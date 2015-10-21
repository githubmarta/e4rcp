 
package com.celad.rental.ui.views;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;

public class RentalTreePart {
	@Inject
	public RentalTreePart() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent, RentalAgency agency) {
		TreeViewer tv = new TreeViewer(parent);
		RentalTreeContentProvider provider = new RentalTreeContentProvider();
		tv.setContentProvider(provider);
		tv.setLabelProvider(provider);
		Collection<RentalAgency> agencies = new ArrayList<RentalAgency>();
		agencies.add(agency);
		tv.setInput(agencies);
	}
	
	
	
	
}

