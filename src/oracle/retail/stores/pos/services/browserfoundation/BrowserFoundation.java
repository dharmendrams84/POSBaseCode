/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    nkgaut 11/14/08 - A new class which acts as an interface between POS and
 *                      JDIC Component for Browser Foundation
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.browserfoundation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.tour.gate.Gateway;

public class BrowserFoundation
{
	private   Logger logger = Logger.getLogger(BrowserFoundation.class);
	/**
	 * Object Instance to be used for Reflection
	 */
	private   Object instance = null;

	/**
	 * Home URL
	 */
	private   String HomeURL = null;

	/**
	 * Name of properties file to look for class to invoke
	 */
	private  String PROPERTIES_FILE = "application";

	/**
	 * BrowserFoundation Object
	 */
	private static BrowserFoundation oBrowserFoundation = null;

	/**
	 * Constructor
	 */
	private BrowserFoundation()
	{
		super();
	}

	/**
	 * This method returns an instance if not already instantiated, else
	 * returns the same instance.
	 * @param
	 */
	public static BrowserFoundation getInstance()
	{
		if (oBrowserFoundation == null)
		{
			oBrowserFoundation = new BrowserFoundation();
		}
		return oBrowserFoundation;

	}

	/**
	 * This method calls the method to set the WebBrowser Instance
	 * @param URL
	 * Home URL for Browser
	 */
	public synchronized void setWebBrowserInstance(String URL)
	throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
	MalformedURLException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		HomeURL = URL;
		setWebBrowser();

	}

	/**
	 * This method returns the WebBrowser Instance
	 * @return
	 */
	public Object getWebBrowserInstance()
	{
		return instance;
	}


	/**
	 * This method sets the WebBrowser Instance
	 * @param
	 */
	private void setWebBrowser()
	throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
	MalformedURLException, InstantiationException, IllegalAccessException, InvocationTargetException
	{

		String WebBrowserClassName = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserClassName", "");
		Class cl = Class.forName(WebBrowserClassName);
		Class[] paramTypes = { URL.class };
		Constructor c = cl.getConstructor(paramTypes);
		instance =  c.newInstance(new Object[]{new URL(HomeURL)});
		c.setAccessible(true);
	}

	/**
	 * @return boolean
	 * Is WebBrowser Instance Created
	 */
	public boolean isWebBrowserInstanceCreated()
	{
		if(instance == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * This method performs the "Back" Action.
	 */
	public void performBackAction()
	{
		String WebBrowserBackMethodname = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserBackMethod", "");
		try
		{
			Class[] initArgs =  new Class[0];
			Object oArgContent[] = new Object[0];
			Class objClass = instance.getClass();

			Method method = objClass.getMethod(WebBrowserBackMethodname, initArgs);
			method.invoke(instance, oArgContent);
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	}

	/**
	 * This method performs the "Forward" Action.
	 */
	public void performForwardAction()
	{
		String WebBrowserForwardMethodname = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserForwardMethod", "");
		try
		{
			Class[] initArgs =  new Class[0];
			Object oArgContent[] = new Object[0];
			Class objClass = instance.getClass();

			Method method = objClass.getMethod(WebBrowserForwardMethodname, initArgs);
			method.invoke(instance, oArgContent);
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	}

	/**
	 * This method performs the "Stop" Action.
	 */
	public void performStopAction()
	{
		String WebBrowserStopMethodname = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserStopMethod", "");
		try
		{
			Class[] initArgs =  new Class[0];
			Object oArgContent[] = new Object[0];
			Class objClass = instance.getClass();

			Method method = objClass.getMethod(WebBrowserStopMethodname, initArgs);
			method.invoke(instance, oArgContent);
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	}

	/**
	 * This method performs the "Refresh" Action.
	 */
	public void performRefreshAction()
	{
		String WebBrowserRefreshMethodname = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserRefreshMethod", "");
		try
		{
			Class[] initArgs =  new Class[0];
			Object oArgContent[] = new Object[0];
			Class objClass = instance.getClass();

			Method method = objClass.getMethod(WebBrowserRefreshMethodname, initArgs);
			method.invoke(instance, oArgContent);
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	}

	/**
	 * This method performs the "Home" Action.
	 */
	public void performHomeAction()
	{
		String WebBrowserHomeMethodname = Gateway.getProperty(PROPERTIES_FILE, "BrowserFoundation.WebBrowserHomeMethod", "");
		try
		{
			Class[] initArgs =  new Class[1];
			initArgs[0] = URL.class;
			Object oArgContent[] = new Object[1];
			oArgContent[0] = new URL(HomeURL);
			Class objClass = instance.getClass();
			Method method = objClass.getMethod(WebBrowserHomeMethodname, initArgs);
			method.invoke(instance, oArgContent);
		}
		catch(Exception e)
		{
			logger.error(e);
		}
	}
}
