function MyConveyorBelt(containerId, startScrollCSSClassName, endScrollCSSClassName)
{
this.Init(containerId, startScrollCSSClassName, endScrollCSSClassName);
}
AdfObject.createSubclass(MyConveyorBelt, AdfObject);

MyConveyorBelt._START_OVERFLOW_SUBID = "ssb"; 
MyConveyorBelt._END_OVERFLOW_SUBID = "esb"; 
MyConveyorBelt._ANIMATION_TIME = 300;

MyConveyorBelt.prototype.Init = function (containerId, startScrollCSSClassName, endScrollCSSClassName) 
{
  MyConveyorBelt.superclass.Init.call(this);

  AdfAssert.assertString(containerId);
  AdfAssert.assertString(startScrollCSSClassName);
  AdfAssert.assertString(endScrollCSSClassName);

  this._containerId = containerId;
  this._scrollButtonCSSClassNameList = [startScrollCSSClassName, endScrollCSSClassName];
  this._scrollButtonIdList = [AdfRichUIPeer.CreateSubId(containerId, MyConveyorBelt._START_OVERFLOW_SUBID), 
                            AdfRichUIPeer.CreateSubId(containerId, MyConveyorBelt._END_OVERFLOW_SUBID)];
  this._clickHandlerCallback = this.createCallback(this._handleClick);
  this._focusHandlerCallback = this.createCallback(this._handleFocus);
  this._blurHandlerCallback = this.createCallback(this._handleBlur);
  this._animateCompleteCallback = this.createCallback(this._animateComplete);
  this._resizeCallback = this.createCallback(this._resize);
  
}

MyConveyorBelt.prototype.initialize = function () 
{
  var agent = AdfAgent.AGENT;
  var scrollButtonCSSClassNameList = this._scrollButtonCSSClassNameList;
  var scrollButtonIdList = this._scrollButtonIdList;
  var containerId = this._containerId;
  var clickCallback = this._clickHandlerCallback;
  var focusCallback = this._focusHandlerCallback;
  var blurCallback = this._blurHandlerCallback;
  var resizeCallback = this._resizeCallback;
 
  var containerDom = agent.getElementById(containerId);
  containerDom.style.overflow = "hidden";

  var containerOffsetWidth = containerDom.offsetWidth;
  var containerOffsetHeight = containerDom.offsetHeight;

  for (var i = 0; i < scrollButtonIdList.length; i++)
  {
    var buttonDom = agent.getElementById(scrollButtonIdList[i]);
    if (!buttonDom) 
    {
      buttonDom = document.createElement("DIV");
      buttonDom.setAttribute("id", scrollButtonIdList[i]);
      buttonDom.style.visibility = "hidden";
  
      MyConveyorBelt.addCSSClassName(buttonDom, scrollButtonCSSClassNameList[i]);
  
      containerDom.appendChild(buttonDom);
      agent.elementsAdded(buttonDom);

      var scrollButtonOffsetHeight = buttonDom.offsetHeight;
      buttonDom.style.top =  (containerOffsetHeight / 2) - (scrollButtonOffsetHeight / 2) + "px";
    
      var scrollButtonOffsetWidth = buttonDom.offsetWidth;
      buttonDom.style.left = (i == 0) ? "0px" : (containerOffsetWidth - scrollButtonOffsetWidth) + "px"; 
      buttonDom.style.visibility = "";
    }
  }

  agent.addBubbleEventListener(containerDom, "click", clickCallback);
  if (agent.getPlatform() == AdfAgent.IE_PLATFORM)
  {
    agent.addBubbleEventListener(containerDom, "focusin", focusCallback);
    agent.addBubbleEventListener(containerDom, "focusout", blurCallback);
  }
  else
  {
    // add capture listeners
    containerDom.addEventListener("focus", focusCallback, true);
    containerDom.addEventListener("blur", blurCallback, true);
  }

  agent.addBubbleEventListener(agent.getDomWindow(), "resize", resizeCallback);
  agent.addBubbleEventListener(containerDom, "animationend", resizeCallback);
  this._validate(containerDom);
}

