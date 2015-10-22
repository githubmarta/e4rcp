package com.celad.rental.ui.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.celad.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;

public class RentalPropertyPart implements RentalUIConstants
{
	public static final String VIEW_ID = "com.opcoach.rental.e4.ui.views.rentalView"; //$NON-NLS-1$

	private Label rentedObjectLabel, customerNameLabel, startDateLabel, endDateLabel;
	private Rental currentRental;
	private Label customerTitle;

	@PostConstruct
	public void createContent(Composite parent, RentalAgency agency, @Named(RENTAL_UI_IMG_REGISTRY) final ImageRegistry registry)
	{
		parent.setLayout(new GridLayout(1, false));

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		infoGroup.setText("Information");
		infoGroup.setLayout(new GridLayout(2, false));

		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);

//		DragSource ds = new DragSource(rentedObjectLabel, DND.DROP_COPY);
//		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		customerTitle = new Label(infoGroup, SWT.NONE);
		customerTitle.setText("Client : ");
		customerNameLabel = new Label(infoGroup, SWT.NONE);
		customerNameLabel.setText("        ");

		Group dateGroup = new Group(parent, SWT.NONE);
		dateGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		dateGroup.setText("Dates");
		dateGroup.setLayout(new GridLayout(2, false));

		Label startDateTitle = new Label(dateGroup, SWT.NONE);
		startDateTitle.setText("Start date :");
		startDateLabel = new Label(dateGroup, SWT.NONE);
		startDateLabel.setText("         ");

		Label endDateTitle = new Label(dateGroup, SWT.NONE);
		endDateTitle.setText("End date");
		endDateLabel = new Label(dateGroup, SWT.NONE);

		// Try with sample (agency injected)
//		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
		setRental(agency.getRentals().get(0));
		
		DragSource ds = new DragSource(rentedObjectLabel, DND.DROP_COPY);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter()
			{
				public void dragSetData(DragSourceEvent event)
				{
					if (TextTransfer.getInstance().isSupportedType(event.dataType))
					{
						event.data = rentedObjectLabel.getText();
					}
				}

				public void dragStart(DragSourceEvent event)
				{
					event.image = registry.get(RentalUIConstants.IMG_AGENCY);
				}

			});	}
	
	public void setRental(Rental r)
	{
		rentedObjectLabel.setText(r.getRentedObject().getName());
		customerNameLabel.setText(r.getCustomer().getDisplayName());
		startDateLabel.setText(r.getStartDate().toString());
		endDateLabel.setText(r.getEndDate().toString());
	}

	@Focus
	private void setFocus()
	{
		rentedObjectLabel.setFocus();
	}
	
	@Inject  @Optional
	public void receiveSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Rental r)
	{
		if (r != null)
			setRental(r);
	}
	
	@Focus
	public void onFocus()
	{
		
	}
	
	
}
