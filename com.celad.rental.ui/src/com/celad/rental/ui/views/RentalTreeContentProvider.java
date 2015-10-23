package com.celad.rental.ui.views;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.celad.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalTreeContentProvider extends LabelProvider implements ITreeContentProvider, IColorProvider, RentalUIConstants
{
	public Object[] getElements(Object inputElement)
	{
		if (inputElement instanceof Collection<?>)
		{
			return ((Collection<?>)inputElement).toArray();
		}
		else
			return null;
	}
	
	public Object[] getChildren(Object parentElement)
	{
		if (parentElement instanceof RentalAgency)
		{ 
			RentalAgency a = (RentalAgency) parentElement;
			return new Node[] { new Node(Node.NODE_CLIENTS, a),
								new Node(Node.NODE_LOCATIONS, a),
								new Node(Node.NODE_OBJETS, a)};
		}
		else if (parentElement instanceof Node)
			return ((Node) parentElement).getChildren();
		else
			return null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		if (element instanceof RentalAgency)
			return ((RentalAgency)element).getName();
		else if (element instanceof Customer)
			return ((Customer)element).getFirstName() + " " + ((Customer)element).getLastName();
		else if (element instanceof Node)
			return ((Node)element).getLabel();
		else if (element instanceof RentalObject)
			return ((RentalObject)element).getName();
		return super.getText(element);
	}
	
	class Node 
	{
		public static final String NODE_OBJETS = "Objets";
		public static final String NODE_LOCATIONS = "Locations";
		public static final String NODE_CLIENTS = "Clients";
		private String label;
		
		public String getLabel() {
			return label;
		}

		private RentalAgency agency;
		
		public Node(String label, RentalAgency agency) {
			super();
			this.label = label;
			this.agency = agency;
		}

		public Object[] getChildren()
		{
		if (label == NODE_CLIENTS)
			return agency.getCustomers().toArray();
		else if (label == NODE_LOCATIONS)
			return agency.getRentals().toArray();
		else if (label == NODE_OBJETS)
			return agency.getObjectsToRent().toArray();
		else
			return null;
		
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((agency == null) ? 0 : agency.hashCode());
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (agency == null) {
				if (other.agency != null)
					return false;
			} else if (!agency.equals(other.agency))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			return true;
		}

		private RentalTreeContentProvider getOuterType() {
			return RentalTreeContentProvider.this;
		}
		
	}

	@Inject
	IPreferenceStore prefStore;
	
	@Override
	public Color getForeground(Object element) {
		if (element instanceof Customer)
		{
			String color = prefStore.getString(PREF_CUSTOMER_COLOR);
			return getPrefColor(color);
		}
		else if (element instanceof RentalObject)
		{
			String color = prefStore.getString(PREF_RENTAL_OBJECT_COLOR);
			return getPrefColor(color);
//			return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
		}
		else if (element instanceof Rental)
		{
			String color = prefStore.getString(PREF_RENTAL_COLOR);
			return getPrefColor(color);
//			return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
//		if (element instanceof Customer)
//		{
//			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//		}
		return null;
	}
	
	private Color getPrefColor(String rgbKey)
	{
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();

		Color result = colorRegistry.get(rgbKey);
		if (result == null)
		{
			// Get value in pref store
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			result = colorRegistry.get(rgbKey);
		}

		return result;

	}
	
	@Inject  @Named(RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry registry;
	
	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		if (element instanceof Customer)
			return registry.get(IMG_CUSTOMER);
		else if (element instanceof RentalObject)
			return registry.get(IMG_RENTAL_OBJECT);
		else if (element instanceof RentalAgency)
			return registry.get(IMG_AGENCY);
				
		return super.getImage(element);
	}
}