MyConveyorBelt.prototype.dispose = function() 
{
  var agent = AdfAgent.AGENT;
  var isIE = agent.getPlatform() == AdfAgent.IE_PLATFORM;
  var scrollButtonIdList = this._scrollButtonIdList;
  var clickCallback = this._clickHandlerCallback;
  var focusCallback = this._focusHandlerCallback;
  var blurCallback = this._blurHandlerCallback;
  var resizeCallback = this._resizeCallback;  
  var containerId = this._containerId;
  
  var containerDom = agent.getElementById(containerId);
  if (containerDom) 
  {
    agent.removeBubbleEventListener(containerDom, "click", clickCallback);
    agent.removeBubbleEventListener(containerDom, "animationend", resizeCallback);
    agent.removeBubbleEventListener(agent.getDomWindow(), "resize", resizeCallback);
    
    if (isIE)
    {
      agent.removeBubbleEventListener(containerDom, "focusin", focusCallback);
      agent.removeBubbleEventListener(containerDom, "focusout", blurCallback);
    }
    else
    {
      containerDom.removeEventListener("focus", focusCallback, true);
      containerDom.removeEventListener("blur", blurCallback, true);
    }
  }
  
  for (var i = 0; i < scrollButtonIdList.length; i++)
  {
    var buttonDom = agent.getElementById(scrollButtonIdList[i]);
    if (buttonDom) 
    {
      agent.elementsRemoved(buttonDom);
      buttonDom.parentNode.removeChild(buttonDom);
    }
  }   
  
  delete this._containerId;
  delete this._scrollButtonCSSClassNameList;
  delete this._scrollButtonIdList;
  delete this._clickHandlerCallback;
  delete this._focusHandlerCallback;
  delete this._blurHandlerCallback;
  delete this._animateCompleteCallback;
  delete this._resizeCallback;
  delete this._animateCompleteCallback;
}

MyConveyorBelt.prototype._resize = function() {
  var agent = AdfAgent.AGENT;
  var scrollButtonIdList = this._scrollButtonIdList;
  
  var containerId = this._containerId;
  var containerDom = agent.getElementById(containerId);
  if (!containerDom)
    return;
    
  var containerOffsetWidth = containerDom.offsetWidth;

  for (var i = 0; i < scrollButtonIdList.length; i++)
  {
    var buttonDom = agent.getElementById(scrollButtonIdList[i]);
    if (!buttonDom)
    {
      // Not yet initialized?
      return;
    }
    var scrollButtonOffsetWidth = buttonDom.offsetWidth;
    buttonDom.style.left = (i == 0) ? "0px" : (containerOffsetWidth - scrollButtonOffsetWidth) + "px"; 
  } 
  
  this._validate(containerDom);
}

MyConveyorBelt.prototype._validate = function (containerDom)
{
  var agent = AdfAgent.AGENT;
  var isIE = agent.getPlatform() == AdfAgent.IE_PLATFORM;
  var scrollButtonIdList = this._scrollButtonIdList;

  var containerOffsetWidth = containerDom.offsetWidth;
  var containerScrollLeft = containerDom.scrollLeft;
  var containerScrollWidth = containerDom.scrollWidth - (isIE ? 1 : 0);

  for (var i = 0; i < scrollButtonIdList.length; i++)
  {
    var buttonDom = agent.getElementById(scrollButtonIdList[i]);
    if (i == 0)
      MyConveyorBelt.setVisible(buttonDom,  containerScrollLeft > 0);
    else
      MyConveyorBelt.setVisible(buttonDom, (containerScrollLeft + containerOffsetWidth) < containerScrollWidth);
  }
}

MyConveyorBelt.prototype._handleClick = function(evt)
{
  var agent = AdfAgent.AGENT;
  var scrollButtonIdList = this._scrollButtonIdList;

  var target = agent.getEventTarget(evt);
  var id = target.getAttribute("id");
  var i = AdfCollections.indexOf(scrollButtonIdList, id);
  if (i == -1)
    return;

  var animateCompleteCallback = this._animateCompleteCallback;
  var containerId = this._containerId;
  var containerDom = agent.getElementById(containerId);
  var containerOffsetWidth = containerDom.offsetWidth;
  var containerScrollLeft = containerDom.scrollLeft;
  var containerScrollWidth = containerDom.scrollWidth;

  var finalScrollLeft;
  if (i == 0)
    finalScrollLeft = Math.max(0, containerScrollLeft - containerOffsetWidth);
  else
    finalScrollLeft = Math.min(containerScrollWidth - containerOffsetWidth, containerScrollLeft + containerOffsetWidth);
              
  this._animating = true;
  var properties = {"scrollLeft": finalScrollLeft};
  MyAnimator.animate(MyAnimator.FRAME_METHOD_CONSTANT_SPEED,
            MyConveyorBelt._ANIMATION_TIME,
            [{"element": containerDom, "properties" :properties}],
            null,
            animateCompleteCallback, 
            containerDom);
}

