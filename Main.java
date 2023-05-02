import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Scanner sk =new Scanner(System.in);       //public scanner accessible to all methods 
    public static ArrayList<City> cities=new ArrayList<>();  //public array list of city type to hold our object
	public static String path=null;                         // directory path of our database text 
	public static File fl=null;                                                     // our .txt file
	
	
	public static void main(String[] args) throws FileNotFoundException               //main method/driver code
{
	
        
		FindFile();
		//objToTxt();
		txtToObj();
		while(true) { mainMenu();}
		
	
}
	// - database File Methods - //
		public static void FindFile() throws FileNotFoundException			// Find File Method
		{
	 		path = new File(".project").getAbsolutePath();					// find .project file in project
			path = path.replace(".project", "database.txt");				// replace the path portion of .project with database.txt
			fl = new File(path);											// set File = path
			
			if(!fl.exists()) {System.out.println("File Not found");}		// if could not locate file, say, file not found 
		}
		 
		public static void txtToObj() throws FileNotFoundException          //txt to object method
		{
			Scanner skf=new Scanner(fl);                                   
			//for infinite times loop we need while
			
			while(skf.hasNextLine())
			{
				String[] obj=skf.nextLine().split("\\s+");                   //a String array  called obj,split each line anywhere there is 1 or more spaces
				
				City c=null;                                                 //instantiate a new city object set to null
				String cityName=" ";                                         //String to store name
				float lat=0;float lng=0;                                     //float to store lat and lng
				int pop=0;
				
				try {                                                        //try to parse String in array to data for object  
					cityName=obj[0];                                         //name=obj element in pos 0
					lat=Float.parseFloat(obj[1]);                            //lat=obj element in pos 1
					lng=Float.parseFloat(obj[2]);                            //lng=obj element in pos 2
					pop=Integer.parseInt(obj[3]);                            //pop=obj element in pos 3 
				}
				catch(Exception e){                                          //our catch block
					System.out.println("Error during conversion:could not parse String objects");  //print error to user
				}
				finally {                                                    //finally block to create city and store it in array list                                                       
					c=new City(cityName,lat,lng,pop);                        //create city using city constructor and data gathered from txt file
					cities.add(c);                                           //add created city into array list of cities     
				}	
			}
		}
		
		public static void objToTxt() throws FileNotFoundException		// obj to txt method
		{
			String data = "";											// instantiate new string object to store our data in (to be written to file)
			
			for(int i = 0; i < cities.size(); i++)						// loop through all cities in our array list and gather data to store in data String
			{
				data += cities.get(i).getName() + " "
						+ cities.get(i).getLat() + " "
						+ cities.get(i).getLng() + " "
						+ cities.get(i).getPop() + " "
						+ System.lineSeparator();
			}
			
			try															// overwrite our database.txt file with new data gathered from loop
			{
				FileWriter writer = new FileWriter(path);				// create a new file writter obj
				writer.write(data);										// write our data to that file
				writer.close();											// close our writer
			}
			
			catch(Exception e) { e.printStackTrace(); }					// error handling catch block 
		}
		
		
//helper methods
public static void p(String _str) {	System.out.print(_str);	}	// simple print method

public static void saveChanges()
{
	p("  1) save changes"+System.lineSeparator());
	p("  2) discard changes"+System.lineSeparator());
	
	if(sk.nextInt()==1) 
	{
		try{objToTxt();} catch(Exception e) {e.printStackTrace();}
	}
}
public static double deg2rad(double deg)
{
	return deg*(Math.PI/180);
	}

public static double getDistance(double _lat1,double _lat2,double _lng1,double _lng2)
{
	double r = 6371;
	double latD = deg2rad(_lat1 - _lat2);
	double lngD = deg2rad(_lng1 - _lng2);
	
	double a = Math.sin(latD/2)*Math.sin(latD/2) + Math.cos(_lat1) *Math.cos(_lat2) * Math.sin(lngD/2) * Math.sin(lngD/2);
	double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	double d = r*c;
	
	d = (Math.round(d*100));
	d = d/100;
	
	return d;
}

