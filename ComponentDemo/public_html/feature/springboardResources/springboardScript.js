
function setObjectNavigatorMode_Springboard(actionEvent)
{
  actionEvent.cancel();
  var eventSource = actionEvent.getSource();
  var object_navigator = eventSource.findComponent("object-navigator");
  // when you change the display mode a springboardChangeEvent is sent, 
  // so the false params say don't persist/propagate this change to the server
  object_navigator.setProperty(AdfRichPanelSpringboard.DISPLAY_MODE, "grid", false, false);
}

 function setObjectNavigatorMode_Iconstrip(actionEvent)
{
  actionEvent.cancel();
  var eventSource = actionEvent.getSource();
  var object_navigator = eventSource.findComponent("object-navigator");
  // when you change the display mode a springboardChangeEvent is sent,
  // so the false params say don't persist/propagate this change to the server
  object_navigator.setProperty(AdfRichPanelSpringboard.DISPLAY_MODE, "strip", false, false);
}

function objectNavigatorPropertyChangeListener(event)
{
    
    var agent = AdfAgent.AGENT;
    var nav = event.getSource();
    var announcements=agent.getElementById("announcements-container");
    var navigation=agent.getElementById("navigation-container");
    //var logo=nav.findComponent(":logo");
    var logo=agent.getElementById('logo');
    var display_mode=nav.getDisplayMode();
    
    //console.log("ann: "+announcements);
    //console.log("nav: "+navigation);
    //console.log("logo: "+logo);
    
    if(display_mode=="strip")
    {
        AdfDomUtils.removeCSSClassName(announcements, "announcements-grid-mode-size");
        AdfDomUtils.removeCSSClassName(navigation, "springboard-grid-mode-size");
        AdfDomUtils.removeCSSClassName(logo, "logo-grid-mode-size");
        
        AdfDomUtils.addCSSClassName(announcements, "announcements-strip-mode-size");
        AdfDomUtils.addCSSClassName(navigation, "springboard-strip-mode-size");
        AdfDomUtils.addCSSClassName(logo, "logo-strip-mode-size");
    }
    else
    {   
        AdfDomUtils.removeCSSClassName(announcements, "announcements-strip-mode-size");
        AdfDomUtils.removeCSSClassName(navigation, "springboard-strip-mode-size");
        AdfDomUtils.removeCSSClassName(logo, "logo-strip-mode-size");
        
        AdfDomUtils.addCSSClassName(announcements, "announcements-grid-mode-size");
        AdfDomUtils.addCSSClassName(navigation, "springboard-grid-mode-size");
        AdfDomUtils.addCSSClassName(logo, "logo-grid-mode-size");
        
    }
    
    //console.log("strip mode: " + strip_mode);
}        

