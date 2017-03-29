/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/validateid/ValidateIDCargoIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/20 04:31:49 rrkohli Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rrkohli   07/01/11 - Encryption CR
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *    abondala  11/03/08 - updated files related to customer id type reason
 *                         code.
 *    abondala  11/03/08 - updated files related to the Patriotic customer ID
 *                         types reason code
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.validateid;

import oracle.retail.stores.common.utility.LocalizedCodeIfc;
import oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.foundation.manager.device.EncipheredDataIfc;
import oracle.retail.stores.foundation.manager.device.MSRModel;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

/**
 * These methods govern the behavior of the validate ID service.
 * 
 * $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public interface ValidateIDCargoIfc extends UserAccessCargoIfc, CargoIfc, TourCamIfc
{
    /**
     * Retrieves the reason code name for obtaining the list of valid ID types
     * 
     * @return The Reason Code Constant
     */
    public String getIDTypeCodeConstant();

    /**
     * Sets the reason code name for obtaining the list of valid ID types
     * 
     * @param codeConstant The Reason Code Constant
     */
    public void setIDTypeCodeConstant(String codeConstant);

    /**
     * Retrieves whether the service should collect countries for ID's that
     * don't reflect a state. Note that if you retrieve a state, you have the
     * country by default.
     * 
     * @return If the ID Issuing country should be captured
     */
    public boolean isCaptureCountry();

    /**
     * Sets whether the service should collect countries for ID's that don't
     * reflect a state. Note that if you retrieve a state, you have the country
     * by default.
     * 
     * @param captureCountry If the ID Issuing country should be captured
     */
    public void setCaptureCountry(boolean captureCountry);

    /**
     * Retrieves whether the service should allow swipe of an ID.
     * 
     * @return If we should allow swipe of an ID.
     */
    public boolean isAllowSwipe();

    /**
     * Sets whether the service should allow swipe of an ID.
     * 
     * @param allowSwipe Whether the service should allow swipe of an ID.
     */
    public void setAllowSwipe(boolean allowSwipe);

    /**
     * Retrieves whether the service should always capture country/state of ID
     * regardless of swipe allowed.
     * 
     * @return If the service should always capture country/state of ID.
     */
    public boolean isAlwaysCaptureIssuer();

    /**
     * Sets whether the service should always capture country/state of ID
     * regardless of swipe allowed.
     * 
     * @param alwaysCaptureIssuer the service should always capture
     *            country/state of ID.
     */
    public void setAlwaysCaptureIssuer(boolean alwaysCaptureIssuer);

    // These methods are returned to the calling service
    /**
     * Retrieves the ID Number used for validation
     * 
     * @return The ID Number as a string
     */
    public String getIDNumber();

    /**
     * Sets the ID Number used for validation
     * 
     * @param idNumber The ID Number as a string
     */
    public void setIDNumber(String idNumber);

    /**
     * Retrieves the MSR Model containing the ID Swipe used for validation
     * 
     * @return The MSR Model
     */
    public MSRModel getMSRModel();

    /**
     * Sets the MSR Model containing the ID Swipe used for validation
     * 
     * @param msrModel The MSR Model
     */
    public void setMSRModel(MSRModel msrModel);

    /**
     * Retrieves the issuing country of the ID used for validation
     * 
     * @return The country as a string
     */
    public String getIDCountry();

    /**
     * Sets the issuing country of the ID used for validation
     * 
     * @param idCountry The country as a string
     */
    public void setIDCountry(String idCountry);

    /**
     * Retrieves the issuing state/province of the ID used for validation
     * 
     * @return The state/province as a string
     */
    public String getIDState();

    /**
     * Sets the issuing state/province of the ID used for validation
     * 
     * @param idState The state/province as a string
     */
    public void setIDState(String idState);

    /**
     * Retrieves the personal id types
     * 
     * @return CodeListIfc
     */
    public CodeListIfc getPersonalIDTypes();

    /**
     * Sets the personal id types
     * 
     * @param personalIDTypes
     */
    public void setPersonalIDTypes(CodeListIfc personalIDTypes);

    /**
     * Retrieves the id type name
     * 
     * @return String idTypeName
     */
    public String getIdTypeName();

    /**
     * Sets the id type name
     * 
     * @param idTypeName
     */
    public void setIdTypeName(String idTypeName);

    /**
     * Retrieves localized Code
     * 
     * @return
     */
    public LocalizedCodeIfc getLocalizedPersonalIDCode();

    /**
     * Sets localized code
     * 
     * @param localizedPersonalIDCode
     */
    public void setLocalizedPersonalIDCode(LocalizedCodeIfc localizedPersonalIDCode);
    
    /**
     * Retrieves the enciphered object for ID Number
     * 
     * @return String idNumberEncipheredData
     */
    public EncipheredDataIfc getIdNumberEncipheredData();
    
    /**
     * Sets enciphered object for ID Number
     * 
     * @param idNumberEncipheredData
     */
    public void setIdNumberEncipheredData(EncipheredDataIfc idNumberEncipheredData);

}
