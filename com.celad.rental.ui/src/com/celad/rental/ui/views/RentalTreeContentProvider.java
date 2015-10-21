package com.celad.rental.ui.views;

import java.util.Collection;

import javax.annotation.security.RunAs;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;

public class RentalTreeContentProvider extends LabelProvider implements ITreeContentProvider
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
		
	}
}