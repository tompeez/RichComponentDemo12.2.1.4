/*
** Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.graph.dataSource;

import java.util.Vector;


import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.QDR;
import oracle.dss.util.DataChangedEvent;

import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Layer;
import oracle.adfdemo.view.feature.rich.graph.dataSource.metadata.Member;
import oracle.adfdemo.view.feature.rich.graph.dataSource.edge.BaseEdge;
import oracle.adfdemo.view.feature.rich.graph.dataSource.edge.SingleLayerEdge;
import oracle.adfdemo.view.feature.rich.graph.dataSource.edge.SymmetricPageEdge;
import oracle.adfdemo.view.feature.rich.graph.dataSource.SimpleDataSource;
import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * The second in a series of four data source samples that demonstrate
 * increasing functionality.
 * This class and the supporting classes that are mentioned in this
 * description reside in the package
 * <code>oracle.adfdemo.view.feature.rich.graph.dataSource</code>.
 * This class extends <code>SimpleDataSource</code>
 * and adds the following capabilities to the new data source sample:
 * <ul>
 * <li>A page edge with multiple layers - Channel and Month
 * <li>The ability to pivot layers among all edges, with the restriction
 * that the row and column edges contain only a single layer.
 * This restriction exists because <code>SimpleDataSource</code> uses the class
 * <code>edge.SingleLayerEdge</code> to construct the row and column edges.
 * </ul>
 *
 * <p> This data source sample uses <code>edge.SymmetricPageEdge</code> to
 * display multiple layers on its page edge.
 * <p>
 * Note: The class <code>edge.AsymmetricPageEdge</code> also displays multiple
 * layers on a page edge and could have been used in this sample.
 * Instead, the sample <code>AsymmetricDrillablePagingDataSource</code>
 * demonstrates the use of <code>edge.AsymmetricPageEdge</code>.
 * 
 * @hidden
 */
public class PagingDataSource extends SimpleDataSource {
	
	/**
	 * Constructor for this data source sample.
	 */
	public PagingDataSource() {
		super();

		Member member=null;
		Vector members=null;

		// create the channel layer
		Layer channel = new Layer();
		channel.setName("CHANNEL");
		channel.setLongLabel("Channel");
		channel.setShortLabel("Channel");
		members = new Vector();
		member = new Member();
		member.setValue("DIRECT");
		member.setLongLabel("Direct");
		member.setShortLabel("Direct");
		members.addElement(member);
		member = new Member();
		member.setValue("INDIRECT");
		member.setLongLabel("Indirect");
		member.setShortLabel("Indirect");
		members.addElement(member);
		channel.setMembers(members);

		// create the month layer
		Layer month = new Layer();
		month.setName("MONTH");
		month.setLongLabel("Month");
		month.setShortLabel("Month");
		members = new Vector();
		member = new Member();
		member.setValue("JAN");
		member.setLongLabel("January");
		member.setShortLabel("Jan");
		members.addElement(member);
		member = new Member();
		member.setValue("FEB");
		member.setLongLabel("February");
		member.setShortLabel("Feb");
		members.addElement(member);
		member = new Member();
		member.setValue("MAR");
		member.setLongLabel("March");
		member.setShortLabel("Mar");
		members.addElement(member);
		month.setMembers(members);
		

		// create and set the page edge
		Layer[] layers = new Layer[2];
		layers[0] = channel;				
		layers[1] = month;
		setPageEdge(new SymmetricPageEdge(layers));	

	}

