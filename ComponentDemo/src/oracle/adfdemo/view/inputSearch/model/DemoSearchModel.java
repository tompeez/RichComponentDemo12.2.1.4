/* Copyright (c) 2016, 2019, Oracle and/or its affiliates. 
All rights reserved.*/

package oracle.adfdemo.view.inputSearch.model;


import com.google.common.collect.Multimap;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Maintains a list of auto-generated list of Employees and Departments.
 */
public class DemoSearchModel implements Serializable
{
  public static final int DEFAULT_ROW_LIMIT = 5000;

  private DemoSearchModel()
  {
    _employees = _initEmployees();
    _departments = _initDepartments(_employees);
  }

  public static DemoSearchModel getInstance()
  {
    return DemoSearchModelHolder.INSTANCE;
  }

  public List<? extends SearchModelBase> getDepartments(
    Multimap<String, String> attributeValueFilterMap,
    int                 rowLimit)
  {
    return _getPreFilteredRows(_departments, attributeValueFilterMap, rowLimit);
  }

  /**
   * Returns all the employees
   */
  public List<Employee> getEmployees()
  {
    return Collections.unmodifiableList(_employees);
  }

  /**
   * Filters the employee which have property values matching the ones in the attributeValueFilterMap
   */
  public List<? extends SearchModelBase> getEmployees(Multimap<String, String> attributeValueFilterMap)
  {
    return this.getEmployees(attributeValueFilterMap, _employees.size(), null);
  }

  /**
   * Filters the employee which have
   * a) property values matching the ones in the attributeValueFilterMap
   * b) Server filtering capability similar to one provided by the component
   */
  public List<? extends SearchModelBase> getEmployees(
    Multimap<String, String> attributeValueFilterMap,
    int                 rowLimit,
    FilterDef           filterDef)
  {
    rowLimit = rowLimit > _employees.size() ? _employees.size() : rowLimit;

    // rowLimit is pre-filter limit, the global limit.
    // So two cases cases:
    // a. rowLimit <= 5000 - Return all rows
    // b. rowLimit > 5000 - Return first 5000 rows post filtering
    List<? extends SearchModelBase> prefilteredEmployees =
      _getPreFilteredRows(_employees, attributeValueFilterMap, rowLimit);
    if (rowLimit <= DEFAULT_ROW_LIMIT)
    {
      return FilterUtils.filter(prefilteredEmployees, filterDef);
    }

    prefilteredEmployees = FilterUtils.filter(prefilteredEmployees, filterDef);
    return prefilteredEmployees.subList(
      0, prefilteredEmployees.size() > DEFAULT_ROW_LIMIT ?
        DEFAULT_ROW_LIMIT :
        prefilteredEmployees.size());
  }

  public void addChangeListener(SearchModelChangeListener listener)
  {
    this._listeners.add(listener);
  }

  /**
   * Adds a new employee. Needed for Inline Create Feature
   */
  public Integer addEmployee(String fName, String lName)
  {
    Integer id = _employees.size();
    _employees.add(new Employee(id, fName, lName,
                                "", fName + "." + lName + "@acme.com", 'N',
                                new Date(), new Date(), "", "",
                                "", "", _listeners));
    return id;
  }

