/*
** Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom;

import java.util.Vector;


import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.QDR;
import oracle.dss.util.DataChangedEvent;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.Edge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.SingleLayerEdge;
import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.RowOutOfRangeException;

/**
 * The first in a series of four data source samples that demonstrate
 * increasing functionality.
 * This class extends <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.EdgeBasedDataSource</code>
 * to construct a DataSource with the following characteristics:
 * <ul>
 * <li> A single layer on the row edge - Product
 * <li> A single layer on the column edge - Geography
 * <li> The ability to pivot row and column edges
 * </ul>
 *
 * <p> This class and its supporting classes, which are mentioned
 * in this description, are located in the package
 * <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code>.
 * This class provides all of the functionality that is necessary to
 * effectively display data in a graph.
 * However, it does not support a page edge.
 * The second sample, <code>PagingDataSource</code>, provides support for a
 * page edge.
 *
 * <p>
 * Since the edges of <code>SimpleDataSource</code> display only a single
 * layer, both the row and column edges use an implementation of
 * <code>edge.SingleLayerEdge</code>.
 *
 * <p>
 * In <code>SimpleDataSource</code>, the layers on the edges and the data
 * are hardcoded.
 * This sample uses the classes <code>metadata.Layer</code> and
 * <code>metadata.Member</code> to describe
 * the layers that are on the edges.
 * Each layer contains a list of members.
 *
 * <p>
 * The data for this data source is stored in a two dimensional array that looks
 * like denormalized data from a relational table.
 * The data array in this class contains more data than is necessary because it
 *  also provides data for the following two data source samples that extend
 * this class:
 * <ul>
 * <li> <code>PagingDataSource</code>
 * <li> <code>DrillablePagingDataSource</code>
 * </ul>
 * @hidden
 */
public class SimpleDataSource extends EdgeBasedDataSource {
	
	/**
	 * Constructor for this class.
	 */
	public SimpleDataSource() {

		Member member=null;
		Vector members=null;

		// create the geography layer
		Layer geography = new Layer();
		geography.setName("GEOGRAPHY");
		geography.setLongLabel("Geography");
		geography.setShortLabel("Geography");
		members = new Vector();
		member = new Member();
		member.setValue("MASSACHUSETTS");
		member.setLongLabel("Massachusetts");
		member.setShortLabel("MA");
		members.addElement(member);
		member = new Member();
		member.setValue("RHODE ISLAND");
		member.setLongLabel("Rhode Island");
		member.setShortLabel("RI");
		members.addElement(member);
		geography.setMembers(members);		

		// create the product layer
		Layer product = new Layer();
		product.setName("PRODUCT");
		product.setLongLabel("Product");
		product.setShortLabel("Product");
		members = new Vector();
		member = new Member();
		member.setValue("AUDIO");
		member.setLongLabel("Audio Components");
		member.setShortLabel("Audio");
		members.addElement(member);
		member = new Member();
		member.setValue("VIDEO");
		member.setLongLabel("Video Components");
		member.setShortLabel("Video");
		members.addElement(member);
		member = new Member();
		member.setValue("GAMES");
		member.setLongLabel("Gaming Systems");
		member.setShortLabel("Games");
		members.addElement(member);
		member = new Member();
		member.setValue("CAMERAS");
		member.setLongLabel("Photography Equipment");
		member.setShortLabel("Cameras");
		members.addElement(member);
		member = new Member();
		member.setValue("PHONES");
		member.setLongLabel("Phones");
		member.setShortLabel("Phones");
		members.addElement(member);
		member = new Member();
		member.setValue("MISC");
		member.setLongLabel("Miscellaneous");
		member.setShortLabel("Misc");
		members.addElement(member);
		product.setMembers(members);
		
		// create and set the row edge
		setRowEdge(new SingleLayerEdge(product));

		
		// create and set the column edge
		setColEdge(new SingleLayerEdge(geography));
	}

			
	///////////////////////////////////////////////////////////////////////////////
	// Overridden oracle.dss.util.DataAccess methods

