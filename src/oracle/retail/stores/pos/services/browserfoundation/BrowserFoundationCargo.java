/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/browserfoundation/BrowserFoundationCargo.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:02 mszekely Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED (MM/DD/YY)
*    cgreen 05/26/10 - convert to oracle packaging
*    abonda 01/03/10 - update header date
*    nkgaut 09/29/08 - Cargo class for browserfoundation service
* ===========================================================================
*/

package oracle.retail.stores.pos.services.browserfoundation;

import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

public class BrowserFoundationCargo extends AbstractFinancialCargo implements CargoIfc
{
  private static final long serialVersionUID = 1L;

  /*
   * Employee Object to have the operator information
   */
  protected EmployeeIfc operator = null;

  /*
   * This method gets the role required to view the Browser on the POS Screen
   * @return int
   *
   * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#getAccessFunctionID()
   */
  public int getAccessFunctionID()
  {
    return RoleFunctionIfc.LAUNCH_BROWSER;
  }

  /*
   * Gets the EmployeeIfc Object. @return EmployeeIfc
   *
   * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#getOperator()
   */
  public EmployeeIfc getOperator()
  {
    return operator;
  }

  /*
   * Sets the EmployeeIfc Object. @param EmployeeIfc
   *
   * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#setOperator(oracle.retail.stores.domain.employee.EmployeeIfc)
   */
  public void setOperator(EmployeeIfc operator)
  {
    this.operator = operator;
  }
}
