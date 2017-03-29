/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/ReverseTenderAuthLaunchShuttle.java /rgbustores_13.4x_generic_branch/5 2011/07/28 21:02:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/28/11 - added support for manager override for card decline
 *    blarsen   07/08/11 - TenderAuthCargo moved from tender.tenderauth (which
 *                         was deleted) into new service tender.reversal.
 *    kelesika  12/06/10 - Multiple reversal of gift cards
 *    kelesika  12/03/10 - Multiple gift card reversals
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.ado.ADO;
import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;
import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.pos.ado.store.StoreADO;
import oracle.retail.stores.pos.ado.store.StoreFactory;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.ado.transaction.TransactionPrototypeEnum;
import oracle.retail.stores.pos.services.tender.TenderCargo;

public class ReverseTenderAuthLaunchShuttle implements ShuttleIfc
{
    /**
     * serial version UID
     */
    private static final long serialVersionUID = 4798389829030710726L;

    /**
     * Sale Cargo
     */
    protected SaleCargoIfc saleCargo;

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    
    public void load(BusIfc bus)
    {
        saleCargo = (SaleCargoIfc)bus.getCargo();
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    
    public void unload(BusIfc bus)
    {
        TenderCargo cargo = (TenderCargo)bus.getCargo();

        TourADOContext context = new TourADOContext(bus);
        context.setApplicationID(cargo.getAppID());
        ContextFactory.getInstance().setContext(context);

        // create a register
        StoreFactory storeFactory = StoreFactory.getInstance();
        RegisterADO registerADO = storeFactory.getRegisterADOInstance();
        registerADO.fromLegacy(saleCargo.getRegister());
        cargo.setOperator(saleCargo.getOperator());

        // create the store
        StoreADO storeADO = storeFactory.getStoreADOInstance();
        storeADO.fromLegacy(saleCargo.getStoreStatus());

        // put store in register
        registerADO.setStoreADO(storeADO);

        // Create/convert/set in cargo ADO transaction
        TransactionIfc t = saleCargo.getTransaction();
        if(t != null)
        {
            TransactionPrototypeEnum txnType = TransactionPrototypeEnum
                        .makeEnumFromTransactionType(t.getTransactionType());
            RetailTransactionADOIfc txnADO = null;
            try
            {
                txnADO = txnType.getTransactionADOInstance();
            }
            catch (ADOException e1)
            {
                e1.printStackTrace();
            }
            ((ADO)txnADO).fromLegacy(t);
            cargo.setCurrentTransactionADO(txnADO);
        }
        //set the register and store status
        cargo.setRegister(saleCargo.getRegister());
        cargo.setStoreStatus(saleCargo.getStoreStatus());
    }

}