MyConveyorBelt.prototype._animateComplete = function(containerDom) 
{
  delete this._animating;
  this._validate(containerDom);
}

MyConveyorBelt.prototype._handleBlur = function(evt)
{
  if (this._animating)
    return;

  var agent = AdfAgent.AGENT;
  var containerId = this._containerId;
  var containerDom = agent.getElementById(containerId);
  this._validate(containerDom);
}

MyConveyorBelt.prototype._handleFocus = function(evt)
{
  if (this._animating)
    return;
  
  var agent = AdfAgent.AGENT;
  var scrollButtonIdList = this._scrollButtonIdList;
  var containerId = this._containerId;
  var containerDom = agent.getElementById(containerId);

  var target = agent.getEventTarget(evt);
  var id = target.getAttribute("id");

  var i = AdfCollections.indexOf(scrollButtonIdList, id);
  if (i > -1 || !MyConveyorBelt.isAncestor(containerDom, target))
  {
    return;
  }

  var targetPos = agent.getElementPageBounds(target);

  for (var i = 0; i < scrollButtonIdList.length; i++)
  {
    var buttonDom = agent.getElementById(scrollButtonIdList[i]);
    var buttonPos = agent.getElementPageBounds(buttonDom);

    if (buttonPos["left"] >= targetPos["left"] && buttonPos["left"] <= targetPos["right"] ||
        buttonPos["right"] >= targetPos["left"] && buttonPos["right"] <= targetPos["right"]) 
    {
      MyConveyorBelt.setVisible(buttonDom, false);
      break;
    }
  }
}

MyConveyorBelt.setVisible = function(domElement, visible)
{
  AdfAssert.assert(domElement != null);
  domElement.style.display = (visible ? "" : "none");
}

MyConveyorBelt.isAncestor = function(ancestorNode, node)
{
  var parentNode = node.parentNode;

  while (parentNode)
  {
    if (parentNode == ancestorNode)
      return true;

    parentNode = parentNode.parentNode;
  }

  return false;
}

MyConveyorBelt.addCSSClassName = function(domElement, className)
{
  AdfAssert.assertDomElement(domElement);
  var added = false;
  if (className != null)
  {
    AdfAssert.assertString(className);
    var currentClassName = domElement.className;
    var newClassName = (currentClassName) ? className + " " + currentClassName: className;
    domElement.className = newClassName;
    added = true;
  }
  return added;
}

/**
 * A utility that simplifies the work needed to perform DOM element animations.
 * <code>
 *   <dl>
 *     <dt><strong>Example: </strong></dt>
 *     <dd>
 *     <pre>MyAnimator.animate(<br>  MyAnimator.FRAME_METHOD_SLOW_FAST_SLOW,<br>  500,<br>  [<br>    {<br>      "element": document.getElementById("div1"),<br>      "properties":<br>      {<br>        "width": 100,<br>        "height": 200,<br>        "alpha": 0<br>      }<br>    },<br>    {<br>      "element": document.getElementById("div2"),<br>      "properties":<br>      {<br>        "width": 200,<br>        "height": 100,<br>        "alpha": 100<br>      }<br>    }<br>  ],<br>  animationFrameRenderedFunction,<br>  animationCompleteFunction,<br>  callbackParameters,<br>  component);</pre>
 *     </dd>
 *   </dl>
 * </code>
 * @constructor {private} Use <code>MyAnimator.animate()</code> to start an animation
 *                        and get its instance.
 * @see #animate
 */
function MyAnimator(
  itemState,
  duringAnimate,
  afterAnimate,
  callbackParameters,
  component,
  frameMethod,
  frameCount)
{
  this.Init(itemState, duringAnimate,  afterAnimate, callbackParameters,
            component, frameMethod, frameCount);
}

