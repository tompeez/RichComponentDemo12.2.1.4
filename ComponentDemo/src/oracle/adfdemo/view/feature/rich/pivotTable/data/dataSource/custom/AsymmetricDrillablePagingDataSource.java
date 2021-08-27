/*
** Copyright (c) 2008, 2012, Oracle and/or its affiliates. 
** All rights reserved. 
**
*/
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom;

import java.util.Vector;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.DataChangedEvent;

import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Layer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.AsymmetricLayer;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.metadata.Member;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.MemberNode;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.BaseEdge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.Edge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.PageEdge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.AsymmetricEdge;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.edge.AsymmetricPageEdge;
import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * This data source is intended for demo, non-production code only.  It was created solely to facilitate the UI demos, 
 * and is not necessarily scalable or performant.  
 * 
 * The final class in a series of four data source samples that demonstrate
 * increasing functionality.
 * This class and the supporting classes that are mentioned in this description
 * reside in the package <code>oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom</code>.
 * This class extends <code>EdgeBasedDataSource</code> and adds the following
 * capabilities:
 * <ul>
 * <li>Asymmetric row, column, and page edges
 * <li>The ability to swap and pivot any layer
 * <li>The ability to perform an asymmetric drill
 * </ul>
 *
 * <p> This class constructs a data source with the following edges and
 * layers:
 * <ul>
 * <li>A row edge that implements <code>edge.AsymmetricEdge</code> - Contains
 * the Channel layer
 * <li>A column edge that implements <code>edge.AsymmetricEdge</code> - Contains
 * the Quarter and Month asymmetric layers
 * <li>A page edge that implements <code>edge.AsymmetricPageEdge</code> -
 * Contains the Product and Geography layers
 * </ul>
 *
 * <p> To add asymmetric layers to edges, this class uses the
 * <code>metadata.AsymmetricLayer</code> class, which provides the ability
 * to define relationships between related parent and child layers.
 *
 * <p> This sample contains two asymmetric layers, quarter and month.
 * If you expand a quarter with a drill operation, then its months will appear.
 * If you collapse a quarter with a drill operation, then its months will
 * disappear.
 *
 * <p> The data for this sample is stored in a two dimensional array that
 * looks like denormalized data from a relational table.
 * 
 * @hidden
 */
public class AsymmetricDrillablePagingDataSource extends EdgeBasedDataSource {
	
