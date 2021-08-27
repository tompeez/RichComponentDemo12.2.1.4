window.resourceDemo = {
  showAlert: function(event)
  {
    event.cancel();
    alert('The show alert function has been invoked.');
    return false;
  }
}