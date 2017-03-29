/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/admin/parametermanager/StoreNewISODateValuesAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:05 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:30:12 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:35 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:29 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/07/20 18:41:52  cdb
 *   @scr 6127 Updated to use validation in validator rather than aisles.
 *
 *   Revision 1.1  2004/03/19 21:02:56  mweis
 *   @scr 4113 Enable ISO_DATE datetype
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.admin.parametermanager;

// java imports
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ISODateParameterBeanModel;
import oracle.retail.stores.pos.ui.beans.ParametersCommon;
import oracle.retail.stores.pos.ui.beans.RetailParameter;

//------------------------------------------------------------------------------
/**
    Store the parameter that the user has chosen in the cargo.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class StoreNewISODateValuesAisle extends LaneActionAdapter
{
    /** revision number **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
        Stores the parameter that the user has chosen in the cargo.
        <p>
        @param bus the bus traversing this lane
    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        LetterIfc letter = new Letter(ParametersCommon.ACCEPT_DATA);
        
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ISODateParameterBeanModel model = (ISODateParameterBeanModel)ui.getModel(POSUIManagerIfc.PARAM_EDIT_ISO_DATE);
        EYSDate newValue = model.getNewValue();
        String newModifiable = (String)model.getModifiableValue();

        ParameterCargo cargo = (ParameterCargo)bus.getCargo();
        RetailParameter cargoParam = cargo.getParameter();

        if (cargoParam instanceof ISODateParameterBeanModel)
        {
            ISODateParameterBeanModel dateModel = (ISODateParameterBeanModel)cargoParam;
            dateModel.setModifiableValue(newModifiable);
            dateModel.setNewValue(newValue);

            dateModel.setValue(newValue.asISODate());           

            try
            {
                cargo.convertBeanToParameter(dateModel);
            }
            catch (ParameterException e)
            {
                letter = new Letter(ParametersCommon.REJECT_DATA);
            }
        }
        bus.mail(letter, BusIfc.CURRENT);        
    }
}