	/**
	 * Constructor for this data source sample.
	 */
	public AsymmetricDrillablePagingDataSource() {
		super();

		MemberNode 	memberRoot=	null;
		Member 			member		=	null;
		Layer[] 		layers 	= null;
		Vector 			members 	= null;
		
		// create the geography layer
		m_geography = null;
		m_geography = new Layer();
		m_geography.setName("GEOGRAPHY");
		m_geography.setLongLabel("Geography");
		m_geography.setShortLabel("Geography");
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
		m_geography.setMembers(members);

		// create the product layer
		m_product = new Layer();
		m_product.setName("PRODUCT");
		m_product.setLongLabel("Product");
		m_product.setShortLabel("Product");
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
		m_product.setMembers(members);

		// create the channel layer
		m_channel = new Layer();
		m_channel.setName("CHANNEL");
		m_channel.setLongLabel("Channel");
		m_channel.setShortLabel("Channel");
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
		m_channel.setMembers(members);		

		// create the month layer, which is an asymmetric layer	
		m_month = new AsymmetricLayer();
		m_month.setName("MONTH");
		m_month.setLongLabel("Month");
		m_month.setShortLabel("Month");
		// set quarter as the related parent layer
		Vector relatedLayers = new Vector();
		relatedLayers.addElement(new String("QUARTER"));
		m_month.setRelatedParentLayers(relatedLayers);
		members = new Vector();
		member = new Member();
		member.setValue("JAN");
		member.setLongLabel("January");
		member.setShortLabel("Jan");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("FEB");
		member.setLongLabel("February");
		member.setShortLabel("Feb");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("MAR");
		member.setLongLabel("March");
		member.setShortLabel("Mar");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member = new Member();
		member.setValue("APR");
		member.setLongLabel("April");
		member.setShortLabel("Apr");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("MAY");
		member.setLongLabel("May");
		member.setShortLabel("May");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("JUN");
		member.setLongLabel("Jun");
		member.setShortLabel("Jun");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("JUL");
		member.setLongLabel("July");
		member.setShortLabel("Jul");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("AUG");
		member.setLongLabel("August");
		member.setShortLabel("Aug");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("SEP");
		member.setLongLabel("September");
		member.setShortLabel("Sep");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("OCT");
		member.setLongLabel("October");
		member.setShortLabel("Oct");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("NOV");
		member.setLongLabel("November");
		member.setShortLabel("Nov");
		member.setLayer(m_month.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("DEC");
		member.setLongLabel("December");
		member.setShortLabel("Dec");
		member.setLayer(m_month.getName());
		members.addElement(member);
		m_month.setMembers(members);

		// create the quarter layer, which is an asymmetric layer
		m_quarter = new AsymmetricLayer();
		m_quarter.setName("QUARTER");
		m_quarter.setLongLabel("Quarter");
		m_quarter.setShortLabel("Quarter");
		// set month as the related child layer
		relatedLayers = new Vector();
		relatedLayers.addElement(new String("MONTH"));
		m_quarter.setRelatedChildLayers(relatedLayers);		
		members = new Vector();
		member = new Member();
		member.setValue("Q1");
		member.setLongLabel("First Quarter");
		member.setShortLabel("Q1");
		member.setLayer(m_quarter.getName());
		members.addElement(member);				
		member = new Member();
		member.setValue("Q2");
		member.setLongLabel("Second Quarter");
		member.setShortLabel("Q2");
		member.setLayer(m_quarter.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("Q3");
		member.setLongLabel("Third Quarter");
		member.setShortLabel("Q3");
		member.setLayer(m_quarter.getName());
		members.addElement(member);
		member = new Member();
		member.setValue("Q4");
		member.setLongLabel("Fourth Quarter");
		member.setShortLabel("Q4");
		member.setLayer(m_quarter.getName());
		members.addElement(member);
		m_quarter.setMembers(members);

		// set the initial drill state of each quarter
		expandQuarter("Q1");		
		collapseQuarter("Q2");
		expandQuarter("Q3");		
		collapseQuarter("Q4");


		// create the row edge
		layers = new Layer[1];
		layers[0] = m_channel;		
		setRowEdge(new AsymmetricEdge(layers));	
		
			
		// create the column edge
		layers = new Layer[2];
		layers[0] = m_quarter;
		layers[1] = m_month;
		setColEdge(new AsymmetricEdge(layers));	
							

		// create the page edge					
		layers = new Layer[2];
		layers[0] = m_product;		
		layers[1] = m_geography;		
		setPageEdge(new AsymmetricPageEdge(layers));	
		
	}


  public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
		boolean bRet = false;
		if( (flags == PIVOT_SWAP) ){			
			// Swap is accomplished with multiple pivots.  Swap performance could be better if the 
			// swap was done as a single operation. 
			if(fromEdge==toEdge){
				if( (fromLayer==toLayer+1) || (fromLayer+1==toLayer)){
					bRet = internalPivot(fromEdge,toEdge,fromLayer,toLayer,PIVOT_MOVE_BEFORE);
				} else if(fromLayer>toLayer){
					bRet = internalPivot(fromEdge,toEdge,fromLayer,toLayer,PIVOT_MOVE_BEFORE);
					if(bRet==true)
						bRet = internalPivot(toEdge, fromEdge, toLayer+1, fromLayer,PIVOT_MOVE_AFTER);									
				} else {
					bRet = internalPivot(toEdge, fromEdge, toLayer, fromLayer,PIVOT_MOVE_BEFORE);									
					if(bRet==true)
						bRet = internalPivot(fromEdge,toEdge,fromLayer+1,toLayer,PIVOT_MOVE_AFTER);
				}
			} else {		
				bRet = internalPivot(fromEdge,toEdge,fromLayer,toLayer,PIVOT_MOVE_AFTER);
				if(bRet==true)
					bRet = internalPivot(toEdge, fromEdge, toLayer, fromLayer,PIVOT_MOVE_BEFORE);
			}
		}	else if( (flags==PIVOT_MOVE_BEFORE) || (flags==PIVOT_MOVE_AFTER))
		{
                    //fixing bug 8728169
                    if (fromEdge==toEdge && fromLayer<toLayer && flags==PIVOT_MOVE_BEFORE)
                        bRet = internalPivot(fromEdge,toEdge,fromLayer,toLayer-1,flags);
                    else
			bRet = internalPivot(fromEdge,toEdge,fromLayer,toLayer,flags);
		} else if (flags==PIVOT_MOVE_TO)
        {        
            int layerCount = getLayerCount(toEdge);
            if (layerCount>0)
            {
                layerCount--;
                bRet = internalPivot(fromEdge,toEdge,fromLayer,layerCount,PIVOT_MOVE_AFTER);
            } else
                bRet = internalPivot(fromEdge,toEdge,fromLayer,0,PIVOT_MOVE_BEFORE);
            
        } else if (flags==PIVOT_EDGES)
        {
            BaseEdge eFrom = getBaseEdge(fromEdge);
            BaseEdge eTo   = getBaseEdge(toEdge);
            Layer[] fromLayers = ((AsymmetricEdge)eFrom).getLayers();
            Layer[] toLayers = ((AsymmetricEdge)eTo).getLayers();            
            ((AsymmetricEdge)eFrom).setLayers(toLayers);
            ((AsymmetricEdge)eTo).setLayers(fromLayers);                    
        }
		
		if(bRet==true){
			// notify listeners of the change, so that they can refresh themselves
			notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.PIVOT_CHANGE, true, true, true, true));					
		}
		return bRet;
  }	

	// internalPivot is called by pivot() if the pivot type is PIVOT_MOVE_BEFORE or PIVOT_MOVE_AFTER
	protected boolean internalPivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws LayerOutOfRangeException{        
		try {
			// get the edges involved in the operation
	    BaseEdge eFrom = getBaseEdge(fromEdge);
  	  BaseEdge eTo 	 = getBaseEdge(toEdge);
    
		  // save the page hPos if we are changing the page edge
		  // this will be used to reset the page values after the pivot has occurred
			int[] hPos = null;
			int changeLayer = 0;
	   	if( (fromEdge==DataDirector.PAGE_EDGE) || (toEdge==DataDirector.PAGE_EDGE) ){
	   		int targetLayer = toLayer;
	   		if(flags==PIVOT_MOVE_AFTER)
	   			targetLayer++;		   		
 		 		hPos = m_pageEdge.getEdgeCurrentHPos();
				if( (fromEdge==DataDirector.PAGE_EDGE) && (toEdge==DataDirector.PAGE_EDGE) ){

					// reorder the hPos indexes 
					int[] newHPos = new int[hPos.length];
					int fromLayerHPos = hPos[fromLayer];					
					for(int i=0;i<hPos.length;i++){						
						if(fromLayer<toLayer){
							if(i<fromLayer)
								newHPos[i] = hPos[i];
							else if( (i>=fromLayer)&&(i<toLayer) )
								newHPos[i] = hPos[i+1];
							else if(i==toLayer)
								newHPos[i]=fromLayerHPos;
							else // i > toLayer
								newHPos[i]=hPos[i];
						} else if(fromLayer>toLayer){
							if(i<toLayer)
								newHPos[i] = hPos[i];
							else if(i==toLayer)
								newHPos[i] = fromLayerHPos;
							else if( (i>toLayer)&&(i<=fromLayer) )
								newHPos[i] = hPos[i-1];
							else // i > fromLayer
								newHPos[i] = hPos[i];
						}
					}
					hPos = newHPos;

					// the lower layer of the change needs to be used as the change layer
					if(fromLayer > toLayer)						
						changeLayer = toLayer;
					else
						changeLayer = fromLayer;						
				} else if(fromEdge==DataDirector.PAGE_EDGE){
					// if we are moving the last layer off of the page edge, then we do not need to reset
					// the page hPos
					if( ((AsymmetricEdge)eFrom).getLayers().length==1)
						hPos = null;
					else {
						// remove the layer from the hPos!
						int[] newHPos = new int[hPos.length-1];
						for(int i=0;i<hPos.length;i++){
							if(i<fromLayer)
								newHPos[i] = hPos[i];
							else if(i>fromLayer)
								newHPos[i-1] = hPos[i];
						}
						hPos = newHPos;
						changeLayer = fromLayer;
					}
				} else if(toEdge==DataDirector.PAGE_EDGE){
					// add the layer to the hPos
					int[] newHPos = new int[hPos.length+1];
					for(int i=0;i<(hPos.length+1);i++){
						if(i<targetLayer)
							newHPos[i] = hPos[i];
						else if(i==targetLayer)
							newHPos[i] = 0;
						else // i>toLayer
							newHPos[i] = hPos[i-1];
					}
					hPos = newHPos;
					changeLayer = targetLayer;
				}
 	 		}

			// if we are pivot on the same edge
	   	if(fromEdge==toEdge){
				Layer[] layers = ((AsymmetricEdge)eFrom).getLayers();
				Layer[] newLayers = new Layer[layers.length];
				Layer layerFrom = layers[fromLayer];
				Layer layerTo = layers[toLayer];

				// reorder the layers on the edge
				for(int i=0;i<layers.length;i++){
					if(fromLayer<toLayer){
						if(i<fromLayer)
							newLayers[i] = layers[i];
						else if( (i>=fromLayer)&&(i<toLayer) )
							newLayers[i] = layers[i+1];
						else if(i==toLayer)
							newLayers[i]=layerFrom;
						else // i > toLayer
							newLayers[i]=layers[i];						
					} else if(fromLayer>toLayer){
						if(i<toLayer)
							newLayers[i] = layers[i];
						else if(i==toLayer)
							newLayers[i] = layerFrom;
						else if( (i>toLayer)&&(i<=fromLayer) )
							newLayers[i] = layers[i-1];
						else // i > fromLayer
							newLayers[i] = layers[i];
					}
				}

				// if both layers are being moved on the page edge then
				// check if a related child layer has been moved above a related parent
				// and set the child layer's hIndex to 0, if it has a related parent on the edge
				// after it, since its values will grow when it is moved before the parent
				if(fromEdge==DataDirector.PAGE_EDGE){					
					if( (fromLayer>toLayer) && (layerFrom instanceof AsymmetricLayer)){
						if(((AsymmetricEdge)m_pageEdge).findMatch(newLayers, toLayer, fromLayer, ((AsymmetricLayer)layerFrom).getRelatedParentLayers())){
							hPos[toLayer]=0;
						}
					}						
				}				
				
				// if quarter or month have been reordered, then update their relationship (drilling info, relations, ...)
				if( (layerFrom==m_quarter) || (layerTo==m_quarter) || (layerFrom==m_month) || (layerTo==m_month) )
					updateQuarterMonthRelationship(newLayers);					
						
				setEdge(fromEdge,new AsymmetricPageEdge(newLayers) );	   		
  	 	}	else {	// we are pivoting on two different edges
				// remove the layer from the edge it is being moved from			
				Layer[] fromLayers = ((AsymmetricEdge)eFrom).getLayers();
				Layer layer = fromLayers[fromLayer];
				Layer[] newFromLayers = new Layer[fromLayers.length - 1];
				for(int i=0;i<fromLayers.length;i++){
					if(i<fromLayer)
						newFromLayers[i] = fromLayers[i];
					else if(i>fromLayer)
						newFromLayers[i-1] = fromLayers[i];					
				}
				updateQuarterMonthRelationship(newFromLayers);
				setEdge(fromEdge,new AsymmetricPageEdge(newFromLayers) );
						
				// add the layer to the edge it is being moved to
				Layer[] toLayers = ((AsymmetricEdge)eTo).getLayers();
				Layer[] newToLayers = new Layer[toLayers.length + 1];
				if(flags==PIVOT_MOVE_AFTER)
					toLayer++;
				for(int i=0;i<toLayers.length+1;i++){
					if(i<toLayer)
						newToLayers[i] = toLayers[i];
					else if (i==toLayer)
						newToLayers[i] = layer;
					else if(i>toLayer)
						newToLayers[i] = toLayers[i-1];					
				}
				updateQuarterMonthRelationship(newToLayers);							
				setEdge(toEdge, new AsymmetricPageEdge(newToLayers));
  	 	}
	
			// reset the page edge's current page to maintain the correct page values for the layers that 
			// were not swapped
			if(hPos!=null){			
				int stopAt;
		   	if((fromEdge==DataDirector.PAGE_EDGE) && (toEdge==DataDirector.PAGE_EDGE)){
		   		if(fromLayer>toLayer)
		   			stopAt = fromLayer+1;
		   		else
		   			stopAt = toLayer+1;
		   	} else
					stopAt = hPos.length;
							
				// reset all layers after and including the second asymmetric layer 
				// to zero
 				Layer[] pageLayers = ((AsymmetricPageEdge)m_pageEdge).getLayers();
				int numAsymLayers=0;
				for(int i=changeLayer;i<stopAt;i++){
					if(pageLayers[i] instanceof AsymmetricLayer){
						numAsymLayers++;
						if(numAsymLayers>1)
							hPos[i]=0;
 					}
				}							
 				// set the new page index from the hPos
				m_pageEdge.changeEdgeCurrentHPos(hPos,m_pageEdge.getLayerCount()-1);				
			}			
		} catch (Throwable t){
			t.printStackTrace();
			return false;
		}    
		return true;
	}

    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException {
		if((flags==PIVOT_MOVE_BEFORE) || (flags==PIVOT_MOVE_AFTER)){
			// do not allow all of the layers to be moved off of the row and column edges			
			if((fromEdge!=PAGE_EDGE) && (getBaseEdge(fromEdge).getLayerCount()==1) )
					return false;
		}
   	return true;
  }

    public boolean drill(int edge, int layer, int fromSlice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
  	Layer[] layers = ((AsymmetricEdge)getBaseEdge(edge)).getLayers();  		

		// check if all quarters are collapsed before the drill, since if they are 
		// then the month layer needs to be added to the edge
		Vector members = m_quarter.getMembers();
		boolean bAllCollapsedBeforeDrill = true;
		for(int i=0;i<members.size();i++){
			if( ((Member)members.elementAt(i)).getDrillState().intValue()==DataDirector.DRILLSTATE_IS_DRILLED)
				bAllCollapsedBeforeDrill = false;				
		}

  	// find out which quarter value was drilled
  	String member = (String)getMemberMetadata(edge, layer, fromSlice,MetadataMap.METADATA_VALUE);
  	Integer drillState = (Integer)getMemberMetadata(edge, layer, fromSlice,MetadataMap.METADATA_DRILLSTATE);
		if(drillState.intValue()==DataDirector.DRILLSTATE_DRILLABLE)
			expandQuarter(member);
		else
			collapseQuarter(member);

		// check if all quarters are collapsed after the drill, since if they are 
		// then the month layer needs to be removed from the edge
		boolean bAllCollapsedAfterDrill = true;
		for(int i=0;i<members.size();i++){
			if( ((Member)members.elementAt(i)).getDrillState().intValue()==DataDirector.DRILLSTATE_IS_DRILLED)
				bAllCollapsedAfterDrill = false;				
		}

		if(bAllCollapsedBeforeDrill==true){
			// add the month layer to the edge after the quarter layer
			Layer[] newLayers = new Layer[layers.length+1];

			for(int i=0;i<layers.length+1;i++){
				if(i<layer+1)
					newLayers[i] = layers[i];
				else if (i==layer+1)
					newLayers[i] = m_month;
				else if(i>layer+1)
					newLayers[i] = layers[i-1];					
			}

			layers = newLayers;
		} else if(bAllCollapsedAfterDrill==true){
			// remove the month layer from the edge			
			
			// find the month layer's layer
			int monthLayer = -1;
			for(int i=0;i<layers.length;i++){
				if(layers[i]==m_month){
					monthLayer = i;
					break;
				}	
			}					
						
			Layer[] newLayers = new Layer[layers.length-1];
			for(int i=0;i<layers.length;i++){
				if(i<monthLayer)
					newLayers[i] = layers[i];
				else if(i>monthLayer)
					newLayers[i-1] = layers[i];					
			}
			layers = newLayers;
		}
		

		// rebuild the edge
		setEdge(edge, new AsymmetricPageEdge(layers));
		  			  	
		// notify listeners of the change, so that they can refresh themselves
		notifyListeners(new DataChangedEvent(this, this, DataChangedEvent.DRILL_CHANGE, true, true, true, true));		
		return false;
  }

    public boolean drillOK(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException, DataDirectorException {
  	// allow drilling
   	return true;
  }

  public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {  			
    try {
  	  // figure out the row,column value that is needed from the QDR, which 
	    // specifies the current product, geography, channel, and month
            QDR qdr = getValueQDR(row, col, DataAccess.QDR_WITH_PAGE);
            String channel 	= (String)qdr.getDimMember("CHANNEL").getData();
            String quarter	= (String)qdr.getDimMember("QUARTER").getData();
            QDRMember member = qdr.getDimMember("MONTH");          
			String month=null;
            if(member!=null)
                month = (String)member.getData();
            String state 		= (String)qdr.getDimMember("GEOGRAPHY").getData();
			String product 	= (String)qdr.getDimMember("PRODUCT").getData();
			// walk over every row in the fake data array and return the value
			// when the layer values in the data array match the dimension values
			// in the QDR.  Take into account the fact that month may not be in the QDR, if
			// all quarters are collapsed/not drilled.
			for(int i=0;i<m_rowCount;i++){
				if((channel!=null) && (channel.equals(m_fakeData[i][0])==false) )
					continue;
				if((quarter!=null) && (quarter.equals(m_fakeData[i][1])==false) )
					continue;
				if((month!=null) && (month.equals(m_fakeData[i][2])==false) )
					continue;
				else if( (month==null) && (!m_fakeData[i][2].equals("")) )
					continue;					
				if((state!=null) && (state.equals(m_fakeData[i][3])==false) )
					continue;
				if((product!=null) && (product.equals(m_fakeData[i][4])==false) )
					continue;
				return (Object) Integer.valueOf(m_fakeData[i][5]);
			}			
			// if a match is not found return "-" for a value
			return new String("-");
    } catch(Throwable t){
    	t.printStackTrace();
    }
		// if an exception is thrown, then return "ERROR" for a value
    return new String("ERROR");
  }

	// internal helper method that sets an edge using the edge index and an Edge interface 
	protected void setEdge(int edgeIndex, BaseEdge edge){
		switch(edgeIndex){
			case DataDirector.PAGE_EDGE:
				setPageEdge((PageEdge)edge);
				break;
			case DataDirector.ROW_EDGE:
				setRowEdge((Edge)edge);
				break;
			case DataDirector.COLUMN_EDGE:
				setColEdge((Edge)edge);
				break;
		}		
	}

	// internal method that adds the drill icons to all of the quarter members
	protected void enableQuarterDrilling(){
		Vector members = m_quarter.getMembers();
		Member member = null;
		for(int i=0;i<members.size();i++){
			member = (Member)members.elementAt(i);
			// based on the child relations, either the quarter is drilled or drillable
			if( m_quarter.getRelatedChildMembers(member.getValue()) != null )
				member.setDrillState(new Integer(DataDirector.DRILLSTATE_IS_DRILLED));			
			else
				member.setDrillState(new Integer(DataDirector.DRILLSTATE_DRILLABLE));							
		}
	}

	// internal method that removes the drill icons to all of the quarter members
	protected void disableQuarterDrilling(){
		Vector members = m_quarter.getMembers();
		Member member = null;
		for(int i=0;i<members.size();i++){
			member = (Member)members.elementAt(i);
			// set the dimension value as not drillable
			member.setDrillState(new Integer(DataDirector.DRILLSTATE_NOT_DRILLABLE));			
		}		
	}

	// internal method that expands quarters by adding child relations for a specific quarter member.
	// The child relations are used when creating the edge tree model in the AsymmetricEdge, 
	// to figure out how to paint asymmetric layers on the same edge. If a relation is 
	// added, then the AsymmetricEdge will use those child members when it encounters the month
	// layer after the quarter layer.  If a relation does not exist, then the AsymmetricEdge
	// will not add any members when it encounters the month layer after the quarter layer, which
	// will cause the layer that is directly above the month layer to span two layers (DepthSpan=DepthSpan+1).
	protected void expandQuarter(String member){
		Vector relatedMembers = m_quarter.getRelatedChildMembers(member);
		if(relatedMembers!=null)
			return; // it is expanded already
				
		if(member.equals("Q1")){
			relatedMembers = new Vector();
			relatedMembers.addElement(m_month.getMember("JAN"));
			relatedMembers.addElement(m_month.getMember("FEB"));
			relatedMembers.addElement(m_month.getMember("MAR"));		
			m_quarter.addRelatedChildMembers("Q1",relatedMembers);				
		} else if(member.equals("Q2")){
			relatedMembers = new Vector();
			relatedMembers.addElement(m_month.getMember("APR"));
			relatedMembers.addElement(m_month.getMember("MAY"));
			relatedMembers.addElement(m_month.getMember("JUN"));		
			m_quarter.addRelatedChildMembers("Q2",relatedMembers);				
			
		} else if(member.equals("Q3")){
			relatedMembers = new Vector();
			relatedMembers.addElement(m_month.getMember("JUL"));
			relatedMembers.addElement(m_month.getMember("AUG"));
			relatedMembers.addElement(m_month.getMember("SEP"));		
			m_quarter.addRelatedChildMembers("Q3",relatedMembers);				
			
		} else if(member.equals("Q4")){
			relatedMembers = new Vector();
			relatedMembers.addElement(m_month.getMember("OCT"));
			relatedMembers.addElement(m_month.getMember("NOV"));
			relatedMembers.addElement(m_month.getMember("DEC"));		
			m_quarter.addRelatedChildMembers("Q4",relatedMembers);				
		}	
		
		m_quarter.getMember(member).setDrillState(new Integer(DataDirector.DRILLSTATE_IS_DRILLED));			
	}

	// internal method that collapses quarters by removing child relations on a specific quarter members.
	protected void collapseQuarter(String member){
		m_quarter.removeRelatedChildMembers(member);
		m_quarter.getMember(member).setDrillState(new Integer(DataDirector.DRILLSTATE_DRILLABLE));			
	}

	// internal method that gets rid of the ability to drill quarters if:
	// a. the quarter and month layer are on different edges. 
	// b. the month layer is before the quarter layer on the edge
	protected void updateQuarterMonthRelationship(Layer[] layers){
		// enable/disable drilling based on their order on the edge.  
		// If quarter is before month, then enable drilling.  If
		// month is before drilling, then expand all quarters and disable the drilling
		int quarterLayer=-1;
		int monthLayer=-1;					

		// find the layers of quarter and month
		for(int i=0;i<layers.length;i++){
			if(m_quarter == layers[i] )
				quarterLayer = i;
			else if(m_month == layers[i] )
				monthLayer = i;	
		}	

		// if both month and quarter are on the same edge				
		if( (quarterLayer!=-1) && (monthLayer!=-1) ){
			if(quarterLayer>monthLayer){
				// expand all quarter and disable drilling
				Vector members = m_quarter.getMembers();
				for(int i=0;i<members.size();i++)
					expandQuarter(((Member)members.elementAt(i)).getValue());
				disableQuarterDrilling();							
			} else {
				// enable drilling
				enableQuarterDrilling();														
			}							
		}	else if( 	((quarterLayer!=-1) && (monthLayer==-1)) ||
								((quarterLayer==-1) && (monthLayer!=-1)) ) {
			// quarter and month are on different edges, so disable quarter drilling
	
			// verify that month was not missing because all quarter values were collapsed
			boolean bFoundDrill=false;
			Vector members = m_quarter.getMembers();
			for(int i=0;i<members.size();i++){
				if(((Member)members.elementAt(i)).getDrillState().intValue() == DataDirector.DRILLSTATE_IS_DRILLED){
					disableQuarterDrilling();
					break;
				}	
			}		
		}													
	}

	///////////////////////////////////////////////////
	// Internal Properties

	protected AsymmetricLayer m_month=null;
	protected AsymmetricLayer m_quarter=null;
	protected Layer m_product=null;
	protected Layer m_channel=null;
	protected Layer m_geography=null;
    
	protected int m_rowCount = 576;
 	protected String m_fakeData[][] = {
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"AUDIO",		 	"15500"},
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"VIDEO",			"10500"},
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"GAMES",			"10700"},
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"CAMERAS",		"10050"},
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"PHONES",		"11550" },
		{"DIRECT", "Q1", "JAN","NEW ENGLAND"		,"MISC",			"11550"},
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"AUDIO",		  "5000"},
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"VIDEO",			"10000"},
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"GAMES",			"10000"},
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"CAMERAS",		"7000"},
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"PHONES",		"10500" },
		{"DIRECT", "Q1", "JAN","MASSACHUSETTS"	,"MISC",			"10500"},
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"AUDIO",			"2000" },
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"VIDEO",			"500" },
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"GAMES",			"700" },
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"CAMERAS",		"3050" },
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"PHONES",		"1050" },
		{"DIRECT", "Q1", "JAN","RHODE ISLAND"		,"MISC",			"1050"},

		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"AUDIO",		 	"7800"},
		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"VIDEO",			"14540"},
		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"GAMES",			"14740"},
		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"CAMERAS",		"10850"},
		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"PHONES",		"15950" },
		{"DIRECT", "Q1", "FEB","NEW ENGLAND"		,"MISC",			"15950"},
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"AUDIO",		  "5400"},
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"VIDEO",			"14000"},
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"GAMES",			"14000"},
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"CAMERAS",		"7400"},
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"PHONES",		"14500" },
		{"DIRECT", "Q1", "FEB","MASSACHUSETTS"	,"MISC",			"14500"},
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"AUDIO",			"2400" },
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"VIDEO",			"540" },
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"GAMES",			"740" },
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"CAMERAS",		"3450" },
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"PHONES",		"1450" },
		{"DIRECT", "Q1", "FEB","RHODE ISLAND"		,"MISC",			"1450"},

		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"AUDIO",		  "8600"},
		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"VIDEO",			"18580"},
		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"GAMES",			"18780"},
		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"CAMERAS",		"11650"},
		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"PHONES",		"20350" },
		{"DIRECT", "Q1", "MAR","NEW ENGLAND"		,"MISC",			"20350"},
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"AUDIO",		  "5800"},
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"VIDEO",			"18000"},
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"GAMES",			"18000"},
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"CAMERAS",		"7800"},
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"PHONES",		"18500" },
		{"DIRECT", "Q1", "MAR","MASSACHUSETTS"	,"MISC",			"18500"},
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"AUDIO",			"2800" },
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"VIDEO",			"580" },
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"GAMES",			"780" },
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"CAMERAS",		"3850" },
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"PHONES",		"1850" },
		{"DIRECT", "Q1", "MAR","RHODE ISLAND"		,"MISC",			"1850"},

		{"DIRECT", "Q1", "","NEW ENGLAND"		,"AUDIO",		  "31900"},
		{"DIRECT", "Q1", "","NEW ENGLAND"		,"VIDEO",			"43620"},
		{"DIRECT", "Q1", "","NEW ENGLAND"		,"GAMES",			"44220"},
		{"DIRECT", "Q1", "","NEW ENGLAND"		,"CAMERAS",		"32550"},
		{"DIRECT", "Q1", "","NEW ENGLAND"		,"PHONES",		"47850" },
		{"DIRECT", "Q1", "","NEW ENGLAND"		,"MISC",			"47850"},
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"AUDIO",		  "16200"},
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"VIDEO",			"42000"},
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"GAMES",			"42000"},
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"CAMERAS",		"22200"},
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"PHONES",		"43500" },
		{"DIRECT", "Q1", "","MASSACHUSETTS"	,"MISC",			"43500"},
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"AUDIO",			"7200" },
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"VIDEO",			"1620" },
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"GAMES",			"2220" },
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"CAMERAS",		"10350" },
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"PHONES",		"4350" },
		{"DIRECT", "Q1", "","RHODE ISLAND"		,"MISC",			"4350"},

		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"AUDIO",		 	"12200"},
		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"VIDEO",			"10200"},
		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"GAMES",			"10700"},
		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"CAMERAS",		"10020"},
		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"PHONES",		"11220" },
		{"DIRECT", "Q2", "APR","NEW ENGLAND"		,"MISC",			"11220"},
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"AUDIO",		  "2000"},
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"VIDEO",			"10000"},
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"GAMES",			"10000"},
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"CAMERAS",		"7000"},
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"PHONES",		"10200" },
		{"DIRECT", "Q2", "APR","MASSACHUSETTS"	,"MISC",			"10200"},
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"AUDIO",			"2000" },
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"VIDEO",			"200" },
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"GAMES",			"700" },
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"CAMERAS",		"3020" },
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"PHONES",		"1020" },
		{"DIRECT", "Q2", "APR","RHODE ISLAND"		,"MISC",			"1020"},

		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"AUDIO",		 	"7800"},
		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"VIDEO",			"14240"},
		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"GAMES",			"14740"},
		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"CAMERAS",		"10820"},
		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"PHONES",		"12920" },
		{"DIRECT", "Q2", "MAY","NEW ENGLAND"		,"MISC",			"12920"},
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"AUDIO",		  "2400"},
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"VIDEO",			"14000"},
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"GAMES",			"14000"},
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"CAMERAS",		"7400"},
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"PHONES",		"14200" },
		{"DIRECT", "Q2", "MAY","MASSACHUSETTS"	,"MISC",			"14200"},
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"AUDIO",			"2400" },
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"VIDEO",			"240" },
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"GAMES",			"740" },
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"CAMERAS",		"3420" },
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"PHONES",		"1420" },
		{"DIRECT", "Q2", "MAY","RHODE ISLAND"		,"MISC",			"1420"},

		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"AUDIO",		  "8600"},
		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"VIDEO",			"18280"},
		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"GAMES",			"18780"},
		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"CAMERAS",		"11620"},
		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"PHONES",		"20320" },
		{"DIRECT", "Q2", "JUN","NEW ENGLAND"		,"MISC",			"20320"},
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"AUDIO",		  "2800"},
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"VIDEO",			"18000"},
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"GAMES",			"18000"},
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"CAMERAS",		"7800"},
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"PHONES",		"18200" },
		{"DIRECT", "Q2", "JUN","MASSACHUSETTS"	,"MISC",			"18200"},
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"AUDIO",			"2800" },
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"VIDEO",			"280" },
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"GAMES",			"780" },
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"CAMERAS",		"3820" },
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"PHONES",		"1820" },
		{"DIRECT", "Q2", "JUN","RHODE ISLAND"		,"MISC",			"1820"},

		{"DIRECT", "Q2", "","NEW ENGLAND"		,"AUDIO",		  "28600"},
		{"DIRECT", "Q2", "","NEW ENGLAND"		,"VIDEO",			"42720"},
		{"DIRECT", "Q2", "","NEW ENGLAND"		,"GAMES",			"44220"},
		{"DIRECT", "Q2", "","NEW ENGLAND"		,"CAMERAS",		"32460"},
		{"DIRECT", "Q2", "","NEW ENGLAND"		,"PHONES",		"44460" },
		{"DIRECT", "Q2", "","NEW ENGLAND"		,"MISC",			"44460"},
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"AUDIO",		  "7200"},
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"VIDEO",			"42000"},
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"GAMES",			"42000"},
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"CAMERAS",		"22200"},
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"PHONES",		"42600" },
		{"DIRECT", "Q2", "","MASSACHUSETTS"	,"MISC",			"42600"},
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"AUDIO",			"7200" },
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"VIDEO",			"720" },
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"GAMES",			"2220" },
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"CAMERAS",		"10260" },
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"PHONES",		"4260" },
		{"DIRECT", "Q2", "","RHODE ISLAND"		,"MISC",			"4260"},

		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"AUDIO",		 	"15500"},
		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"VIDEO",			"10500"},
		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"GAMES",			"10700"},
		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"CAMERAS",		"10050"},
		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"PHONES",		"11550" },
		{"DIRECT", "Q3", "JUL","NEW ENGLAND"		,"MISC",			"11550"},
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"AUDIO",		  "5000"},
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"VIDEO",			"10000"},
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"GAMES",			"10000"},
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"CAMERAS",		"7000"},
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"PHONES",		"10500" },
		{"DIRECT", "Q3", "JUL","MASSACHUSETTS"	,"MISC",			"10500"},
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"AUDIO",			"2000" },
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"VIDEO",			"500" },
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"GAMES",			"700" },
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"CAMERAS",		"3050" },
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"PHONES",		"1050" },
		{"DIRECT", "Q3", "JUL","RHODE ISLAND"		,"MISC",			"1050"},

		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"AUDIO",		 	"7800"},
		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"VIDEO",			"14540"},
		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"GAMES",			"14740"},
		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"CAMERAS",		"10850"},
		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"PHONES",		"15950" },
		{"DIRECT", "Q3", "AUG","NEW ENGLAND"		,"MISC",			"15950"},
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"AUDIO",		  "5400"},
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"VIDEO",			"14000"},
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"GAMES",			"14000"},
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"CAMERAS",		"7400"},
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"PHONES",		"14500" },
		{"DIRECT", "Q3", "AUG","MASSACHUSETTS"	,"MISC",			"14500"},
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"AUDIO",			"2400" },
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"VIDEO",			"540" },
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"GAMES",			"740" },
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"CAMERAS",		"3450" },
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"PHONES",		"1450" },
		{"DIRECT", "Q3", "AUG","RHODE ISLAND"		,"MISC",			"1450"},

		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"AUDIO",		  "8600"},
		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"VIDEO",			"18580"},
		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"GAMES",			"18780"},
		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"CAMERAS",		"11650"},
		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"PHONES",		"20350" },
		{"DIRECT", "Q3", "SEP","NEW ENGLAND"		,"MISC",			"20350"},
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"AUDIO",		  "5800"},
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"VIDEO",			"18000"},
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"GAMES",			"18000"},
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"CAMERAS",		"7800"},
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"PHONES",		"18500" },
		{"DIRECT", "Q3", "SEP","MASSACHUSETTS"	,"MISC",			"18500"},
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"AUDIO",			"2800" },
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"VIDEO",			"580" },
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"GAMES",			"780" },
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"CAMERAS",		"3850" },
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"PHONES",		"1850" },
		{"DIRECT", "Q3", "SEP","RHODE ISLAND"		,"MISC",			"1850"},

		{"DIRECT", "Q3", "","NEW ENGLAND"		,"AUDIO",		  "31900"},
		{"DIRECT", "Q3", "","NEW ENGLAND"		,"VIDEO",			"43620"},
		{"DIRECT", "Q3", "","NEW ENGLAND"		,"GAMES",			"44220"},
		{"DIRECT", "Q3", "","NEW ENGLAND"		,"CAMERAS",		"32550"},
		{"DIRECT", "Q3", "","NEW ENGLAND"		,"PHONES",		"47850" },
		{"DIRECT", "Q3", "","NEW ENGLAND"		,"MISC",			"47850"},
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"AUDIO",		  "16200"},
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"VIDEO",			"42000"},
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"GAMES",			"42000"},
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"CAMERAS",		"22200"},
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"PHONES",		"43500" },
		{"DIRECT", "Q3", "","MASSACHUSETTS"	,"MISC",			"43500"},
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"AUDIO",			"7200" },
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"VIDEO",			"1620" },
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"GAMES",			"2220" },
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"CAMERAS",		"10350" },
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"PHONES",		"4350" },
		{"DIRECT", "Q3", "","RHODE ISLAND"		,"MISC",			"4350"},

		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"AUDIO",		 	"15544"},
		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"VIDEO",			"14544"},
		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"GAMES",			"14744"},
		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"CAMERAS",		"14454"},
		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"PHONES",		"11554" },
		{"DIRECT", "Q4", "OCT","NEW ENGLAND"		,"MISC",			"11554"},
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"AUDIO",		  "5444"},
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"VIDEO",			"14444"},
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"GAMES",			"14444"},
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"CAMERAS",		"7444"},
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"PHONES",		"14544" },
		{"DIRECT", "Q4", "OCT","MASSACHUSETTS"	,"MISC",			"14544"},
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"AUDIO",			"2444" },
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"VIDEO",			"544" },
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"GAMES",			"744" },
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"CAMERAS",		"3454" },
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"PHONES",		"1454" },
		{"DIRECT", "Q4", "OCT","RHODE ISLAND"		,"MISC",			"1454"},

		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"AUDIO",		 	"7844"},
		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"VIDEO",			"14544"},
		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"GAMES",			"14744"},
		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"CAMERAS",		"14854"},
		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"PHONES",		"15954" },
		{"DIRECT", "Q4", "NOV","NEW ENGLAND"		,"MISC",			"15954"},
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"AUDIO",		  "5444"},
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"VIDEO",			"14444"},
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"GAMES",			"14444"},
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"CAMERAS",		"7444"},
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"PHONES",		"14544" },
		{"DIRECT", "Q4", "NOV","MASSACHUSETTS"	,"MISC",			"14544"},
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"AUDIO",			"2444" },
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"VIDEO",			"544" },
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"GAMES",			"744" },
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"CAMERAS",		"3454" },
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"PHONES",		"1454" },
		{"DIRECT", "Q4", "NOV","RHODE ISLAND"		,"MISC",			"1454"},

		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"AUDIO",		  "8644"},
		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"VIDEO",			"18584"},
		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"GAMES",			"18784"},
		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"CAMERAS",		"11654"},
		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"PHONES",		"24354" },
		{"DIRECT", "Q4", "DEC","NEW ENGLAND"		,"MISC",			"24354"},
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"AUDIO",		  "5844"},
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"VIDEO",			"18444"},
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"GAMES",			"18444"},
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"CAMERAS",		"7844"},
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"PHONES",		"18544" },
		{"DIRECT", "Q4", "DEC","MASSACHUSETTS"	,"MISC",			"18544"},
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"AUDIO",			"2844" },
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"VIDEO",			"584" },
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"GAMES",			"784" },
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"CAMERAS",		"3854" },
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"PHONES",		"1854" },
		{"DIRECT", "Q4", "DEC","RHODE ISLAND"		,"MISC",			"1854"},

		{"DIRECT", "Q4", "","NEW ENGLAND"		,"AUDIO",		  "32032"},
		{"DIRECT", "Q4", "","NEW ENGLAND"		,"VIDEO",			"47672"},
		{"DIRECT", "Q4", "","NEW ENGLAND"		,"GAMES",			"48272"},
		{"DIRECT", "Q4", "","NEW ENGLAND"		,"CAMERAS",		"40962"},
		{"DIRECT", "Q4", "","NEW ENGLAND"		,"PHONES",		"51862" },
		{"DIRECT", "Q4", "","NEW ENGLAND"		,"MISC",			"51862"},
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"AUDIO",		  "16732"},
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"VIDEO",			"47332"},
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"GAMES",			"47332"},
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"CAMERAS",		"1672"},
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"PHONES",		"47632" },
		{"DIRECT", "Q4", "","MASSACHUSETTS"	,"MISC",			"47632"},
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"AUDIO",			"7732" },
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"VIDEO",			"1672" },
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"GAMES",			"2272" },
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"CAMERAS",		"10762" },
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"PHONES",		"4762" },
		{"DIRECT", "Q4", "","RHODE ISLAND"		,"MISC",			"4762"},

		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"AUDIO",		  "700"},
		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"VIDEO",			"1050"},
		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"GAMES",			"1070"},
		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"CAMERAS",		"1005"},
		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"PHONES",		"1155" },
		{"INDIRECT", "Q1", "JAN","NEW ENGLAND"		,"MISC",			"1105"},
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"AUDIO",		  "500"},
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"VIDEO",			"1000"},
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"GAMES",			"1000"},
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"CAMERAS",		"700"},
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"PHONES",		"1050" },
		{"INDIRECT", "Q1", "JAN","MASSACHUSETTS"	,"MISC",			"1000"},
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"AUDIO",			"200" },
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"VIDEO",			"50" },
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"GAMES",			"70" },
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"CAMERAS",		"305" },
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"PHONES",		"105" },
		{"INDIRECT", "Q1", "JAN","RHODE ISLAND"		,"MISC",			"105"},

		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"AUDIO",		  "780"},
		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"VIDEO",			"1454"},
		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"GAMES",			"1474"},
		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"CAMERAS",		"1085"},
		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"PHONES",		"1595" },
		{"INDIRECT", "Q1", "FEB","NEW ENGLAND"		,"MISC",			"1595"},
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"AUDIO",		  "540"},
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"VIDEO",			"1400"},
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"GAMES",			"1400"},
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"CAMERAS",		"740"},
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"PHONES",		"1450" },
		{"INDIRECT", "Q1", "FEB","MASSACHUSETTS"	,"MISC",			"1450"},
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"AUDIO",			"240" },
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"VIDEO",			"54" },
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"GAMES",			"74" },
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"CAMERAS",		"345" },
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"PHONES",		"145" },
		{"INDIRECT", "Q1", "FEB","RHODE ISLAND"		,"MISC",			"145"},

		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"AUDIO",		  "860"},
		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"VIDEO",			"1858"},
		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"GAMES",			"1878"},
		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"CAMERAS",		"1165"},
		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"PHONES",		"2035" },
		{"INDIRECT", "Q1", "MAR","NEW ENGLAND"		,"MISC",			"2035"},
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"AUDIO",		  "580"},
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"VIDEO",			"1800"},
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"GAMES",			"1800"},
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"CAMERAS",		"780"},
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"PHONES",		"1850" },
		{"INDIRECT", "Q1", "MAR","MASSACHUSETTS"	,"MISC",			"1850"},
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"AUDIO",			"280" },
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"VIDEO",			"58" },
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"GAMES",			"78" },
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"CAMERAS",		"385" },
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"PHONES",		"185" },
		{"INDIRECT", "Q1", "MAR","RHODE ISLAND"		,"MISC",			"185"},

		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"AUDIO",		  "2340"},
		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"VIDEO",			"4362"},
		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"GAMES",			"4422"},
		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"CAMERAS",		"3255"},
		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"PHONES",		"4785" },
		{"INDIRECT", "Q1", "","NEW ENGLAND"		,"MISC",			"4735"},
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"AUDIO",		  "1620"},
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"VIDEO",			"4200"},
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"GAMES",			"4200"},
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"CAMERAS",		"2220"},
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"PHONES",		"4350" },
		{"INDIRECT", "Q1", "","MASSACHUSETTS"	,"MISC",			"4300"},
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"AUDIO",			"720" },
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"VIDEO",			"162" },
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"GAMES",			"222" },
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"CAMERAS",		"1035" },
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"PHONES",		"435" },
		{"INDIRECT", "Q1", "","RHODE ISLAND"		,"MISC",			"435"},

		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"AUDIO",		  "777"},
		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"VIDEO",			"1757"},
		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"GAMES",			"1777"},
		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"CAMERAS",		"1775"},
		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"PHONES",		"1155" },
		{"INDIRECT", "Q2", "APR","NEW ENGLAND"		,"MISC",			"1175"},
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"AUDIO",		  "577"},
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"VIDEO",			"1777"},
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"GAMES",			"1777"},
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"CAMERAS",		"777"},
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"PHONES",		"1757" },
		{"INDIRECT", "Q2", "APR","MASSACHUSETTS"	,"MISC",			"1777"},
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"AUDIO",			"277" },
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"VIDEO",			"57" },
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"GAMES",			"77" },
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"CAMERAS",		"375" },
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"PHONES",		"175" },
		{"INDIRECT", "Q2", "APR","RHODE ISLAND"		,"MISC",			"175"},

		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"AUDIO",		  "787"},
		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"VIDEO",			"1454"},
		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"GAMES",			"1474"},
		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"CAMERAS",		"1785"},
		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"PHONES",		"1595" },
		{"INDIRECT", "Q2", "MAY","NEW ENGLAND"		,"MISC",			"1595"},
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"AUDIO",		  "547"},
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"VIDEO",			"1477"},
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"GAMES",			"1477"},
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"CAMERAS",		"747"},
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"PHONES",		"1457" },
		{"INDIRECT", "Q2", "MAY","MASSACHUSETTS"	,"MISC",			"1457"},
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"AUDIO",			"247" },
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"VIDEO",			"54" },
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"GAMES",			"74" },
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"CAMERAS",		"345" },
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"PHONES",		"145" },
		{"INDIRECT", "Q2", "MAY","RHODE ISLAND"		,"MISC",			"145"},

		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"AUDIO",		  "867"},
		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"VIDEO",			"1858"},
		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"GAMES",			"1878"},
		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"CAMERAS",		"1165"},
		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"PHONES",		"2735" },
		{"INDIRECT", "Q2", "JUN","NEW ENGLAND"		,"MISC",			"2735"},
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"AUDIO",		  "587"},
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"VIDEO",			"1877"},
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"GAMES",			"1877"},
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"CAMERAS",		"787"},
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"PHONES",		"1857" },
		{"INDIRECT", "Q2", "JUN","MASSACHUSETTS"	,"MISC",			"1857"},
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"AUDIO",			"287" },
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"VIDEO",			"58" },
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"GAMES",			"78" },
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"CAMERAS",		"385" },
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"PHONES",		"185" },
		{"INDIRECT", "Q2", "JUN","RHODE ISLAND"		,"MISC",			"185"},

		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"AUDIO",		  "2431"},
		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"VIDEO",			"5069"},
		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"GAMES",			"5129"},
		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"CAMERAS",		"4725"},
		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"PHONES",		"5485" },
		{"INDIRECT", "Q2", "","NEW ENGLAND"		,"MISC",			"5505"},
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"AUDIO",		  "1711"},
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"VIDEO",			"5131"},
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"GAMES",			"5131"},
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"CAMERAS",		"505"},
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"PHONES",		"5071" },
		{"INDIRECT", "Q2", "","MASSACHUSETTS"	,"MISC",			"5091"},
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"AUDIO",			"811" },
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"VIDEO",			"169" },
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"GAMES",			"229" },
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"CAMERAS",		"1105" },
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"PHONES",		"505" },
		{"INDIRECT", "Q2", "","RHODE ISLAND"		,"MISC",			"505"},

		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"AUDIO",		  "700"},
		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"VIDEO",			"1050"},
		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"GAMES",			"1070"},
		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"CAMERAS",		"1005"},
		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"PHONES",		"1155" },
		{"INDIRECT", "Q3", "JUL","NEW ENGLAND"		,"MISC",			"1105"},
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"AUDIO",		  "500"},
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"VIDEO",			"1000"},
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"GAMES",			"1000"},
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"CAMERAS",		"700"},
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"PHONES",		"1050" },
		{"INDIRECT", "Q3", "JUL","MASSACHUSETTS"	,"MISC",			"1000"},
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"AUDIO",			"200" },
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"VIDEO",			"50" },
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"GAMES",			"70" },
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"CAMERAS",		"305" },
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"PHONES",		"105" },
		{"INDIRECT", "Q3", "JUL","RHODE ISLAND"		,"MISC",			"105"},

		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"AUDIO",		  "780"},
		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"VIDEO",			"1454"},
		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"GAMES",			"1474"},
		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"CAMERAS",		"1085"},
		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"PHONES",		"1595" },
		{"INDIRECT", "Q3", "AUG","NEW ENGLAND"		,"MISC",			"1595"},
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"AUDIO",		  "540"},
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"VIDEO",			"1400"},
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"GAMES",			"1400"},
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"CAMERAS",		"740"},
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"PHONES",		"1450" },
		{"INDIRECT", "Q3", "AUG","MASSACHUSETTS"	,"MISC",			"1450"},
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"AUDIO",			"240" },
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"VIDEO",			"54" },
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"GAMES",			"74" },
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"CAMERAS",		"345" },
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"PHONES",		"145" },
		{"INDIRECT", "Q3", "AUG","RHODE ISLAND"		,"MISC",			"145"},

		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"AUDIO",		  "860"},
		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"VIDEO",			"1858"},
		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"GAMES",			"1878"},
		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"CAMERAS",		"1165"},
		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"PHONES",		"2035" },
		{"INDIRECT", "Q3", "SEP","NEW ENGLAND"		,"MISC",			"2035"},
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"AUDIO",		  "580"},
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"VIDEO",			"1800"},
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"GAMES",			"1800"},
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"CAMERAS",		"780"},
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"PHONES",		"1850" },
		{"INDIRECT", "Q3", "SEP","MASSACHUSETTS"	,"MISC",			"1850"},
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"AUDIO",			"280" },
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"VIDEO",			"58" },
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"GAMES",			"78" },
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"CAMERAS",		"385" },
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"PHONES",		"185" },
		{"INDIRECT", "Q3", "SEP","RHODE ISLAND"		,"MISC",			"185"},

		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"AUDIO",		  "2340"},
		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"VIDEO",			"4362"},
		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"GAMES",			"4422"},
		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"CAMERAS",		"3255"},
		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"PHONES",		"4785" },
		{"INDIRECT", "Q3", "","NEW ENGLAND"		,"MISC",			"4735"},
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"AUDIO",		  "1620"},
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"VIDEO",			"4200"},
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"GAMES",			"4200"},
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"CAMERAS",		"2220"},
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"PHONES",		"4350" },
		{"INDIRECT", "Q3", "","MASSACHUSETTS"	,"MISC",			"4300"},
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"AUDIO",			"720" },
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"VIDEO",			"162" },
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"GAMES",			"222" },
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"CAMERAS",		"1035" },
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"PHONES",		"435" },
		{"INDIRECT", "Q3", "","RHODE ISLAND"		,"MISC",			"435"},

		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"AUDIO",		  "722"},
		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"VIDEO",			"1252"},
		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"GAMES",			"1272"},
		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"CAMERAS",		"1225"},
		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"PHONES",		"1155" },
		{"INDIRECT", "Q4", "OCT","NEW ENGLAND"		,"MISC",			"1125"},
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"AUDIO",		  "522"},
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"VIDEO",			"1222"},
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"GAMES",			"1222"},
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"CAMERAS",		"722"},
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"PHONES",		"1252" },
		{"INDIRECT", "Q4", "OCT","MASSACHUSETTS"	,"MISC",			"1222"},
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"AUDIO",			"222" },
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"VIDEO",			"52" },
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"GAMES",			"72" },
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"CAMERAS",		"325" },
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"PHONES",		"125" },
		{"INDIRECT", "Q4", "OCT","RHODE ISLAND"		,"MISC",			"125"},

		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"AUDIO",		  "782"},
		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"VIDEO",			"1454"},
		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"GAMES",			"1474"},
		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"CAMERAS",		"1285"},
		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"PHONES",		"1595" },
		{"INDIRECT", "Q4", "NOV","NEW ENGLAND"		,"MISC",			"1595"},
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"AUDIO",		  "542"},
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"VIDEO",			"1422"},
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"GAMES",			"1422"},
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"CAMERAS",		"742"},
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"PHONES",		"1452" },
		{"INDIRECT", "Q4", "NOV","MASSACHUSETTS"	,"MISC",			"1452"},
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"AUDIO",			"242" },
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"VIDEO",			"54" },
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"GAMES",			"74" },
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"CAMERAS",		"345" },
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"PHONES",		"145" },
		{"INDIRECT", "Q4", "NOV","RHODE ISLAND"		,"MISC",			"145"},

		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"AUDIO",		  "862"},
		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"VIDEO",			"1858"},
		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"GAMES",			"1878"},
		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"CAMERAS",		"1165"},
		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"PHONES",		"2235" },
		{"INDIRECT", "Q4", "DEC","NEW ENGLAND"		,"MISC",			"2235"},
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"AUDIO",		  "582"},
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"VIDEO",			"1822"},
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"GAMES",			"1822"},
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"CAMERAS",		"782"},
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"PHONES",		"1852" },
		{"INDIRECT", "Q4", "DEC","MASSACHUSETTS"	,"MISC",			"1852"},
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"AUDIO",			"282" },
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"VIDEO",			"58" },
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"GAMES",			"78" },
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"CAMERAS",		"385" },
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"PHONES",		"185" },
		{"INDIRECT", "Q4", "DEC","RHODE ISLAND"		,"MISC",			"185"},

		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"AUDIO",		  "2366"},
		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"VIDEO",			"4564"},
		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"GAMES",			"4624"},
		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"CAMERAS",		"3675"},
		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"PHONES",		"4985" },
		{"INDIRECT", "Q4", "","NEW ENGLAND"		,"MISC",			"4955"},
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"AUDIO",		  "1646"},
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"VIDEO",			"4466"},
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"GAMES",			"4466"},
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"CAMERAS",		"4526"},
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"PHONES",		"4526" },
		{"INDIRECT", "Q4", "","MASSACHUSETTS"	,"MISC",			"4526"},
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"AUDIO",			"746" },
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"VIDEO",			"164" },
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"GAMES",			"224" },
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"CAMERAS",		"1055" },
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"PHONES",		"455" },
		{"INDIRECT", "Q4", "","RHODE ISLAND"		,"MISC",			"455"}
			
	};	
    
   
        	
}