  public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
    try {
    // figure out the fake data value that is needed from the QDR, which 
    // specifies the current product and geography
    QDR qdr = getValueQDR(row, col, DataAccess.QDR_WITH_PAGE);    
		String geography 		= (String)qdr.getDimMember("GEOGRAPHY").getData();
		String product 	= (String)qdr.getDimMember("PRODUCT").getData();

		// walk over every row in the fake data array and return the value
		// when the geography and product match the geography and product from 
		// the QDR
		for(int i=0;i<m_rowCount;i++){
			if( geography.equals(m_fakeData[i][2]) 	&& 
					product.equals(m_fakeData[i][3]))
				return (Object) Integer.valueOf(m_fakeData[i][4]);
		}
		
		// if a match is not found return "??" for a value
	}
	catch (Exception e) {}
		return new String("??");
  }

	///////////////////////////////////////////////////////////////////////////////
	// Overridden oracle.dss.util.DataDirector methods

  public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
    System.out.println("Pivot");
    // whenever a pivot request comes in, swap the row and column edge
    Edge rowEdge=null;
    Edge colEdge=null;
		rowEdge = getRowEdge();    
		colEdge = getColEdge();
		setRowEdge(colEdge);
		setColEdge(rowEdge);
		
		try {
			// notify listeners of the change, so that they can refresh themselves
			notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.PIVOT_CHANGE, true, true, true, false));		
		} catch (Throwable t){
			t.printStackTrace();
		}
		return true;
  }	

  public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
    // prevent pivoting from or to the page edge, since this sample does not have one
    if( (fromEdge == DataDirector.PAGE_EDGE) || (toEdge == DataDirector.PAGE_EDGE)) 
    	return false;
    else
    	return true;
  }

	protected int m_rowCount = 108;
	// Format: "CHANNEL" - "MONTH" - "GEOGRAPHY" - "PRODUCT" - "SALES"
	protected String m_fakeData[][] = {
		{"DIRECT",  "JAN","NEW ENGLAND"		,"AUDIO",		 	"15500"},
		{"DIRECT",  "JAN","NEW ENGLAND"		,"VIDEO",			"10500"},
		{"DIRECT",  "JAN","NEW ENGLAND"		,"GAMES",			"10700"},
		{"DIRECT",  "JAN","NEW ENGLAND"		,"CAMERAS",		"10050"},
		{"DIRECT",  "JAN","NEW ENGLAND"		,"PHONES",		"11550" },
		{"DIRECT",  "JAN","NEW ENGLAND"		,"MISC",			"11550"},
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"AUDIO",		  "5000"},
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"VIDEO",			"10000"},
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"GAMES",			"10000"},
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"CAMERAS",		"7000"},
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"PHONES",		"10500" },
		{"DIRECT",  "JAN","MASSACHUSETTS"	,"MISC",			"10500"},
		{"DIRECT",  "JAN","RHODE ISLAND"		,"AUDIO",			"2000" },
		{"DIRECT",  "JAN","RHODE ISLAND"		,"VIDEO",			"500" },
		{"DIRECT",  "JAN","RHODE ISLAND"		,"GAMES",			"700" },
		{"DIRECT",  "JAN","RHODE ISLAND"		,"CAMERAS",		"3050" },
		{"DIRECT",  "JAN","RHODE ISLAND"		,"PHONES",		"1050" },
		{"DIRECT",  "JAN","RHODE ISLAND"		,"MISC",			"1050"},

		{"DIRECT",  "FEB","NEW ENGLAND"		,"AUDIO",		 	"7800"},
		{"DIRECT",  "FEB","NEW ENGLAND"		,"VIDEO",			"14540"},
		{"DIRECT",  "FEB","NEW ENGLAND"		,"GAMES",			"14740"},
		{"DIRECT",  "FEB","NEW ENGLAND"		,"CAMERAS",		"10850"},
		{"DIRECT",  "FEB","NEW ENGLAND"		,"PHONES",		"15950" },
		{"DIRECT",  "FEB","NEW ENGLAND"		,"MISC",			"15950"},
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"AUDIO",		  "5400"},
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"VIDEO",			"14000"},
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"GAMES",			"14000"},
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"CAMERAS",		"7400"},
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"PHONES",		"14500" },
		{"DIRECT",  "FEB","MASSACHUSETTS"	,"MISC",			"14500"},
		{"DIRECT",  "FEB","RHODE ISLAND"		,"AUDIO",			"2400" },
		{"DIRECT",  "FEB","RHODE ISLAND"		,"VIDEO",			"540" },
		{"DIRECT",  "FEB","RHODE ISLAND"		,"GAMES",			"740" },
		{"DIRECT",  "FEB","RHODE ISLAND"		,"CAMERAS",		"3450" },
		{"DIRECT",  "FEB","RHODE ISLAND"		,"PHONES",		"1450" },
		{"DIRECT",  "FEB","RHODE ISLAND"		,"MISC",			"1450"},

		{"DIRECT",  "MAR","NEW ENGLAND"		,"AUDIO",		  "8600"},
		{"DIRECT",  "MAR","NEW ENGLAND"		,"VIDEO",			"18580"},
		{"DIRECT",  "MAR","NEW ENGLAND"		,"GAMES",			"18780"},
		{"DIRECT",  "MAR","NEW ENGLAND"		,"CAMERAS",		"11650"},
		{"DIRECT",  "MAR","NEW ENGLAND"		,"PHONES",		"20350" },
		{"DIRECT",  "MAR","NEW ENGLAND"		,"MISC",			"20350"},
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"AUDIO",		  "5800"},
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"VIDEO",			"18000"},
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"GAMES",			"18000"},
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"CAMERAS",		"7800"},
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"PHONES",		"18500" },
		{"DIRECT",  "MAR","MASSACHUSETTS"	,"MISC",			"18500"},
		{"DIRECT",  "MAR","RHODE ISLAND"		,"AUDIO",			"2800" },
		{"DIRECT",  "MAR","RHODE ISLAND"		,"VIDEO",			"580" },
		{"DIRECT",  "MAR","RHODE ISLAND"		,"GAMES",			"780" },
		{"DIRECT",  "MAR","RHODE ISLAND"		,"CAMERAS",		"3850" },
		{"DIRECT",  "MAR","RHODE ISLAND"		,"PHONES",		"1850" },
		{"DIRECT",  "MAR","RHODE ISLAND"		,"MISC",			"1850"},

		{"INDIRECT",  "JAN","NEW ENGLAND"		,"AUDIO",		  "700"},
		{"INDIRECT",  "JAN","NEW ENGLAND"		,"VIDEO",			"1050"},
		{"INDIRECT",  "JAN","NEW ENGLAND"		,"GAMES",			"1070"},
		{"INDIRECT",  "JAN","NEW ENGLAND"		,"CAMERAS",		"1005"},
		{"INDIRECT",  "JAN","NEW ENGLAND"		,"PHONES",		"1155" },
		{"INDIRECT",  "JAN","NEW ENGLAND"		,"MISC",			"1105"},
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"AUDIO",		  "500"},
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"VIDEO",			"1000"},
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"GAMES",			"1000"},
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"CAMERAS",		"700"},
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"PHONES",		"1050" },
		{"INDIRECT",  "JAN","MASSACHUSETTS"	,"MISC",			"1000"},
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"AUDIO",			"200" },
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"VIDEO",			"50" },
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"GAMES",			"70" },
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"CAMERAS",		"305" },
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"PHONES",		"105" },
		{"INDIRECT",  "JAN","RHODE ISLAND"		,"MISC",			"105"},

		{"INDIRECT",  "FEB","NEW ENGLAND"		,"AUDIO",		  "780"},
		{"INDIRECT",  "FEB","NEW ENGLAND"		,"VIDEO",			"1454"},
		{"INDIRECT",  "FEB","NEW ENGLAND"		,"GAMES",			"1474"},
		{"INDIRECT",  "FEB","NEW ENGLAND"		,"CAMERAS",		"1085"},
		{"INDIRECT",  "FEB","NEW ENGLAND"		,"PHONES",		"1595" },
		{"INDIRECT",  "FEB","NEW ENGLAND"		,"MISC",			"1595"},
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"AUDIO",		  "540"},
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"VIDEO",			"1400"},
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"GAMES",			"1400"},
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"CAMERAS",		"740"},
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"PHONES",		"1450" },
		{"INDIRECT",  "FEB","MASSACHUSETTS"	,"MISC",			"1450"},
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"AUDIO",			"240" },
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"VIDEO",			"54" },
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"GAMES",			"74" },
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"CAMERAS",		"345" },
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"PHONES",		"145" },
		{"INDIRECT",  "FEB","RHODE ISLAND"		,"MISC",			"145"},

		{"INDIRECT",  "MAR","NEW ENGLAND"		,"AUDIO",		  "860"},
		{"INDIRECT",  "MAR","NEW ENGLAND"		,"VIDEO",			"1858"},
		{"INDIRECT",  "MAR","NEW ENGLAND"		,"GAMES",			"1878"},
		{"INDIRECT",  "MAR","NEW ENGLAND"		,"CAMERAS",		"1165"},
		{"INDIRECT",  "MAR","NEW ENGLAND"		,"PHONES",		"2035" },
		{"INDIRECT",  "MAR","NEW ENGLAND"		,"MISC",			"2035"},
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"AUDIO",		  "580"},
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"VIDEO",			"1800"},
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"GAMES",			"1800"},
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"CAMERAS",		"780"},
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"PHONES",		"1850" },
		{"INDIRECT",  "MAR","MASSACHUSETTS"	,"MISC",			"1850"},
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"AUDIO",			"280" },
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"VIDEO",			"58" },
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"GAMES",			"78" },
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"CAMERAS",		"385" },
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"PHONES",		"185" },
		{"INDIRECT",  "MAR","RHODE ISLAND"		,"MISC",			"185"}

	};		

}