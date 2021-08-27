package oracle.adfdemo.view.components.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.ExtendedUploadedFile;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class DemoFileBean implements Serializable
{

  private List<DemoFile> _fileList = new ArrayList<DemoFile>();
  
  public DemoFileBean()
  {
  }
  
  public List<DemoFile> getFileList()
  {
    return _fileList;
  }
  
  public void setFileList(List<DemoFile> value)
  {
    _fileList = value;
  }

  public void fileUpdate(ValueChangeEvent valueChangeEvent)
  {
    List<UploadedFile> fileList = (List<UploadedFile>) valueChangeEvent.getNewValue();
    if (fileList != null)
    {
      for (UploadedFile file : fileList)
      {
        DemoFile demoFile = new DemoFile();
        demoFile.setFilename(file.getFilename());
        if (file instanceof ExtendedUploadedFile)
        {
          Object desc = ((ExtendedUploadedFile)file).getProperties().get("description");
          if (desc != null)
            demoFile.setDescription((String) desc);
        }
        demoFile.setLength(file.getLength());
        _fileList.add(demoFile);
      }
    }
  }
  
  public static class DemoFile implements Serializable
  {
    private String _filename = null;
    private String _description = null;
    private long _length = 0;
    
    public String getFilename()
    {
      return _filename;
    }
    
    public void setFilename(String value)
    {
      _filename = value;
    }
    
    public String getDescription()
    {
      return _description;
    }
    
    public void setDescription(String value)
    {
      _description = value;
    }
    
    public long getLength()
    {
      return _length;
    }
    
    public void setLength(long value)
    {
      _length = value;
    }
  }
}
