/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.data.RichCarousel;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.CarouselSpinEvent;

import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.component.visit.VisitTreeUtils;

/**
 * Managed bean for data associated with the carousel.
 */
public class CarouselBean implements Serializable
{
  /**
   * Default constructor.
   */
  public CarouselBean()
  {
    _imageList = new ArrayList<ImageInfo>();

    _createImageInfo("breadCrumbs");
    _createImageInfo("calendar");
    _createImageInfo("carousel");
    _createImageInfo("carouselItem");
    _createImageInfo("chooseColor");
    _createImageInfo("chooseDate");
    _createImageInfo("commandButton");
    _createImageInfo("commandImageLink");
    _createImageInfo("commandLink");
    _createImageInfo("commandMenuItem");
    _createImageInfo("commandNavigationItem");
    _createImageInfo("commandToolbarButton");
    _createImageInfo("decorativeBox");
    _createImageInfo("dialog");
    _createImageInfo("goButton");
    _createImageInfo("goLink");
    _createImageInfo("goMenuItem");
    _createImageInfo("group");
    _createImageInfo("icon");
    _createImageInfo("image");
    _createImageInfo("inlineFrame");
    _createImageInfo("inputColor");
    _createImageInfo("inputComboboxListOfValues");
    _createImageInfo("inputDate");
    _createImageInfo("inputFile");
    _createImageInfo("inputListOfValues");
    _createImageInfo("inputNumberSlider");
    _createImageInfo("inputNumberSpinbox");
    _createImageInfo("inputRangeSlider");
    _createImageInfo("inputText");
    _createImageInfo("iterator");
    _createImageInfo("media");
    _createImageInfo("menu");
    _createImageInfo("menuBar");
    _createImageInfo("message");
    _createImageInfo("messages");
    _createImageInfo("navigationPane");
    _createImageInfo("noteWindow");
    _createImageInfo("outputFormatted");
    _createImageInfo("outputLabel");
    _createImageInfo("outputText");
    _createImageInfo("pageTemplate");
    _createImageInfo("panelAccordion");
    _createImageInfo("panelBorderLayout");
    _createImageInfo("panelBox");
    _createImageInfo("panelCollection");
    _createImageInfo("panelDashboard");
    _createImageInfo("panelFormLayout");
    _createImageInfo("panelGroupLayout");
    _createImageInfo("panelHeader");
    _createImageInfo("panelLabelAndMessage");
    _createImageInfo("panelList");
    _createImageInfo("panelSplitter");
    _createImageInfo("panelStretchLayout");
    _createImageInfo("panelTabbed");
    _createImageInfo("panelWindow");
    _createImageInfo("popup");
    _createImageInfo("progressIndicator");
    _createImageInfo("query");
    _createImageInfo("queryCriteria");
    _createImageInfo("quickQuery");
    _createImageInfo("region");
    _createImageInfo("resetButton");
    _createImageInfo("richTextEditor");
    _createImageInfo("selectBooleanCheckbox");
    _createImageInfo("selectBooleanRadio");
    _createImageInfo("selectItem");
    _createImageInfo("selectManyCheckbox");
    _createImageInfo("selectManyChoice");
    _createImageInfo("selectManyListbox");
    _createImageInfo("selectManyShuttle");
    _createImageInfo("selectOneChoice");
    _createImageInfo("selectOneListbox");
    _createImageInfo("selectOneRadio");
    _createImageInfo("selectOrderShuttle");
    _createImageInfo("separator");
    _createImageInfo("showDetail");
    _createImageInfo("showDetailHeader");
    _createImageInfo("showDetailItem");
    _createImageInfo("spacer");
    _createImageInfo("statusIndicator");
    _createImageInfo("subform");
    _createImageInfo("switcher");
    _createImageInfo("table");
    _createImageInfo("toolbar");
    _createImageInfo("toolbox");
    _createImageInfo("train");
    _createImageInfo("trainButtonBar");
    _createImageInfo("tree");
    _createImageInfo("treeTable");
  }

  public void handleCarouselSpin(CarouselSpinEvent event)
  {
    RichCarousel carousel = (RichCarousel)event.getComponent();
    carousel.setRowKey(event.getNewItemKey());
    ImageInfo itemData = (ImageInfo)carousel.getRowData();
    _currentImageInfo = itemData;
  }

  /**
   * Extract the details from the dropped component.
   * @param dropEvent the DropEvent
   * @return the details about the dropped component where index 0 is the text and 1 is the source
   */
  private String[] _getDropDetails(DropEvent dropEvent)
  {
    UIComponent movedComponent = dropEvent.getDragComponent();

    DraggedComponentVisitResult callback = new DraggedComponentVisitResult();
    VisitTreeUtils.visitSingleComponent(
      FacesContext.getCurrentInstance(),
      movedComponent.getClientId(),
      callback);

    UIComponent dropComponent = dropEvent.getDropComponent();

    // PPR-update the container for the drop well so the new image source and text are displayed:
    AdfFacesContext.getCurrentInstance().addPartialTarget(
      dropComponent.getParent().getParent());

    return callback.getDraggedTextAndSource();
  }