  public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
    // this routine will treate a pivot as a swap operation
    if ( (flags == PIVOT_SWAP) || (flags==PIVOT_MOVE_BEFORE) || (flags==PIVOT_MOVE_AFTER)){
			// get the edges involved in the operation
  	  BaseEdge eFrom = getBaseEdge(fromEdge);
    	BaseEdge eTo 	 = getBaseEdge(toEdge);
    
			// if the page edge is involved in the operation, then figure out 
			// the page edge's new layer value selections
			int[] hPos = null;
	   	if( (fromEdge==DataDirector.PAGE_EDGE) || (toEdge==DataDirector.PAGE_EDGE) ){
	   		// get the current page hPos, which defines the page edge's current member selections
	   		// using member indexes
				hPos = m_pageEdge.getEdgeCurrentHPos();
				if( (fromEdge==DataDirector.PAGE_EDGE) && (toEdge==DataDirector.PAGE_EDGE) ){
	  	 		// if both the layers being swapped are on the page edge, then
  		 		// both of their current selections are maintained
					int index = hPos[toLayer];
					hPos[toLayer] = hPos[fromLayer];
					hPos[fromLayer] = index;
				} else if(fromEdge==DataDirector.PAGE_EDGE) {
					// if the from edge is the page edge, then the fromLayer needs to be reset to 0, since
					// the layer at that depth is new to the page edge
					hPos[fromLayer] = 0;
				} else {
					// if the from edge is the page edge, then the toLayer needs to be reset to 0, since
					// the layer at that depth is new to the page edge
					hPos[toLayer] = 0;
				}				
   		}
			

			// get the From edge's layer and members			
			Layer fromDim = null;
			if(fromEdge==DataDirector.PAGE_EDGE)
				fromDim = ((SymmetricPageEdge)eFrom).getLayers()[fromLayer];
			else 
				fromDim = ((SingleLayerEdge)eFrom).getLayer();
			
			// get the To edge's layer and members
			Layer toDim = null;
			if(toEdge==DataDirector.PAGE_EDGE)
				toDim = ((SymmetricPageEdge)eTo).getLayers()[toLayer];
			else
				toDim = ((SingleLayerEdge)eTo).getLayer();
			
						
			// set the From edge's layer and members
			if(fromEdge==DataDirector.PAGE_EDGE)
				((SymmetricPageEdge)eFrom).getLayers()[fromLayer] = toDim;
			else
				((SingleLayerEdge)eFrom).setLayer(toDim);

			// set the To edge's layer and members
			if(toEdge==DataDirector.PAGE_EDGE)
				((SymmetricPageEdge)eTo).getLayers()[toLayer] = fromDim;
			else 
				((SingleLayerEdge)eTo).setLayer(fromDim);
			
			// reset the page edge's current page to maintain the correct page values for the layers that 
			// were not swapped
			if(hPos!=null){
				try {
					getPageEdge().changeEdgeCurrentHPos(hPos,hPos.length-1);				
				} 
				catch(SliceOutOfRangeException e){
					e.printStackTrace();
				}
			}
			// notify listeners of the change, so that they can refresh themselves
			notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.PIVOT_CHANGE, true, true, true, true));		
		} 
    
		return true;
  }	

    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
  	// enable all types of pivoting
   	return true;
  }

  public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {  			
    try {
    // figure out the fake data value that is needed from the QDR, which 
    // specifies the current product, geography, channel, and month
    QDR qdr = getValueQDR(row, col, DataAccess.QDR_WITH_PAGE);
		String channel 	= (String)qdr.getDimMember("CHANNEL").getData();
		String month 		= (String)qdr.getDimMember("MONTH").getData();
		String geography= (String)qdr.getDimMember("GEOGRAPHY").getData();
		String product 	= (String)qdr.getDimMember("PRODUCT").getData();

		// walk over every row in the fake data array and return the value
		// when the members in the data array match the members
		// in the QDR 
		for(int i=0;i<m_rowCount;i++){
			if(	channel.equals(m_fakeData[i][0]) && 
					month.equals(m_fakeData[i][1]) 		&& 
					geography.equals(m_fakeData[i][2]) 	&& 
					product.equals(m_fakeData[i][3]))
				return (Object) Integer.valueOf(m_fakeData[i][4]);
		}
	}
	catch (Exception e) {}
		// if a match is not found return "??" for a value
		return new String("??");
  }

}