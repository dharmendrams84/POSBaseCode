/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/mailcheck/UpdateCustomerActionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:48 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:30:39 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:26:34 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:15:25 PM  Robert Pearse   
 *
 * Revision 1.8  2004/07/28 19:54:29  dcobb
 * @scr 6355 Can still search on original business name after it was changed
 * Modified JdbcSelectBusiness to search for name from pa_cnct table.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.mailcheck;

import java.util.zip.DataFormatException;

import oracle.retail.stores.pos.services.tender.TenderCargo;
import oracle.retail.stores.pos.services.tender.tdo.MailBankCheckTDO;
import oracle.retail.stores.pos.tdo.TDOException;
import oracle.retail.stores.pos.tdo.TDOFactory;
import oracle.retail.stores.pos.tdo.TDOUIIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.CustomerUpdateDataTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.customer.CaptureCustomerIfc;
import oracle.retail.stores.domain.utility.AddressConstantsIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.MailBankCheckInfoBeanModel;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
//-----------------------------------------------------------------------------
/**
 * Present the Mail Bank Check UI
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//-----------------------------------------------------------------------------
public class UpdateCustomerActionSite extends PosSiteActionAdapter
{
    /**
     * revision number for this class
     */
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.0:77; $EKW:";

    // Site name
    public static final String SITENAME = "UpdateCustomerActionSite";

    //-------------------------------------------------------------------------
    /**
     * @param bus
     *            the bus arriving at this site
     */
    //-------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        boolean mailLetter = false;

        TenderCargo cargo = (TenderCargo) bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        // get the info from the ui here
        MailBankCheckInfoBeanModel model =
            (MailBankCheckInfoBeanModel) ui.getModel(POSUIManagerIfc.MAIL_BANK_CHECK_INFO);

        TDOUIIfc tdo = null;
        try
        {
            tdo = (TDOUIIfc) TDOFactory.create("tdo.tender.MailBankCheck");
        }
        catch (TDOException tdoe)
        {
            logger.error("Error creating MailBankCheck TDO object", tdoe);
        }

        if (model.getChangeState()) // data was changed at MBC Customer screen
        {
            // attempt to validate postal code and do the database update
            try
            {
                TenderableTransactionIfc transaction = cargo.getTransaction();
                CaptureCustomerIfc customer = (CaptureCustomerIfc)((MailBankCheckTDO) tdo).copyFromModelToNewCustomer(model);
                if (transaction.getCustomer() != null)
                    customer.setCustomerID(cargo.getTransaction().getCustomer().getCustomerID());
                if (cargo.getCustomer()!= null)
                    customer.setCustomerID(cargo.getCustomer().getCustomerID());

                AddressIfc address = DomainGateway.getFactory().getAddressInstance();
                String postalString = address.validatePostalCode(model.getPostalCode(), model.getCountry());
                model.setPostalCode(postalString);

                if (customer.getAddressByType(AddressConstantsIfc.ADDRESS_TYPE_UNSPECIFIED) != null)
                {
                    customer.getAddressByType(AddressConstantsIfc.ADDRESS_TYPE_UNSPECIFIED).setAddressType(
                        AddressConstantsIfc.ADDRESS_TYPE_HOME);
                }

                CustomerUpdateDataTransaction customerTransaction = null;
                
                customerTransaction = (CustomerUpdateDataTransaction) DataTransactionFactory.create(DataTransactionKeys.CUSTOMER_UPDATE_DATA_TRANSACTION);

                // If there is a linked customer and we are not in training mode
                // update the database
                //
                if (customer != null && !cargo.getRegister().getWorkstation().isTrainingMode())
                {
                    customerTransaction.saveCustomer(customer);
                }
                else
                {
                    logger.warn("No customer to save or register in training mode!");
                }

                // set the captured customer in the transaction
                customer.setTransactionID(transaction.getFormattedTransactionSequenceNumber());
                customer.setStoreID(transaction.getWorkstation().getStoreID());
                customer.setWsID(transaction.getWorkstation().getWorkstationID());
                customer.setBusinessDay(transaction.getBusinessDay());
                transaction.setCaptureCustomer(customer);
                
                cargo.setCustomer(customer);
                cargo.setFindOrAddOrUpdateLinked(true);
                
                mailLetter = true;
            }
            catch (DataFormatException e)
            {
                //DialogBeanModel dialogModel = new DialogBeanModel();
                //dialogModel.setResourceID("InvalidPostalCode");
                //dialogModel.setType(DialogScreensIfc.ERROR);
                //ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
                displayErrorDialog(ui, "InvalidPostalCode");

                return;
            }
            catch (DataException de)
            {
                logger.error("UpdateCustomerActionSite.arrive() - Database error while updating customer", de);

                //DialogBeanModel dialogModel = new DialogBeanModel();
                //dialogModel.setResourceID("CustDatabaseError");
                //dialogModel.setType(DialogScreensIfc.ERROR);
                //ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
                displayErrorDialog(ui, "CustDatabaseError");

                return;
            }
        }
        else
        {
            cargo.setUpdateWithoutModify(true);
            //cargo.setFindOrAddOrUpdateLinked(false);

            //DialogBeanModel dialogModel = new DialogBeanModel();
            //dialogModel.setResourceID("UpdateModificationError");
            //dialogModel.setType(DialogScreensIfc.ERROR);
            //ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
            displayErrorDialog(ui, "UpdateModificationError");

            return;
        }

        //        Send the Accept letter if save succeeded.
        if (mailLetter)
        {
            // Using "generic dialog bean".
            DialogBeanModel dialogModel = new DialogBeanModel();

            dialogModel.setResourceID("UpdateSuccessful");
            dialogModel.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
            dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Success");

            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Display the specified Error Dialog
     * 
     * @param String
     *            name of the Error Dialog to display
     * @param POSUIManagerIfc
     *            UI Manager to handle the IO
     */
    //-------------------------------------------------------------------------
    protected void displayErrorDialog(POSUIManagerIfc ui, String name)
    {
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID(name);
        dialogModel.setType(DialogScreensIfc.ERROR);
        //dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, CommonLetterIfc.INVALID);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }

    // end getRevisionNumber()
}
