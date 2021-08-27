/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.perf.rich;

import javax.faces.event.PhaseId;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;

import oracle.adf.share.logging.ADFLogger;

/**
 * Just a little diagnostic PhaseListener which logs the time it takes
 * to execute each phase.
 */
public class TimingPhaseListener implements PhaseListener
{
  public PhaseId getPhaseId()
  {
    return PhaseId.ANY_PHASE;
  }

  public void beforePhase(PhaseEvent event)
  {
    // Get the start time
    _startTime = System.currentTimeMillis();
  }

  public void afterPhase(PhaseEvent event)
  {
    long elapsedTime = System.currentTimeMillis() - _startTime;

    _LOG.info(event.getPhaseId() + ": " + elapsedTime + "ms");
  }

  private long _startTime;

  private static final ADFLogger _LOG =
    ADFLogger.createADFLogger(TimingPhaseListener.class);

}
