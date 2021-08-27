/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
function TestPerfFramework(title, settings, count, add)
{
  this._init(title, settings, count, add);
}

TestPerfFramework.initialize = function(title, settings, count, add)
{
  TestPerfFramework._INSTANCE =
    new TestPerfFramework(title, settings, count, add);
}

TestPerfFramework._LINE_SEPARATOR = "\r";
TestPerfFramework._LABEL_TEST_EVERYTHING = "Automate Tests";
TestPerfFramework._TIMEOUT_AFTER_AUTOMATED_CREATE = 750; // milliseconds
TestPerfFramework._COUNTS =
  [ 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1500, 2000, 3000 ];

TestPerfFramework.prototype._init = function(title, settings, count, add)
{
  this._frameworkSettings = settings;

  var sb = new StringBuilder();
  sb.append("<form name='testForm'><fieldset><legend>");
  sb.append(title);
  sb.append("</legend><table><tbody>");

  sb.append("<tr><th align='right'>Automation Iterations</th><td>");
  sb.append("<input id='automationIterations' value='5'/></td></tr>");

  sb.append("<tr><th align='right'>Count</th><td>");
  sb.append("<input id='countInput' value='");
  sb.append(count);
  sb.append("'/></td></tr>");

  sb.append("<tr><th align='right'></th>");
  sb.append("<td><input id='addInput' type='checkbox' ");
  if (true == add)
  {
    sb.append("checked");
  }
  sb.append(" ");
  if (add == null)
  {
    sb.append("style='display: none;'");
  }
  sb.append("/>");
  sb.append("<label for='addInput' ");
  if (add == null)
  {
    sb.append("style='display: none;'");
  }
  sb.append(">Add</label></td></tr>");

  for (var i=0; i<settings.length; i++)
  {
    var settingId = settings[i][0];
    sb.append("<tr><th align='right'>");
    sb.append(settingId);
    sb.append("</th><td><select id='");
    sb.append(settingId);
    sb.append("'>");
    var settingItems = settings[i][1];
    for (var j=0; j<settingItems.length; j++)
    {
      var settingItem = settingItems[j];
      sb.append("<option value='");
      sb.append(settingItem);
      sb.append("'>");
      sb.append(settingItem);
      sb.append("</option>");
    }
    sb.append("</select></td></tr>");
  }

  sb.append("</tbody></table>");
  sb.append("<hr/>");
  sb.append("<div>");

  sb.append("<button onclick='TestPerfFramework._INSTANCE._create();return false;' ");
  sb.append("id='createButton'>Create</button>");

  sb.append("<button onclick='TestPerfFramework._INSTANCE._destroy();return false;' ");
  sb.append("id='destroyButton'>Destroy</button>");

  sb.append("&nbsp;&nbsp;&nbsp;");

  sb.append("<button onclick='TestPerfFramework._INSTANCE._testEverything();return false;' ");
  sb.append("id='testEverythingButton'>");
  sb.append(TestPerfFramework._LABEL_TEST_EVERYTHING);
  sb.append("</button>");

  sb.append("</div></fieldset>");

  sb.append("<div id='rootDiv'></div></form>");

  document.body.innerHTML = sb.toString();
}

TestPerfFramework.prototype._testEverything = function()
{
  var testEverythingButton = document.getElementById("testEverythingButton");
  if (testEverythingButton.innerHTML != TestPerfFramework._LABEL_TEST_EVERYTHING)
  {
    alert("Reloading...");
    window.location.reload();
  }
  testEverythingButton.innerHTML = "Test in Progress...";
  var settings = this._frameworkSettings;
  var settingCounters = new Array(settings.length);
  for (var i=0; i<settings.length; i++)
  {
    settingCounters[i] = 0; // specific setting counter
  }
  this._testSettingCounters = settingCounters;
  this._testIteration = 0;
  this._countsIndex = 0;
  this._testResults = new Array();
  this._destroy(); // clean up any leftover test artifacts
  window.setTimeout("TestPerfFramework._INSTANCE._nextAutomation()", 1); // setTimeout to let the browser redraw
}