// make MyAnimator a subclass of AdfObject
AdfObject.createSubclass(MyAnimator);

/**
 * Initialize the MyAnimator.
 * Use <code>MyAnimator.animate()</code> to start an animation and get its instance.
 * @param {Array} itemState an Array of internal details for the animation
 * @param {Function} duringAnimate the function to be executed at each processed animation frame (if
 *                                 a frame is skipped, the function won't be called); you may
 *                                 specify null if no execution is needed
 * @param {Function} afterAnimate the function to be executed after animation is complete
 *                                or null if no execution is needed
 * @param {Object} callbackParameters an optional object containing key-value pairs that will be
 *                                    passed to the duringAnimate and the afterAnimate function if
 *                                    applicable
 * @param {AdfUIComponent} component the component being animated; used to ensure descendant resize
 *                                   notifications get invoked after animations are complete
 * @param {Object} the <code>MyAnimator.FRAME_METHOD_*</code> choice for
 *                             how frames are spaced on the timeline
 * @param {Number} the number of frames that this animation has to render
 * @see #animate
 */
MyAnimator.prototype.Init = function(
  itemState,
  duringAnimate,
  afterAnimate,
  callbackParameters,
  component,
  frameMethod,
  frameCount)
{
  MyAnimator.superclass.Init.call(this);

  this._itemState = itemState;
  this._duringAnimate = duringAnimate;
  this._afterAnimate = afterAnimate;
  this._callbackParameters = callbackParameters;
  this._component = component;
  this._startTime = (new Date()).getTime();
  this._frameMethod = frameMethod;
  this._frameCount = frameCount;
  //this._intervalID = undefined;
}

/**
 * Stops the animation from rendering any future frames.
 * Stopping will not invoke any more during or after animate functions associated with the animator.
 * @return {Object} the callback parameters originally passed into the animator (if provided)
 */
MyAnimator.prototype.stop = function()
{
  this._stopped = true;
  window.clearInterval(this._intervalID);
  var callbackParameters = this._callbackParameters;
  this._destroy();
  return callbackParameters;
}

/**
 * Runs the described animation.
 * @param {Object} frameMethod the <code>MyAnimator.FRAME_METHOD_*</code> choice for
 *                             how frames are spaced on the timeline
 * @param {Number} timeLength milliseconds for how long the animation will last
 * @param {Array<Object>} items an array of animation item <code>Object</code>s whose properties are:
 *                      <dl>
 *                        <dt>"element"</dt>
 *                        <dd>the element to animate</dd>
 *                        <dt>"properties"</dt>
 *                        <dd>an <code>Object</code> listing the element's properties in the desired
 *                          final state (no initial state is necessary since the element already has
 *                          such properties defined on it), valid property names are:
 *                          <dl>
 *                            <dt>"width"</dt>
 *                            <dd>a non-negative integer representing the number of pixels wide the
 *                                element is</dd>
 *                            <dt>"height"</dt>
 *                            <dd>a non-negative integer representing the number of pixels wide the
 *                                element is</dd>
 *                            <dt>"top"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                AdfAgent.AGENT.getElementTop(element) returns</dd>
 *                            <dt>"left"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                AdfAgent.AGENT.getElementLeft(element) returns</dd>
 *                            <dt>"offsetTop"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                element.offsetTop returns</dd>
 *                            <dt>"offsetLeft"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                element.offsetLeft returns</dd>
 *                            <dt>"scrollTop"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                element.scrollTop returns</dd>
 *                            <dt>"scrollLeft"</dt>
 *                            <dd>an integer representing the number of pixels that
 *                                element.scrollLeft returns</dd>
 *                            <dt>"alpha"</dt>
 *                            <dd>an integer between 0 and 100 (inclusive) where representing how
 *                                opaque the element is where a value of 0 means the element is
 *                                completely transparent and a value of 100 means the element is
 *                                completely opaque</dd>
 *                            <dt>"zIndex"</dt>
 *                            <dd>an integer representing a z-axis location in the positioned
 *                                element's stacking order</dd>
 *                          </dl>
 *                        </dd>
 *                      </dl>
 * @param {Function} duringAnimate the function to be executed at each processed animation frame (if
 *                                 a frame is skipped, the function won't be called); you may
 *                                 specify null if no execution is needed
 * @param {Function} afterAnimate the function to be executed after animation is complete
 *                                or null if no execution is needed
 * @param {Object} callbackParameters an optional object containing key-value pairs that will be
 *                                    passed to the duringAnimate and the afterAnimate function if
 *                                    applicable
 * @param {AdfUIComponent} component the component being animated; used to ensure descendant resize
 *                                   notifications get invoked after animations are complete
 * @return {MyAnimator} the animator that has been started (so you can stop it)
 */
