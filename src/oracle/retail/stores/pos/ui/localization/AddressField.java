/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/localization/AddressField.java /main/5 2011/01/28 13:03:07 cgreene Exp $
 * ===========================================================================
 * NOTES
 * A Java enum for address fields.
 *
 * MODIFIED    (MM/DD/YY)
 *  nkgautam    06/22/10 - bill pay changes
 *  abondala    01/03/10 - update header date
 *  ebthorne    10/31/08 - Code review feedback. Reworked getInstance.
 *  ebthorne    10/24/08 - First pass of Address Field enumeration.
 *  ebthorne    10/13/08 - Initial revision
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.localization;

import java.util.Map;
import java.util.HashMap;

/**
 * Provides an enumeration of field name constants for
 * Address fields in the user interface.
 *
 * @version $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/localization/AddressField.java /main/4 2010/06/24 21:17:09 nkgautam Exp $
 * @author  ebthorne
 * @since   13.1
 */
public enum AddressField {
    FIRST_NAME("FirstName"),
    MIDDLE_NAME("MiddleName"),
    LAST_NAME("LastName"),
    BUSINESS_NAME("BusinessName"),
    ADDRESS_LINE_1("AddressLine1"),
    ADDRESS_LINE_2("AddressLine2"),
    ADDRESS_LINE_3("AddressLine3"),
    CITY("City"),
    STATE("State"),
    POSTAL_CODE("PostalCode"),
    COUNTRY("Country"),
    TELEPHONE_TYPE("TelephoneType"),
    TELEPHONE("Telephone"),
    BILL_NUMBER("BillNumber"),
    EMAIL("Email");

    // storage for easy instance retrieval
    private static final Map<String, AddressField> instances =
            new HashMap<String, AddressField>();

    static {
        // for every enumeration defined
        for (AddressField field : AddressField.values())
        {
            // add it to the instances map for later retrieval
            instances.put(field.getFieldName(), field);
        }
    }

    // storage for the user visible field name
    private final String fieldName;

    /**
     * Constructs a new AddressField with the given field name.
     * @param fieldName the field name for this AddressField
     */
    private AddressField(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the field name for this AddressField.
     * @return the field name
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * Returns the AddressField instance for the given field name
     * or null if there is no AddressField with that field name.
     *
     * @param fieldName the field name to search for
     * @return the AddressField instance or null if it cannot be found
     */
    public static AddressField getInstance(String fieldName)
    {
        return instances.get(fieldName);
    }
}