  private static List<? extends SearchModelBase> _getPreFilteredRows(
    List<? extends SearchModelBase>      rows,
    Multimap<String, String>                  attributeValueFilterMap,
    int                                  rowLimit)
  {
    if (rowLimit == 0)
    {
      return Collections.EMPTY_LIST;
    }

    // Let's try to ensure the lists of size <5000 are subsets of list size=5000
    int fakeLimit = rowLimit <= DEFAULT_ROW_LIMIT ? DEFAULT_ROW_LIMIT : rowLimit;

    List<SearchModelBase> fakeLimitfilteredList = new ArrayList<SearchModelBase>();
    for (int i = 0, jump = rows.size() / fakeLimit, j = 0; i < rows.size() && j < fakeLimit; i += jump, j++)
    {
      boolean matched = false;
      SearchModelBase row = rows.get(i);
      if (attributeValueFilterMap.isEmpty())
      {
        fakeLimitfilteredList.add(row);
      }
      else
      {
        for (String attrName : attributeValueFilterMap.keySet())
        {
          Object rowValue = row.getProperty(attrName);

          Collection<String> attrValues = attributeValueFilterMap.get(attrName);
          for (String value : attrValues)
          {
            // different values for same key is treated as OR operator
            matched = rowValue != null && rowValue.toString().equals(value);
            if (matched)
              break;
          }

          if (!matched) // Enforces AND operator matching if there are multple keys
          {
            break;
          }
        }
        
        if (matched)
        {
          fakeLimitfilteredList.add(row);
        }
      }
    }

    // Let's try to ensure the lists of size <5000 are subsets of list size=5000
    List<SearchModelBase> filteredList = fakeLimitfilteredList;
    if (fakeLimit > rowLimit)
    {
      filteredList = new ArrayList<SearchModelBase>();
      for (int i = 0, jump = fakeLimitfilteredList.size() / rowLimit, j = 0;
           i < fakeLimitfilteredList.size() && j < rowLimit;
           i += jump, j++)
      {
        filteredList.add(fakeLimitfilteredList.get(i));
      }
    }
    return Collections.unmodifiableList(filteredList);
  }

  private List<Department> _initDepartments(List<Employee> employees)
  {
    List<Department> depts = new ArrayList<Department>();

    int i = 7000;
    Random randomizer = new Random();
    for (String dept : _DEPARTMENTS)
    {
      depts.add(
        new Department(i++,
                       dept,
                       _LOCATIONS[randomizer.nextInt(_LOCATIONS.length)],
                       employees.get(randomizer.nextInt(employees.size())).getId(),
                       null));
    }
    return depts;
  }

  private List<Employee> _initEmployees()
  {
    List<Employee> employees = new ArrayList<Employee>();
    Random randomizer = new Random();

    for (int empId = 0; empId < _TABLE_MAX_LIMIT; empId++)
    {
      char genderCode = _GENDERS[randomizer.nextInt(2)];
      String fName = (genderCode == 'M')? _MALE_F_NAMES[randomizer.nextInt(_MALE_F_NAMES.length)] :
                                        _FEMALE_F_NAMES[randomizer.nextInt(_FEMALE_F_NAMES.length)];
      String lName = _L_NAMES[randomizer.nextInt(_L_NAMES.length)];
      Date hireDate = new Date(100 + randomizer.nextInt(5000)/312,
                               randomizer.nextInt(11),
                               1 + randomizer.nextInt(27));
      Date dateOfBirth = new Date(randomizer.nextInt(100),
                                  randomizer.nextInt(11),
                                  1 + randomizer.nextInt(27));

      String deptName = _DEPARTMENTS[randomizer.nextInt(_DEPARTMENTS.length)];
      String location = _LOCATIONS[randomizer.nextInt(_LOCATIONS.length)];
      String profileKey = ((randomizer.nextInt(11) % 4 == 0)? "" :
                            randomizer.nextInt(25)) + "_" + genderCode + ".jpg";

      String jobTitle = (empId % 1000 == 0)? _JOB_TITLES[4] :
          ((empId % 100 == 0)? _JOB_TITLES[3] : _JOB_TITLES[randomizer.nextInt(3)]);

      int tagCount = randomizer.nextInt(4);
      String tags = "";

      while(tagCount > 0)
      {
        String tempTag = _TAGS[randomizer.nextInt(_TAGS.length)];

        if (!tags.contains(tempTag))
        {
          tags = tags + tempTag + ", ";
          tagCount--;
        }
      }

      employees.add(new Employee(empId, fName, lName,
                                  jobTitle, fName + "." + lName + "@acme.com", genderCode,
                                  hireDate, dateOfBirth, deptName, location,
                                  profileKey, tags, _listeners));
    }

    Collections.sort(employees);
    return employees;
  }