MyAnimator.animate = function(
  frameMethod,
  timeLength,
  items,
  duringAnimate,
  afterAnimate,
  callbackParameters,
  component)
{
  // compute the platform-specific initial and final state for each item
  var agent = AdfAgent.AGENT;
  var itemCount = items.length;
  var itemState = new Array(itemCount);
  for (var i=0; i<itemCount; i++)
  {
    var item = items[i];
    var finalProperties = item["properties"];
    var element = item["element"];
    var state = {};

    // gather the "width" information if applicable
    MyAnimator._gatherSizeState(
      state,
      finalProperties,
      element,
      "width",
      "offsetWidth",
      "borderLeftWidth",
      "borderRightWidth");

    // gather the "height" information if applicable
    MyAnimator._gatherSizeState(
      state,
      finalProperties,
      element,
      "height",
      "offsetHeight",
      "borderTopWidth",
      "borderBottomWidth");

    // gather the "alpha" information if applicable
    var finalAlpha = finalProperties["alpha"];
    if ( (finalAlpha != null) && !isNaN(finalAlpha) )
    {
      var initialAlpha = element.style.opacity;
      if ( element.ownerDocument.all && (initialAlpha == null) )
      {
        try
        {
          initialAlpha = element.filters.alpha.opacity / 100;
        }
        catch (problem) {}
      }
      if ( (initialAlpha == null) || ( (""+initialAlpha) == "") )
      {
        initialAlpha = 1; // fully opaque
      }
      state["opacity"] = [ initialAlpha, finalAlpha / 100, true ];
    }

    // gather the "zIndex" information if applicable
    var finalZIndex = finalProperties["zIndex"];
    if ( (finalZIndex != null) && !isNaN(finalZIndex) )
    {
      state["zIndex"] = [ MyAnimator._getElementZIndex(agent, element), finalZIndex, true ];
    }

    // gather the "top" information if applicable
    var finalTop = finalProperties["top"];
    if ( (finalTop != null) && !isNaN(finalTop) )
    {
      // If a style is provide use it so that we account for absolute/relative to a container
      var styleTop = element.style.top;
      var top = (styleTop && styleTop != "auto")?parseInt(styleTop):agent.getElementTop(element);
      state["top"] = [ top, finalTop ];
    }

    // gather the "left" information if applicable
    var finalLeft = finalProperties["left"];
    if ( (finalLeft != null) && !isNaN(finalLeft) )
    {
      // If a style is provide use it so that we account for absolute/relative to a container
      var styleLeft = element.style.left;
      var left = (styleLeft && styleLeft != "auto")?parseInt(styleLeft):
                                                    agent.getElementLeft(element);
      state["left"] = [ left, finalLeft ];
    }

    // gather the "offsetTop" information if applicable
    var finalOffsetTop = finalProperties["offsetTop"];
    if ( (finalOffsetTop != null) && !isNaN(finalOffsetTop) )
    {
      state["offsetTop"] = [ element.offsetTop, finalOffsetTop ];
    }

    // gather the "left" information if applicable
    var finalOffsetLeft = finalProperties["offsetLeft"];
    if ( (finalOffsetLeft != null) && !isNaN(finalOffsetLeft) )
    {
      state["offsetLeft"] = [ element.offsetLeft, finalOffsetLeft ];
    }

    // gather the "scrollLeft" information if applicable
    var finalScrollLeft = finalProperties["scrollLeft"];
    if ( (finalScrollLeft != null) && !isNaN(finalScrollLeft) )
    {
      state["scrollLeft"] = [ element.scrollLeft, finalScrollLeft, true ];
    }

    // gather the "scrollTop" information if applicable
    var finalScrollTop = finalProperties["scrollTop"];
    if ( (finalScrollTop != null) && !isNaN(finalScrollTop) )
    {
      state["scrollTop"] = [ element.scrollTop, finalScrollTop, true ];
    }

    // "element" is the DOMElement whose state is to be altered
    // "state" is an Array with 3 entries:
    // 1.) Initial value
    // 2.) Final value
    // 3.) Boolean whether to abstain from adding "px" to the computed values (true means don't add)
    itemState[i] =
    {
      "element": element,
      "state": state
    };

    // We might also want to consider supporting these attributes too:
    // clip, color, backgroundColor, resize, rotate
  }

  // popuplate the frames array
  var frameCount =
    Math.max(1, Math.round(timeLength * MyAnimator._FRAMES_PER_MILLISECOND)); // at least 1 frame
  if (!AdfPage.PAGE.isAnimationEnabled())
  {
    // Animation is disabled
    frameCount = 1;
  }

  // Run the animation:
  return (new MyAnimator(
    itemState,
    duringAnimate,
    afterAnimate,
    callbackParameters,
    component,
    frameMethod,
    frameCount))._start();
}

