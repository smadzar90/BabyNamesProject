import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.Scanner;
import java.io.File;

public class BabyNames
{
    
    static Scanner scan = new Scanner(System.in);

    public void totalBirths(FileResource fr) {
        int totalBabies = 0;
        int maleBabies = 0;
        int femaleBabies = 0;
        int girlNames = 0;
        int boyNames = 0;
        int totalNames = 0;
        
        for(CSVRecord names : fr.getCSVParser(false)) {
            
            totalBabies +=  Integer.parseInt(names.get(2));
            totalNames++;
            
            if(names.get(1).equals("M")) {
                maleBabies += Integer.parseInt(names.get(2));
                boyNames++;
            }
            else {
                femaleBabies += Integer.parseInt(names.get(2));
                girlNames++;
            }
            
        }
        
        System.out.println("Total babies: " + totalBabies);
        System.out.println("Male babies: " + maleBabies);
        System.out.println("Female babies: " + femaleBabies);
        System.out.println("Total names: " + totalNames);
        System.out.println("Boy names: " + boyNames);
        System.out.println("Girl names: " + girlNames);
    }
    
    public int getRank(String year, String name, String gender) {
        
        String fileName = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        int rank = -1;
        int maleCount = 1;
        int femaleCount = 1;
        
        for(CSVRecord names : fr.getCSVParser(false)) {
            
            if(gender.equals("F")) {
                
                if(names.get(0).equals(name)) {
                    rank = femaleCount;
                    return rank;
                }
                femaleCount++;
            }
            else {
                
                if(names.get(1).equals("M")) {
                    
                    if(names.get(0).equals(name)) {
                        rank = maleCount;
                        return rank;
                    }
                    maleCount++;
                   
                }
            }
        }
        return rank;
    }
    
    public String getName(int year, int rank, String gender) {
        
        String name = "NO NAME";
        String fileName = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        int maleCount = 1;
        int femaleCount = 1;
        
        for(CSVRecord names : fr.getCSVParser(false)) {
            
            if(gender.equals("F")) {
                
                if(femaleCount == rank) {
                    name = names.get(0);
                    return name;
                }
                femaleCount++;  
            }
            else {
                
                if(names.get(1).equals("M")) {
                    
                   if(maleCount == rank) {
                    name = names.get(0);
                    return name;
                   }
                   maleCount++;
                }
                
            }
        }
        return name;
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        String year2 = Integer.toString(year);
        int rank = getRank(year2, name, gender);
        String newName = getName(newYear, rank, gender);
        
        if(gender.equals("M")) {
            System.out.println(name + " born in " + year + " would be " +
            newName + " if he was born in " + newYear + ".");
        }
        else {
            System.out.println(name + " born in " + year + " would be " +
            newName + " if she was born in " + newYear + ".");
        }
        
    }
    
    public int yearOfHighestRank(String name, String gender) {
        
        int lowestRank = -1;
        int rank;
        String rankedYear = "-1";
        String year;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()) {
            
            year = f.getName().substring(3, 7);
            rank = getRank(year, name, gender);
            
            if(lowestRank == -1 || (rank < lowestRank && rank != -1)) {
                lowestRank = rank;
                rankedYear = year;
            }
            
        }
        
        if(lowestRank == -1) {
            return -1;
        }
        else {
            return Integer.parseInt(rankedYear);
        }
    }
    
    public double getAverageRank(String name, String gender) {
        
        
        int rank = 0;
        double sum = 0.0;
        String year;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()) {
            
            year = f.getName().substring(3, 7);
            rank += getRank(year, name, gender);
            
            sum++;
        }
        
        return rank / sum;
    }
    
    public int getTotalBirthsRankedHigher(String year, String name, String gender) {
        
        String fileName = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        int births = 0;
        
        for(CSVRecord names : fr.getCSVParser(false)) {
            
            if(gender.equals("F")) {
                
                if(names.get(0).equals(name)) {
                    break;
                }
                births += Integer.parseInt(names.get(2));
                 
            }
            else {
                
                if(names.get(1).equals("M")) {
                    
                    if(names.get(0).equals(name)) {
                    break;
                    }
                    births += Integer.parseInt(names.get(2));

                   
                }
                
            }
        }
        return births;
    }
    
    public void testGetTotalBirthsRankedHigher() {
        
        String year, name, gender;
        
        System.out.print("Enter a year: ");
        year = scan.nextLine();
        System.out.print("Enter a name: ");
        name = scan.nextLine();
        System.out.print("Enter a gender(M/F): ");
        gender = scan.nextLine();
        System.out.println("Total births: " + getTotalBirthsRankedHigher(year, name, gender));
    }
    
    public void testGetAvarageRank() {
        String name, gender;
        System.out.print("Enter a name: ");
        name = scan.nextLine();
        System.out.print("Enter a gender: ");
        gender = scan.nextLine();
        double avarage = getAverageRank(name, gender);
        System.out.println("Avarage rank is: " + String.format("%.2f", avarage));
    }
    
    public void testYearOfHighestRank() {
        String name, gender;
        System.out.print("Enter a name: ");
        name = scan.nextLine();
        System.out.print("Enter a gender: ");
        gender = scan.nextLine();
        int year = yearOfHighestRank(name, gender);
        System.out.println("Highest rank was in: " + year);
    }
    
    public void testWhatIsNameInYear() {
        
        String name, gender;
        int year, newYear;
        
        System.out.print("Enter a name: ");
        name = scan.nextLine();
        System.out.print("Enter a year: ");
        year = scan.nextInt();
        System.out.print("Enter a new year: ");
        newYear = scan.nextInt();
        scan.nextLine();
        System.out.print("Enter a gender: ");
        gender = scan.nextLine();
        whatIsNameInYear(name, year, newYear, gender);
        
    }
    
    public void testGetName() {
        
        int year, rank;
        String gender;
        
        System.out.print("Enter a year: ");
        year = scan.nextInt();
        System.out.print("Enter a rank: ");
        rank = scan.nextInt();
        System.out.print("Enter a gender(M/F): ");
        scan.nextLine();
        gender = scan.nextLine();
        System.out.println("The name is: " + getName(year, rank, gender));
    }
    
    public void testGetRank() {
        
        String year, name, gender;
        System.out.print("Enter a year: ");
        year = scan.nextLine();
        System.out.print("Enter a name: ");
        name = scan.nextLine();
        System.out.print("Enter a gender(M/F): ");
        gender = scan.nextLine();
        System.out.println("Rank: " + getRank(year, name, gender));
    }
    
    public void testTotalBirths() {
        System.out.print("Enter a year: ");
        int year = scan.nextInt();
        String fileName = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        totalBirths(fr);
    }
    
}