TestPerfFramework.prototype._incrementElementCount = function()
{
  var currentCountIndex = this._countsIndex;
  var currentCountLength = TestPerfFramework._COUNTS.length;
  if (currentCountIndex >= currentCountLength - 1)
  {
    // Reset this counter to zero and increment the next greater counter:
    this._countsIndex = 0;
  }
  else
  {
    // Increment the counter and indicate that a test needs to be run:
    this._countsIndex++;
    return true;
  }
  // Indicate that there is nothing more to test:
  return false;
}

TestPerfFramework.prototype._incrementSettingCounters = function(
  settings,
  settingCounters)
{
  for (var i=settingCounters.length-1; i>-1; i--)
  {
    var currentCounter = settingCounters[i];
    var currentCounterLength = settings[i][1].length;
    if (currentCounter >= currentCounterLength - 1)
    {
      // Reset this counter to zero and increment the next greater counter:
      settingCounters[i] = 0;
    }
    else
    {
      // Increment the counter and indicate that a test needs to be run:
      settingCounters[i]++;
      return true;
    }
  }
  // Indicate that there is nothing more to test:
  return false;
}

TestPerfFramework.prototype._nextAutomation = function()
{
  var settings = this._frameworkSettings;
  var settingCounters = this._testSettingCounters;
  var iterations = parseInt(document.getElementById("automationIterations").value);
  if ( isNaN(iterations) || (iterations < 1) )
  {
    alert("Automation iterations must be a number >= 1");
    this._testResults = null;
    var testEverythingButton = document.getElementById("testEverythingButton");
    testEverythingButton.innerHTML = TestPerfFramework._LABEL_TEST_EVERYTHING;
    return;
  }
  if (this._testIteration >= iterations)
  {
    // Advance to the next test or conclude testing if no more tests:
    this._testIteration = 0;
    if (!this._incrementElementCount())
    {
      if (!this._incrementSettingCounters(settings, settingCounters))
      {
        // Conclude the testing:
        this._reportAutomationResults();
        this._testResults = null;
        this._testSettingCounters = null;
        var testEverythingButton = document.getElementById("testEverythingButton");
        testEverythingButton.innerHTML = TestPerfFramework._LABEL_TEST_EVERYTHING;
        return; // nothing more to test
      }
    }
  }

  // Set the appropriate selected item for each setting:
  var progresssInfo = new StringBuilder();
  progresssInfo.append("Test in Progress... (");
  for (var i=0; i<settings.length; i++)
  {
    var settingId = settings[i][0];
    var settingSelect = document.getElementById(settingId);
    var selectedIndex = settingCounters[i];
    settingSelect.selectedIndex = selectedIndex;
    progresssInfo.append(selectedIndex).append(" ");
  }
  var count = TestPerfFramework._COUNTS[this._countsIndex];
  document.getElementById("countInput").value = count;
  progresssInfo.append(count).append(" ");
  progresssInfo.append(this._testIteration).append(")");

  // Update the button's label to provide feedback on the test progress:
  var testEverythingButton = document.getElementById("testEverythingButton");
  testEverythingButton.innerHTML = progresssInfo.toString();

  window.setTimeout("TestPerfFramework._INSTANCE._automatedCreate()", 1); // setTimeout to let the browser redraw
}

TestPerfFramework.prototype._automatedCreate = function()
{
  this._create();
  window.setTimeout("TestPerfFramework._INSTANCE._automatedDestroy()",
    TestPerfFramework._TIMEOUT_AFTER_AUTOMATED_CREATE); // setTimeout to let the browser redraw
}

TestPerfFramework.prototype._automatedDestroy = function()
{
  this._destroy();
  this._testIteration++;
  window.setTimeout("TestPerfFramework._INSTANCE._nextAutomation()", 1); // setTimeout to let the browser redraw
}