MyAnimator._getElementZIndex = function(agent, element)
{
  var style = agent.getComputedStyle(element);
  if (style)
  {
    var zIndex = style.zIndex;
    if (!isNaN(zIndex))
    {
      return zIndex;
    }
  }
  return 0;
}

MyAnimator._gatherSizeState = function(
  state,
  finalProperties,
  element,
  sizeKey,
  currentSizeProperty,
  borderStartKey,
  borderEndKey)
{
  var finalSize = finalProperties[sizeKey];
  if ( (finalSize != null) && !isNaN(finalSize) )
  {
    var initialSize = element[currentSizeProperty];
    // Since initialSize includes border sizes and since style sizes do no, we
    // must subtract any border sizes that may be present.
    if (element.style != null)
    {
      initialSize =
        MyAnimator._subtractBorderSize(initialSize, element.style[borderStartKey]);
      initialSize =
        MyAnimator._subtractBorderSize(initialSize, element.style[borderEndKey]);
    }
    state[sizeKey] = [ initialSize, finalSize ];
  }
}

/**
 * Starts the animation.
 */
MyAnimator.prototype._start = function()
{
  this._animationStepCallback = this.createCallback(this._animationStep);
  var intervalLength = Math.floor(1 / MyAnimator._FRAMES_PER_MILLISECOND);
  this._intervalID = self.setInterval(this._animationStepCallback, intervalLength);
  return this;
}

MyAnimator.prototype._destroy = function()
{
  delete this._itemState;
  delete this._duringAnimate;
  delete this._afterAnimate;
  delete this._callbackParameters;
  delete this._component;
  delete this._startTime;
  delete this._intervalID;
  delete this._animationStepCallback;
  delete this._stopped;
}

/**
 * Static animation step called by the system.
 * This is not an API.
 */
MyAnimator.prototype._animationStep = function()
{
  if (this._stopped || this._intervalID == null)
  {
    return;
  }

  if (this._performAfterAnimate)
  {
    window.clearInterval(this._intervalID);

    if (this._afterAnimate != null)
    {
      this._afterAnimate(this._callbackParameters);
    }

    // Animated dimension changes potentially cause descentant resize to be required.
    // This is likely something that has changed the dimensions or other aspect of layout of the
    // page.  We need to allow components that care about descendant changes to have the opportunity
    // to make adjustments if necessary.  (Just like what happens during a server-handled change,
    // e.g. a PPR.):
    if (this._component)
    {
      var component = this._component;

      // If the component has no peer, it's dead - so it may have been replaced via PPR.
      // Try to re-find that component.  If that fails, it may have been deleted altogether, in
      // which case it should be dropped.
      if (!component.getPeer())
      {
        var clientId = this._component.getClientId();
        component = AdfPage.PAGE.findComponent(clientId);
      }

      if (component)
      {
        AdfPage.PAGE.__queueDescendantResizeNotifySource(component);
        AdfPage.PAGE.__doDescendantResizeNotify();
      }
    }

    // cleanup the static variables:
    this._destroy();
    return;
  }

  var currentTime = (new Date()).getTime();
  var elapsedMillis = currentTime - this._startTime;
  var itemState = this._itemState;
  var itemCount = itemState.length;
  var frameIndex = Math.round(MyAnimator._FRAMES_PER_MILLISECOND * elapsedMillis);
  var frameCount = this._frameCount;
  var frameMethod = this._frameMethod;
  var isLastFrame = false;

  if (frameIndex >= frameCount - 1)
  {
    isLastFrame = true
    // execute the after animate function on the next round
    this._performAfterAnimate = true;
  }

  // compute an operation for each item in this frame
  for (var j=0; j<itemCount; j++)
  {
    var currentItemState = itemState[j];
    var element = currentItemState["element"];
    var state = currentItemState["state"];
    var pValue;

    for (var x in state)
    {
      // For the last frame use the final state
      pValue = isLastFrame? state[x][1] : MyAnimator._computeFrameProperty(
                                              frameIndex,
                                              parseFloat(state[x][0]),
                                              parseFloat(state[x][1]),
                                              frameMethod,
                                              frameCount);

      // Add px to the property if necessary
      if (!state[x][2])
      {
        pValue += "px";
      }
      MyAnimator._renderFrameProperty(element, pValue, x);
    }
  }

  var duringAnimate  = this._duringAnimate;
  // execute the during animate function
  if (duringAnimate != null)
  {
    duringAnimate(this._callbackParameters);
  }

}

