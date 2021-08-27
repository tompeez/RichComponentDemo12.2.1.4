/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource;

import java.util.Vector;


import oracle.dss.util.DataDirector;
import oracle.dss.util.DataChangedEvent;

import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Member;
import oracle.adfdemo.view.feature.rich.graph.dataSource.edge.SingleLayerEdge;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * The third in a series of four data source samples that demonstrate
 * increasing functionality.
 * This class and the supporting classes that are mentioned in this description
 * reside in the package <code>oracle.adfdemo.view.feature.rich.graph.dataSource</code>.
 * This class extends <code>PagingDataSource</code> and has the following
 * layers on its edges:
 * <ul>
 * <li>A single layer on the row edge - Product
 * <li>A single layer on the column edge - Geography
 * <li>Multiple layers on the page edge - Channel and Month
 * </ul>
 * This class adds drilling to the Geography layer by adding the member
 * "New England", which includes the child members "Massachusetts" and
 * "Rhode Island".
 * 
 * @hidden
 */ 
public class DrillablePagingDataSource extends PagingDataSource {
	
	/**
	 * Constructor for this sample data source.
	 */
	public DrillablePagingDataSource() {
		super();

		// Get the Geography layer, which is initially on the col edge,
		SingleLayerEdge edge = (SingleLayerEdge)getColEdge();		

		// Add New England as a member 
		Member member = new Member();
		member.setValue("NEW ENGLAND");
		member.setLongLabel("New England");
		member.setShortLabel("NE");
		// insert New England at the beginning of the members
		edge.getLayer().getMembers().insertElementAt(member, 0);
		
		// set the initial drill state
		setGeographyCollapsed(edge.getLayer().getMembers());		
	}

    public boolean drill(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
  	// get the vector of members from the edge
  	Vector members = ((SingleLayerEdge)getBaseEdge(edge)).getLayer().getMembers();  		
  	
  	// we know that New England is at the beginning of the members, so check 
  	// its drillstate to understand if we need to expand or collapse the layer.
 		if(((Member)members.elementAt(0)).getDrillState().intValue()==DataDirector.DRILLSTATE_DRILLABLE)
 			setGeographyExpanded(members);
 		else
 			setGeographyCollapsed(members);
  			  	
		// notify listeners of the change, so that they can refresh themselves
		notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.DRILL_CHANGE, true, true, true, true));		
		return false;
  }

    public boolean drillOK(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
		// allow all types of drilling
   	return true;
  }

	/**
	 * Specifies the drillstate for Geography to be expanded and adds child
   * members.
	 *
	 * @param geogValues The current Vector of Geography members
   *
	 */
	protected void setGeographyExpanded(Vector geogValues){			
		((Member)geogValues.elementAt(0)).setDrillState(new Integer(DataDirector.DRILLSTATE_IS_DRILLED));
		Member member = new Member();
		member.setValue("MASSACHUSETTS");
		member.setLongLabel("Massachusetts");
		member.setShortLabel("MA");
		member.setIndent(new Integer(1));
		geogValues.addElement(member);
		member = new Member();
		member.setValue("RHODE ISLAND");
		member.setLongLabel("Rhode Island");
		member.setShortLabel("RI");
		member.setIndent(new Integer(1));
		geogValues.addElement(member);		
	}

	/**
	 * Specifies the drillstate for Geography to be collapsed and removes
   * child members.
	 *
	 * @param geogValues the current Vector of Geography members
   *
	 */
	protected void setGeographyCollapsed(Vector geogValues){
		((Member)geogValues.elementAt(0)).setDrillState(new Integer(DataDirector.DRILLSTATE_DRILLABLE));
		geogValues.removeElementAt(2);
		geogValues.removeElementAt(1);
	}

}