  // Lazy initialization holder class idiom
  private static class DemoSearchModelHolder
  {
    static final DemoSearchModel INSTANCE = new DemoSearchModel();
  }

  private final List<Employee> _employees;
  private final List<Department> _departments;
  private final List<SearchModelChangeListener> _listeners =
    new CopyOnWriteArrayList<SearchModelChangeListener>();

  // Seed list of department names
  private static String[] _DEPARTMENTS = new String[] {
    "Accounting",
    "Payroll",
    "Human Resources",
    "Sales",
    "Marketing",
    "Customer Support",
    "Logistics",
    "After sales",
    "Professional Services",
    "Billing",
    "IT",
    "Real Estate",
    "Security",
    "Public Relations",
    "Front Desk",
    "Legal",
    "Inventory",
    "Purchasing",
    "Licenses",
    "Operations"
  };

  // Seed list of department locations
  private static String[] _LOCATIONS = new String[] {
    "New Delhi, India",
    "Bangalore, India",
    "Bombay, India",
    "Hong Kong, PRC",
    "London, UK",
    "New York, USA",
    "Singapore",
    "Paris, France",
    "Denver, USA",
    "Sydney, Australia"
  };

  private static char[] _GENDERS = new char[] {'M', 'F'};

  // Seed list of person names
  private static String[] _MALE_F_NAMES = new String[] {
    "Tuan", "Tad", "Gregory", "Enrique", "Elvis", "Mauro",
    "Ernie", "Alfredo", "Agustin", "Kip", "Sung", "Wayne",
    "Warren", "Edwin", "Rey", "Raymond", "Darrell", "Sal",
    "Chung", "Louie", "Hobert", "Kenton", "Asa", "Johnathon",
    "Genaro", "Merle", "Larry", "Brett", "Lesley", "Erwin",
    "Randy", "Barrett", "Miguel", "Daniel", "Harvey", "Hollis",
    "Joaquin", "Alexis", "Darrel", "Jay", "Billie", "Leonard",
    "Sherman", "Eugene", "Lane", "Roberto", "Ashok", "Rod",
    "Myron", "Raul", "Wilson", "Nigel", "Russell", "Michel",
    "Denver", "Rigoberto", "Jeffrey", "Darius", "Mary", "Mervin",
    "Cole", "Arthur", "Ronald", "Wilbur", "Clement", "Enoch",
    "Erik", "Russel", "Sid", "Scotty", "Francis", "Daryl",
    "Chester", "Mitchel", "Stanton", "Nickolas", "Terrence",
    "Rudy", "Gale", "Margarito", "Clint", "Gavin", "Lloyd",
    "Tommy", "Miguel", "Rudolf", "Jon", "Carrol", "Raphael",
    "Roland", "Abe", "Kareem", "Arturo", "Benito", "Lon",
    "Elbert", "Zackary", "Oscar", "Trey", "Max-well", "Faustino",
    "Craig", "Lee", "Jamie", "Raymon", "Josiah", "Napoleon",
    "Moses", "Jody", "Gary", "Darell", "Tyree", "Sonny", "Carey",
    "Marion", "Fidel", "Willy", "Julian", "Jonah", "Dorsey",
    "Rusty", "Alden", "Jessie", "Zackary", "Denver", "Lyndon",
    "Clemente", "Coleman", "Teddy", "Mauro", "Damian", "Benton",
    "Domenic", "Darryl", "Odell", "Numbers", "Terence", "Adan",
    "Jordan", "Orville", "Pedro", "Abel", "Eusebio", "Carmen",
    "Rocco", "Maxwell", "Nathaniel", "Deshawn", "Wilford", "Cortez",
    "Dominick", "Mose", "Philip", "Ashley", "Paris",
    "Preston", "Hassan", "Manual", "Emanuel", "Duncan", "Antonia",
    "Rolando", "Lester", "Keith", "Rosario", "Vincent",
    "Lacy", "Nestor", "Earle", "Kent", "Kerry", "Ignacio", "Greg",
    "Hung", "Dee", "Wilton", "Gaylord", "Israel",
    "Jayson", "Ward", "Demetrius", "Forest", "Johnathan",
    "Francesco", "Santiago", "Jeff", "Jeremiah", "Sergio",
    "Reggie", "Jonathan", "Dana", "Shelby", "Josue", "Sammie",
    "Ronald", "Bo", "Frankie", "Valentine", "Hunter",
    "Douglas", "Barabara", "Machelle", "Divina", "Evelyne",
    "Katelynn", "Tiny", "Tiffany", "Selma", "Palmira",
    "Esperanza", "Virgen", "Celinda", "Cheryl", "Norma",
    "Alfreda", "Mackenzie", "Mao", "Lilia", "Ingeborg",
    "Joaquina", "Diedre", "Meg", "Carley", "Sachiko",
    "Tova", "Mittie", "Scarlett", "Mimi", "Merideth", "Daniele",
    "Arjun", "Ashwin", "Chandramouli", "Ravi", "Sanjay",
    "Srinath", "Sriram", "Vinay", "Alok", "Aminur", "Anand",
    "Arul", "Biren", "Chethan", "Dhandapani", "Abhay",
    "Harish", "Jagadeesh", "Janadardhan", "Damodhar",
    "Shivanand", "Manoj", "Karthik", "Karuppiah" /*,"חיוב", "נושא", "מכונות זמן" */}; // some hebrew words