MyAnimator._renderFrameProperty = function(element, pValue, pName)
{
  if ((pName == "opacity") && (AdfAgent.AGENT.getPlatform()==AdfAgent.IE_PLATFORM))
  {
    var ieOpacity = pValue * 100;
    if (ieOpacity == 1)
    {
      element.style.filter = "";
    }
    else
    {
      element.style.filter = "alpha(opacity=" + ieOpacity + ")";
    }
  }
  else if (pName == "offsetLeft")
  {
    element.style.left = pValue;
  }
  else if (pName == "offsetTop")
  {
    element.style.top = pValue;
  }
  else if (pName == "scrollLeft")
  {
    AdfAgent.AGENT.scrollToPos(element, pValue, null);
  }
  else if (pName == "scrollTop")
  {
    AdfAgent.AGENT.scrollToPos(element, null, pValue);
  }
  else
  {
    element.style[pName] = pValue;
  }
}

/**
 * Computes a frame property.
 */
MyAnimator._computeFrameProperty = function(
  frameNumber,
  initialValue,
  finalValue,
  frameMethod,
  lastFrameNumber)
{
  var time0To1 = frameNumber / lastFrameNumber; // a.k.a. percent complete
  var dist0To1; // computed below
  switch (frameMethod)
  {
    case MyAnimator.FRAME_METHOD_CONSTANT_SPEED:
      // dist = time for all values of time and dist
      dist0To1 = time0To1;
      break;
    case MyAnimator.FRAME_METHOD_ACCELERATING:
      // dist = time^2 where dist & time are in { 0 to 1 }
      dist0To1 = Math.pow(time0To1, 2);
      break;
    case MyAnimator.FRAME_METHOD_DECELERATING:
      // dist = 1 - (time - 1)^2 where dist & time are in { 0 to 1 }
      dist0To1 = 1 - Math.pow( (time0To1 - 1), 2 );
      break;
    case MyAnimator.FRAME_METHOD_SLOW_FAST_SLOW:
      // dist = (cos(time*pi + pi) + 1) / 2 where dist & time are in { 0 to 1 }
      dist0To1 = (Math.cos(time0To1*Math.PI + Math.PI) + 1) / 2;
      break;
    default:
      AdfLogger.LOGGER.severe("Invalid MyAnimator framing method: " + frameMethod);
      dist0To1 = 1; // jump to the end
  }
  var distDelta = finalValue - initialValue;
  return initialValue + dist0To1 * distDelta;
}

MyAnimator._subtractBorderSize = function(value, borderWidthStyle)
{
  if ( (borderWidthStyle != null) && (borderWidthStyle != "") )
  {
    value -= parseInt(borderWidthStyle);
  }
  return value;
}

MyAnimator._FRAMES_PER_MILLISECOND = 0.06; // 60 frames per second

// framing method constants:
MyAnimator.FRAME_METHOD_CONSTANT_SPEED = 0;
MyAnimator.FRAME_METHOD_ACCELERATING = 1;
MyAnimator.FRAME_METHOD_DECELERATING = 2;
MyAnimator.FRAME_METHOD_SLOW_FAST_SLOW = 3;