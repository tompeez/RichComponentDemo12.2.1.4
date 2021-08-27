function closeParentDialog(actionEvent)
{
  for (var comp = actionEvent.getSource(); comp != null; comp = comp.getParent())
  {
    if (comp.getComponentType() == 'oracle.adf.RichPopup')
    {
      comp.hide();
      break;
    }
  }
  actionEvent.cancel();
  return false;
}