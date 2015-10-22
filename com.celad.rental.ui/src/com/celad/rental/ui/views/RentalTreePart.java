 
package com.celad.rental.ui.views;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.celad.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.RentalAgency;

public class RentalTreePart implements RentalUIConstants{
	@Inject
	private ESelectionService selectionService;
	private TreeViewer tv;

	@Inject
	public RentalTreePart() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent, RentalAgency agency, IEclipseContext context, EMenuService menuService) {
		tv = new TreeViewer(parent);
		RentalTreeContentProvider provider = ContextInjectionFactory.make(RentalTreeContentProvider.class, context) ;
		tv.setContentProvider(provider);
		tv.setLabelProvider(provider);
		Collection<RentalAgency> agencies = new ArrayList<RentalAgency>();
		agencies.add(agency);
		tv.setInput(agencies);
		
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// TODO Auto-generated method stub
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				selectionService.setSelection(sel.size() == 1 ? sel.getFirstElement() : sel.toArray());
				
			}
		});
		menuService.registerContextMenu(tv.getControl(), "com.celad.rental.eap.popupmenu.0");
	}

	@Inject
	public void refreshTree(@Preference(value=PREF_CUSTOMER_COLOR) String custColor)
	{
		if (tv != null)
			tv.refresh();
	}

}