public static void mainMenu() { 
                               //main menu
	
	p("Main Menu: "+System.lineSeparator()                      //print statement
	+ "  1. display all cities"+System.lineSeparator()
	+ "  2.search for a city "+ System.lineSeparator()
	+ "  3.Insert a new city "+ System.lineSeparator()
	+ "  4.delete a city " +System.lineSeparator()
	+ "  5.update a city" + System.lineSeparator()
	+ "  6.find distance between 2 cities " +System.lineSeparator()
	+ "  7) find nearby cities " + System.lineSeparator()
	+ "  8) exit city database " + System.lineSeparator());
		
	boolean valid=false;                                         //boolean condition use
	while(!valid) {                                              //invalid input loop
		                                                         
		int input=sk.nextInt();                                  //accept user input
		
		valid=true;
		
		switch(input)
		{
		case 1:
		//	p("1"+System.lineSeparator());
			displayAllCities();
			break;
		case 2:
		//	p("2"+System.lineSeparator());
			searchForCity();
			break;
		case 3:
			//p("3"+System.lineSeparator());
			insertNewCity();
			break;
		case 4:
			//p("4"+System.lineSeparator());
			deleteCity();
			break;
		case 5:
			//p("5"+System.lineSeparator());
			updateCity();
			break;
		case 6:
			//p("6"+System.lineSeparator());
			distance1();
			break;
		case 7:
			//p("7"+System.lineSeparator());
			distance2();
			break;
		case 8:
		     p("8"+System.lineSeparator());
		     System.exit(0);
			 break;
		default:
			p("invalid input"+System.lineSeparator());
			valid=false;
			break;
		}
	}
}

public static void displayAllCities()
{p("______________________________________________________________________"+System.lineSeparator());
p("City:			| Latitude	| Longitude	| population  |"+System.lineSeparator());
for(int i = 0; i < cities.size(); i++)
{
	p(cities.get(i)+"");
}
p("______________________________________________________________________|"+System.lineSeparator()+System.lineSeparator()); 
	}