  private class DraggedComponentVisitResult implements VisitCallback
  {
    public String[] getDraggedTextAndSource()
    {
      return _draggedTextAndSource;
    }

    public VisitResult visit(VisitContext vc, UIComponent target)
    {
      RichImage movedImage     = (RichImage)target;
      String    imageId        = movedImage.getId();
      String    text           = null; // image short desc
      String    source         = null; // image source
      String    carouselBeanEl = null;
      if ("mainImg".equals(imageId))
      {
        carouselBeanEl = "#{visualDesignMainCarouselItem}";
      }
      else if ("auxImg".equals(imageId))
      {
        carouselBeanEl = "#{visualDesignAuxCarouselItem}";
      }
      else
      {
        System.err.println("Unknown carousel image ID: " + imageId);
      }

      if (carouselBeanEl != null)
      {
        try
        {
          FacesContext fc = vc.getFacesContext();
          CarouselBean carouselBean = fc.getApplication().evaluateExpressionGet(fc, carouselBeanEl, CarouselBean.class);
          ImageInfo imageInfo = carouselBean.getCurrentImageInfo();
          text   = imageInfo.getTitle();
          source = imageInfo.getUrl();
        }
        catch (Exception e)
        {
          System.err.println("Unable to compute the details of the selected carousel image " + imageId);
          e.printStackTrace();
        }
      }

      _draggedTextAndSource = new String[] { text, source };
      return VisitResult.COMPLETE;
    }

    private String[] _draggedTextAndSource;
  }

  /**
   * Handle the drop event for drop well A.
   * @param dropEvent the DropEvent
   * @return the DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleDropA(DropEvent dropEvent)
  {
    String[] dropDetails = _getDropDetails(dropEvent);
    _dropTitleA          = dropDetails[0];
    _sourceA             = dropDetails[1];

    // Return DnDAction.NONE because we already registered something else to be PPR-ed:
    return DnDAction.NONE;
  }

  /**
   * Handle the drop event for drop well B.
   * @param dropEvent the DropEvent
   * @return the DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleDropB(DropEvent dropEvent)
  {
    String[] dropDetails = _getDropDetails(dropEvent);
    _dropTitleB          = dropDetails[0];
    _sourceB             = dropDetails[1];

    // Return DnDAction.NONE because we already registered something else to be PPR-ed:
    return DnDAction.NONE;
  }

  /**
   * Handle the drop event for drop well C.
   * @param dropEvent the DropEvent
   * @return the DnDAction performed or DnDAction.NONE, if drop is cancelled
   */
  public DnDAction handleDropC(DropEvent dropEvent)
  {
    String[] dropDetails = _getDropDetails(dropEvent);
    _dropTitleC          = dropDetails[0];
    _sourceC             = dropDetails[1];

    // Return DnDAction.NONE because we already registered something else to be PPR-ed:
    return DnDAction.NONE;
  }

  public String getSourceA()
  {
    return _sourceA;
  }

  public String getDropTitleA()
  {
    return _dropTitleA;
  }

  public String getSourceB()
  {
    return _sourceB;
  }

  public String getDropTitleB()
  {
    return _dropTitleB;
  }

  public String getSourceC()
  {
    return _sourceC;
  }

  public String getDropTitleC()
  {
    return _dropTitleC;
  }

  public List<ImageInfo> getItems()
  {
    return _imageList;
  }

  public ImageInfo getCurrentImageInfo()
  {
    if (_currentImageInfo == null)
    {
      _currentImageInfo = _imageList.get(0);
    }
    return _currentImageInfo;
  }

  private void _createImageInfo(String tagName)
  {
    ImageInfo imageInfo = new ImageInfo(tagName);

    // Add to the list of image URLs:
    _imageList.add(imageInfo);
  }

  public static class ImageInfo implements Serializable
  {
    ImageInfo(String tagName)
    {
      _destination = "/components/" + tagName + ".jspx";
      _title       = tagName;
      _url         = "/images/carousel/" + tagName + ".jpg";
    }

    public String getDestination()
    {
      return _destination;
    }

    public String getTitle()
    {
      return _title;
    }

    public String getUrl()
    {
      return _url;
    }

    private String _destination;
    private String _title;
    private String _url;

    @SuppressWarnings("compatibility:-8251923220419450485")
    private static final long serialVersionUID = 1L;
  }

  private List<ImageInfo> _imageList;
  private ImageInfo       _currentImageInfo;
  private String          _sourceA = "/afr/t.gif";
  private String          _sourceB = _sourceA;
  private String          _sourceC = _sourceA;
  private String          _dropTitleA = "Drop Zone";
  private String          _dropTitleB = _dropTitleA;
  private String          _dropTitleC = _dropTitleA;

  @SuppressWarnings("compatibility:-5512232324674875399")
  private static final long serialVersionUID = 1L;
}
