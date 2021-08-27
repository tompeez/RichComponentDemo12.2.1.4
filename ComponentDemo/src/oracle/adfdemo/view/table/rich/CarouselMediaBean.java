/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichCarousel;
import oracle.adf.view.rich.event.CarouselSpinEvent;

/**
 * Managed bean for data associated with the carousel.
 */
public class CarouselMediaBean implements Serializable
{
  /**
   * Default constructor.
   */
  public CarouselMediaBean()
  {
    _mediaList = new ArrayList<MediaInfo>();

    _createMediaInfo("Alice in Wonderland", "Family", "alice-in-wonderland");
    _createMediaInfo("Basic Instinct", "Action", "basic-instinct");
    _createMediaInfo("The Blair Witch Project", "Suspense", "the-blair-witch-project");
    _createMediaInfo("The Cave", "Suspense", "the-cave");
    _createMediaInfo("Charlie's Angels", "Action", "charlies-angels");
    _createMediaInfo("The Covenant", "Action", "the-covenant");
    _createMediaInfo("Cruel Intentions", "Drama", "cruel-intentions");
    _createMediaInfo("Cube", "Sci-Fi", "cube");
    _createMediaInfo("Dirty Dancing", "Drama", "dirty-dancing");
    _createMediaInfo("Fargo", "Drama", "fargo");
    _createMediaInfo("Final Destination", "Sci-Fi", "final-destination");
    _createMediaInfo("Go", "Action", "go");
    _createMediaInfo("Hot Wheels World Race", "Family", "hot-wheels-world-race");
    _createMediaInfo("The Karate Kid", "Family", "the-karate-kid");
    _createMediaInfo("Legally Blonde 2", "Comedy", "legally-blonde-2");
    _createMediaInfo("Men in Black", "Sci-Fi", "men-in-black");
    _createMediaInfo("Monty Python and the Holy Grail", "Comedy", "monty-python-and-the-holy-grail");
    _createMediaInfo("Panic Room", "Sci-Fi", "panic-room");
    _createMediaInfo("Pi", "Sci-Fi", "pi");
    _createMediaInfo("Rent", "Musical", "rent");
    _createMediaInfo("Resident Evil Apocalypse", "Sci-Fi", "resident-evil-apocalypse");
    _createMediaInfo("Rush Hour", "Comedy", "rush-hour");
    _createMediaInfo("Sleepless in Seattle", "Romantic Comedy", "sleepless-in-seattle");
    _createMediaInfo("Stargate", "Sci-Fi", "stargate");
    _createMediaInfo("Terminator 2 Judgement Day", "Sci-Fi", "terminator-2-judgement-day");
    _createMediaInfo("Terminator 2 T2 Extreme", "Sci-Fi", "terminator-2-t2-extreme");
    _createMediaInfo("Total Recall", "Action", "total-recall");
    _createMediaInfo("The Return of the Pink Panther", "Comedy", "the-return-of-the-pink-panther");
    _createMediaInfo("Ultraviolet", "Action", "ultraviolet");
    _createMediaInfo("Underworld", "Action", "underworld");
  }

  public void handleCarouselSpin(CarouselSpinEvent event)
  {
    RichCarousel carousel = (RichCarousel)event.getComponent();
    carousel.setRowKey(event.getNewItemKey());
    MediaInfo itemData = (MediaInfo)carousel.getRowData();

    _currentMediaInfo = itemData;
  }

  public List<MediaInfo> getItems()
  {
    return _mediaList;
  }

  public MediaInfo getCurrentMediaInfo()
  {
    if (_currentMediaInfo == null)
    {
      _currentMediaInfo = _mediaList.get(0);
    }
    return _currentMediaInfo;
  }

  private void _createMediaInfo(String title, String genre, String imageName)
  {
    MediaInfo MediaInfo = new MediaInfo(title, genre, imageName);

    // Add to the list of Media names:
    _mediaList.add(MediaInfo);
  }

  public static class MediaInfo implements Serializable
  {
    MediaInfo(String title, String genre, String imageName)
    {
      _title = title;
      _genre = genre;
      _url   = "/images/carousel/media/" + imageName + ".jpg";
    }

    public String getTitle()
    {
      return _title;
    }

    public String getGenre()
    {
      return _genre;
    }

    public String getUrl()
    {
      return _url;
    }

    private String _title;
    private String _genre;
    private String _url;

    @SuppressWarnings("compatibility:-2903337997603823737")
    private static final long serialVersionUID = 1L;
  }

  private List<MediaInfo> _mediaList;
  private MediaInfo       _currentMediaInfo;

  @SuppressWarnings("compatibility:2773056323007948411")
  private static final long serialVersionUID = 1L;
}