public static void searchForCity()
{
	p("Enter city name:");
	
	String cn=sk.next().toLowerCase();
	
	boolean exists=false;
	
	for(int i=0;i<cities.size();i++) {
		if(cities.get(i).getName().toLowerCase().contentEquals(cn)) 
		{
		    exists=true;	
		    System.out.println("City:"+cities.get(i).getName()+System.lineSeparator()
		                        +"Latitude:"+cities.get(i).getLat()+System.lineSeparator()
		                        +"Longitude:"+cities.get(i).getLng()+System.lineSeparator()
		                        +"Population:"+cities.get(i).getLng()+System.lineSeparator());
		   break;
		}
			
		}
	if(exists==false) {p("City does not exist"+System.lineSeparator());}
	
	}

 public static void insertNewCity()
 {
	 p("Enter city name:");
		
		String cn=sk.next().toLowerCase();
		
		boolean exists=false;
		
		for(int i=0;i<cities.size();i++) {
			if(cities.get(i).getName().toLowerCase().contentEquals(cn)) 
			{
				exists=true;
			  p("City already exists!"); 
			   break;
			}
				
			}
		if(exists==false)
		{
			p("Enter Latitude:"+System.lineSeparator());
			float lat=sk.nextFloat();
			
			p("Enter Longitude:"+System.lineSeparator());
			float lng=sk.nextFloat();
			
			p("Enter Population:"+System.lineSeparator());
			int pop=sk.nextInt();
			 City c=new City(cn,lat,lng,pop);
			 cities.add(c);
			 
			 p(System.lineSeparator());
			 
			 saveChanges();
			}
		
 }

 public static void deleteCity()
 {
	 p("Enter city name:");
		
		String cn=sk.next().toLowerCase();
		
		boolean exists=false;           //set exists=true since we found the city
		
		for(int i=0;i<cities.size();i++) {
			if(cities.get(i).getName().toLowerCase().contentEquals(cn)) 
			{
			    exists=true; 
			    System.out.println("Nuke"+" "+cities.get(i).getName()+"?"+System.lineSeparator());
			  p("  1)nuke");
			  p("  2)spare");
			  if(sk.nextInt()==1) 
			  {
				  cities.remove(i);
				  saveChanges();
			  }
			  
			    break;
			}
				
			}
		if(exists==false) {p("City does not exist"+System.lineSeparator());}
		
 }
 public static void updateCity()
 {
	 p("Enter city name:");
		
		String cn=sk.next().toLowerCase();
		
		boolean exists=false;           //set exists=true since we found the city
		
		for(int i=0;i<cities.size();i++) {
			if(cities.get(i).getName().toLowerCase().contentEquals(cn)) 
			{
			    exists=true; 
			   p("Update Name?      1)Yess   2)No"+System.lineSeparator());
			  if(sk.nextInt()==1)
			  {
				  p("Name:  ");
				  cities.get(i).setName(sk.next());
				  System.out.println();
			  }
			  p("Update Population?      1)Yess   2)No"+System.lineSeparator());
			  if(sk.nextInt()==1)
			  {
				  p("Population:  ");
				  cities.get(i).setPop(sk.nextInt());
				  System.out.println();
			  }
		       	 saveChanges();
			    break;
			}
				
			}
		if(exists==false) {p("City does not exist"+System.lineSeparator());}
		
 }
 public static void distance1()
 {
	 p("City1: ");
	 String cn1=sk.next().toLowerCase();
	 
	 p("City2:  ");
	 String cn2=sk.next().toLowerCase();
	 
	 boolean c1=false,c2=false;
	  double [] ls=new double[4];
	  
	  for(int i=0;i<cities.size();i++)
	  {
		  if(cities.get(i).getName().toLowerCase().contentEquals(cn1))
		  {
			  c1=true;
			  ls[0]=cities.get(i).getLat();
			  ls[1]=cities.get(i).getLng();
		  }
		  if(cities.get(i).getName().toLowerCase().contentEquals(cn2))
		  {
			  c2=true;
			  ls[2]=cities.get(i).getLat();
			  ls[3]=cities.get(i).getLng();
		  }
	  }
	  if(c1&&c2)
	  {
		  p("Distance"+getDistance(ls[0],ls[1],ls[2],ls[3])+"Km"+System.lineSeparator()+System.lineSeparator());
	  }
	  else
	  {
		  p("City does not exist.");
	  }
 }
 public static void distance2()					// find nearby cities method
	{
		p("City: ");								// prints city
		String cn = sk.next();						// store city name input
		
		p("Distance: ");							// prints distance
		float d = sk.nextFloat();					// stores distance input
		
		float l1 = 0, l2 = 0;						// floats lat and long set to 0
		
		boolean exists = false;						// exists boolean
		
		for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
		{
			if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
			{
				exists = true;							// set exists = true since we found city
				l1 = cities.get(i).getLat();			// set l1 to city lat
				l2 = cities.get(i).getLng();			// set l2 to city lng
				break;									// break out of loop so that we do not keep iteration after we find city
			}
		}
		
		if(exists)	// if we find the city	
		{
			for(int i = 0; i < cities.size(); i++)		// loop through array of cities
			{
				double d2 = getDistance(l1, cities.get(i).getLat(), l2, cities.get(i).getLng());	// get distance between city of choice and all other cities in array
				
				if(d2<=d && d2>0)	// if that distance is less than distance from user input, and not equal to zero, since we dont want distance to our own city
				{
					p(cities.get(i).getName() + ": " + d2 + "KM " + System.lineSeparator());	// print that city and its distance
				}
			}
			
			p(System.lineSeparator());	// skip line
		}
		else // if exists is still false at this point, we have not found city, so let user koww
		{
			p("City does not exist" + System.lineSeparator()+System.lineSeparator());	
		}
	}
}
 



 

