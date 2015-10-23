package com.celad.rental.ui;

import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class RentalUIActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		RentalUIActivator.context = bundleContext;
		getExtensionQuickAccess(Platform.getExtensionRegistry());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		RentalUIActivator.context = null;
	}
	
	public void getExtensionQuickAccess(IExtensionRegistry reg)
	{
		for (IConfigurationElement elt : reg.getConfigurationElementsFor("org.eclipse.core.runtime.adapters"))
		{
			String name = elt.getAttribute("class");
			String plugin = elt.getNamespaceIdentifier();
			String type = elt.getAttribute("adaptableType");
			System.out.println("Element: " + elt.getName() + " name = " + name + "\n\t type = " + type  + "\n\t plugin = " + plugin);
		}
			
	}

}