TestPerfFramework.prototype._reportAutomationResults = function()
{
  var iterations = parseInt(document.getElementById("automationIterations").value);
  var results = this._testResults;
  var summary = new StringBuilder();
  var previousTest = null;
  var createTotal = 0;
  var addTotal = 0;
  for (var i=0; i<results.length; i++)
  {
    var currentTest = results[i][0];
    if ( (previousTest != null) && (previousTest != currentTest) )
    {
      // Report the average of the iteration:
      this._appendReportLine(
        summary, previousTest, (createTotal / iterations), (addTotal / iterations));
      summary.append(TestPerfFramework._LINE_SEPARATOR);
      createTotal = 0;
      addTotal = 0;
    }
    previousTest = currentTest;
    createTotal += results[i][1]; // elapsedCreateTimeMillis
    addTotal += results[i][2]; // addElapsedMillis
  }
  // Report the average of the last iteration:
  this._appendReportLine(
    summary, previousTest, (createTotal / iterations), (addTotal / iterations));

  // Since Internet Explorer enforces a limit to the size of text in an alert
  // box, we will use a textarea instead:
  var textarea = document.createElement("textarea");
  textarea.style.width = "100%";
  textarea.style.height = "300px";
  textarea.appendChild(document.createTextNode("Average results:" +
    TestPerfFramework._LINE_SEPARATOR + summary.toString()));
  var rootDiv = document.getElementById("rootDiv");
  rootDiv.appendChild(textarea);
}

TestPerfFramework.prototype._appendReportLine = function(
  summary,
  testName,
  createTime,
  addTime)
{
  summary.append("Results for ");
  summary.append(testName);
  summary.append(": create = ").append(createTime);
  if (addTime != -1)
  {
    summary.append(" ms, add = ").append(addTime);
  }
  summary.append(" ms.");
}

TestPerfFramework.prototype._create = function()
{
  var rootDiv = document.getElementById("rootDiv");

  var countInput = document.getElementById("countInput");
  var count = parseInt(countInput.value);
  if ( isNaN(count) || (count < 1) )
  {
    alert("Count must be a number >= 1");
    return;
  }

  var addInput = document.getElementById("addInput");
  var add = addInput.checked;
  
  // Gather the selection values because they will be concatenated to build the
  // name of the function to execute:
  var settings = this._frameworkSettings;
  var sb = new StringBuilder();
  for (var i=0; i<settings.length; i++)
  {
    var settingId = settings[i][0];
    var settingSelect = document.getElementById(settingId);
    var settingValue = settingSelect.value;
    sb.append(settingValue);
  }

  var functionName = sb.toString();
  var createFunc = eval(functionName);
  this._currentTest = functionName + " (" + count + ")";

  this._createDom(rootDiv, count, add, createFunc);
}

TestPerfFramework.prototype._createDom = function(
  rootDiv,
  count,
  add,
  createFunc)
{
  var elements = new Array(count);

  var startCreateTime = new Date();

  for (var i = 0; i < count; i++)
  {
    var element = createFunc();
    elements[i] = element;
  }

  var endCreateTime = new Date();
  var endAddTime = null;

  if (add)
  {
    for (var i = 0; i < count; i++)
    {
     rootDiv.appendChild(elements[i]);
    }

    endAddTime = new Date();
  }

  this._logElapsedTime(startCreateTime, endCreateTime, endAddTime);
}

TestPerfFramework.prototype._destroy = function()
{
  var rootDiv = document.getElementById("rootDiv");
  var childNodes = rootDiv.childNodes;
  var childCount = childNodes.length;

  for (var i = (childCount - 1); i > -1; i--)
  {
    var child = childNodes[i];
    rootDiv.removeChild(child);
  }
}

TestPerfFramework.prototype._logElapsedTime = function(
  startCreateTime,
  endCreateTime,
  endAddTime
  )
{
  var startCreateTimeMillis = startCreateTime.getTime();
  var endCreateTimeMillis = endCreateTime.getTime();
  var elapsedCreateTimeMillis = endCreateTimeMillis - startCreateTimeMillis;

  var addElapsedMillis = -1;
  if (endAddTime)
  {
    addElapsedMillis = endAddTime.getTime() - endCreateTimeMillis;
  }

  if (this._testResults == null)
  {
    var message = new StringBuilder();
    message.append(this._currentTest);
    message.append(":  Create = ");
    message.append(elapsedCreateTimeMillis);
    if (addElapsedMillis != -1)
    {
      message.append(" ms,  Add = ");
      message.append(addElapsedMillis);
    }
    message.append(" ms.");
    alert(message.toString());
  }
  else
  {
    this._testResults[this._testResults.length] = [
      this._currentTest,
      elapsedCreateTimeMillis,
      addElapsedMillis
    ];
  }
}
