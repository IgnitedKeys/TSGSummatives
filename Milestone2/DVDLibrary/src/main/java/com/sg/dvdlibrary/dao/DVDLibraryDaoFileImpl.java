
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author angeladrees
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {
    
    //hashMap for DVDs
    Map<String, DVD> dvds = new HashMap<>();
    
    public static final String DVD_FILE = "dvdLibrary.txt";
    public static final String DELIMITER = "::";
    

    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadLibrary();
        DVD prevDvd = dvds.put(title, dvd);
        writeLibrary();
        return prevDvd;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        loadLibrary();
        DVD dvdRemove = dvds.remove(title);
        writeLibrary();
        return dvdRemove;
    }

    @Override
    public DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadLibrary();
        DVD editedDVD = dvds.put(title,dvd);
        writeLibrary();
        return editedDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
        loadLibrary();
        return new ArrayList(dvds.values());
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException {
        loadLibrary();
        return dvds.get(title);
    }
    
    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        
        DVD dvdFromFile = new DVD(title);
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setRating(dvdTokens[2]);
        dvdFromFile.setDirectorsName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserRating(dvdTokens[5]);
        
        return dvdFromFile;
    }
    
    private void loadLibrary() throws DVDLibraryDaoException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(DVD_FILE)));
        } catch(FileNotFoundException e) {
            throw new DVDLibraryDaoException("Could not load dvd library into memory.", e);
        }
        String currentLine;
        DVD currentDVD;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);
            
            dvds.put(currentDVD.getTitle(),currentDVD);
        }
        scanner.close();
    }
    
    private String marshallDVD(DVD aDvd) {
        String aDvdAsText = aDvd.getTitle() + DELIMITER;
        aDvdAsText += aDvd.getReleaseDate() + DELIMITER;
        aDvdAsText += aDvd.getRating() + DELIMITER;
        aDvdAsText += aDvd.getDirectorsName() + DELIMITER;
        aDvdAsText+= aDvd.getStudio() + DELIMITER;
        aDvdAsText += aDvd.getUserRating();
        
        return aDvdAsText;
    }
    
    private void writeLibrary() throws DVDLibraryDaoException {
        
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch(IOException e) {
            throw new DVDLibraryDaoException("Could not save dvd date",e);
        }
        String dvdAsText;
        List<DVD> dvdList = this.getAllDVDs();
        for(DVD dvd : dvdList) {
            dvdAsText = marshallDVD(dvd);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }

}