  private static String[] _FEMALE_F_NAMES = new String[] {
    "Anglea", "Deidra", "Maybelle", "Assunta", "Gabriella",
    "Zola", "Dalene", "Shanda", "Keshia", "Garnet", "Phyliss",
    "Maryellen", "Dorethea", "Andera", "Audrie", "Gussie",
    "Kaycee", "Jennefer", "Tuyet", "Keturah", "Kathey", "Kalyn",
    "Lauryn", "Yu", "Georgie", "Argelia", "Suzi", "Trudi",
    "Bethany", "Lara", "Viviana", "Iesha", "Lorraine", "Maurita",
    "Usha", "Maire", "Deana", "Alfredia", "Mikki", "Hyun",
    "Kandy", "Yuriko", "Iluminada", "Janell", "Dreama", "Kathryne",
    "Emeline", "Denisha", "Josie", "Latoya", "Deloris",
    "Ione", "Leona", "Vella", "Deeann", "Ciara", "Luetta", "Jana",
    "Gigi", "Melda", "Verlie", "Sunday", "Denese", "Kenisha",
    "Bettie", "Annemarie", "Deonna", "Allena", "Marcelina",
    "Alycia", "Leticia", "Berneice", "Dessie", "Mika", "Ute",
    "Lonna", "Janel", "Gina", "Nicol", "Deeann", "Maryann",
    "Samatha", "Frieda", "Apolonia", "Janice", "Astrid", "Lida",
    "Drucilla", "Beula", "Iesha", "Tona", "Danae", "Edwina",
    "Livia", "Thea", "Hattie", "Earlie", "Esther", "Claribel",
    "Twila", "Shenna", "Lolita", "Teri", "Eleanora",
    "Jaqueline", "Myesha", "Carie", "Fanny", "Mattie", "Colleen",
    "Jami", "Tamala", "Shawanda", "Jenise", "Hedy",
    "Tequila", "Nenita", "Marci", "Margot", "Sylvia", "Catherine",
    "Aletha", "Ruthe", "Stephani", "Ok", "Despina",
    "Christi", "Carline", "Stormy", "Danyel", "Hui", "Nettie",
    "Noelle", "Corrie", "Mellie", "Eva", "Wava", "Michaela",
    "Geraldine", "Launa", "Concha", "Wilda", "Dorthy", "Petronila",
    "Dorothy", "Brigitte", "Laurette", "Lanell",
    "Heide", "Rolanda", "Cristine", "Kaitlin", "Glory", "Maryellen",
    "Sommer", "Kiley", "Melody", "Belia", "Margarete",
    "Guillermina", "Myrna", "Heidi", "Beatrice", "Karri", "Liane",
    "Kaylene", "Yasmin", "Carin", "Myrtis", "Clara",
    "Lakshmi", "Surabhi", "Anshu", "Deepika", "Fathima", "Namratha",
    "Gayathry", "Haritha", "Isha", "Aishwarya" /*,"חיוב", "נושא", "מכונות זמן" */};

  private static String[] _L_NAMES = new String[] {
    "Smothers", "Longoria", "Massie", "Mayhew", "Alderman",
    "Pope", "Macleod", "Wenzel", "Alcala", "Linares", "Spriggs",
    "Stahl", "Schmidt", "Hanes", "Royer", "Hawkins", "Petit",
    "Dowdy", "Matheson", "Stearns", "Coulter", "Cox",
    "Putman", "Herron", "Mims", "Worrell", "Poland", "Callahan",
    "Thomson", "Irby", "Berman", "Whitfield", "Freed",
    "Bunch", "Garcia", "Saunders", "Koenig", "Whalen",
    "Livingston", "Matlock", "Murillo", "Bland", "Gainey", "Patrick",
    "Abreu", "Stokes", "Hodges", "Karr", "Richter",
    "Pendleton", "Scroggins", "Isaacson", "Easterling", "Marcum",
    "Omalley", "Gerard", "Merchant", "Langley", "Caraway",
    "Dangelo", "Jude", "Portillo", "Seals", "Langford", "Fenner",
    "Randolph", "Marlowe", "Michel", "Broyles", "Nava",
    "Schultz", "Noll", "Vaughan", "Nobles", "Fontenot",
    "Malone", "Mast", "Huston", "Kuntz", "Chambliss",
    "Barlow", "Crisp", "Conner", "Chu", "Somerville", "Ferrell",
    "Heyward", "Brumfield", "Derosa", "Merritt", "Purnell",
    "Alonso", "Bedford", "Hardman", "Enriquez", "Jenkins",
    "Dew", "Shull", "Dewitt", "Blocker", "Rogers", "Schmitt",
    "Foust", "Araujo", "Corbitt", "Stoddard", "Begay",
    "Crook", "Hildreth", "Mcmullen", "Salcido", "Hendrick",
    "Everett", "Hadden", "Mann", "Waggoner", "Vigil",
    "Walden", "Brower", "Child", "Krieger", "Lemon", "Brackett",
    "Kidwell", "Bean", "Pulido", "Gould", "Grice", "Shields",
    "Carlos", "Quick", "Muncy", "Ripley", "Singer", "Reilly",
    "Larkin", "Mcreynolds", "Timm", "Vail", "Roche", "Strong",
    "Egan", "Nunes", "Horvath", "Quillen", "Milne", "Bartlett",
    "Enright", "Frick", "Sharpe", "Pressley", "Gaines",
    "Bishop", "Maclean", "Prentice", "Levi", "Gleason", "Roden",
    "Webster", "Clifford", "Pickering", "Rodrigues",
    "Weller", "Thayer", "Rubio", "Donald", "Espinosa", "Summerlin",
    "Jack", "Rosenberg", "Humphrey", "Flint", "Betz",
    "Binkley", "Levesque", "Lovejoy", "Bellamy", "Rosenbaum",
    "Overstreet", "Turnbull", "Sorrell", "Maier",
    "Lashley", "Dyson", "Grossman", "Osborn", "Goulet", "Kendrick",
    "Tapia", "Bruton", "York", "Cope", "Lemmon",
    "Kirchner", "Goins", "Mcgriff", "Zepeda", "Khan", "Benefield",
    "Thorn", "Lara", "Cates", "Saylor", "Mcfall",
    "Ebert", "Chong", "Staggs", "Messina", "Kilpatrick", "Jordan",
    "Nickel", "Croteau", "Briscoe", "Redmond", "Pratt",
    "Roldan", "Harris", "Kahn", "Easter", "Bauman", "Mcmurray",
    "Handy", "Vandyke", "Candelaria", "Ely", "Fincher",
    "Thorton", "Howland", "Daily", "Mayers", "Crump", "Minton",
    "Juarez", "Shockley", "Lacy", "Lear", "Cady",
    "Sanderson", "Cardenas", "Delong", "Wimberly", "Stowe", "Moyer",
    "Nelms", "Liu", "Allan", "Yoon", "Curley",
    "Gilley", "Manzo", "Barrios", "Mcgrew", "Adcock", "Epperson",
    "Mansfield", "Posey", "Denning", "Painter",
    "Crooks", "Grady", "Devine", "Holguin", "Ruffin", "Stamm",
    "Boyle", "Heckman", "Theriault", "Julian", "Curran",
    "Montemayor", "Getz", "Cupp", "Clemons", "Eaves", "Thorpe",
    "Herr", "Bolden", "Pond", "Swann", "Sweat", "Hanlon",
    "Crenshaw", "Stapleton", "Gage", "Moniz", "Jackson", "Lenz",
    "Saldana", "Ridenour", "Hoyle", "Ladd", "Coleman",
    "Gutierrez", "Yates", "Esparza", "Price", "Matson", "Gross",
    "Kaufman", "Slater", "Staples", "Brent", "Peel",
    "Jordon", "Bumgarner", "Gallo", "Skelton", "Booth", "Quezada",
    "Carrillo", "Dorsey", "Crum", "Denny", "Coats",
    "Connell", "Rice", "Ramon", "Burton", "Ouellette", "Allred",
    "Emmons", "Lanham", "Peter.man", "Whitt", "Garvey",
    "Gonsalves", "Marr", "Chamberlain", "Bryant", "Hidalgo",
    "Nunez", "Milligan", "Early", "Franks", "Gillette",
    "Savoy", "Fitzgerald", "Beers", "Horsley", "Barham", "Morrell",
    "Lindsey", "Curtin", "Shay", "Richards", "Starling",
    "Cardona", "Amundson", "Sides", "Hawley", "Dupont", "Mead",
    "Herzog", "Hammonds", "Nettles", "Mcfarland", "Rawls",
    "Charles", "Kopp", "Sprague", "Ivey", "Luciano", "Bragg",
    "Marquez", "Walston", "Burnett", "Judd", "Collier",
    "Blunt", "Ruiz", "Dang", "Max - well", "Ferris", "Willard",
    "Bynum", "Cohn", "Baggett", "Whitten", "Poirier", "Ham",
    "Steinberg", "Mathias", "Dominquez", "Stark", "Prabhu",
    "Vade", "Nath", "Patil", "Tiwary", "Reddy", "Kumar", "Pai",
    "Yadav", "Agarwal", "Rashid" /*,"חיוב", "נושא", "מכונות זמן" */ };

  // Seed list of job titles
  private static String[] _JOB_TITLES = new String[] {
    "Analyst",
    "Contractor (Temporary)",
    "Supervisor",
    "Manager",
    "Director"
    /*,"חיוב", "נושא", "מכונות זמן" */
  };

  // Seed list of person tags
  private static String[] _TAGS = new String[] {
    "Excel",
    "Shipping",
    "support",
    "veteran",
    "fresher",
    "mentor",
    "negotiator",
    "physio",
    "analytics",
    "filing",
    "tech",
    "tools",
    "ERT"
    /*,"חיוב", "נושא", "מכונות זמן" */
  };

  // Number of employees to be auto-generated
  private static final int _TABLE_MAX_LIMIT = 125000;

  private static final long serialVersionUID = 5238029863384377715L;
}
